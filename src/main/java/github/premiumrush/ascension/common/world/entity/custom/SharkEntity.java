package github.premiumrush.ascension.common.world.entity.custom;

import github.premiumrush.ascension.common.world.entity.ai.goal.SharkAttackGoal;
import github.premiumrush.ascension.common.init.EntityInit;
import github.premiumrush.ascension.common.init.MobEffectInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SharkEntity extends AbstractFish {
    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(SharkEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;

    public SharkEntity(EntityType<? extends AbstractFish> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 5;
        this.moveControl = new SharkMoveControl(this);
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    public void setupAnimationStates() {
        if(this.isAttacking() && attackAnimationTimeout == 0) {
            attackAnimationTimeout = 25;
            attackAnimationState.start(this.tickCount);
        } else {
            if(this.attackAnimationTimeout != 0) {
                --this.attackAnimationTimeout;
            }
        }

        if(!this.isAttacking()) {
            attackAnimationState.stop();
        }
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }
    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACKING, false);
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
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SharkAttackGoal(this, 4.5, false));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 4.3, 16));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 0.65, 1));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, SharkEntity.class));
        if (level().getDifficulty() != Difficulty.PEACEFUL) {
            this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 20, true, true, null));
        }
    }

    @Override
    public void aiStep() {
        if (!this.isInWater() && this.onGround() && this.verticalCollision) {
            this.setDeltaMovement(this.getDeltaMovement().add((double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F), 0.4000000059604645, (double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)));
            this.setOnGround(false);
            this.hasImpulse = true;
            this.makeSound(this.getFlopSound());
        }
        super.aiStep();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractFish.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 40)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.ENTITY_INTERACTION_RANGE, 3.7)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.ARMOR, 3);
    }

    @Override
    protected int getBaseExperienceReward() {
        return this.xpReward;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (entity instanceof LivingEntity target) {
            level().playLocalSound(blockPosition(), SoundEvents.RAVAGER_ATTACK, SoundSource.HOSTILE, 1,0, true);
            if(!target.isDamageSourceBlocked(this.damageSources().mobAttack(this))) {
                target.addEffect(new MobEffectInstance(MobEffectInit.BLEEDING, 100), this);
                if(random.nextInt(0, 13) == 5) {
                    FlockSharkEntity flockShark = new FlockSharkEntity(EntityInit.FLOCK_SHARK.get(), level());
                    Vec3 addedPosition = this.position().vectorTo(entity.position()).multiply(3, -2, 3);
                    Vec3 shark_to_target = entity.position().add(addedPosition);
                    flockShark.moveTo(shark_to_target, -this.getYRot(), -this.getXRot());
                    level().addFreshEntity(flockShark);
                }
            }
        }
        return super.doHurtTarget(entity);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.DROWN)) {
            return false;
        }
        return super.hurt(source, amount);
    }



    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SALMON_HURT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SALMON_DEATH;
    }
    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.DOLPHIN_SWIM;
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SALMON_AMBIENT;
    }
    protected SoundEvent getFlopSound() {
        return SoundEvents.SALMON_FLOP;
    }

    @Override
    public ItemStack getBucketItemStack() {
        return null;
    }

    static class SharkMoveControl extends MoveControl {
        private final SharkEntity entity;

        SharkMoveControl(SharkEntity sharkEntity) {
            super(sharkEntity);
            this.entity = sharkEntity;
        }

        public void tick() {
            if (this.operation == Operation.MOVE_TO && !this.entity.getNavigation().isDone()) {
                float speed_times_movementSpeed = (float)(this.speedModifier * this.entity.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.entity.setSpeed(Mth.lerp(0.5F, this.entity.getSpeed(), speed_times_movementSpeed));
                double d0 = this.wantedX - this.entity.getX();
                double d1 = (this.wantedY+0.4) - this.entity.getY();
                double d2 = this.wantedZ - this.entity.getZ();
                double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);

                if (d1 != 0.0) {
                    if ((d1 / d3) <= 0 || !(this.entity.level().getBlockState(new BlockPos((int)entity.getX(), (int)entity.getY()+1, (int)entity.getZ())).is(Blocks.AIR))) {
                        this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0, this.entity.getSpeed() * (d1 / d3) * 0.045, 0));
                    }
                }

                if (d0 != 0.0 || d2 != 0.0) {
                    float f1 = (float)(Mth.atan2(d2, d0) * 180.0 / 3.1415927410125732) - 90.0F;
                    this.entity.setYRot(this.rotlerp(this.entity.getYRot(), f1, 90.0F));
                    this.entity.yBodyRot = this.entity.getYRot();
                    this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(this.entity.getSpeed()*(d0/d3)*0.01, 0, this.entity.getSpeed()*(d2/d3)*0.01));
                }
            } else {
                this.entity.setSpeed(0.0F);
            }
        }
    }
}
