/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.naruto.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.mcreator.naruto.NarutoMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class NarutoModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NarutoMod.MODID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NARUTO_MOD_SCROLLS = REGISTRY.register("naruto_mod_scrolls",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.naruto.naruto_mod_scrolls")).icon(() -> new ItemStack(NarutoModItems.SCROLL_GREAT_FIRE_ANNIHILATION.get())).displayItems((parameters, tabData) -> {
				tabData.accept(NarutoModItems.SCROLL_GREAT_FIREBALL.get());
				tabData.accept(NarutoModItems.SCROLL_PHOENIX_FIRE.get());
				tabData.accept(NarutoModItems.SCROLL_HIDING_IN_ASH.get());
				tabData.accept(NarutoModItems.SCROLL_GREAT_FIRE_ANNIHILATION.get());
				tabData.accept(NarutoModItems.SCROLL_FIRE_DRAGON_BULLET.get());
				tabData.accept(NarutoModItems.SCROLL_GREAT_FLAME.get());
				tabData.accept(NarutoModItems.SCROLL_HIDING_IN_MIST.get());
				tabData.accept(NarutoModItems.SCROLL_WATER_CLONE.get());
				tabData.accept(NarutoModItems.SCROLL_WATER_PRISON.get());
				tabData.accept(NarutoModItems.SCROLL_WATER_DRAGON_BULLET.get());
				tabData.accept(NarutoModItems.SCROLL_WATER_BULLET.get());
				tabData.accept(NarutoModItems.SCROLL_SEVERING_WAVE.get());
				tabData.accept(NarutoModItems.SCROLL_EARTH_DRAGON_BULLET.get());
				tabData.accept(NarutoModItems.SCROLL_EARTH_WALL.get());
				tabData.accept(NarutoModItems.SCROLL_ROCK_FISTS.get());
				tabData.accept(NarutoModItems.SCROLL_EARTH_FLOW_SPEARS.get());
				tabData.accept(NarutoModItems.SCROLL_EARTH_GOLEM.get());
			}).build());

	@SubscribeEvent
	public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			tabData.accept(NarutoModItems.DUMMY_SPAWN_EGG.get());
		}
	}
}