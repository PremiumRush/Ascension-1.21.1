package github.premiumrush.ascension.world.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class VexalCrystalItem extends Item {
    public VexalCrystalItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.ascension.vexal_crystal.tooltip"));
        super.appendHoverText(stack,context,tooltipComponents,tooltipFlag);
    }
}
