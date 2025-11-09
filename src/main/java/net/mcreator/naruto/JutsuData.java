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
	private String type;
	private String nature;
	private int chakraCost;
	private int chargeTime;
	private int cooldown;
	private boolean isToggle;
	private int color;

	public JutsuData(String name, String type, String nature, int chakraCost, int chargeTime, int cooldown, boolean isToggle, int color) {
		this.name = name;
		this.type = type;
		this.nature = nature;
		this.chakraCost = chakraCost;
		this.chargeTime = chargeTime;
		this.cooldown = cooldown;
		this.isToggle = isToggle;
		this.color = color;
	}

	// getters
	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getNature() {
		return nature;
	}

	public int getChakraCost() {
		return chakraCost;
	}

	public int getChargeTime() {
		return chargeTime;
	}

	public int getCooldown() {
		return cooldown;
	}

	public boolean getIsToggle() {
		return isToggle;
	}

	public int getColor() {
		return color;
	}
}