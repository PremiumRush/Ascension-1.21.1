package github.premiumrush.ascension.client.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import github.premiumrush.ascension.common.world.blockentity.EnchantmentRefinerBlockEntity;
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

public class EnchantmentRefinerBlockEntityRenderer implements BlockEntityRenderer<EnchantmentRefinerBlockEntity> {
    ItemRenderer itemRenderer;

    public EnchantmentRefinerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(EnchantmentRefinerBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        ItemStack baseStack = blockEntity.getItemStackAt(0);
        ItemStack topStack = blockEntity.getItemStackAt(1);
        Player player = Minecraft.getInstance().player;
        float rotation = player.tickCount * 2 + partialTick;
        renderItem(baseStack, new Vec3(0.5, 1.02, 0.5), 180, 90, 0.6f, 0.6f, 0.6f, blockEntity, partialTick, poseStack, multiBufferSource, packedLight, packedOverlay);
        renderItem(topStack, new Vec3(0.5, 1.45, 0.5), rotation, 0, 0.5f, 0.5f, 0.5f, blockEntity, partialTick, poseStack, multiBufferSource, packedLight, packedOverlay);
    }

    private void renderItem(ItemStack stack, Vec3 offset, float yRot, float xRot, float xScale, float yScale, float zScale, EnchantmentRefinerBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        int renderId = (int) blockEntity.getBlockPos().asLong();
        poseStack.translate(offset.x, offset.y, offset.z);
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(xRot));
        poseStack.scale(xScale, yScale, zScale);
        itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos()), packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), renderId);
        poseStack.popPose();
    }
}
