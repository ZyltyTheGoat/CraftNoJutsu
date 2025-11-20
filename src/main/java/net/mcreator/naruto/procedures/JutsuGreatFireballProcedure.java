package net.mcreator.naruto.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.registries.Registries;

import net.mcreator.naruto.network.NarutoModVariables;
import net.mcreator.naruto.init.NarutoModEntities;
import net.mcreator.naruto.entity.GreatFireballEntity;
import net.mcreator.naruto.JutsuRegistry;
import net.mcreator.naruto.JutsuData;

import java.util.Comparator;

public class JutsuGreatFireballProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity fireball = null;
		double chakraCost = 0;
		double maxChargeTime = 0;
		JutsuRegistry.initializeJutsus();
		JutsuData jutsuData = JutsuRegistry.getJutsu("fireball");
		chakraCost = jutsuData.getChakraCost();
		maxChargeTime = jutsuData.getChargeTime();
		if (entity.getData(NarutoModVariables.PLAYER_VARIABLES).currentChakra - chakraCost >= 0) {
			{
				NarutoModVariables.PlayerVariables _vars = entity.getData(NarutoModVariables.PLAYER_VARIABLES);
				_vars.currentChakra = entity.getData(NarutoModVariables.PLAYER_VARIABLES).currentChakra - chakraCost;
				_vars.syncPlayerVariables(entity);
			}
			{
				NarutoModVariables.PlayerVariables _vars = entity.getData(NarutoModVariables.PLAYER_VARIABLES);
				_vars.attackDamage = 19 * (1 + (entity.getData(NarutoModVariables.PLAYER_VARIABLES).chargeTime / maxChargeTime) * 0.5);
				_vars.syncPlayerVariables(entity);
			}
			DamageCalculationProcedure.execute(entity);
			{
				Entity _shootFrom = entity;
				Level projectileLevel = _shootFrom.level();
				if (!projectileLevel.isClientSide()) {
					Projectile _entityToSpawn = initArrowProjectile(new GreatFireballEntity(NarutoModEntities.GREAT_FIREBALL.get(), 0, 0, 0, projectileLevel, createArrowWeaponItemStack(projectileLevel, 1, (byte) 0)), entity,
							(float) entity.getData(NarutoModVariables.PLAYER_VARIABLES).attackDamage, true, false, false, AbstractArrow.Pickup.DISALLOWED);
					_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
					_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, 0);
					projectileLevel.addFreshEntity(_entityToSpawn);
				}
			}
			fireball = findEntityInWorldRange(world, GreatFireballEntity.class, x, y, z, 4);
			if (!(fireball == null)) {
				fireball.getPersistentData().putString("ownerUUID", (entity.getStringUUID()));
				fireball.getPersistentData().putDouble("damage", entity.getData(NarutoModVariables.PLAYER_VARIABLES).attackDamage);
				fireball.getPersistentData().putDouble("motionX", (entity.getLookAngle().x));
				fireball.getPersistentData().putDouble("motionY", (entity.getLookAngle().y));
				fireball.getPersistentData().putDouble("motionZ", (entity.getLookAngle().z));
				fireball.getPersistentData().putDouble("playerCharge", entity.getData(NarutoModVariables.PLAYER_VARIABLES).chargeTime);
			}
		}
	}

	private static AbstractArrow initArrowProjectile(AbstractArrow entityToSpawn, Entity shooter, float damage, boolean silent, boolean fire, boolean particles, AbstractArrow.Pickup pickup) {
		entityToSpawn.setOwner(shooter);
		entityToSpawn.setBaseDamage(damage);
		if (silent)
			entityToSpawn.setSilent(true);
		if (fire)
			entityToSpawn.igniteForSeconds(100);
		if (particles)
			entityToSpawn.setCritArrow(true);
		entityToSpawn.pickup = pickup;
		return entityToSpawn;
	}

	private static ItemStack createArrowWeaponItemStack(Level level, int knockback, byte piercing) {
		ItemStack weapon = new ItemStack(Items.ARROW);
		if (knockback > 0)
			weapon.enchant(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK), knockback);
		if (piercing > 0)
			weapon.enchant(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.PIERCING), piercing);
		return weapon;
	}

	private static Entity findEntityInWorldRange(LevelAccessor world, Class<? extends Entity> clazz, double x, double y, double z, double range) {
		return (Entity) world.getEntitiesOfClass(clazz, AABB.ofSize(new Vec3(x, y, z), range, range, range), e -> true).stream().sorted(Comparator.comparingDouble(e -> e.distanceToSqr(x, y, z))).findFirst().orElse(null);
	}
}