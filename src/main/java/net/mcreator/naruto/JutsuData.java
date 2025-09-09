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

public class JutsuData {
	private String name;
	private int cost;
	private int cooldown;
	private int chargeTime;
	private String description;
	private String nature;
	private String rank;

	public JutsuData(String name, int cost, int cooldown, int chargeTime, String description, String nature, String rank) {
		this.name = name;
		this.cost = cost;
		this.cooldown = cooldown;
		this.chargeTime = chargeTime;
		this.description = description;
		this.nature = nature;
		this.rank = rank;
	}

	// getters
	public String getName() {
		return name;
	}

	public int getCost() {
		return cost;
	}

	public int getCooldown() {
		return cooldown;
	}

	public int getChargeTime() {
		return chargeTime;
	}

	public String getDescription() {
		return description;
	}

	public String getNature() {
		return nature;
	}

	public String getRank() {
		return rank;
	}
}