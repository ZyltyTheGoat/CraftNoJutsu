/*
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.mcreator.naruto as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.mcreator.naruto;

import java.util.Map;
import java.util.LinkedHashMap;

public class JutsuRegistry {
	private static final Map<String, JutsuData> jutsuRegistry = new LinkedHashMap<>();

	// initialize all jutsus
	public static void initializeJutsus() {
		/*
		 * JutsuData parameters:
		 * name - display name of the jutsu
		 * type - jutsu type (NATURE, NINJUTSU, KG, DOJUTSU)
		 * nature - chakra nature (FIRE, WATER, EARTH, LIGHTNING, WIND, YIN, YANG, WOOD, LAVA, BOIL, ICE, MAGNET, EXPLOSION, STORM, DUST, NONE)
		 * chakraCost - chakra cost to use (or per tick for toggleable)
		 * chargeTime - time to charge in ticks before full power (0 for toggleable/instant)
		 * cooldown - cooldown time in ticks (20 ticks = 1 second)
		 * isToggle - true if move is a toggle on/off
		 * color - 0xFFFFFFFF format
		 */
		// === NATURE RELEASES ===
		// Fire Release
		registerJutsu("fireball", new JutsuData("Great Fireball", "NATURE", "FIRE", 30, 60, 100, false, 0x96e03636));
		registerJutsu("phoenix_flower", new JutsuData("Phoenix Flower", "NATURE", "FIRE", 25, 40, 80, false, 0x96e03636));
		registerJutsu("hiding_in_ash", new JutsuData("Hiding in Ash", "NATURE", "FIRE", 25, 0, 100, true, 0x96e03636));
		registerJutsu("fire_dragon_flame_bullet", new JutsuData("Fire Dragon Bullet", "NATURE", "FIRE", 45, 80, 120, false, 0x96e03636));
		registerJutsu("ash_pile_burning", new JutsuData("Ash Pile Burning", "NATURE", "FIRE", 35, 50, 100, false, 0x96e03636));
		registerJutsu("bomb_blast_dance", new JutsuData("Bomb Blast Dance", "NATURE", "FIRE", 60, 100, 160, false, 0x96e03636));
		registerJutsu("great_fire_annihilation", new JutsuData("Great Fire Annihilation", "NATURE", "FIRE", 60, 100, 160, false, 0x96e03636));
		// Water Release
		registerJutsu("water_bullet", new JutsuData("Water Bullet", "NATURE", "WATER", 20, 30, 60, false, 0x96367de0));
		registerJutsu("water_dragon", new JutsuData("Water Dragon Bullet", "NATURE", "WATER", 50, 90, 140, false, 0x96367de0));
		registerJutsu("water_wall", new JutsuData("Water Wall", "NATURE", "WATER", 25, 40, 100, true, 0x96367de0));
		registerJutsu("water_shark", new JutsuData("Water Shark Bullet", "NATURE", "WATER", 35, 50, 100, false, 0x96367de0));
		registerJutsu("water_prison", new JutsuData("Water Prison", "NATURE", "WATER", 40, 60, 120, true, 0x96367de0));
		// Earth Release
		registerJutsu("earth_wall", new JutsuData("Earth Wall", "NATURE", "EARTH", 30, 50, 100, true, 0x96703f14));
		registerJutsu("earth_spear", new JutsuData("Earth Spear", "NATURE", "EARTH", 25, 35, 80, true, 0x96703f14));
		registerJutsu("rock_fist", new JutsuData("Rock Fist", "NATURE", "EARTH", 20, 30, 60, true, 0x96703f14));
		registerJutsu("earth_dragon", new JutsuData("Earth Dragon Bullet", "NATURE", "EARTH", 45, 70, 120, false, 0x96703f14));
		registerJutsu("earth_golem", new JutsuData("Earth Golem", "NATURE", "EARTH", 100, 70, 120, false, 0x96703f14));
		// Lightning Release
		registerJutsu("chidori", new JutsuData("Chidori", "NATURE", "LIGHTNING", 50, 80, 140, false, 0x96d2e036));
		registerJutsu("lightning_blade", new JutsuData("Lightning Blade", "NATURE", "LIGHTNING", 60, 100, 160, false, 0x96d2e036));
		registerJutsu("false_darkness", new JutsuData("False Darkness", "NATURE", "LIGHTNING", 40, 60, 120, false, 0x96d2e036));
		registerJutsu("lightning_beast", new JutsuData("Lightning Beast", "NATURE", "LIGHTNING", 35, 50, 100, false, 0x96d2e036));
		registerJutsu("lighthing_chakra_mode", new JutsuData("Lightning Chakra Mode", "NATURE", "LIGHTNING", 25, 40, 100, true, 0x96d2e036));
		registerJutsu("kirin", new JutsuData("Kirin", "NATURE", "LIGHTNING", 200, 40, 100, false, 0x96d2e036));
		// Wind Release
		registerJutsu("great_breakthrough", new JutsuData("Great Breakthrough", "NATURE", "WIND", 25, 35, 80, false, 0x96FFFFFF));
		registerJutsu("wind_blade", new JutsuData("Wind Blade", "NATURE", "WIND", 30, 45, 100, false, 0x96FFFFFF));
		registerJutsu("vacuum_sphere", new JutsuData("Vacuum Sphere", "NATURE", "WIND", 35, 50, 100, false, 0x96FFFFFF));
		registerJutsu("rasenshuriken", new JutsuData("Rasenshuriken", "NATURE", "WIND", 80, 120, 200, false, 0x96FFFFFF));
		registerJutsu("wind_cutter", new JutsuData("Wind Cutter", "NATURE", "WIND", 20, 30, 60, false, 0x96FFFFFF));
	}

	// register a jutsu
	public static void registerJutsu(String id, JutsuData jutsu) {
		jutsuRegistry.put(id, jutsu);
	}

	// get jutsu by id
	public static JutsuData getJutsu(String id) {
		return jutsuRegistry.get(id);
	}

	// check if jutsu exists
	public static boolean jutsuExists(String id) {
		return jutsuRegistry.containsKey(id);
	}

	// get all jutsu of nature
	public static Map<String, JutsuData> getJutsusByNature(String nature) {
		Map<String, JutsuData> filtered = new LinkedHashMap<>();
		for (Map.Entry<String, JutsuData> entry : jutsuRegistry.entrySet()) {
			if (entry.getValue().getNature().equalsIgnoreCase(nature)) {
				filtered.put(entry.getKey(), entry.getValue());
			}
		}
		return filtered;
	}

	// get all jutsu of type
	public static Map<String, JutsuData> getJutsusByType(String type) {
		Map<String, JutsuData> filtered = new LinkedHashMap<>();
		for (Map.Entry<String, JutsuData> entry : jutsuRegistry.entrySet()) {
			if (entry.getValue().getType().equalsIgnoreCase(type)) {
				filtered.put(entry.getKey(), entry.getValue());
			}
		}
		return filtered;
	}

	// get all jutsu ids
	public static String[] getAllJutsuIds() {
		return jutsuRegistry.keySet().toArray(new String[0]);
	}
}