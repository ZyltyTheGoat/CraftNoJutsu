package net.mcreator.naruto.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.mcreator.naruto.network.NarutoModVariables;
import net.mcreator.naruto.JutsuRegistry;
import net.mcreator.naruto.JutsuData;

public class UseAbilityOnKeyPressedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double chakraCost = 0;
		JutsuRegistry.initializeJutsus();
		JutsuData jutsuData = JutsuRegistry.getJutsu(entity.getData(NarutoModVariables.PLAYER_VARIABLES).activeJutsu);
		if (jutsuData == null)
			return;
		chakraCost = jutsuData.getChakraCost();
		if (entity.getData(NarutoModVariables.PLAYER_VARIABLES).currentChakra - chakraCost >= 0) {
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
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("Insufficient chakra"), true);
		}
	}
}