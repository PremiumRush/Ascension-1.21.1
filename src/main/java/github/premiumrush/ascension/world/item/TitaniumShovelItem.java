package github.premiumrush.ascension.world.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TitaniumShovelItem extends ShovelItem {
    public TitaniumShovelItem(Tier p_43114_, Properties p_43117_) {
        super(p_43114_, p_43117_);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext tooltipContext, List<Component> pToolTipComponents, TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.titanium_item.tooltip"));
        super.appendHoverText(pStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }
}
