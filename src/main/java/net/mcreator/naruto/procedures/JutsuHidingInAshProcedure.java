package net.mcreator.naruto.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;

import net.mcreator.naruto.network.NarutoModVariables;
import net.mcreator.naruto.init.NarutoModParticleTypes;
import net.mcreator.naruto.JutsuRegistry;
import net.mcreator.naruto.JutsuData;

import javax.annotation.Nullable;

import java.util.Comparator;

@EventBusSubscriber
public class JutsuHidingInAshProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double chakraCost = 0;
		double raytrace_distance = 0;
		boolean entity_found = false;
		if (entity.getData(NarutoModVariables.PLAYER_VARIABLES).activeToggleJutsu.contains("hiding_in_ash")) {
			JutsuRegistry.initializeJutsus();
			JutsuData jutsuData = JutsuRegistry.getJutsu("hiding_in_ash");
			chakraCost = jutsuData.getChakraCost();
			if (entity.getData(NarutoModVariables.PLAYER_VARIABLES).currentChakra - chakraCost >= 0 && !entity.isInWaterRainOrBubble()) {
				{
					NarutoModVariables.PlayerVariables _vars = entity.getData(NarutoModVariables.PLAYER_VARIABLES);
					_vars.currentChakra = entity.getData(NarutoModVariables.PLAYER_VARIABLES).currentChakra - chakraCost;
					_vars.syncPlayerVariables(entity);
				}
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.LARGE_SMOKE, x, y, z, 8, 2, 2, 2, 0);
				if (world instanceof ServerLevel _level)
					_level.sendParticles((SimpleParticleType) (NarutoModParticleTypes.BIG_SMOKE.get()), x, y, z, 8, 2, 2, 2, 0);
				{
					final Vec3 _center = new Vec3(x, y, z);
					for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList()) {
						if (!(entityiterator == entity) && !(entityiterator instanceof TamableAnimal _tamIsTamedBy && entity instanceof LivingEntity _livEnt ? _tamIsTamedBy.isOwnedBy(_livEnt) : false) && entityiterator instanceof LivingEntity) {
							entityiterator.igniteForSeconds(5);
						}
					}
				}
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 20, 0, true, false));
			} else {
				NarutoModVariables.PlayerVariables _vars = entity.getData(NarutoModVariables.PLAYER_VARIABLES);
				_vars.activeToggleJutsu = entity.getData(NarutoModVariables.PLAYER_VARIABLES).activeToggleJutsu.replace("hiding_in_ash,", "").replace(",hiding_in_ash", "").replace("hiding_in_ash", "");
				_vars.syncPlayerVariables(entity);
				if (entity instanceof LivingEntity _entity)
					_entity.removeEffect(MobEffects.INVISIBILITY);
			}
		}
	}
}