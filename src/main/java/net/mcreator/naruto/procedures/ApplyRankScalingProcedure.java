package net.mcreator.naruto.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.mcreator.naruto.network.NarutoModVariables;

import javax.annotation.Nullable;

@EventBusSubscriber
public class ApplyRankScalingProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (world.dayTime() % 20 == 0) {
			if ((entity.getData(NarutoModVariables.PLAYER_VARIABLES).rank).equals("Genin")) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, -1, 1, true, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, -1, 1, true, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, 0, true, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, -1, 2, true, false));
				if (entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(Attributes.ARMOR))
					_livingEntity5.getAttribute(Attributes.ARMOR).setBaseValue(2);
				if (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Attributes.ARMOR_TOUGHNESS))
					_livingEntity6.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(1);
				if (entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(Attributes.KNOCKBACK_RESISTANCE))
					_livingEntity7.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.15);
				{
					NarutoModVariables.PlayerVariables _vars = entity.getData(NarutoModVariables.PLAYER_VARIABLES);
					_vars.maxChakra = 200;
					_vars.syncPlayerVariables(entity);
				}
			}
			if ((entity.getData(NarutoModVariables.PLAYER_VARIABLES).rank).equals("Chunin")) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, -1, 3, true, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, -1, 4, true, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, 1, true, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, -1, 5, true, false));
				if (entity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(Attributes.ARMOR))
					_livingEntity12.getAttribute(Attributes.ARMOR).setBaseValue(5);
				if (entity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(Attributes.ARMOR_TOUGHNESS))
					_livingEntity13.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(2);
				if (entity instanceof LivingEntity _livingEntity14 && _livingEntity14.getAttributes().hasAttribute(Attributes.KNOCKBACK_RESISTANCE))
					_livingEntity14.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.3);
				{
					NarutoModVariables.PlayerVariables _vars = entity.getData(NarutoModVariables.PLAYER_VARIABLES);
					_vars.maxChakra = 600;
					_vars.syncPlayerVariables(entity);
				}
			}
			if ((entity.getData(NarutoModVariables.PLAYER_VARIABLES).rank).equals("Jonin")) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, -1, 10, true, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, -1, 6, true, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, 2, true, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, -1, 8, true, false));
				if (entity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(Attributes.ARMOR))
					_livingEntity19.getAttribute(Attributes.ARMOR).setBaseValue(17);
				if (entity instanceof LivingEntity _livingEntity20 && _livingEntity20.getAttributes().hasAttribute(Attributes.ARMOR_TOUGHNESS))
					_livingEntity20.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(6);
				if (entity instanceof LivingEntity _livingEntity21 && _livingEntity21.getAttributes().hasAttribute(Attributes.KNOCKBACK_RESISTANCE))
					_livingEntity21.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.6);
				{
					NarutoModVariables.PlayerVariables _vars = entity.getData(NarutoModVariables.PLAYER_VARIABLES);
					_vars.maxChakra = 1000;
					_vars.syncPlayerVariables(entity);
				}
			}
			if ((entity.getData(NarutoModVariables.PLAYER_VARIABLES).rank).equals("Kage")) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, -1, 20, true, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, -1, 9, true, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, 2, true, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, -1, 10, true, false));
				if (entity instanceof LivingEntity _livingEntity26 && _livingEntity26.getAttributes().hasAttribute(Attributes.ARMOR))
					_livingEntity26.getAttribute(Attributes.ARMOR).setBaseValue(30);
				if (entity instanceof LivingEntity _livingEntity27 && _livingEntity27.getAttributes().hasAttribute(Attributes.ARMOR_TOUGHNESS))
					_livingEntity27.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(12);
				if (entity instanceof LivingEntity _livingEntity28 && _livingEntity28.getAttributes().hasAttribute(Attributes.KNOCKBACK_RESISTANCE))
					_livingEntity28.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(2.5);
				{
					NarutoModVariables.PlayerVariables _vars = entity.getData(NarutoModVariables.PLAYER_VARIABLES);
					_vars.maxChakra = 2000;
					_vars.syncPlayerVariables(entity);
				}
			}
			if ((entity.getData(NarutoModVariables.PLAYER_VARIABLES).rank).equals("Legend")) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, -1, 29, true, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, -1, 9, true, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, 3, true, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, -1, 11, true, false));
				if (entity instanceof LivingEntity _livingEntity33 && _livingEntity33.getAttributes().hasAttribute(Attributes.ARMOR))
					_livingEntity33.getAttribute(Attributes.ARMOR).setBaseValue(30);
				if (entity instanceof LivingEntity _livingEntity34 && _livingEntity34.getAttributes().hasAttribute(Attributes.ARMOR_TOUGHNESS))
					_livingEntity34.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(15);
				if (entity instanceof LivingEntity _livingEntity35 && _livingEntity35.getAttributes().hasAttribute(Attributes.KNOCKBACK_RESISTANCE))
					_livingEntity35.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(2.9);
				{
					NarutoModVariables.PlayerVariables _vars = entity.getData(NarutoModVariables.PLAYER_VARIABLES);
					_vars.maxChakra = 4000;
					_vars.syncPlayerVariables(entity);
				}
			}
		}
	}
}