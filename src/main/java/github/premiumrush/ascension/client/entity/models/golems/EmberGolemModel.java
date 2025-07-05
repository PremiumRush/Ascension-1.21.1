package github.premiumrush.ascension.client.entity.models.golems;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import github.premiumrush.ascension.client.entity.animations.EmberGolemAnimationDefinitions;
import github.premiumrush.ascension.common.world.entity.custom.EmberGolemEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class EmberGolemModel<T extends Entity> extends HierarchicalModel<T> {

	private final ModelPart body;
	private final ModelPart legs;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;
	private final ModelPart arms;
	private final ModelPart rightArm;
	private final ModelPart leftArm;
	private final ModelPart head;
	private final ModelPart corpus;

	public EmberGolemModel(ModelPart root) {
		this.body = root.getChild("body");
		this.legs = this.body.getChild("legs");
		this.rightLeg = this.legs.getChild("rightLeg");
		this.leftLeg = this.legs.getChild("leftLeg");
		this.arms = this.body.getChild("arms");
		this.rightArm = this.arms.getChild("rightArm");
		this.leftArm = this.arms.getChild("leftArm");
		this.head = this.body.getChild("head");
		this.corpus = this.body.getChild("corpus");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition legs = body.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, -15.0F, 0.0F));

		PartDefinition rightLeg = legs.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.5F, 0.0F, 0.0F));

		PartDefinition leftLeg = legs.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, 0.0F, 0.0F));

		PartDefinition arms = body.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, -38.0F, 0.5F));

		PartDefinition rightArm = arms.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(74, 0).addBox(-6.0F, 1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(70, 24).addBox(-7.5F, 13.0F, -3.5F, 7.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.0F, 1.0F, 0.0F));

		PartDefinition rightArmCap_r1 = rightArm.addOrReplaceChild("rightArmCap_r1", CubeListBuilder.create().texOffs(48, 9).addBox(-6.5947F, -2.4271F, -3.0F, 7.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition leftArm = arms.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(74, 0).addBox(2.0F, 1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(70, 24).addBox(0.5F, 13.0F, -3.5F, 7.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 1.0F, 0.0F));

		PartDefinition leftArmCap_r1 = leftArm.addOrReplaceChild("leftArmCap_r1", CubeListBuilder.create().texOffs(92, 51).addBox(-0.4053F, -2.4271F, -3.0F, 7.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 46).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -38.0F, -2.0F));

		PartDefinition corpus = body.addOrReplaceChild("corpus", CubeListBuilder.create().texOffs(40, 52).addBox(-9.0F, -4.375F, -3.125F, 18.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(74, 1).addBox(-5.0F, -12.375F, -2.125F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(74, 1).addBox(1.0F, -12.375F, -2.125F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(3, 25).addBox(-11.0F, -22.375F, -4.125F, 22.0F, 10.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.625F, 0.125F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		this.animateWalk(EmberGolemAnimationDefinitions.WALKINGANIMATION, limbSwing, limbSwingAmount, 3.0f, 3.0f);

		this.animate(((EmberGolemEntity) entity).attackAnimationState, EmberGolemAnimationDefinitions.ATTACKANIMATION, ageInTicks, 1f);
		this.animate(((EmberGolemEntity) entity).wavingAnimationState, EmberGolemAnimationDefinitions.WAVINGANIMATION, ageInTicks, 1f);
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
		return body;
	}
}