package net.mcreator.naruto.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;

import net.mcreator.naruto.init.NarutoModParticleTypes;

public class GreatFireballOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
		if (immediatesourceentity == null)
			return;
		if (immediatesourceentity.getPersistentData().getDouble("life") <= 50) {
			immediatesourceentity.getPersistentData().putDouble("life", (immediatesourceentity.getPersistentData().getDouble("life") + 1));
			immediatesourceentity
					.setDeltaMovement(new Vec3((immediatesourceentity.getPersistentData().getDouble("motionX")), (immediatesourceentity.getPersistentData().getDouble("motionY")), (immediatesourceentity.getPersistentData().getDouble("motionZ"))));
			if (world.dayTime() % 7 == 0 || immediatesourceentity.getPersistentData().getDouble("life") == 1) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.FLAME, x, y, z, 35, 1, 1, 1, 0);
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 5, 1, 1, 1, 0);
				if (world instanceof ServerLevel serverLevel) {
					for (ServerPlayer player : serverLevel.players()) {
						if (player.distanceToSqr(immediatesourceentity) < 10000) {
							player.connection.send(
									new net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket((SimpleParticleType) NarutoModParticleTypes.FIREBALL_PARTICLE.get(), true, immediatesourceentity.getX(), immediatesourceentity.getY() + 1,
											immediatesourceentity.getZ(), (float) immediatesourceentity.getDeltaMovement().x(), (float) immediatesourceentity.getDeltaMovement().y(), (float) immediatesourceentity.getDeltaMovement().z(), 1.0f, 0));
						}
					}
				}
			}
		} else {
			if (!immediatesourceentity.level().isClientSide())
				immediatesourceentity.discard();
		}
	}
}