package github.premiumrush.ascension.world.item;

import github.premiumrush.ascension.init.ItemInit;
import github.premiumrush.ascension.init.MobEffectInit;
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
        pToolTipComponents.add(Component.translatable("tooltip.ascension.ferrotitanium_armor.tooltip"));
        super.appendHoverText(itemStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide() && entity instanceof Player player && hasBoots(player) && hasLeggings(player) && hasChestplate(player) && hasHelmet(player)) {
            player.removeEffect(MobEffectInit.RADIATION);
        }
    }

    private boolean hasFerrotitaniumArmorSetOn(Player player) {
        return player.getInventory().getArmor(0).is(ItemInit.FERROTITANIUM_BOOTS.get()) &&
                player.getInventory().getArmor(1).is(ItemInit.FERROTITANIUM_LEGGINGS.get()) &&
                player.getInventory().getArmor(2).is(ItemInit.FERROTITANIUM_CHESTPLATE.get()) &&
                player.getInventory().getArmor(3).is(ItemInit.FERROTITANIUM_HELMET.get());
    }
    private boolean hasBoots(Player player) {
         return player.getInventory().getArmor(0).is(ItemInit.FERROTITANIUM_BOOTS.get()) || player.getInventory().getArmor(0).is(ItemInit.FLEROVIUM_BOOTS.get());
    }

    private boolean hasLeggings(Player player) {
         return player.getInventory().getArmor(1).is(ItemInit.FERROTITANIUM_LEGGINGS.get()) || player.getInventory().getArmor(1).is(ItemInit.FLEROVIUM_LEGGINGS.get());
    }

    private boolean hasChestplate(Player player) {
        return player.getInventory().getArmor(2).is(ItemInit.FERROTITANIUM_CHESTPLATE.get()) || player.getInventory().getArmor(2).is(ItemInit.FLEROVIUM_CHESTPLATE.get());
    }

    private boolean hasHelmet(Player player) {
        return player.getInventory().getArmor(3).is(ItemInit.FERROTITANIUM_HELMET.get()) || player.getInventory().getArmor(3).is(ItemInit.FLEROVIUM_HELMET.get());
    }





}
