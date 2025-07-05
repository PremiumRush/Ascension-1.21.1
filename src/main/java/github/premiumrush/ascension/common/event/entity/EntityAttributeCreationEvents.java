package github.premiumrush.ascension.common.event.entity;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.common.world.entity.custom.*;
import github.premiumrush.ascension.common.init.EntityInit;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = Ascension.MODID, bus = EventBusSubscriber.Bus.MOD)
public class EntityAttributeCreationEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityInit.EMBER_GOLEM.get(), EmberGolemEntity.createAttributes().build());
        event.put(EntityInit.ICE_BLAZE.get(), IceBlazeEntity.createAttributes().build());
        event.put(EntityInit.TITANIUM_GOLEM.get(), TitaniumGolemEntity.createAttributes().build());
        event.put(EntityInit.FERROTITANIUM_GOLEM.get(), FerrotitaniumGolemEntity.createAttributes().build());
        event.put(EntityInit.SHARK.get(), SharkEntity.createAttributes().build());
        event.put(EntityInit.FLOCK_SHARK.get(), FlockSharkEntity.createAttributes().build());
        event.put(EntityInit.GHOST.get(), AbstractGhostEntity.createAttributes().build());
        event.put(EntityInit.TAMED_GHOST.get(), AbstractGhostEntity.createAttributes().build());
        event.put(EntityInit.BANSHEE.get(), AbstractGhostEntity.createAttributes().build());
    }
}
