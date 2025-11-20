/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.naruto.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.registries.Registries;

import net.mcreator.naruto.entity.PhoenixFlowerEntity;
import net.mcreator.naruto.entity.GreatFireballEntity;
import net.mcreator.naruto.entity.DummyEntity;
import net.mcreator.naruto.NarutoMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class NarutoModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, NarutoMod.MODID);
	public static final DeferredHolder<EntityType<?>, EntityType<DummyEntity>> DUMMY = register("dummy",
			EntityType.Builder.<DummyEntity>of(DummyEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.ridingOffset(-0.6f).sized(0.6f, 1.8f));
	public static final DeferredHolder<EntityType<?>, EntityType<PhoenixFlowerEntity>> PHOENIX_FLOWER = register("phoenix_flower",
			EntityType.Builder.<PhoenixFlowerEntity>of(PhoenixFlowerEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final DeferredHolder<EntityType<?>, EntityType<GreatFireballEntity>> GREAT_FIREBALL = register("great_fireball",
			EntityType.Builder.<GreatFireballEntity>of(GreatFireballEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(3f, 3f));

	// Start of user code block custom entities
	// End of user code block custom entities
	private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent
	public static void init(RegisterSpawnPlacementsEvent event) {
		DummyEntity.init(event);
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(DUMMY.get(), DummyEntity.createAttributes().build());
	}
}