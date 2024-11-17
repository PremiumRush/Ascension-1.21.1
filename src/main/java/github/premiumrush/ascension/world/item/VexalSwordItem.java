package github.premiumrush.ascension.world.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VexalSwordItem extends SwordItem {
    public VexalSwordItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext tooltipContext, List<Component> pToolTipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.vexal_sword.tooltip"));
        super.appendHoverText(pStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        if(pTarget instanceof Vex ||
                pTarget instanceof Phantom ||
                pTarget instanceof Ghast ||
                pTarget instanceof EnderMan ||
                pTarget instanceof Shulker)
        {
            pTarget.hurt(pTarget.damageSources().generic(),9.0F);
            pTarget.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,100,1));
        }
        return super.hurtEnemy(pStack,pTarget,pAttacker);
    }
}
