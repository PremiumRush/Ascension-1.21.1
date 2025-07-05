package github.premiumrush.ascension.common.world.item;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InactiveUpgradeItem extends Item {
    public InactiveUpgradeItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext tooltipContext, List<Component> pToolTipComponents, TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.inactive_upgrade_item.tooltip"));
        pToolTipComponents.add(CommonComponents.EMPTY);
        pToolTipComponents.add(Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("smithing_template.applies_to"))).withStyle(ChatFormatting.GRAY));
        pToolTipComponents.add(CommonComponents.space().append(Component.translatable("tooltip.ascension.flerovium_upgrade_applies_to.tooltip")));
        pToolTipComponents.add(Component.translatable(Util.makeDescriptionId("item", ResourceLocation.parse("smithing_template.ingredients"))).withStyle(ChatFormatting.GRAY));
        pToolTipComponents.add(CommonComponents.space().append(Component.translatable("tooltip.ascension.flerovium_upgrade_ingredients.tooltip")));
        super.appendHoverText(pStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }
}
