package github.premiumrush.ascension.client.entity.renderers.projectile.arrows;

import github.premiumrush.ascension.Ascension;
import github.premiumrush.ascension.common.world.entity.custom.InfusedVexalArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class InfusedVexalArrowRenderer extends ArrowRenderer<InfusedVexalArrow> {
    public InfusedVexalArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(InfusedVexalArrow arrow) {
        return ResourceLocation.fromNamespaceAndPath(Ascension.MODID,"textures/entity/infused_vexal_arrow_projectile.png");
    }
}
