package github.premiumrush.ascension.client.entity.renderers.sharks;

import com.mojang.blaze3d.vertex.PoseStack;
import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.client.entity.EntityModelLayers;
import github.premiumrush.ascension.client.entity.models.SharkModel;
import github.premiumrush.ascension.common.world.entity.custom.SharkEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SharkRenderer extends MobRenderer<SharkEntity, SharkModel<SharkEntity>> {
    public SharkRenderer(EntityRendererProvider.Context context) {
        super(context, new SharkModel<>(context.bakeLayer(EntityModelLayers.SHARK_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(SharkEntity sharkEntity) {
        return ResourceLocation.fromNamespaceAndPath(Ascension.MODID, "textures/entity/shark.png");
    }

    @Override
    public void render(SharkEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
