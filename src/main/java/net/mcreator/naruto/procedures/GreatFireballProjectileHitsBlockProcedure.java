package net.mcreator.naruto.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import java.util.UUID;
import java.util.Comparator;

public class GreatFireballProjectileHitsBlockProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity) {
		if (entity == null || immediatesourceentity == null)
			return;
		boolean entity_found = false;
		double raytrace_distance = 0;
		Entity owner = null;
		BlockDestructionProcedure.execute(world, x, y, z, false, 8);
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.explode")), SoundSource.NEUTRAL, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.explode")), SoundSource.NEUTRAL, 1, 1, false);
			}
		}
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 25, 3, 3, 3, 0);
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.EXPLOSION, x, y, z, 10, 3, 3, 3, 0.1);
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.FLAME, x, y, z, 25, 1, 1, 1, 0.1);
		{
			final Vec3 _center = new Vec3(x, y, z);
			for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList()) {
				if ((!(entityiterator == entity) || !(entityiterator == immediatesourceentity)) && !(entityiterator instanceof TamableAnimal _tamIsTamedBy && entity instanceof LivingEntity _livEnt ? _tamIsTamedBy.isOwnedBy(_livEnt) : false)
						&& !(immediatesourceentity.getPersistentData().getString("ownerUUID")).equals(entityiterator.getStringUUID()) && entityiterator instanceof LivingEntity) {
					entityiterator.igniteForSeconds(5);
					if (!(new Object() {
						Entity entityFromStringUUID(String uuid, Level world) {
							Entity _uuidentity = null;
							if (world instanceof ServerLevel _server) {
								try {
									_uuidentity = _server.getEntity(UUID.fromString(uuid));
								} catch (Exception e) {
								}
							}
							return _uuidentity;
						}
					}.entityFromStringUUID((immediatesourceentity.getPersistentData().getString("ownerUUID")), (Level) world) == null)) {
						entityiterator.hurt(new DamageSource(world.holderOrThrow(DamageTypes.FIREBALL), owner), (float) immediatesourceentity.getPersistentData().getDouble("damage"));
					} else {
						entityiterator.hurt(new DamageSource(world.holderOrThrow(DamageTypes.FIREBALL)), (float) immediatesourceentity.getPersistentData().getDouble("damage"));
					}
				}
			}
		}
		if (!immediatesourceentity.level().isClientSide())
			immediatesourceentity.discard();
	}
}