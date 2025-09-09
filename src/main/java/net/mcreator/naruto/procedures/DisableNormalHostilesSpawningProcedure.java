package net.mcreator.naruto.procedures;

import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class DisableNormalHostilesSpawningProcedure {
	@SubscribeEvent
	public static void onEntitySpawned(EntityJoinLevelEvent event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable EntityJoinLevelEvent event, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Zombie || entity instanceof ZombieHorse || entity instanceof ZombieVillager || entity instanceof Husk || entity instanceof Skeleton || entity instanceof SkeletonHorse || entity instanceof Creeper
				|| entity instanceof CaveSpider || entity instanceof Spider || entity instanceof EnderMan) {
			if (event != null) {
				event.setCanceled(true);
			}
		}
	}
}