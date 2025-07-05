package github.premiumrush.ascension.common.world.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class IceBlazeEntity extends Monster {
    private float allowedHeightOffset = 0.5F;
    private int nextHeightOffsetChangeTick;
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID;

    public IceBlazeEntity(EntityType<? extends IceBlazeEntity> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
        this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0.0F);
        this.xpReward = 10;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new IceBlazeAttackGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[0])).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 6.0).add(Attributes.MOVEMENT_SPEED, 0.23000000417232513).add(Attributes.FOLLOW_RANGE, 32.0);
    }

    public static boolean checkIceBlazeSpawnRules(EntityType<? extends IceBlazeEntity> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL && checkMobSpawnRules(type, level, spawnType, pos, random);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_FLAGS_ID, (byte)0);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if(source.is(DamageTypes.FREEZE)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.AMETHYST_BLOCK_CHIME;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.BLAZE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.BLAZE_DEATH;
    }

    protected void playThrowSound() {
        this.makeSound(SoundEvents.POWDER_SNOW_STEP);
    }

    public void aiStep() {
        if (!this.onGround() && this.getDeltaMovement().y < 0.0) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.6, 1.0));
        }

        if (this.level().isClientSide) {
            if (this.random.nextInt(100) == 0 && !this.isSilent()) {
                this.level().playLocalSound(this.getX() + 0.5, this.getY() + 0.5, this.getZ() + 0.5, SoundEvents.AMETHYST_BLOCK_CHIME, this.getSoundSource(), 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.4F, false);
            }

            for(int i = 0; i < 2; ++i) {
                this.level().addParticle(ParticleTypes.SNOWFLAKE, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
            }
        }

        super.aiStep();
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        DamageSource damagesource = this.damageSources().source(DamageTypes.FREEZE);
        Level var5 = this.level();
        if (var5 instanceof ServerLevel serverlevel) {
            f = EnchantmentHelper.modifyDamage(serverlevel, this.getWeaponItem(), entity, damagesource, f);
        }

        boolean flag = entity.hurt(damagesource, f);
        if (flag) {
            float f1 = this.getKnockback(entity, damageSources().mobAttack(this));
            if (f1 > 0.0F && entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                livingentity.knockback((double)(f1 * 0.5F), (double) Mth.sin(this.getYRot() * 0.017453292F), (double)(-Mth.cos(this.getYRot() * 0.017453292F)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }

            Level var7 = this.level();
            if (var7 instanceof ServerLevel) {
                ServerLevel serverlevel1 = (ServerLevel)var7;
                EnchantmentHelper.doPostAttackEffects(serverlevel1, entity, damagesource);
            }

            this.setLastHurtMob(entity);
            this.playAttackSound();
        }

        return flag;
    }

    @Override
    public boolean isSensitiveToWater() {
        return false;
    }

    protected void customServerAiStep() {
        --this.nextHeightOffsetChangeTick;
        if (this.nextHeightOffsetChangeTick <= 0) {
            this.nextHeightOffsetChangeTick = 100;
            this.allowedHeightOffset = (float)this.random.triangle(0.5, 6.891);
        }

        LivingEntity livingentity = this.getTarget();
        if (livingentity != null && livingentity.getEyeY() > this.getEyeY() + (double)this.allowedHeightOffset && this.canAttack(livingentity)) {
            Vec3 vec3 = this.getDeltaMovement();
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, (0.30000001192092896 - vec3.y) * 0.30000001192092896, 0.0));
            this.hasImpulse = true;
        }

        super.customServerAiStep();
    }



    private boolean isCharged() {
        return ((Byte)this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    void setCharged(boolean charged) {
        byte b0 = (Byte)this.entityData.get(DATA_FLAGS_ID);
        if (charged) {
            b0 = (byte)(b0 | 1);
        } else {
            b0 &= -2;
        }

        this.entityData.set(DATA_FLAGS_ID, b0);
    }

    static {
        DATA_FLAGS_ID = SynchedEntityData.defineId(IceBlazeEntity.class, EntityDataSerializers.BYTE);
    }

    static class IceBlazeAttackGoal extends Goal {
        private final IceBlazeEntity iceBlaze;
        private int attackStep;
        private int attackTime;
        private int lastSeen;

        public IceBlazeAttackGoal(IceBlazeEntity iceBlaze) {
            this.iceBlaze = iceBlaze;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity livingentity = this.iceBlaze.getTarget();
            return livingentity != null && livingentity.isAlive() && this.iceBlaze.canAttack(livingentity);
        }

        public void start() {
            this.attackStep = 0;
        }

        public void stop() {
            this.iceBlaze.setCharged(false);
            this.lastSeen = 0;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            --this.attackTime;
            LivingEntity livingentity = this.iceBlaze.getTarget();
            if (livingentity != null) {
                boolean lineOfSightCondition = this.iceBlaze.getSensing().hasLineOfSight(livingentity);
                if (lineOfSightCondition) {
                    this.lastSeen = 0;
                } else {
                    ++this.lastSeen;
                }

                double pDistanceToSqr = this.iceBlaze.distanceToSqr(livingentity);
                if (pDistanceToSqr < 4.0) {
                    if (!lineOfSightCondition) {
                        return;
                    }

                    if (this.attackTime <= 0) {
                        this.attackTime = 20;
                        this.iceBlaze.doHurtTarget(livingentity);
                    }

                    this.iceBlaze.getMoveControl().setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 1.0);
                } else if (pDistanceToSqr < this.getFollowDistance() * this.getFollowDistance() && lineOfSightCondition) {
                    double d1 = livingentity.getX() - this.iceBlaze.getX();
                    double d2 = livingentity.getY(0.5) - this.iceBlaze.getY(0.5);
                    double d3 = livingentity.getZ() - this.iceBlaze.getZ();
                    if (this.attackTime <= 0) {
                        ++this.attackStep;
                        if (this.attackStep == 1) {
                            this.attackTime = 60;
                            this.iceBlaze.setCharged(true);
                        } else if (this.attackStep <= 4) {
                            this.attackTime = 6;
                        } else {
                            this.attackTime = 100;
                            this.attackStep = 0;
                            this.iceBlaze.setCharged(false);
                        }

                        if (this.attackStep > 1) {
                            double d4 = Math.sqrt(Math.sqrt(pDistanceToSqr)) * 0.5;
                            if (!this.iceBlaze.isSilent()) {
                                this.iceBlaze.playThrowSound();
                            }

                            for(int i = 0; i < 1; ++i) {
                                Vec3 vec3 = new Vec3(this.iceBlaze.getRandom().triangle(d1, 2.297 * d4), d2, this.iceBlaze.getRandom().triangle(d3, 2.297 * d4));
                                SmallSnowBall smallSnowBall = new SmallSnowBall(this.iceBlaze, vec3.normalize(), this.iceBlaze.level());
                                smallSnowBall.setPos(smallSnowBall.getX(), this.iceBlaze.getY(0.5) + 0.5, smallSnowBall.getZ());
                                this.iceBlaze.level().addFreshEntity(smallSnowBall);
                            }
                        }
                    }

                    this.iceBlaze.getLookControl().setLookAt(livingentity, 10.0F, 10.0F);
                } else if (this.lastSeen < 5) {
                    this.iceBlaze.getMoveControl().setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 1.0);
                }

                super.tick();
            }

        }

        private double getFollowDistance() {
            return this.iceBlaze.getAttributeValue(Attributes.FOLLOW_RANGE);
        }
    }
}
