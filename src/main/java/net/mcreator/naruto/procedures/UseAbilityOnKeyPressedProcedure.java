package net.mcreator.naruto.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.naruto.network.NarutoModVariables;

public class UseAbilityOnKeyPressedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			NarutoModVariables.PlayerVariables _vars = entity.getData(NarutoModVariables.PLAYER_VARIABLES);
			_vars.chargeTime = 0;
			_vars.syncPlayerVariables(entity);
		}
		{
			NarutoModVariables.PlayerVariables _vars = entity.getData(NarutoModVariables.PLAYER_VARIABLES);
			_vars.chargingAbility = true;
			_vars.syncPlayerVariables(entity);
		}
	}
}