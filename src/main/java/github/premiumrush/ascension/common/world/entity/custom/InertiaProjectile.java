package github.premiumrush.ascension.common.world.entity.custom;

import github.premiumrush.ascension.common.init.EntityInit;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class InertiaProjectile extends Projectile {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK;
    public double accelerationPower;

    public InertiaProjectile(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
        this.accelerationPower = 0.1;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_ITEM_STACK, this.getDefaultItem());
    }

    private ItemStack getDefaultItem() {
        return new ItemStack(Items.AIR);
    }

    protected ClipContext.Block getClipType() {
        return ClipContext.Block.COLLIDER;
    }

    @Override
    public void tick() {
        super.tick();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity, this.getClipType());
        if (hitresult.getType() != HitResult.Type.MISS && !net.neoforged.neoforge.event.EventHooks.onProjectileImpact(this, hitresult)) {
            this.hitTargetOrDeflectSelf(hitresult);
        }

        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        ProjectileUtil.rotateTowardsMovement(this, 0.2F);
        float f;
        if (this.isInWater()) {
            for (int i = 0; i < 4; i++) {
                this.level().addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25, d1 - vec3.y * 0.25, d2 - vec3.z * 0.25, vec3.x, vec3.y, vec3.z);
            }
            f = 0.8F;
        } else {
            f = 0.95F;
        }

        this.setDeltaMovement(vec3.add(vec3.normalize().scale(this.accelerationPower)).scale((double)f));

        ParticleOptions particleoptions = this.getTrailParticle();
        if (particleoptions != null) {
            for (int i = 0; i < 4; i++) {
                this.level().addParticle(particleoptions, d0, d1 + 0.05, d2, 0.0, 0.0, 0.0);
            }
        }
        this.applyGravity();
        this.setPos(d0, d1, d2);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (result.getType() != HitResult.Type.ENTITY || !this.ownedBy(((EntityHitResult)result).getEntity())) {
            if (!this.level().isClientSide) {
                InertiaSparkEntity entity = new InertiaSparkEntity(EntityInit.SPARK_OF_INERTIA.get(), level());
                entity.moveTo(blockPosition(), getYRot(), getXRot());
                level().addFreshEntity(entity);
                this.discard();
            }
        }
    }

    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Nullable
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.ASH;
    }

    @Override
    protected double getDefaultGravity() {
        return 0.018;
    }

    static {
        DATA_ITEM_STACK = SynchedEntityData.defineId(InertiaProjectile.class, EntityDataSerializers.ITEM_STACK);
    }
}
