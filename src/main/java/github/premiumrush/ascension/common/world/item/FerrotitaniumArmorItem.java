package github.premiumrush.ascension.common.world.item;

import github.premiumrush.ascension.common.init.ItemInit;
import github.premiumrush.ascension.common.init.MobEffectInit;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FerrotitaniumArmorItem extends ArmorItem {
    public FerrotitaniumArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @NotNull TooltipContext tooltipContext, List<Component> pToolTipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.ferrotitanium_item.tooltip"));
        if (itemStack.is(ItemInit.FERROTITANIUM_CHESTPLATE.get())) {
            pToolTipComponents.add(Component.translatable("tooltip.ascension.radiation_chestplate.tooltip"));
        }
        pToolTipComponents.add(Component.translatable("tooltip.ascension.ferrotitanium_armor.tooltip"));
        super.appendHoverText(itemStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide() && entity instanceof Player player && hasChestplate(player)) {
            player.removeEffect(MobEffectInit.RADIATION);
        }
    }

    private boolean hasChestplate(Player player) {
        return player.getInventory().getArmor(2).is(ItemInit.FERROTITANIUM_CHESTPLATE.get());
    }
}
