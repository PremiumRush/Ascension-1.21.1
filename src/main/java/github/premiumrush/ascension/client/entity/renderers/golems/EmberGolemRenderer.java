package github.premiumrush.ascension.client.entity.renderers.golems;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.client.entity.EntityModelLayers;
import github.premiumrush.ascension.client.entity.models.golems.EmberGolemModel;
import github.premiumrush.ascension.common.world.entity.custom.EmberGolemEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EmberGolemRenderer extends MobRenderer<EmberGolemEntity, EmberGolemModel<EmberGolemEntity>> {
    public EmberGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new EmberGolemModel<>(context.bakeLayer(EntityModelLayers.EMBER_GOLEM_LAYER)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(EmberGolemEntity emberGolemEntity) {
        return ResourceLocation.fromNamespaceAndPath(Ascension.MODID, "textures/entity/ember_golem.png");
    }
}
