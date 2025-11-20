package net.mcreator.naruto.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;

import net.mcreator.naruto.network.NarutoModVariables;

public class DamageCalculationProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double base = 0;
		double strengthAmplifier = 0;
		double finalDamage = 0;
		{
			NarutoModVariables.PlayerVariables _vars = entity.getData(NarutoModVariables.PLAYER_VARIABLES);
			_vars.attackDamage = entity.getData(NarutoModVariables.PLAYER_VARIABLES).attackDamage
					* (1 + (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(MobEffects.DAMAGE_BOOST) ? _livEnt.getEffect(MobEffects.DAMAGE_BOOST).getAmplifier() : 0) * 0.3);
			_vars.syncPlayerVariables(entity);
		}
	}
}