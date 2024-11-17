package github.premiumrush.ascension.world.item;

import github.premiumrush.ascension.init.MobEffectInit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FleroviumSwordItem extends SwordItem {
    public FleroviumSwordItem(Tier p_40521_, Properties p_40524_) {
        super(p_40521_, p_40524_);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext tooltipContext, List<Component> pToolTipComponents, TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.flerovium_sworditem.tooltip"));
        super.appendHoverText(pStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pTarget.addEffect(new MobEffectInstance(MobEffectInit.RADIATION, 60, 1),pAttacker);
        return super.hurtEnemy(pStack,pTarget,pAttacker);
    }
}
