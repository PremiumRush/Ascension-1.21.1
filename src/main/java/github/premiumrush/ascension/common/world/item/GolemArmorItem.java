package github.premiumrush.ascension.common.world.item;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GolemArmorItem extends ArmorItem {
    public GolemArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @NotNull TooltipContext tooltipContext, List<Component> pToolTipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.golem_armor1.tooltip"));
        pToolTipComponents.add(Component.translatable("tooltip.ascension.golem_armor2.tooltip"));
        super.appendHoverText(itemStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }
}
