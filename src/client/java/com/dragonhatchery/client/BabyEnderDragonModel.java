package com.dragonhatchery.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

/**
 * A simple low-poly dragon: body, head, tail and two wings.
 * Feel free to replace this with a Blockbench-exported model later.
 */
public class BabyEnderDragonModel extends EntityModel<BabyEnderDragonRenderState> {
	private final ModelPart head;
	private final ModelPart tail;
	private final ModelPart leftWing;
	private final ModelPart rightWing;

	public BabyEnderDragonModel(ModelPart root) {
		ModelPart body = root.getChild("body");
		this.head = body.getChild("head");
		this.tail = body.getChild("tail");
		this.leftWing = body.getChild("left_wing");
		this.rightWing = body.getChild("right_wing");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		PartDefinition body = root.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 16.0F),
				PartPose.offset(0.0F, 16.0F, 0.0F));

		body.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(0, 24).addBox(-3.0F, -3.0F, -6.0F, 6.0F, 6.0F, 6.0F),
				PartPose.offset(0.0F, -1.0F, -9.0F));

		body.addOrReplaceChild("tail",
				CubeListBuilder.create().texOffs(0, 36).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 10.0F),
				PartPose.offset(0.0F, 0.0F, 8.0F));

		body.addOrReplaceChild("left_wing",
				CubeListBuilder.create().texOffs(24, 0).addBox(0.0F, -1.0F, -4.0F, 10.0F, 1.0F, 8.0F),
				PartPose.offset(4.0F, -3.0F, 0.0F));

		body.addOrReplaceChild("right_wing",
				CubeListBuilder.create().texOffs(24, 12).addBox(-10.0F, -1.0F, -4.0F, 10.0F, 1.0F, 8.0F),
				PartPose.offset(-4.0F, -3.0F, 0.0F));

		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void setupAnim(BabyEnderDragonRenderState state) {
		float flap = state.flapAngle;
		this.leftWing.zRot = (float) Math.sin(flap * 0.6F) * 0.6F;
		this.rightWing.zRot = -this.leftWing.zRot;
		this.tail.yRot = (float) Math.sin(flap * 0.3F) * 0.2F;
	}
}
