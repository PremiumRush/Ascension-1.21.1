package github.premiumrush.ascension.client.entity.renderers.golems;

import com.mojang.blaze3d.vertex.PoseStack;
import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.client.entity.EntityModelLayers;
import github.premiumrush.ascension.client.entity.models.golems.TitaniumGolemModel;
import github.premiumrush.ascension.common.world.entity.custom.TitaniumGolemEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TitaniumGolemRenderer extends MobRenderer<TitaniumGolemEntity, TitaniumGolemModel<TitaniumGolemEntity>> {
    public TitaniumGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new TitaniumGolemModel<>(context.bakeLayer(EntityModelLayers.TITANIUM_GOLEM_LAYER)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(TitaniumGolemEntity emberGolemEntity) {
        return ResourceLocation.fromNamespaceAndPath(Ascension.MODID, "textures/entity/titanium_golem.png");
    }

    @Override
    public void render(TitaniumGolemEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
