package github.premiumrush.ascension.common.world.entity.custom;

import github.premiumrush.ascension.common.world.entity.ai.goal.GhostAttackGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractGhostEntity extends Monster implements Enemy {
    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(AbstractGhostEntity.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;
    public int lastAttackFallTimer;

    public AbstractGhostEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 10;
        this.lastAttackFallTimer = 0;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new GhostAttackGoal(this, 1.7, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.9));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, Blaze.class, Ghast.class));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.FOLLOW_RANGE, 24)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.ENTITY_INTERACTION_RANGE, 2f);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACKING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("last_attack_fall_timer", lastAttackFallTimer);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        compound.getInt("last_attack_fall_timer");
    }

    public static boolean spawnRules(@NotNull EntityType<? extends Mob> entityType, ServerLevelAccessor level, @NotNull MobSpawnType spawnType, @NotNull BlockPos blockPos, RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL && checkMobSpawnRules(entityType, level, spawnType, blockPos, random);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getTarget() != null) {
            this.getLookControl().setLookAt(this.getTarget());
        }
        if (this.lastAttackFallTimer > 0) {
            this.lastAttackFallTimer--;
        }
        // AnimationStates
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0 && this.lastAttackFallTimer == 0) {
            this.setDeltaMovement(vec3.multiply(1.0, 0.55, 1.0));
        }
    }

    private void setupAnimationStates() {
        if (this.isAttacking() && attackAnimationTimeout == 0) {
            attackAnimationState.stop();
            attackAnimationTimeout = 20;
            attackAnimationState.start(this.tickCount);
        } else if (this.attackAnimationTimeout != 0) {
            --this.attackAnimationTimeout;
        }

        if (!this.isAttacking() && this.attackAnimationTimeout == 0) {
            attackAnimationState.stop();
        }


        this.idleAnimationState.animateWhen(this.hasPose(Pose.STANDING) || this.walkAnimation.isMoving(), this.tickCount);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }
    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        float f;
        if(this.getPose() == Pose.STANDING) {
            f = Math.min(partialTick * 6F, 1F);
        } else {
            f = 0;
        }
        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (this.hasEffect(MobEffects.INVISIBILITY)) {
            this.removeEffect(MobEffects.INVISIBILITY);
        }
        return super.doHurtTarget(entity);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL)) {
            return false;
        }
        if (this.getLastAttacker() != null && this.getLastAttacker() instanceof Player && this.getRandom().nextInt(0,3) == 1 && !this.hasEffect(MobEffects.INVISIBILITY)) {
            this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 60, 0, false, false));
        }
        return super.hurt(source, amount);
    }

    @Override
    protected int decreaseAirSupply(int currentAir) {
        return currentAir;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.VEX_AMBIENT;
    }
    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.VEX_DEATH;
    }
    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.VEX_HURT;
    }
}
