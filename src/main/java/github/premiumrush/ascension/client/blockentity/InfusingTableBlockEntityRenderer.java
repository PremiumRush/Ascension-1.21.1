package github.premiumrush.ascension.client.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import github.premiumrush.ascension.common.world.blockentity.InfusingTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class InfusingTableBlockEntityRenderer implements BlockEntityRenderer<InfusingTableBlockEntity> {
    ItemRenderer itemRenderer;

    public InfusingTableBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(InfusingTableBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack baseStack = blockEntity.getBaseItemStack();
        ItemStack catalystStack = blockEntity.getCatalystItemStack();
        Player player = Minecraft.getInstance().player;
        float rotation = player.tickCount * 2 + partialTick;
        if (blockEntity.shouldRenderCatalyst()) {
            renderItem(catalystStack, new Vec3(0.5, 1.45, 0.5), rotation, blockEntity, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
        } else if (!blockEntity.getBaseItemStack().isEmpty()) {
            renderItem(baseStack, new Vec3(0.5, 1.45, 0.5), rotation, blockEntity, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
        } else {
            renderItem(blockEntity.getItems().get(2), new Vec3(0.5, 1.45, 0.5), rotation, blockEntity, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
        }
    }

    private void renderItem(ItemStack stack, Vec3 offset, float yRot, InfusingTableBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        int renderId = (int) blockEntity.getBlockPos().asLong();
        poseStack.translate(offset.x, offset.y, offset.z);
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        poseStack.scale(0.5f, 0.5f, 0.5f);
        itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos()), packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), renderId);
        poseStack.popPose();
    }
}
