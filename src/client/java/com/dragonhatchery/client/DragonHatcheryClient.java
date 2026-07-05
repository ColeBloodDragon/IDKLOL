package com.dragonhatchery.client;

import com.dragonhatchery.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class DragonHatcheryClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BABY_ENDER_DRAGON, BabyEnderDragonModel::createBodyLayer);
		EntityRendererRegistry.register(ModEntities.BABY_ENDER_DRAGON, BabyEnderDragonRenderer::new);
	}
}
