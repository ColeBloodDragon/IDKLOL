package com.dragonhatchery.item;

import com.dragonhatchery.DragonHatchery;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {

	public static final Item ANCIENT_DRAGONFLAME_CHARGE = register(
			"ancient_dragonflame_charge",
			Item::new,
			new Item.Properties().stacksTo(16)
	);

	public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM,
				Identifier.fromNamespaceAndPath(DragonHatchery.MOD_ID, name));
		T item = itemFactory.apply(settings.setId(itemKey));
		return Registry.register(BuiltInRegistries.ITEM, itemKey, item);
	}

	public static void registerItems() {
		DragonHatchery.LOGGER.info("Registering items for " + DragonHatchery.MOD_ID);

		// NOTE: If ItemGroupEvents has been renamed on your target build (some 26.x
		// snapshots renamed this to CreativeModeTabEvents), swap the class below accordingly.
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS)
				.register(entries -> entries.add(ANCIENT_DRAGONFLAME_CHARGE));
	}
}
