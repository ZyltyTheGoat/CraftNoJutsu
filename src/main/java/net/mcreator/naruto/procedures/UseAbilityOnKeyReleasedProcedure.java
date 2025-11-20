package net.mcreator.naruto.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.mcreator.naruto.network.NarutoModVariables;

public class UseAbilityOnKeyReleasedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity.getData(NarutoModVariables.PLAYER_VARIABLES).chargingAbility) {
			{
				NarutoModVariables.PlayerVariables _vars = entity.getData(NarutoModVariables.PLAYER_VARIABLES);
				_vars.chargingAbility = false;
				_vars.syncPlayerVariables(entity);
			}
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal(""), true);
			if ((entity.getData(NarutoModVariables.PLAYER_VARIABLES).activeJutsu).equals("fireball")) {
				JutsuGreatFireballProcedure.execute(world, x, y, z, entity);
			}
		}
	}
}