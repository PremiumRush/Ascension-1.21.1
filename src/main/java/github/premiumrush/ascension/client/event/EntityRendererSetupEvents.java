package github.premiumrush.ascension.client.event;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.client.entity.EntityModelLayers;
import github.premiumrush.ascension.client.entity.models.*;
import github.premiumrush.ascension.client.entity.models.ghosts.*;
import github.premiumrush.ascension.client.entity.models.golems.*;
import github.premiumrush.ascension.client.entity.renderers.*;
import github.premiumrush.ascension.client.entity.renderers.ghosts.*;
import github.premiumrush.ascension.client.entity.renderers.golems.*;
import github.premiumrush.ascension.client.entity.renderers.projectile.*;
import github.premiumrush.ascension.client.entity.renderers.projectile.arrows.*;
import github.premiumrush.ascension.client.entity.renderers.sharks.*;
import github.premiumrush.ascension.common.init.EntityInit;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Ascension.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntityRendererSetupEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(EntityModelLayers.EMBER_GOLEM_LAYER, EmberGolemModel::createBodyLayer);
        event.registerLayerDefinition(EntityModelLayers.ICE_BLAZE_LAYER, IceBlazeModel::createBodyLayer);
        event.registerLayerDefinition(EntityModelLayers.TITANIUM_GOLEM_LAYER, TitaniumGolemModel::createBodyLayer);
        event.registerLayerDefinition(EntityModelLayers.FERROTITANIUM_GOLEM_LAYER, FerrotitaniumGolemModel::createBodyLayer);
        event.registerLayerDefinition(EntityModelLayers.SHARK_LAYER, SharkModel::createBodyLayer);
        event.registerLayerDefinition(EntityModelLayers.FLOCK_SHARK_LAYER, SharkModel::createBodyLayer);
        event.registerLayerDefinition(EntityModelLayers.GHOST_LAYER, GhostModel::createBodyLayer);
        event.registerLayerDefinition(EntityModelLayers.BANSHEE_LAYER, BansheeModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(EntityInit.EMBER_GOLEM.get(), EmberGolemRenderer::new);
        EntityRenderers.register(EntityInit.ICE_BLAZE.get(), IceBlazeRenderer::new);
        EntityRenderers.register(EntityInit.SMALL_SNOWBALL.get(), SmallSnowBallRenderer::new);
        EntityRenderers.register(EntityInit.TITANIUM_GOLEM.get(), TitaniumGolemRenderer::new);
        EntityRenderers.register(EntityInit.FERROTITANIUM_GOLEM.get(), FerrotitaniumGolemRenderer::new);
        EntityRenderers.register(EntityInit.SPARK_OF_INERTIA.get(), GeneralSparkRenderer::new);
        EntityRenderers.register(EntityInit.INERTIA_PROJECTILE.get(), InertiaProjectileRenderer::new);
        EntityRenderers.register(EntityInit.INFUSION_BLAST_SPARK.get(), GeneralSparkRenderer::new);
        EntityRenderers.register(EntityInit.SHARK.get(), SharkRenderer::new);
        EntityRenderers.register(EntityInit.FLOCK_SHARK.get(), FlockSharkRenderer::new);
        EntityRenderers.register(EntityInit.GHOST.get(), GhostRenderer::new);
        EntityRenderers.register(EntityInit.TAMED_GHOST.get(), TamedGhostRenderer::new);
        EntityRenderers.register(EntityInit.BANSHEE.get(), BansheeRenderer::new);
        EntityRenderers.register(EntityInit.VEXAL_ARROW.get(), VexalArrowRenderer::new);
        EntityRenderers.register(EntityInit.INFUSED_VEXAL_ARROW.get(), InfusedVexalArrowRenderer::new);
    }
}
