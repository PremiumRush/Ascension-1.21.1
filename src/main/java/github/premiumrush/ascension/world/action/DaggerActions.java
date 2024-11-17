package github.premiumrush.ascension.world.action;

import com.google.common.collect.Sets;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DaggerActions extends ItemAbilities {
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
