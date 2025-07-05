package github.premiumrush.ascension.common.world.entity.custom;

import github.premiumrush.ascension.common.world.entity.ai.goal.EmberGolemAttackGoal;
import github.premiumrush.ascension.common.world.entity.ai.goal.EmberGolemWaveAtPlayerGoal;
import github.premiumrush.ascension.common.world.item.GolemArmorItem;
import github.premiumrush.ascension.common.util.AscensionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EmberGolemEntity extends AbstractGolem implements Enemy, AscensionUtil {
    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(EmberGolemEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> WAVING = SynchedEntityData.defineId(EmberGolemEntity.class, EntityDataSerializers.BOOLEAN);

    public EmberGolemEntity(EntityType<? extends AbstractGolem> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 15;
    }

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;
    public final AnimationState wavingAnimationState = new AnimationState();
    public int wavingAnimationTimeout = 0;

    @Override
    public void tick() {
        super.tick();

        if(this.isOnFire()) {
            this.extinguishFire();
        }

        // AnimationStates
        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        // Attacking
        if(this.isAttacking() && attackAnimationTimeout == 0) {
            attackAnimationTimeout = 20;
            attackAnimationState.start(this.tickCount);
        } else {
            if(this.attackAnimationTimeout != 0) {
                --this.attackAnimationTimeout;
            }
        }
        if(!this.isAttacking()) {
            attackAnimationState.stop();
        }

        // Waving
        if (this.isWaving() && wavingAnimationTimeout <= 0) {
            wavingAnimationTimeout = (120 + this.random.nextIntBetweenInclusive(0,250));
            wavingAnimationState.start(this.tickCount);
        } else {
            --this.wavingAnimationTimeout;
        }
        if(!this.isWaving()) {
            wavingAnimationState.stop();
        }
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }
    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }
    public void setWaving(boolean waving) {
        this.entityData.set(WAVING, waving);
    }
    public boolean isWaving() {
        return this.entityData.get(WAVING);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACKING, false);
        builder.define(WAVING, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        if(!(this.getTarget() == null)) {
            if (this.distanceTo(this.getTarget()) >= 0.9F) {
                this.goalSelector.addGoal(0, new MoveTowardsTargetGoal(this, 1.0, 4.0F));
            }
        }
        this.goalSelector.addGoal(1, new EmberGolemAttackGoal(this, 1.1, true));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 1.0, 32.0F));
        this.goalSelector.addGoal(3, new EmberGolemWaveAtPlayerGoal(this, Player.class, 10f));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, Blaze.class, Ghast.class));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::noNeutral));
    }

    private boolean noNeutral(LivingEntity target) {
        return !hasArmorSet((Player) target, GolemArmorItem.class);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractGolem.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 100)
                .add(Attributes.MOVEMENT_SPEED, 0.275)
                .add(Attributes.ARMOR, 8f)
                .add(Attributes.ARMOR_TOUGHNESS, 0.05f)
                .add(Attributes.KNOCKBACK_RESISTANCE,1)
                .add(Attributes.ATTACK_DAMAGE, 14)
                .add(Attributes.STEP_HEIGHT, 1)
                .add(Attributes.FOLLOW_RANGE,28)
                .add(Attributes.ENTITY_INTERACTION_RANGE, 3.0f);
    }

    public static boolean spawnRules(@NotNull EntityType<? extends Mob> entityType, ServerLevelAccessor level, @NotNull MobSpawnType spawnType, @NotNull BlockPos blockPos, RandomSource random) {
        return checkMobSpawnRules(entityType, level, spawnType, blockPos, random) || spawnType == MobSpawnType.SPAWNER;
    }

    public boolean doHurtTarget(Entity entity) {
        this.level().broadcastEntityEvent(this, (byte)4);
        float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = (int)f > 0 ? f / 2.0F + (float)this.random.nextInt((int)f) : f;
        DamageSource damagesource = this.damageSources().mobAttack(this);
        boolean flag = entity.hurt(damagesource, f1);
        if (flag) {
            double knockbackResistance;
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                knockbackResistance = livingentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            } else {
                knockbackResistance = 0.0;
            }

            double d0 = knockbackResistance;
            double d1 = Math.max(0.0, 1.0 - d0);
            entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, 0.4000000059604645 * d1, 0.0));
            Level var11 = this.level();
            if (var11 instanceof ServerLevel) {
                ServerLevel serverlevel = (ServerLevel)var11;
                EnchantmentHelper.doPostAttackEffects(serverlevel, entity, damagesource);
            }
        }

        this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        return flag;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if(source.is(DamageTypes.ARROW) ||
           source.is(DamageTypes.MOB_PROJECTILE) ||
           source.is(DamageTypes.FALL) ||
           source.is(DamageTypes.CAMPFIRE) ||
           source.is(DamageTypes.HOT_FLOOR) ||
           source.is(DamageTypes.IN_FIRE) ||
           source.is(DamageTypes.LAVA) ||
           source.is(DamageTypes.ON_FIRE)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }
    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }
    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.IRON_GOLEM_HURT;
    }
}
