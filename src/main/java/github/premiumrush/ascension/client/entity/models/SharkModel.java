package github.premiumrush.ascension.client.entity.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import github.premiumrush.ascension.client.entity.animations.SharkAnimationDefinitions;
import github.premiumrush.ascension.common.world.entity.custom.SharkEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class SharkModel<T extends Entity> extends HierarchicalModel<T> {
    private final ModelPart body;
    private final ModelPart main;
    private final ModelPart fins;
    private final ModelPart top;
    private final ModelPart left;
    private final ModelPart right;
    private final ModelPart back_fin;
    private final ModelPart tail;
    private final ModelPart front;
    private final ModelPart back;
    private final ModelPart head;
    private final ModelPart jaw;

    public SharkModel(ModelPart root) {
        this.body = root.getChild("body");
        this.main = this.body.getChild("main");
        this.fins = this.body.getChild("fins");
        this.top = this.fins.getChild("top");
        this.left = this.fins.getChild("left");
        this.right = this.fins.getChild("right");
        this.back_fin = this.fins.getChild("back_fin");
        this.tail = this.body.getChild("tail");
        this.front = this.tail.getChild("front");
        this.back = this.tail.getChild("back");
        this.head = this.body.getChild("head");
        this.jaw = this.head.getChild("jaw");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -3.0F));

        PartDefinition main = body.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -4.0F, -8.0F, 10.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(-3.0F, 4.0F, -6.0F, 6.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -3.0F));

        PartDefinition fins = body.addOrReplaceChild("fins", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, -5.0F));

        PartDefinition top = fins.addOrReplaceChild("top", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 2.0F));

        PartDefinition cube_r1 = top.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 61).addBox(-1.5F, -5.0F, -1.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -2.0F, 1.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition left = fins.addOrReplaceChild("left", CubeListBuilder.create(), PartPose.offset(4.0F, 2.5F, 0.0F));

        PartDefinition cube_r2 = left.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(34, 49).addBox(-1.2465F, -0.5F, -3.8177F, 4.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6981F, 0.1745F));

        PartDefinition right = fins.addOrReplaceChild("right", CubeListBuilder.create(), PartPose.offset(-4.0F, 2.5F, 0.0F));

        PartDefinition cube_r3 = right.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 51).addBox(-2.7786F, -0.5F, -3.8968F, 4.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.6981F, -0.1745F));

        PartDefinition back_fin = fins.addOrReplaceChild("back_fin", CubeListBuilder.create().texOffs(62, 39).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 26.0F));

        PartDefinition cube_r4 = back_fin.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(26, 51).addBox(-0.5F, -3.0F, -1.2F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 3.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r5 = back_fin.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(10, 61).addBox(-0.5F, -6.0F, -1.5F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.48F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 5.0F));

        PartDefinition front = tail.addOrReplaceChild("front", CubeListBuilder.create().texOffs(38, 24).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r6 = front.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(60, 54).addBox(-0.5F, -1.0F, -1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 1.0F, 4.0F, 0.0F, -0.4363F, 0.0F));

        PartDefinition cube_r7 = front.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(60, 49).addBox(-1.5F, -2.0F, -1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.0F, 4.0F, 0.0F, 0.4363F, 0.0F));

        PartDefinition back = tail.addOrReplaceChild("back", CubeListBuilder.create().texOffs(52, 0).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 9.0F));

        PartDefinition cube_r8 = back.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(16, 61).addBox(-0.5F, -2.0F, -2.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 3.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 38).addBox(-4.0F, -2.0F, -9.0F, 8.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(42, 59).addBox(-3.5F, 2.0F, -8.0F, 0.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(58, 59).addBox(3.5F, 2.0F, -8.0F, 0.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(52, 20).addBox(-3.5F, 2.0F, -8.0F, 7.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, -11.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(34, 39).addBox(-3.0F, -0.9957F, -7.4347F, 6.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(52, 11).addBox(2.9F, -1.4957F, -7.4347F, 0.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(26, 59).addBox(-2.9F, -1.4957F, -7.4347F, 0.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(52, 21).addBox(-3.0F, -1.4957F, -7.3347F, 6.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.5F, 0.0F, 0.1309F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 80, 80);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);
        this.animateWalk(SharkAnimationDefinitions.SWIMMINGANIMATION, limbSwing, limbSwingAmount, 3.5f,5);
        this.animate(((SharkEntity) entity).attackAnimationState, SharkAnimationDefinitions.ATTACKANIMATION, ageInTicks, 1f);
    }

    private void applyHeadRotation(float netHeadYaw, float headPitch, float ageInTicks) {
        netHeadYaw = Mth.clamp(netHeadYaw, -30, 30);
        headPitch = Mth.clamp(headPitch, -25, 45);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return this.body;
    }
}
