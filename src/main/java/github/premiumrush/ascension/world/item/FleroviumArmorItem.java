package github.premiumrush.ascension.world.item;

import github.premiumrush.ascension.init.ItemInit;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FleroviumArmorItem extends ArmorItem {
    public FleroviumArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext tooltipContext, List<Component> pToolTipComponents, TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.flerovium_armor1.tooltip"));
        pToolTipComponents.add(Component.translatable("tooltip.ascension.flerovium_armor2.tooltip"));
        super.appendHoverText(pStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide() && entity instanceof Player player && hasFleroviumArmorSetOn(player)) {
            player.removeEffect(MobEffects.POISON);
        }
    }

    private boolean hasFleroviumArmorSetOn(Player player) {
        return player.getInventory().getArmor(0).is(ItemInit.FLEROVIUM_BOOTS.get()) &&
                player.getInventory().getArmor(1).is(ItemInit.FLEROVIUM_LEGGINGS.get()) &&
                player.getInventory().getArmor(2).is(ItemInit.FLEROVIUM_CHESTPLATE.get()) &&
                player.getInventory().getArmor(3).is(ItemInit.FLEROVIUM_HELMET.get());
    }
}
