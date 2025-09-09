/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.naruto.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

import net.minecraft.world.item.Item;

import net.mcreator.naruto.NarutoMod;

public class NarutoModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(NarutoMod.MODID);
	public static final DeferredItem<Item> DUMMY_SPAWN_EGG = REGISTRY.register("dummy_spawn_egg", () -> new DeferredSpawnEggItem(NarutoModEntities.DUMMY, -1, -1, new Item.Properties()));
	// Start of user code block custom items
	// End of user code block custom items
}