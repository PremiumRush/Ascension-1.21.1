package github.premiumrush.ascension.common.world.item;

import github.premiumrush.ascension.common.world.entity.custom.AbstractGhostEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import org.jetbrains.annotations.NotNull;

public class BaseVexalSwordItem extends SwordItem {
    public BaseVexalSwordItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if(target instanceof Vex ||
                target instanceof Phantom ||
                target instanceof Ghast ||
                target instanceof EnderMan ||
                target instanceof Shulker ||
                target instanceof AbstractGhostEntity)
        {
            target.hurt(target.damageSources().generic(),10.0F);
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,100,1));
        }
        return super.hurtEnemy(stack,target,attacker);
    }
}
