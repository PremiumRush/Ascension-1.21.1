package github.premiumrush.ascension.common.world.item;

import com.google.common.collect.Sets;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BaseDaggerItem extends SwordItem {
    public BaseDaggerItem(Tier p_tier, Properties p_properties) {
        super(p_tier, p_properties);
    }

    @Override
    public boolean canPerformAction(@NotNull ItemStack itemStack, @NotNull ItemAbility itemAbility) {
        return DaggerActions.DEFAULT_DAGGER_ACTIONS.contains(itemAbility);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if(stack.is(BaseDaggerItem.this) && enchantment.is(ResourceLocation.parse("sweeping_edge"))) {
            return false;
        }
        return super.supportsEnchantment(stack, enchantment);
    }

    public static class DaggerActions extends ItemAbilities {
        public static final ItemAbility DAGGER_DIG = ItemAbility.get("sword_dig");
        public static final Set DEFAULT_DAGGER_ACTIONS;

        public DaggerActions() {
        }
        private static Set of(ItemAbility... actions) {
            return Stream.of(actions).collect(Collectors.toCollection(Sets::newIdentityHashSet));
        }
        static {
            DEFAULT_DAGGER_ACTIONS = of(SWORD_DIG);
        }
    }
}
