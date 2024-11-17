package github.premiumrush.ascension.world.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IceSwordItem extends SwordItem {
    public IceSwordItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext tooltipContext, List<Component> pToolTipComponents, TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.ice_sword_item.tooltip"));
        super.appendHoverText(pStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        pTarget.setTicksFrozen(160);

        if(pTarget.hasEffect(MobEffects.WEAKNESS)) {
            pTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,160,1),pAttacker);
        }
        else pTarget.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 160, 1),pAttacker);

        return super.hurtEnemy(pStack,pTarget,pAttacker);
    }
}
