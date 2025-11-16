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

	public int getCooldown() {
		return cooldown;
	}

	public int getChargeTime() {
		return chargeTime;
	}

	public boolean getIsToggle() {
		return isToggle;
	}

	public int getColor() {
		return color;
	}

	public String getIconName() {
		return this.nature.toLowerCase() + "_icon";
	}
}