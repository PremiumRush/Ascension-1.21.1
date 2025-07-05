package github.premiumrush.ascension.common.world.effect;

import github.premiumrush.ascension.common.init.DamageTypeInit;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BleedingMobEffect extends MobEffect {
    public BleedingMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        livingEntity.hurt(livingEntity.level().damageSources().source(DamageTypeInit.BLEEDING), 1.0f);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 75 >> amplifier;
        return i > 0 ? duration % i == 0 : true;
    }
}
