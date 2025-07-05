package github.premiumrush.ascension.common.event.player;

import github.premiumrush.ascension.common.world.item.TitaniumArmorItem;
import github.premiumrush.ascension.common.util.AscensionUtil;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;

public class PlayerXpEvents implements AscensionUtil {
    @SubscribeEvent
    public void titaniumArmorDoubleXpEvent(PlayerXpEvent.XpChange event) {
        if (hasArmorSet(event.getEntity(), TitaniumArmorItem.class) && event.getAmount() > 0) {
            event.setAmount(event.getAmount() * 2);
        }
    }
}
