package github.premiumrush.ascension.client.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import github.premiumrush.ascension.common.world.blockentity.item_pedestals.variants.BasePedestalBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class BasePedestalBlockEntityRenderer implements BlockEntityRenderer<BasePedestalBlockEntity> {
    ItemRenderer itemRenderer;
    public BasePedestalBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(BasePedestalBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack stack = blockEntity.getCurrentItemStack();
        renderItem(stack, new Vec3(0.5, getCurrentYOffset(blockEntity), 0.5), getCurrentRotationSpeed(blockEntity, partialTick), blockEntity, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
    }

    public float getCurrentRotationSpeed(BasePedestalBlockEntity blockEntity, float partialTick) {
        Player player = Minecraft.getInstance().player;
        float setRotationSpeed = blockEntity.getRenderType();
        switch (blockEntity.getRenderType()) {
            case 1 -> setRotationSpeed = player.tickCount * 2 + partialTick;
            case 2 -> setRotationSpeed = (player.tickCount * 2 + partialTick) * 2f;
            case 3 -> setRotationSpeed = -(player.tickCount * 2 + partialTick);
            case 4 -> setRotationSpeed = -((player.tickCount * 2 + partialTick) * 2f);
            case 5, 6, 7, 0 -> setRotationSpeed = 0;
        }
        return setRotationSpeed;
    }
    public double getCurrentYOffset(BasePedestalBlockEntity blockEntity) {
        double setCurrentYOffset = 0;
        switch (blockEntity.getRenderType()) {
            case 1,2,3,4,5,6,7-> setCurrentYOffset = 1.2;
            case 0 -> setCurrentYOffset = 0.89;
        }
        return setCurrentYOffset;
    }

    private void renderItem(ItemStack stack, Vec3 offset, float yRot, BasePedestalBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        int renderId = (int) blockEntity.getBlockPos().asLong();
        poseStack.translate(offset.x, offset.y, offset.z);
        float angle = blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(angle + yRot));
        switch (blockEntity.getRenderType()) {
            case 6 -> poseStack.mulPose(Axis.ZP.rotationDegrees(-45));
            case 7 -> poseStack.mulPose(Axis.ZP.rotationDegrees(135));
            case 0 -> poseStack.mulPose(Axis.XP.rotationDegrees(90));
            default -> {}
        }
        poseStack.scale(0.5f, 0.5f, 0.5f);
        itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos()), packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), renderId);
        poseStack.popPose();
    }
}
