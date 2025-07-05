package github.premiumrush.ascension.client.entity.models;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

import java.util.Arrays;

public class IceBlazeModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart[] upperBodyParts;
    private final ModelPart head;

    public IceBlazeModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.upperBodyParts = new ModelPart[12];
        Arrays.setAll(this.upperBodyParts, (p_170449_) -> {
            return root.getChild(getPartName(p_170449_));
        });
    }

    private static String getPartName(int index) {
        return "part" + index;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
        float f = 0.0F;
        CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F);

        int k;
        float f5;
        float f7;
        float f9;
        for(k = 0; k < 4; ++k) {
            f5 = Mth.cos(f) * 9.0F;
            f7 = -2.0F + Mth.cos((float)(k * 2) * 0.25F);
            f9 = Mth.sin(f) * 9.0F;
            partdefinition.addOrReplaceChild(getPartName(k), cubelistbuilder, PartPose.offset(f5, f7, f9));
            ++f;
        }

        f = 0.7853982F;

        for(k = 4; k < 8; ++k) {
            f5 = Mth.cos(f) * 7.0F;
            f7 = 2.0F + Mth.cos((float)(k * 2) * 0.25F);
            f9 = Mth.sin(f) * 7.0F;
            partdefinition.addOrReplaceChild(getPartName(k), cubelistbuilder, PartPose.offset(f5, f7, f9));
            ++f;
        }

        f = 0.47123894F;

        for(k = 8; k < 12; ++k) {
            f5 = Mth.cos(f) * 5.0F;
            f7 = 11.0F + Mth.cos((float)k * 1.5F * 0.5F);
            f9 = Mth.sin(f) * 5.0F;
            partdefinition.addOrReplaceChild(getPartName(k), cubelistbuilder, PartPose.offset(f5, f7, f9));
            ++f;
        }

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public ModelPart root() {
        return this.root;
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = ageInTicks * 3.1415927F * -0.1F;

        int k;
        for(k = 0; k < 4; ++k) {
            this.upperBodyParts[k].y = -2.0F + Mth.cos(((float)(k * 2) + ageInTicks) * 0.25F);
            this.upperBodyParts[k].x = Mth.cos(f) * 9.0F;
            this.upperBodyParts[k].z = Mth.sin(f) * 9.0F;
            ++f;
        }

        f = 0.7853982F + ageInTicks * 3.1415927F * 0.03F;

        for(k = 4; k < 8; ++k) {
            this.upperBodyParts[k].y = 2.0F + Mth.cos(((float)(k * 2) + ageInTicks) * 0.25F);
            this.upperBodyParts[k].x = Mth.cos(f) * 7.0F;
            this.upperBodyParts[k].z = Mth.sin(f) * 7.0F;
            ++f;
        }

        f = 0.47123894F + ageInTicks * 3.1415927F * -0.05F;

        for(k = 8; k < 12; ++k) {
            this.upperBodyParts[k].y = 11.0F + Mth.cos(((float)k * 1.5F + ageInTicks) * 0.5F);
            this.upperBodyParts[k].x = Mth.cos(f) * 5.0F;
            this.upperBodyParts[k].z = Mth.sin(f) * 5.0F;
            ++f;
        }

        this.head.yRot = netHeadYaw * 0.017453292F;
        this.head.xRot = headPitch * 0.017453292F;
    }
}
