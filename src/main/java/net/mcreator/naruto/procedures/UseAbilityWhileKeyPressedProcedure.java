package net.mcreator.naruto.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.mcreator.naruto.network.NarutoModVariables;
import net.mcreator.naruto.JutsuRegistry;
import net.mcreator.naruto.JutsuData;

import javax.annotation.Nullable;

@EventBusSubscriber
public class UseAbilityWhileKeyPressedProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		double maxChargeTime = 0;
		double chargePercent = 0;
		if (entity.getData(NarutoModVariables.PLAYER_VARIABLES).chargingAbility) {
			JutsuRegistry.initializeJutsus();
			JutsuData jutsuData = JutsuRegistry.getJutsu(entity.getData(NarutoModVariables.PLAYER_VARIABLES).activeJutsu);
			if (jutsuData == null)
				return;
			maxChargeTime = jutsuData.getChargeTime();;
			if (maxChargeTime <= 0) {
				return;
			}
			if (entity.getData(NarutoModVariables.PLAYER_VARIABLES).chargeTime < maxChargeTime) {
				{
					NarutoModVariables.PlayerVariables _vars = entity.getData(NarutoModVariables.PLAYER_VARIABLES);
					_vars.chargeTime = entity.getData(NarutoModVariables.PLAYER_VARIABLES).chargeTime + 1;
					_vars.syncPlayerVariables(entity);
				}
				chargePercent = (entity.getData(NarutoModVariables.PLAYER_VARIABLES).chargeTime * 100) / maxChargeTime;
				if (chargePercent % 5 == 0) {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal(("Charging: " + new java.text.DecimalFormat("##").format(chargePercent) + "%")), true);
				}
			} else {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal("Fully charged!"), true);
			}
		}
	}
}