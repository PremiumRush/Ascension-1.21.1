package github.premiumrush.ascension.common.world.item;

import github.premiumrush.ascension.common.init.MobEffectInit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static github.premiumrush.ascension.common.util.AscensionData.BLEEDING_INVULNERABLE;

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
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        if(!BLEEDING_INVULNERABLE.contains(pTarget.getType())) {
            pTarget.addEffect(new MobEffectInstance(MobEffectInit.BLEEDING, 120, 1), pAttacker);
        }
        return super.hurtEnemy(pStack,pTarget,pAttacker);
    }
}
