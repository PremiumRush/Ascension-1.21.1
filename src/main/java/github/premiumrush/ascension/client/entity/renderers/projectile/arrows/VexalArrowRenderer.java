package github.premiumrush.ascension.client.entity.renderers.projectile.arrows;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.common.world.entity.custom.VexalArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class VexalArrowRenderer extends ArrowRenderer<VexalArrow> {
    public VexalArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(VexalArrow arrow) {
        return ResourceLocation.fromNamespaceAndPath(Ascension.MODID,"textures/entity/vexal_arrow_projectile.png");
    }
}
