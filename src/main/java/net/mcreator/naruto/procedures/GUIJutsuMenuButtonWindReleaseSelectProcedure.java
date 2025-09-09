package net.mcreator.naruto.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.naruto.network.NarutoModVariables;

public class GUIJutsuMenuButtonWindReleaseSelectProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			NarutoModVariables.PlayerVariables _vars = entity.getData(NarutoModVariables.PLAYER_VARIABLES);
			_vars.jutsuMenuSelectedRelease = "wind";
			_vars.syncPlayerVariables(entity);
		}
	}
}