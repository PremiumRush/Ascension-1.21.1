package github.premiumrush.ascension.world.effect;

import github.premiumrush.ascension.init.DamageTypeInit;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class RadiationMobEffect extends MobEffect {
    public RadiationMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        livingEntity.hurt(livingEntity.level().damageSources().source(DamageTypeInit.RADIATION), 1.0f);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 30 >> amplifier;
        return i > 0 ? duration % i == 0 : true;
    }
}
