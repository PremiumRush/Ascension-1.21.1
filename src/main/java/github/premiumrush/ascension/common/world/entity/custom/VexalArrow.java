package github.premiumrush.ascension.common.world.entity.custom;

import github.premiumrush.ascension.common.init.EntityInit;
import github.premiumrush.ascension.common.init.ItemInit;
import github.premiumrush.ascension.common.init.MobEffectInit;
import github.premiumrush.ascension.common.util.AscensionData;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import javax.annotation.Nullable;
import java.util.Iterator;

public class VexalArrow extends AbstractArrow {
    private static final EntityDataAccessor<Integer> ID_EFFECT_COLOR = SynchedEntityData.defineId(VexalArrow.class, EntityDataSerializers.INT);;

    public VexalArrow(EntityType<? extends VexalArrow> entityType, Level level) {
        super(entityType, level);
    }
    public VexalArrow(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(EntityInit.VEXAL_ARROW.get(), x, y, z, level, pickupItemStack, firedFromWeapon);
        this.updateColor();
    }
    public VexalArrow(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(EntityInit.VEXAL_ARROW.get(), owner, level, pickupItemStack, firedFromWeapon);
        this.updateColor();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        this.setBaseDamage(this.getBaseDamage()*1.5);
        if (result.getEntity() instanceof LivingEntity livingEntity) {
            if (this.level().isClientSide()) {
                level().playLocalSound(result.getEntity().blockPosition(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, 1,-1,false);
            }
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 3));
            if (!AscensionData.BLEEDING_INVULNERABLE.contains(livingEntity.getType())) {
                livingEntity.addEffect(new MobEffectInstance(MobEffectInit.BLEEDING, 100, 1));
            }
        }
        super.onHitEntity(result);
    }

    private PotionContents getPotionContents() {
        return this.getPickupItemStackOrigin().getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
    }

    private void setPotionContents(PotionContents potionContents) {
        this.getPickupItemStackOrigin().set(DataComponents.POTION_CONTENTS, potionContents);
        this.updateColor();
    }

    protected void setPickupItemStack(ItemStack pickupItemStack) {
        super.setPickupItemStack(pickupItemStack);
        this.updateColor();
    }

    private void updateColor() {
        PotionContents potioncontents = this.getPotionContents();
        this.entityData.set(ID_EFFECT_COLOR, potioncontents.equals(PotionContents.EMPTY) ? -1 : potioncontents.getColor());
    }

    public void addEffect(MobEffectInstance effectInstance) {
        this.setPotionContents(this.getPotionContents().withEffectAdded(effectInstance));
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ID_EFFECT_COLOR, -1);
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.makeParticle(1);
                }
            } else {
                this.makeParticle(2);
            }
        } else if (this.inGround && this.inGroundTime != 0 && !this.getPotionContents().equals(PotionContents.EMPTY) && this.inGroundTime >= 600) {
            this.level().broadcastEntityEvent(this, (byte)0);
            this.setPickupItemStack(new ItemStack(ItemInit.VEXAL_ARROW.get()));
        }
    }

    private void makeParticle(int particleAmount) {
        int i = this.getColor();
        if (i != -1 && particleAmount > 0) {
            for(int j = 0; j < particleAmount; ++j) {
                this.level().addParticle(ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, i), this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
            }
        }
    }

    public int getColor() {
        return this.entityData.get(ID_EFFECT_COLOR);
    }

    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        Entity entity = this.getEffectSource();
        PotionContents potioncontents = this.getPotionContents();
        Iterator iterator;
        MobEffectInstance mobeffectinstance1;
        if (potioncontents.potion().isPresent()) {
            iterator = ((Potion)((Holder)potioncontents.potion().get()).value()).getEffects().iterator();

            while(iterator.hasNext()) {
                mobeffectinstance1 = (MobEffectInstance)iterator.next();
                target.addEffect(new MobEffectInstance(mobeffectinstance1.getEffect(), Math.max(mobeffectinstance1.mapDuration((duration) -> duration / 8), 1), mobeffectinstance1.getAmplifier(), mobeffectinstance1.isAmbient(), mobeffectinstance1.isVisible()), entity);
            }
        }
        iterator = potioncontents.customEffects().iterator();
        while(iterator.hasNext()) {
            mobeffectinstance1 = (MobEffectInstance)iterator.next();
            target.addEffect(mobeffectinstance1, entity);
        }
    }

    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ItemInit.VEXAL_ARROW.get());
    }

    public void handleEntityEvent(byte id) {
        if (id == 0) {
            int i = this.getColor();
            if (i != -1) {
                float f = (float)(i >> 16 & 255) / 255.0F;
                float f1 = (float)(i >> 8 & 255) / 255.0F;
                float f2 = (float)(i >> 0 & 255) / 255.0F;

                for(int j = 0; j < 20; ++j) {
                    this.level().addParticle(ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, f, f1, f2), this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
                }
            }
        } else {
            super.handleEntityEvent(id);
        }
    }
}
