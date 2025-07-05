package github.premiumrush.ascension.client.event;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.client.blockentity.BasePedestalBlockEntityRenderer;
import github.premiumrush.ascension.client.blockentity.EnchantmentRefinerBlockEntityRenderer;
import github.premiumrush.ascension.client.blockentity.InfusingTableBlockEntityRenderer;
import github.premiumrush.ascension.common.init.BlockEntityInit;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Ascension.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BlockEntityRendererEvents {
    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityInit.INFUSING_TABLE_BLOCK_ENTITY.get(), InfusingTableBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityInit.ENCHANTMENT_REFINER_BLOCK_ENTITY.get(), EnchantmentRefinerBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityInit.GOLD_ITEM_PEDESTAL_BLOCK_ENTITY.get(), BasePedestalBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityInit.IRON_ITEM_PEDESTAL_BLOCK_ENTITY.get(), BasePedestalBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityInit.NETHERITE_ITEM_PEDESTAL_BLOCK_ENTITY.get(), BasePedestalBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityInit.COPPER_ITEM_PEDESTAL_BLOCK_ENTITY.get(), BasePedestalBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityInit.TITANIUM_ITEM_PEDESTAL_BLOCK_ENTITY.get(), BasePedestalBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityInit.FERROTITANIUM_ITEM_PEDESTAL_BLOCK_ENTITY.get(), BasePedestalBlockEntityRenderer::new);
    }
}
