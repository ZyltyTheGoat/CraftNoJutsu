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
		registerJutsu("fireball", new JutsuData("Great Fireball", "NATURE", "FIRE", 30, 60, 100, false, 0x96bc0712));
		registerJutsu("phoenix_flower", new JutsuData("Phoenix Flower", "NATURE", "FIRE", 25, 40, 80, false, 0x96bc0712));
		registerJutsu("hiding_in_ash", new JutsuData("Hiding in Ash", "NATURE", "FIRE", 25, 0, 100, true, 0x96bc0712));
		registerJutsu("fire_dragon_bullet", new JutsuData("Fire Dragon Bullet", "NATURE", "FIRE", 45, 80, 120, false, 0x96bc0712));
		registerJutsu("great_flame", new JutsuData("Great Flame", "NATURE", "FIRE", 60, 100, 160, false, 0x96bc0712));
		registerJutsu("great_fire_annihilation", new JutsuData("Great Fire Annihilation", "NATURE", "FIRE", 60, 100, 160, false, 0x96bc0712));
		// Water Release
		registerJutsu("water_bullet", new JutsuData("Water Bullet", "NATURE", "WATER", 20, 30, 60, false, 0x960e85fe));
		registerJutsu("water_dragon_bullet", new JutsuData("Water Dragon Bullet", "NATURE", "WATER", 50, 90, 140, false, 0x960e85fe));
		registerJutsu("hiding_in_mist", new JutsuData("Hiding in Mist", "NATURE", "WATER", 25, 40, 100, true, 0x960e85fe));
		registerJutsu("water_prison", new JutsuData("Water Prison", "NATURE", "WATER", 35, 50, 100, false, 0x960e85fe));
		registerJutsu("water_clone", new JutsuData("Water Clone", "NATURE", "WATER", 40, 60, 120, true, 0x960e85fe));
		registerJutsu("severing_wave", new JutsuData("Severing Wave", "NATURE", "WATER", 40, 60, 120, true, 0x960e85fe));
		// Earth Release
		registerJutsu("earth_wall", new JutsuData("Earth Wall", "NATURE", "EARTH", 30, 50, 100, true, 0x967c5013));
		registerJutsu("earth_flow_spears", new JutsuData("Earth Spear", "NATURE", "EARTH", 25, 35, 80, true, 0x967c5013));
		registerJutsu("rock_fists", new JutsuData("Rock Fists", "NATURE", "EARTH", 20, 30, 60, true, 0x967c5013));
		registerJutsu("earth_dragon_bullet", new JutsuData("Earth Dragon Bullet", "NATURE", "EARTH", 45, 70, 120, false, 0x967c5013));
		registerJutsu("earth_golem", new JutsuData("Earth Golem", "NATURE", "EARTH", 100, 70, 120, false, 0x967c5013));
		// Lightning Release
		registerJutsu("chidori", new JutsuData("Chidori", "NATURE", "LIGHTNING", 50, 80, 140, false, 0x96d3c62b));
		registerJutsu("lightning_clone", new JutsuData("Lightning Clone", "NATURE", "LIGHTNING", 60, 100, 160, false, 0x96d3c62b));
		registerJutsu("false_darkness", new JutsuData("False Darkness", "NATURE", "LIGHTNING", 40, 60, 120, false, 0x96d3c62b));
		registerJutsu("beast_tracking_fang", new JutsuData("Beast Tracking Fang", "NATURE", "LIGHTNING", 35, 50, 100, false, 0x96d3c62b));
		registerJutsu("lighthing_chakra_mode", new JutsuData("Lightning Chakra Mode", "NATURE", "LIGHTNING", 25, 40, 100, true, 0x96d3c62b));
		registerJutsu("kirin", new JutsuData("Kirin", "NATURE", "LIGHTNING", 200, 40, 100, false, 0x96d3c62b));
		// Wind Release
		registerJutsu("great_breakthrough", new JutsuData("Great Breakthrough", "NATURE", "WIND", 25, 35, 80, false, 0x961cb878));
		registerJutsu("air_bullets", new JutsuData("Air Bullets", "NATURE", "WIND", 30, 45, 100, false, 0x961cb878));
		registerJutsu("vacuum_wave", new JutsuData("Vacuum Wave", "NATURE", "WIND", 35, 50, 100, false, 0x961cb878));
		registerJutsu("rasenshuriken", new JutsuData("Rasenshuriken", "NATURE", "WIND", 80, 120, 200, false, 0x961cb878));
		registerJutsu("chakra_flow", new JutsuData("Chakra Flow", "NATURE", "WIND", 20, 30, 60, false, 0x961cb878));
		registerJutsu("wind_blade", new JutsuData("Blade of Wind", "NATURE", "WIND", 20, 30, 60, false, 0x961cb878));
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