package github.premiumrush.ascension.client.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.client.entity.EntityModelLayers;
import github.premiumrush.ascension.client.entity.models.IceBlazeModel;
import github.premiumrush.ascension.common.world.entity.custom.IceBlazeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class IceBlazeRenderer extends MobRenderer<IceBlazeEntity, IceBlazeModel<IceBlazeEntity>> {
    public IceBlazeRenderer(EntityRendererProvider.Context context) {
        super(context, new IceBlazeModel<>(context.bakeLayer(EntityModelLayers.ICE_BLAZE_LAYER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(IceBlazeEntity iceBlazeEntity) {
        return ResourceLocation.fromNamespaceAndPath(Ascension.MODID, "textures/entity/ice_blaze.png");
    }

    @Override
    public void render(IceBlazeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
