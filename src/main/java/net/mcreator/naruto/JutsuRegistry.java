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
		//
		// ===== NATURE RELEASES =====
		//
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
		//
		// ===== DOJUTSU ======
		//
		// == Sharingan ==
		registerJutsu("sharingan_activate", new JutsuData("Activate", "DOJUTSU", "SHARINGAN", 20, 30, 60, false, 0x96b30000));
		registerJutsu("genjutsu", new JutsuData("Genjutsu", "DOJUTSU", "SHARINGAN", 20, 30, 60, false, 0x96b30000));
		// Itachi Sharingan
		registerJutsu("sharingan_itachi_activate", new JutsuData("Activate", "DOJUTSU", "SHARINGAN_ITACHI", 20, 30, 60, false, 0x96b30000));
		registerJutsu("tsukuyomi", new JutsuData("Tsukuyomi", "DOJUTSU", "SHARINGAN_ITACHI", 20, 30, 60, false, 0x96b30000));
		registerJutsu("amaterasu_itachi", new JutsuData("Amaterasu", "DOJUTSU", "SHARINGAN_ITACHI", 20, 30, 60, false, 0x96b30000));
		registerJutsu("susanoo_itachi", new JutsuData("Susanoo", "DOJUTSU", "SHARINGAN_ITACHI", 20, 30, 60, false, 0x96b30000));
		// Kamui Sharingan
		registerJutsu("sharingan_kamui_activate", new JutsuData("Activate", "DOJUTSU", "SHARINGAN_KAMUI", 20, 30, 60, false, 0x96b30000));
		registerJutsu("kamui_short", new JutsuData("Kamui - Short Range", "DOJUTSU", "SHARINGAN_KAMUI", 20, 30, 60, false, 0x96b30000));
		registerJutsu("kamui_long", new JutsuData("Kamui - Long Range", "DOJUTSU", "SHARINGAN_KAMUI", 20, 30, 60, false, 0x96b30000));
		registerJutsu("kamui_intagibility", new JutsuData("Intagibility", "DOJUTSU", "SHARINGAN_KAMUI", 20, 30, 60, false, 0x96b30000));
		registerJutsu("susanoo_kamui", new JutsuData("Susanoo", "DOJUTSU", "SHARINGAN_KAMUI", 20, 30, 60, false, 0x96b30000));
		// Sasuke Sharingan
		registerJutsu("sharingan_sasuke_activate", new JutsuData("Activate", "DOJUTSU", "SHARINGAN_SASUKE", 20, 30, 60, false, 0x96b30000));
		registerJutsu("amaterasu_sasuke", new JutsuData("Amaterasu", "DOJUTSU", "SHARINGAN_SASUKE", 20, 30, 60, false, 0x96b30000));
		registerJutsu("kagutsuchi", new JutsuData("Kagutsuchi", "DOJUTSU", "SHARINGAN_SASUKE", 20, 30, 60, false, 0x96b30000));
		registerJutsu("susanoo_sasuke", new JutsuData("Susanoo", "DOJUTSU", "SHARINGAN_SASUKE", 20, 30, 60, false, 0x96b30000));
		// Kotoamatsukami Sharingan
		registerJutsu("sharingan_koto_activate", new JutsuData("Activate", "DOJUTSU", "SHARINGAN_KOTOAMATSUKAMI", 20, 30, 60, false, 0x96b30000));
		registerJutsu("kotoamatsukami", new JutsuData("Kotoamatsukami", "DOJUTSU", "SHARINGAN_KOTOAMATSUKAMI", 20, 30, 60, false, 0x96b30000));
		registerJutsu("susanoo_kotoamatsukami", new JutsuData("Susanoo", "DOJUTSU", "SHARINGAN_KOTOAMATSUKAMI", 20, 30, 60, false, 0x96b30000));
		// Ōhirume Sharingan
		registerJutsu("sharingan_ohirume_activate", new JutsuData("Activate", "DOJUTSU", "SHARINGAN_OHIRUME", 20, 30, 60, false, 0x96b30000));
		registerJutsu("ohirume", new JutsuData("Ōhirume", "DOJUTSU", "SHARINGAN_OHIRUME", 20, 30, 60, false, 0x96b30000));
		registerJutsu("susanoo_ohirume", new JutsuData("Susanoo", "DOJUTSU", "SHARINGAN_OHIRUME", 20, 30, 60, false, 0x96b30000));
		// == Byakugan ==
		registerJutsu("byakugan_activate", new JutsuData("Activate", "DOJUTSU", "BYAKUGAN", 20, 30, 60, false, 0x96FFFFFF));
		registerJutsu("enhanced_vision", new JutsuData("Enhanced Vision", "DOJUTSU", "BYAKUGAN", 20, 30, 60, false, 0x96FFFFFF));
		registerJutsu("eight_trigrams_vacuum_palm", new JutsuData("8 Trigrams Vacuum Palm", "DOJUTSU", "BYAKUGAN", 20, 30, 60, false, 0x96FFFFFF));
		registerJutsu("eight_trigrams_sixty_four_lms", new JutsuData("8 Trigrams 64 Palms", "DOJUTSU", "BYAKUGAN", 20, 30, 60, false, 0x96FFFFFF));
		registerJutsu("eight_trigrams_revolving_heaven", new JutsuData("8 Trigrams Revolving Heaven", "DOJUTSU", "BYAKUGAN", 20, 30, 60, false, 0x96FFFFFF));
		// == Rinnegan ==
		registerJutsu("rinnegan_activate", new JutsuData("Activate", "DOJUTSU", "RINNEGAN", 20, 30, 60, false, 0x9690889e));
		registerJutsu("rinnegan_almighty_push_pull", new JutsuData("Almighty Push/Pull", "DOJUTSU", "RINNEGAN", 20, 30, 60, false, 0x9690889e));
		registerJutsu("planetary_devestation", new JutsuData("Planetary Devestation", "DOJUTSU", "RINNEGAN", 20, 30, 60, false, 0x9690889e));
		registerJutsu("king_of_hell", new JutsuData("King of Hell", "DOJUTSU", "RINNEGAN", 20, 30, 60, false, 0x9690889e));
		registerJutsu("flaming_arrow_warhead", new JutsuData("Flaming Arrow Warhead", "DOJUTSU", "RINNEGAN", 20, 30, 60, false, 0x9690889e));
		registerJutsu("soul_extraction", new JutsuData("Soul Extraction", "DOJUTSU", "RINNEGAN", 20, 30, 60, false, 0x9690889e));
		registerJutsu("summon_multi_headed_dog", new JutsuData("Multi-Headed Dog", "DOJUTSU", "RINNEGAN", 20, 30, 60, false, 0x9690889e));
		registerJutsu("summon_rhino", new JutsuData("Rhino", "DOJUTSU", "RINNEGAN", 20, 30, 60, false, 0x9690889e));
		registerJutsu("summon_centipede", new JutsuData("Centipede", "DOJUTSU", "RINNEGAN", 20, 30, 60, false, 0x9690889e));
		registerJutsu("demonic_statue_of_the_outer_path", new JutsuData("Demonic Statue", "DOJUTSU", "RINNEGAN", 20, 30, 60, false, 0x9690889e));
		// == Tenseigan ==
		registerJutsu("tenseigan_activate", new JutsuData("Activate", "DOJUTSU", "TENSEIGAN", 20, 30, 60, false, 0x9634758f));
		registerJutsu("tenseigan_almighty_push_pull", new JutsuData("Almighty Push/Pull", "DOJUTSU", "TENSEIGAN", 20, 30, 60, false, 0x9634758f));
		registerJutsu("tenseigan_chakra_mode", new JutsuData(" Tenseigan Chakra Mode", "DOJUTSU", "TENSEIGAN", 20, 30, 60, false, 0x9634758f));
		registerJutsu("summon_truth_seeking_balls", new JutsuData("Truth-Seeking Balls", "DOJUTSU", "TENSEIGAN", 20, 30, 60, false, 0x9634758f));
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