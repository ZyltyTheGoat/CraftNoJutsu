package net.mcreator.naruto.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

public class DashProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double power = 0;
		double yaw = 0;
		double pitch = 0;
		double dirX = 0;
		double dirY = 0;
		double dirZ = 0;
		double normalized = 0;
		power = 3;
		yaw = entity.getYRot();
		pitch = entity.getXRot();
		dirX = Math.sin(yaw * (Math.PI / 180)) * (-1) * Math.cos(pitch * (Math.PI / 180));
		dirY = Math.sin(pitch * (Math.PI / 180)) * (-1);
		dirZ = Math.cos(yaw * (Math.PI / 180)) * Math.cos(pitch * (Math.PI / 180));
		normalized = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
		dirX = dirX * (power / normalized);
		dirY = dirY * (power / normalized);
		dirZ = dirZ * (power / normalized);
		entity.setDeltaMovement(new Vec3(dirX, dirY, dirZ));
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.CLOUD, x, y, z, 10, 0.1, 0.1, 0.1, 0.25);
	}
}