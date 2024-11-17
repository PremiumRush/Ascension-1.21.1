package github.premiumrush.ascension.world.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FerrotitaniumPickaxeItem extends PickaxeItem {
    public FerrotitaniumPickaxeItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext tooltipContext, List<Component> pToolTipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.ferrotitanium_item.tooltip"));
        super.appendHoverText(pStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }
}
