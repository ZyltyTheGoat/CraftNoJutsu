package net.mcreator.naruto.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.naruto.network.NarutoModVariables;
import net.mcreator.naruto.JutsuRegistry;
import net.mcreator.naruto.JutsuData;

public class JutsuPhoenixFlowerProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double chakraCost = 0;
		Entity fireball = null;
		JutsuRegistry.initializeJutsus();
		JutsuData jutsuData = JutsuRegistry.getJutsu("fireball");
		chakraCost = jutsuData.getChakraCost();
		if (entity.getData(NarutoModVariables.PLAYER_VARIABLES).currentChakra - chakraCost >= 0) {
			{
				NarutoModVariables.PlayerVariables _vars = entity.getData(NarutoModVariables.PLAYER_VARIABLES);
				_vars.currentChakra = entity.getData(NarutoModVariables.PLAYER_VARIABLES).currentChakra - chakraCost;
				_vars.syncPlayerVariables(entity);
			}
		}
	}
}