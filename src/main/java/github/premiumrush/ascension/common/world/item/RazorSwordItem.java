package github.premiumrush.ascension.common.world.item;

import github.premiumrush.ascension.common.init.MobEffectInit;
import github.premiumrush.ascension.common.init.TagInit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RazorSwordItem extends SwordItem {
    public RazorSwordItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext tooltipContext, List<Component> pToolTipComponents, TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.razor_sword_item.tooltip"));
        super.appendHoverText(pStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (!target.getType().is(TagInit.INVULNERABLE_TO_BLEEDING)) {
            target.addEffect(new MobEffectInstance(MobEffectInit.BLEEDING, 120, 1), attacker);
        }
        return super.hurtEnemy(stack,target,attacker);
    }
}
