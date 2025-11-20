package net.mcreator.naruto.procedures;

import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.network.chat.Component;

import net.mcreator.naruto.entity.DummyEntity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class DummyHitProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(Entity entity, Entity sourceentity, double amount) {
		execute(null, entity, sourceentity, amount);
	}

	private static void execute(@Nullable LivingIncomingDamageEvent event, Entity entity, Entity sourceentity, double amount) {
		if (entity == null || sourceentity == null)
			return;
		if (entity instanceof DummyEntity) {
			double finalDamage = amount;
			// Check if dummy has resistance effect
			if (entity instanceof LivingEntity livingEntity) {
				if (livingEntity.hasEffect(MobEffects.DAMAGE_RESISTANCE)) {
					int resistanceLevel = livingEntity.getEffect(MobEffects.DAMAGE_RESISTANCE).getAmplifier() + 1;
					// Each level of resistance reduces damage by 20%
					// Resistance reduction formula: damage * (1 - (0.20 * level))
					double resistanceReduction = 1.0 - (0.20 * resistanceLevel);
					// Cap at 0 to prevent negative damage
					resistanceReduction = Math.max(0.0, resistanceReduction);
					finalDamage = amount * resistanceReduction;
					// Display with resistance info
					if (sourceentity instanceof Player _player && !_player.level().isClientSide()) {
						_player.displayClientMessage(Component.literal(String.format("%.2f (Base: %.2f, Resistance %d: -%.0f%%)", finalDamage, amount, resistanceLevel, (resistanceLevel * 20.0))), true);
					}
				} else {
					// No resistance effect
					if (sourceentity instanceof Player _player && !_player.level().isClientSide()) {
						_player.displayClientMessage(Component.literal(String.format("%.2f", finalDamage)), true);
					}
				}
			}
			if (event != null) {
				event.setCanceled(true);
			}
		}
	}
}