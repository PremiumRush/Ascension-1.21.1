package github.premiumrush.ascension.common.world.item;

import github.premiumrush.ascension.common.init.ItemInit;
import github.premiumrush.ascension.common.init.MobEffectInit;
import github.premiumrush.ascension.common.util.AscensionUtil;
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

public class FleroviumArmorItem extends ArmorItem implements AscensionUtil {
    public FleroviumArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext tooltipContext, List<Component> pToolTipComponents, TooltipFlag pIsAdvanced) {
        pToolTipComponents.add(Component.translatable("tooltip.ascension.flerovium_armor1.tooltip"));
        if (pStack.is(ItemInit.FLEROVIUM_CHESTPLATE.get())) {
            pToolTipComponents.add(Component.translatable("tooltip.ascension.radiation_chestplate.tooltip"));
        }
        pToolTipComponents.add(Component.translatable("tooltip.ascension.flerovium_armor2.tooltip"));
        super.appendHoverText(pStack, tooltipContext, pToolTipComponents, pIsAdvanced);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide() && entity instanceof Player player) {
            if (hasArmorSet(player, FleroviumArmorItem.class)) {
                player.removeEffect(MobEffects.POISON);
            }
            if (player.getInventory().getArmor(2).is(ItemInit.FLEROVIUM_CHESTPLATE.get())) {
                player.removeEffect(MobEffectInit.RADIATION);
            }
        }
    }
}
