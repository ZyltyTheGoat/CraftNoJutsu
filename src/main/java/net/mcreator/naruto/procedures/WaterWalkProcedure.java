package net.mcreator.naruto.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

@EventBusSubscriber
public class WaterWalkProcedure {
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
		if ((world.getBlockState(BlockPos.containing(x, y - 0.1, z))).getBlock() instanceof LiquidBlock) {
			if ((entity.isInWater() || entity.isInLava()) && !entity.isSwimming() && !entity.isShiftKeyDown()) {
				entity.setDeltaMovement(new Vec3((entity.getDeltaMovement().x()), 0.1D, (entity.getDeltaMovement().z())));
			} else if (entity.getDeltaMovement().y() < 0 && !entity.isSwimming() && !entity.isShiftKeyDown()) {
				entity.setDeltaMovement(new Vec3((entity.getDeltaMovement().x()), 0.01D, (entity.getDeltaMovement().z())));
			}
			entity.setOnGround(true);
		}
	}
}