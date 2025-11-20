package net.mcreator.naruto.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.mcreator.naruto.init.NarutoModGameRules;

public class BlockDestructionProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, boolean particle, double range) {
		double size = 0;
		double xStart = 0;
		double yStart = 0;
		double zStart = 0;
		double xPos = 0;
		double yPos = 0;
		double zPos = 0;
		double dx = 0;
		double dy = 0;
		double dz = 0;
		double distance = 0;
		if (!world.getLevelData().getGameRules().getBoolean(NarutoModGameRules.CRAFT_NO_JUTSU_ENABLE_BLOCK_BREAKING)) {
			return;
		}
		xStart = Math.round(x - Math.floor(range * 0.5D));
		yStart = Math.round(y - Math.floor(range * 0.5D));
		zStart = Math.round(z - Math.floor(range * 0.5D));
		for (int index0 = 0; index0 < (int) range; index0++) {
			for (int index1 = 0; index1 < (int) range; index1++) {
				for (int index2 = 0; index2 < (int) range; index2++) {
					xPos = xStart + index0;
					yPos = yStart + index1;
					zPos = zStart + index2;
					if ((world.getBlockState(BlockPos.containing(xPos, yPos, zPos))).getBlock() == Blocks.AIR) {
						continue;
					}
					dx = xPos - x;
					dy = yPos - y;
					dz = zPos - z;
					distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2) + Math.pow(dz, 2));
					if (distance > range * 0.5) {
						continue;
					}
					if (world.getBlockState(BlockPos.containing(xPos, yPos, zPos)).getDestroySpeed(world, BlockPos.containing(xPos, yPos, zPos)) <= 5) {
						if (particle) {
							world.destroyBlock(BlockPos.containing(xPos, yPos, zPos), false);
						} else {
							world.setBlock(BlockPos.containing(xPos, yPos, zPos), Blocks.AIR.defaultBlockState(), 3);
						}
					}
				}
			}
		}
	}
}