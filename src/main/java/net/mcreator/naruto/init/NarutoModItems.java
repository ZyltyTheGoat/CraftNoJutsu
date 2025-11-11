/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.naruto.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

import net.minecraft.world.item.Item;

import net.mcreator.naruto.item.ScrollWaterPrisonItem;
import net.mcreator.naruto.item.ScrollWaterDragonBulletItem;
import net.mcreator.naruto.item.ScrollWaterCloneItem;
import net.mcreator.naruto.item.ScrollWaterBulletItem;
import net.mcreator.naruto.item.ScrollVacuumWaveItem;
import net.mcreator.naruto.item.ScrollSeveringWaveItem;
import net.mcreator.naruto.item.ScrollRockFistsItem;
import net.mcreator.naruto.item.ScrollRasenshurikenItem;
import net.mcreator.naruto.item.ScrollPhoenixFireItem;
import net.mcreator.naruto.item.ScrollLightningCloneItem;
import net.mcreator.naruto.item.ScrollLightningChakraModeItem;
import net.mcreator.naruto.item.ScrollKirinItem;
import net.mcreator.naruto.item.ScrollHidingInMistItem;
import net.mcreator.naruto.item.ScrollHidingInAshItem;
import net.mcreator.naruto.item.ScrollGreatFlameItem;
import net.mcreator.naruto.item.ScrollGreatFireballItem;
import net.mcreator.naruto.item.ScrollGreatFireAnnihilationItem;
import net.mcreator.naruto.item.ScrollGreatBreakthroughItem;
import net.mcreator.naruto.item.ScrollFireDragonBulletItem;
import net.mcreator.naruto.item.ScrollFalseDarknessItem;
import net.mcreator.naruto.item.ScrollEarthWallItem;
import net.mcreator.naruto.item.ScrollEarthGolemItem;
import net.mcreator.naruto.item.ScrollEarthFlowSpearsItem;
import net.mcreator.naruto.item.ScrollEarthDragonBulletItem;
import net.mcreator.naruto.item.ScrollChidoriItem;
import net.mcreator.naruto.item.ScrollChakraFlowItem;
import net.mcreator.naruto.item.ScrollBladeOfWindItem;
import net.mcreator.naruto.item.ScrollBeastTrackingFangItem;
import net.mcreator.naruto.item.ScrollAirBulletsItem;
import net.mcreator.naruto.NarutoMod;

