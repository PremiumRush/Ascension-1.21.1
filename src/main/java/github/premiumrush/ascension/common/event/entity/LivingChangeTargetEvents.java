package github.premiumrush.ascension.common.event.entity;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;

import java.util.List;

public class LivingChangeTargetEvents {
    @SubscribeEvent
    public void blindnessNoNewTargetEvent(LivingChangeTargetEvent event) {
        List<EntityType<?>> blacklistEntities = List.of(EntityType.WARDEN, EntityType.ENDER_DRAGON);
        for (EntityType<?> excludedType : blacklistEntities) {
            if (event.getEntity().getType() != (excludedType) && event.getEntity().hasEffect(MobEffects.BLINDNESS) && !(event.getEntity().getLastAttacker() instanceof Player))
            {
                event.setNewAboutToBeSetTarget(null);
            }
        }
    }
}
