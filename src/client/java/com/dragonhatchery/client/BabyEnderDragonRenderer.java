package com.dragonhatchery.client;

import com.dragonhatchery.DragonHatchery;
import com.dragonhatchery.entity.BabyEnderDragonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class BabyEnderDragonRenderer extends MobRenderer<BabyEnderDragonEntity, BabyEnderDragonRenderState, BabyEnderDragonModel> {

	private static final Identifier TEXTURE =
			Identifier.fromNamespaceAndPath(DragonHatchery.MOD_ID, "textures/entity/baby_ender_dragon.png");

	public BabyEnderDragonRenderer(EntityRendererProvider.Context context) {
		super(context, new BabyEnderDragonModel(context.bakeLayer(ModModelLayers.BABY_ENDER_DRAGON)), 0.5F);
	}

	@Override
	public Identifier getTextureLocation(BabyEnderDragonRenderState state) {
		return TEXTURE;
	}

	@Override
	public BabyEnderDragonRenderState createRenderState() {
		return new BabyEnderDragonRenderState();
	}

	@Override
	public void extractRenderState(BabyEnderDragonEntity entity, BabyEnderDragonRenderState state, float partialTick) {
		super.extractRenderState(entity, state, partialTick);
		state.flapAngle = entity.getFlapProgress(partialTick);
	}
}