public class NarutoModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(NarutoMod.MODID);
	public static final DeferredItem<Item> DUMMY_SPAWN_EGG = REGISTRY.register("dummy_spawn_egg", () -> new DeferredSpawnEggItem(NarutoModEntities.DUMMY, -1, -1, new Item.Properties()));
	public static final DeferredItem<Item> SCROLL_GREAT_FIREBALL = REGISTRY.register("scroll_great_fireball", ScrollGreatFireballItem::new);
	public static final DeferredItem<Item> SCROLL_PHOENIX_FIRE = REGISTRY.register("scroll_phoenix_fire", ScrollPhoenixFireItem::new);
	public static final DeferredItem<Item> SCROLL_HIDING_IN_ASH = REGISTRY.register("scroll_hiding_in_ash", ScrollHidingInAshItem::new);
	public static final DeferredItem<Item> SCROLL_GREAT_FIRE_ANNIHILATION = REGISTRY.register("scroll_great_fire_annihilation", ScrollGreatFireAnnihilationItem::new);
	public static final DeferredItem<Item> SCROLL_FIRE_DRAGON_BULLET = REGISTRY.register("scroll_fire_dragon_bullet", ScrollFireDragonBulletItem::new);
	public static final DeferredItem<Item> SCROLL_GREAT_FLAME = REGISTRY.register("scroll_great_flame", ScrollGreatFlameItem::new);
	public static final DeferredItem<Item> SCROLL_HIDING_IN_MIST = REGISTRY.register("scroll_hiding_in_mist", ScrollHidingInMistItem::new);
	public static final DeferredItem<Item> SCROLL_WATER_CLONE = REGISTRY.register("scroll_water_clone", ScrollWaterCloneItem::new);
	public static final DeferredItem<Item> SCROLL_WATER_PRISON = REGISTRY.register("scroll_water_prison", ScrollWaterPrisonItem::new);
	public static final DeferredItem<Item> SCROLL_WATER_DRAGON_BULLET = REGISTRY.register("scroll_water_dragon_bullet", ScrollWaterDragonBulletItem::new);
	public static final DeferredItem<Item> SCROLL_WATER_BULLET = REGISTRY.register("scroll_water_bullet", ScrollWaterBulletItem::new);
	public static final DeferredItem<Item> SCROLL_SEVERING_WAVE = REGISTRY.register("scroll_severing_wave", ScrollSeveringWaveItem::new);
	public static final DeferredItem<Item> SCROLL_EARTH_DRAGON_BULLET = REGISTRY.register("scroll_earth_dragon_bullet", ScrollEarthDragonBulletItem::new);
	public static final DeferredItem<Item> SCROLL_EARTH_WALL = REGISTRY.register("scroll_earth_wall", ScrollEarthWallItem::new);
	public static final DeferredItem<Item> SCROLL_ROCK_FISTS = REGISTRY.register("scroll_rock_fists", ScrollRockFistsItem::new);
	public static final DeferredItem<Item> SCROLL_EARTH_FLOW_SPEARS = REGISTRY.register("scroll_earth_flow_spears", ScrollEarthFlowSpearsItem::new);
	public static final DeferredItem<Item> SCROLL_EARTH_GOLEM = REGISTRY.register("scroll_earth_golem", ScrollEarthGolemItem::new);
	public static final DeferredItem<Item> SCROLL_GREAT_BREAKTHROUGH = REGISTRY.register("scroll_great_breakthrough", ScrollGreatBreakthroughItem::new);
	public static final DeferredItem<Item> SCROLL_VACUUM_WAVE = REGISTRY.register("scroll_vacuum_wave", ScrollVacuumWaveItem::new);
	public static final DeferredItem<Item> SCROLL_BLADE_OF_WIND = REGISTRY.register("scroll_blade_of_wind", ScrollBladeOfWindItem::new);
	public static final DeferredItem<Item> SCROLL_AIR_BULLETS = REGISTRY.register("scroll_air_bullets", ScrollAirBulletsItem::new);
	public static final DeferredItem<Item> SCROLL_CHAKRA_FLOW = REGISTRY.register("scroll_chakra_flow", ScrollChakraFlowItem::new);
	public static final DeferredItem<Item> SCROLL_RASENSHURIKEN = REGISTRY.register("scroll_rasenshuriken", ScrollRasenshurikenItem::new);
	public static final DeferredItem<Item> SCROLL_BEAST_TRACKING_FANG = REGISTRY.register("scroll_beast_tracking_fang", ScrollBeastTrackingFangItem::new);
	public static final DeferredItem<Item> SCROLL_FALSE_DARKNESS = REGISTRY.register("scroll_false_darkness", ScrollFalseDarknessItem::new);
	public static final DeferredItem<Item> SCROLL_CHIDORI = REGISTRY.register("scroll_chidori", ScrollChidoriItem::new);
	public static final DeferredItem<Item> SCROLL_LIGHTNING_CLONE = REGISTRY.register("scroll_lightning_clone", ScrollLightningCloneItem::new);
	public static final DeferredItem<Item> SCROLL_LIGHTNING_CHAKRA_MODE = REGISTRY.register("scroll_lightning_chakra_mode", ScrollLightningChakraModeItem::new);
	public static final DeferredItem<Item> SCROLL_KIRIN = REGISTRY.register("scroll_kirin", ScrollKirinItem::new);
	// Start of user code block custom items
	// End of user code block custom items
}