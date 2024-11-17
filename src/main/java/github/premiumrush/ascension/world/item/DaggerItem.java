package github.premiumrush.ascension.world.item;

import github.premiumrush.ascension.world.action.DaggerActions;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;

public class DaggerItem extends SwordItem {
    public DaggerItem(Tier p_tier, Properties p_properties) {
        super(p_tier, p_properties);
    }

    @Override
    public boolean canPerformAction(@NotNull ItemStack itemStack, @NotNull ItemAbility itemAbility) {
        return DaggerActions.DEFAULT_DAGGER_ACTIONS.contains(itemAbility);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if(stack.is(DaggerItem.this) && enchantment.is(ResourceLocation.parse("sweeping_edge"))) {
            return false;
        }
        return super.supportsEnchantment(stack, enchantment);
    }
}
