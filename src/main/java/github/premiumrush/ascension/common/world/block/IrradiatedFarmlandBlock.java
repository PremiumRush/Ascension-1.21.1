package github.premiumrush.ascension.common.world.block;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.FarmBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IrradiatedFarmlandBlock extends FarmBlock {
    public IrradiatedFarmlandBlock(Properties properties) {
        super(properties);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull Item.TooltipContext tooltipContext, List<Component> pToolTipComponents, TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.irradiated_farmland.tooltip"));
        super.appendHoverText(pStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }
}
