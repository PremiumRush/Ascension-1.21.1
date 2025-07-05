package github.premiumrush.ascension.client.entity.renderers.ghosts;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.client.entity.EntityModelLayers;
import github.premiumrush.ascension.client.entity.models.ghosts.BansheeModel;
import github.premiumrush.ascension.common.world.entity.custom.BansheeEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BansheeRenderer extends MobRenderer<BansheeEntity, BansheeModel<BansheeEntity>> {
    public BansheeRenderer(EntityRendererProvider.Context context) {
        super(context, new BansheeModel<>(context.bakeLayer(EntityModelLayers.BANSHEE_LAYER)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(BansheeEntity bansheeEntity) {
        return ResourceLocation.fromNamespaceAndPath(Ascension.MODID, "textures/entity/banshee.png");
    }
}
