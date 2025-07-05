package github.premiumrush.ascension.common.world.entity.custom;

import github.premiumrush.ascension.common.init.EntityInit;
import github.premiumrush.ascension.common.init.ItemInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.Random;

import static github.premiumrush.ascension.Ascension.LOGGER;

public class InfusedVexalArrow extends AbstractArrow {
    public static final EntityDataAccessor<Integer> DESPAWN_TIMER = SynchedEntityData.defineId(InfusedVexalArrow.class, EntityDataSerializers.INT);
    public InfusedVexalArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
    }
    public InfusedVexalArrow(Level level, double x, double y, double z, ItemStack pickupItemStack, @javax.annotation.Nullable ItemStack firedFromWeapon) {
        super(EntityInit.INFUSED_VEXAL_ARROW.get(), x, y, z, level, pickupItemStack, firedFromWeapon);
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
    }
    public InfusedVexalArrow(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(EntityInit.INFUSED_VEXAL_ARROW.get(), owner, level, pickupItemStack, firedFromWeapon);
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DESPAWN_TIMER, -1);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        this.setBaseDamage(this.getBaseDamage()*1.5);
        if (this.level().isClientSide()) {
            level().playLocalSound(result.getEntity().blockPosition(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, 1,-1,false);
        }
        super.onHitEntity(result);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        InfusionBlastSparkEntity sparkEntity;
        if (this.getOwner() != null) {
            sparkEntity = new InfusionBlastSparkEntity(this.level(), this.getOwner().getUUID());
        } else {
            sparkEntity = new InfusionBlastSparkEntity(this.level(), null);
        }
        sparkEntity.moveTo(result.getLocation());
        level().addFreshEntity(sparkEntity);
    }

    @Override
    public void tick() {
        super.tick();
        System.out.println("EntityData despawn timer: "+this.entityData.get(DESPAWN_TIMER));
        if (this.entityData.get(DESPAWN_TIMER) > 0) {
            this.entityData.set(DESPAWN_TIMER, (this.entityData.get(DESPAWN_TIMER)-1));
        } else if (this.entityData.get(DESPAWN_TIMER) == 0) {
            if (this.level().isClientSide()) {
                this.spawnParticleSet(this.level(), 0, 1, 16);
            }
            this.discard();
            System.out.println("Arrow Discarded");
        }
        if (!this.level().isClientSide() && this.inGround && this.inGroundTime != 0 && this.inGroundTime >= 600) {
            this.level().broadcastEntityEvent(this, (byte)0);
            this.setPickupItemStack(new ItemStack(ItemInit.INFUSED_VEXAL_ARROW.get()));
        }
        if (this.inGroundTime == 120) {
            this.entityData.set(DESPAWN_TIMER, 1);
            /*if (this.level().isClientSide()) {
                this.spawnParticleSet(this.level(), 1, 1, 16);
            }
            this.discard();*/
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ItemInit.INFUSED_VEXAL_ARROW.get());
    }

    private void spawnParticleSet(Level level, double origin, double bound, int amount) {
        if (level.isClientSide()) {
            for (int i = 0; i < amount; i++) {
                this.addParticle(level, origin, bound);
            }
        }
    }
    private void addParticle(Level level, double origin, double bound) {
        if (!(bound > origin)) {
            LOGGER.warn("bound is not bigger than origin");
            return;
        }
        System.out.println("Particles Played");
        Random random = new Random();
        double xRandom = random.nextDouble(origin, bound);
        double yRandom = random.nextDouble(0, 0.2);
        double zRandom = random.nextDouble(origin, bound);
        level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, getX()+0.5+xRandom, getY()+0.5+yRandom, getZ()+0.5+zRandom, 0,0,0);
    }
}
