package com.dragonhatchery.entity;

import com.dragonhatchery.DragonHatchery;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {

	public static final EntityType<BabyEnderDragonEntity> BABY_ENDER_DRAGON = register(
			"baby_ender_dragon",
			FabricEntityTypeBuilder.<BabyEnderDragonEntity>create(MobCategory.CREATURE, BabyEnderDragonEntity::new)
					.dimensions(EntityDimensions.scalable(0.9F, 1.1F))
					.trackRangeBlocks(64)
					.trackedUpdateRate(3)
	);

	private static EntityType<BabyEnderDragonEntity> register(String name, FabricEntityTypeBuilder<BabyEnderDragonEntity> builder) {
		ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE,
				Identifier.fromNamespaceAndPath(DragonHatchery.MOD_ID, name));
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
	}

	public static void registerEntities() {
		FabricDefaultAttributeRegistry.register(BABY_ENDER_DRAGON, BabyEnderDragonEntity.createAttributes());
		DragonHatchery.LOGGER.info("Registering entities for " + DragonHatchery.MOD_ID);
	}
}
