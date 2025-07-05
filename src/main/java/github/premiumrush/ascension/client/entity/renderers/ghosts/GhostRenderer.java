package github.premiumrush.ascension.client.entity.renderers.ghosts;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.client.entity.EntityModelLayers;
import github.premiumrush.ascension.client.entity.models.ghosts.GhostModel;
import github.premiumrush.ascension.common.world.entity.custom.GhostEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GhostRenderer extends MobRenderer<GhostEntity, GhostModel<GhostEntity>> {
    public GhostRenderer(EntityRendererProvider.Context context) {
        super(context, new GhostModel<>(context.bakeLayer(EntityModelLayers.GHOST_LAYER)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(GhostEntity ghostEntity) {
        return ResourceLocation.fromNamespaceAndPath(Ascension.MODID, "textures/entity/ghost.png");
    }
}
