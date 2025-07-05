package github.premiumrush.ascension.common.world.entity.custom;

import github.premiumrush.ascension.common.init.EntityInit;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SmallSnowBall extends SnowBall {
    public SmallSnowBall(EntityType<? extends SmallSnowBall> entityType, Level level) {
        super(entityType, level);
    }

    protected SmallSnowBall(LivingEntity owner, Vec3 movement, Level level) {
        super(EntityInit.SMALL_SNOWBALL.get(), owner, movement, level);
    }

    protected SmallSnowBall(Level level, double x, double y, double z, Vec3 movement) {
        super(EntityInit.SMALL_SNOWBALL.get(), x, y, z, movement, level);
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Level level = this.level();
        if (level instanceof ServerLevel serverlevel) {
            Entity entity1 = result.getEntity();
            entity1.setTicksFrozen(200);
            DamageSource damageSource = this.damageSources().freeze();
            entity1.hurt(damageSource, 2.0F);
            EnchantmentHelper.doPostAttackEffects(serverlevel, entity1, damageSource);
        }
    }

    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.SNOWFLAKE;
    }



    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    protected boolean shouldBurn() {
        return false;
    }
}
