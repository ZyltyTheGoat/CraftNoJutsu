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
import java.util.HashMap;

public class JutsuRegistry {
	private static final Map<String, JutsuData> jutsuRegistry = new HashMap<>();

	// initialize all jutsus
	public static void initializeJutsus() {
		/*
		 * JutsuData parameters:
		 * name - display name of the jutsu
		 * cost - chakra cost to use
		 * cooldown - cooldown time in ticks (20 ticks = 1 second)
		 * chargeTime - time to charge in ticks before activation
		 * description - jutsu description text
		 * nature - chakra nature (Fire, Water, Earth, Lightning, Wind, None)
		 * rank - jutsu rank (E, D, C, B, A, S)
		 */
		registerJutsu("fireball", new JutsuData("Fire Release: Great Fireball Technique", 30, 5000, 60, "A classic fire release technique that creates a large fireball", "Fire", "C"));
		registerJutsu("phoenix_flower", new JutsuData("Fire Release: Phoenix Sage Fire Technique", 25, 4000, 40, "Multiple small fireballs that can change direction", "Fire", "C"));
		registerJutsu("water_bullet", new JutsuData("Water Release: Water Bullet Technique", 20, 3000, 30, "Fires a concentrated water bullet at the target", "Water", "C"));
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

	// get all jutsu ids
	public static String[] getAllJutsuIds() {
		return jutsuRegistry.keySet().toArray(new String[0]);
	}
}