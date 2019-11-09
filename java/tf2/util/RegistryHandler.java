package tf2.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import tf2.TF2Core;
import tf2.TFBlocks;
import tf2.TFItems;
import tf2.entity.EntityDimension;
import tf2.entity.mob.enemy.EntityEnemyMTT4;
import tf2.entity.mob.enemy.EntityTM02;
import tf2.entity.mob.enemy.EntityTM03;
import tf2.entity.mob.enemy.EntityTM04;
import tf2.entity.mob.enemy.EntityTM05;
import tf2.entity.mob.enemy.EntityTM06;
import tf2.entity.mob.enemy.EntityTM07;
import tf2.entity.mob.enemy.EntityTM11;
import tf2.entity.mob.enemy.EntityTM12;
import tf2.entity.mob.enemy.EntityTM26A;
import tf2.entity.mob.enemy.EntityTM26B;
import tf2.entity.mob.enemy.EntityTM26C;
import tf2.entity.mob.enemy.EntityTM26D;
import tf2.entity.mob.enemy.EntityTM41;
import tf2.entity.mob.frend.EntityBike;
import tf2.entity.mob.frend.EntityCFR12;
import tf2.entity.mob.frend.EntityEvent1;
import tf2.entity.mob.frend.EntityEvent2;
import tf2.entity.mob.frend.EntityEvent3;
import tf2.entity.mob.frend.EntityLicoris;
import tf2.entity.mob.frend.EntityMTT1;
import tf2.entity.mob.frend.EntityMTT2;
import tf2.entity.mob.frend.EntityMTT3;
import tf2.entity.mob.frend.EntityMTT4;
import tf2.entity.mob.frend.EntityTF77B;
import tf2.entity.mob.frend.EntityTF78R;
import tf2.entity.mob.frend.EntityTF79P;
import tf2.entity.mob.frend.EntityTF80G;
import tf2.entity.projectile.EntityBarrier;
import tf2.entity.projectile.enemy.EntityEnemyBullet;
import tf2.entity.projectile.enemy.EntityEnemyBulletHE;
import tf2.entity.projectile.enemy.EntityEnemyGrenade;
import tf2.entity.projectile.enemy.EntityEnemyHowitzer;
import tf2.entity.projectile.enemy.EntityEnemyImpact;
import tf2.entity.projectile.enemy.EntityEnemyMortar;
import tf2.entity.projectile.player.EntityAreaHeal;
import tf2.entity.projectile.player.EntityBullet;
import tf2.entity.projectile.player.EntityBulletBig;
import tf2.entity.projectile.player.EntityBulletCorrosion;
import tf2.entity.projectile.player.EntityFriendBullet;
import tf2.entity.projectile.player.EntityFriendImpact;
import tf2.entity.projectile.player.EntityFriendMortar;
import tf2.entity.projectile.player.EntityFriendShell;
import tf2.entity.projectile.player.EntityFriendSoundwave;
import tf2.entity.projectile.player.EntityGrenade;
import tf2.entity.projectile.player.EntityGrenadeHe;
import tf2.entity.projectile.player.EntityShell;


public class RegistryHandler
{
	public static int entityId;

	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(TFItems.ITEMS.toArray(new Item[0]));
	}

	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(TFBlocks.BLOCKS.toArray(new Block[0]));
	}

	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Item item : TFItems.ITEMS)
		{
			if(item instanceof IHasModel)
			{
				((IHasModel)item).registerModel();
			}
		}

		for(Block block : TFBlocks.BLOCKS)
		{
			if(block instanceof IHasModel)
			{
				((IHasModel)block).registerModel();
			}
		}
	}

	public static ResourceLocation getKey(String key)
	{
		return new ResourceLocation(Reference.MOD_ID, key);
	}

	public static void registerEntity(Class<? extends Entity> entityClass, String key, String name, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityRegistry.registerModEntity(getKey(key), entityClass, name, entityId++, TF2Core.INSTANCE, trackingRange, updateFrequency, sendsVelocityUpdates);
	}

	public static void registerEntity(Class<? extends Entity> entityClass, String key, String name, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int primaryColor, int secondaryColor)
	{
		EntityRegistry.registerModEntity(getKey(key), entityClass, name, entityId++, TF2Core.INSTANCE, trackingRange, updateFrequency, sendsVelocityUpdates, primaryColor, secondaryColor);
	}

	public static void registerMob(Class<? extends Entity> entityClass, String key, String name)
	{
		registerEntity(entityClass, key, name, 128, 3, true);
	}

	public static void registerMob(Class<? extends Entity> entityClass, String key, String name, int primaryColor, int secondaryColor)
	{
		registerEntity(entityClass, key, name, 128, 1, true, primaryColor, secondaryColor);

		EntitySpawnPlacementRegistry.setPlacementType(entityClass, SpawnPlacementType.ON_GROUND);
	}

	public static void registerEntities()
	{
		EntityBullet.registerEntity(EntityBullet.class, prefix("bullet"), "Bullet", 128, 5, true);
		EntityGrenade.registerEntity(EntityGrenade.class, prefix("grenade"), "Grenade", 256, 5, true);
		EntityShell.registerEntity(EntityShell.class, prefix("shell"), "Shell", 256, 1, true);
		EntityAreaHeal.registerEntity(EntityAreaHeal.class, prefix("areaheal"), "AreaHeal", 256, 1, true);
//		EntityBeam.registerEntity(EntityBeam.class, prefix("beam"), "Beam", 256, 1, true);
		EntityBulletBig.registerEntity(EntityBulletBig.class, prefix("bulletbig"), "BulletBig", 256, 1, true);
		EntityBulletCorrosion.registerEntity(EntityBulletCorrosion.class, prefix("bulletcorrosion"), "BulletCorrosion", 128, 5, true);
		EntityGrenadeHe.registerEntity(EntityGrenadeHe.class, prefix("grenadehe"), "GrenadeHe", 256, 1, true);
		EntityBarrier.registerEntity(EntityBarrier.class, prefix("barrier"), "Barrier", 128, 1, true);

		EntityDimension.registerEntity(EntityDimension.class, prefix("dimension"), "Dimension", 256, 10, true);

		EntityEnemyBullet.registerEntity(EntityEnemyBullet.class, prefix("enemybullet"), "EnemyBullet", 128, 5, true);
		EntityEnemyGrenade.registerEntity(EntityEnemyGrenade.class, prefix("enemygrenade"), "EnemyGrenade", 128, 5, true);
//		EntityEnemyBlade.registerEntity(EntityEnemyBlade.class, prefix("enemyblade"), "EnemyBlade", 128, 1, true);
//		EntityEnemyBulletAP.registerEntity(EntityEnemyBulletAP.class, prefix("enemybulletap"), "EnemyBulletAP", 256, 1, true);
		EntityEnemyBulletHE.registerEntity(EntityEnemyBulletHE.class, prefix("enemybullethe"), "EnemyBulletHE", 256, 1, true);
//		EntityEnemyMissile.registerEntity(EntityEnemyMissile.class, prefix("enemymissile"), "EnemyMissile", 256, 1, true);
//		EntityEnemyBulletCube.registerEntity(EntityEnemyBulletCube.class, prefix("enemybulletcube"), "EnemyBulletCube", 256, 1, true);
		EntityEnemyImpact.registerEntity(EntityEnemyImpact.class, prefix("enemyimpact"), "EnemyImpact", 256, 1, true);
//		EntityEnemySlashFragment.registerEntity(EntityEnemySlashFragment.class, prefix("enemyslashfragment"), "EnemySlashFragment", 256, 1, true);
//		EntityEnemySlashWide.registerEntity(EntityEnemySlashWide.class, prefix("enemyslashwide"), "EnemySlashWide", 256, 1, true);
		EntityEnemyMortar.registerEntity(EntityEnemyMortar.class, prefix("enemymortar"), "EnemyMortar", 256, 1, true);
		EntityEnemyHowitzer.registerEntity(EntityEnemyHowitzer.class, prefix("enemyhowitzer"), "EnemyHowitzer", 256, 1, true);

		EntityFriendBullet.registerEntity(EntityFriendBullet.class, prefix("friendbullet"), "FriendBullet", 128, 5, true);
//		EntityFriendGrenade.registerEntity(EntityFriendGrenade.class, prefix("friendgrenade"), "FriendGrenade", 128, 1, true);
		EntityFriendShell.registerEntity(EntityFriendShell.class, prefix("friendshell"), "FriendShell", 256, 1, true);
		EntityFriendSoundwave.registerEntity(EntityFriendSoundwave.class, prefix("friendsoundwave"), "FriendSoundwave", 256, 1, true);
		EntityFriendImpact.registerEntity(EntityFriendImpact.class, prefix("friendimpact"), "FriendImpact", 256, 1, true);
		EntityFriendMortar.registerEntity(EntityFriendMortar.class, prefix("friendmortar"), "FriendMortar", 256, 1, true);

		registerMob(EntityTM02.class, "TM02", "TM02", 0x2F2F2F, 0xFF0000);
		registerMob(EntityTM03.class, "TM03", "TM03", 0x5F5F5F, 0xFF5000);
		registerMob(EntityTM04.class, "TM04", "TM04", 0xB22222, 0xB0C4DE);
		registerMob(EntityTM05.class, "TM05", "TM05", 0x228B22, 0xFF0000);
		registerMob(EntityTM06.class, "TM06", "TM06", 0x707070, 0x00FF7F);
		registerMob(EntityTM07.class, "TM07", "TM07", 0x007000, 0xAAAAAA);
		registerMob(EntityTM11.class, "TM11", "TM11", 0x4169E1, 0x00FFFF);
		registerMob(EntityTM12.class, "TM12", "TM12", 0x4169F0, 0xFF0000);
//		registerMob(EntityTM17.class, "TM17", "TM17", 0x005000, 0xFF0000);
//		registerMob(EntityTM18.class, "TM18", "TM18", 0x3104B4, 0xFF0000);
//		registerMob(EntityTM22.class, "TM22", "TM22", 0x006000, 0xFFA500);
		registerMob(EntityTM26A.class, "TM26A", "TM26A", 0x013ADF, 0xEEEEEE);
		registerMob(EntityTM26B.class, "TM26B", "TM26B", 0xB40404, 0xEEEEEE);
		registerMob(EntityTM26C.class, "TM26C", "TM26C", 0x008000, 0xEEEEEE);
		registerMob(EntityTM26D.class, "TM26D", "TM26D", 0xFFD700, 0xEEEEEE);
//		registerMob(EntityTM29.class, "TM29", "TM29", 0x9932CC, 0x4169E1);
//		registerMob(EntityTM31.class, "TM31", "TM31", 0x13ADFF, 0xEE0000);
//		registerMob(EntityTM33.class, "TM33", "TM33", 0x00BFFF, 0xFF5500);
//		registerMob(EntityTM34.class, "TM34", "TM34", 0x0000FF, 0xFF2500);
		registerMob(EntityTM41.class, "TM41", "TM41", 0xCC0404, 0xFFFF50);
//
//		registerMob(EntityTF08A.class, "TF08A", "TF08A", 0xFFFFFF, 0x77CFFF);
//		registerMob(EntityTF08B.class, "TF08B", "TF08B", 0x0000CF, 0x77CFFF);
//		registerMob(EntityTF08C.class, "TF08C", "TF08C", 0x008000, 0x77CFFF);
//
//		registerMob(EntityTF44.class, "TF44", "TF44", 0x000000, 0xFF0000);

//		registerMob(EntityDefence2.class, "Defence2", "Defence2", 0xEEEEEE, 0xCCCCCC);
//		registerMob(EntityDefence3.class, "Defence3", "Defence3", 0xEEEEEE, 0xCCCCCC);
//		registerMob(EntitySoldier.class, "Soldier", "Soldier", 0x008000, 0x77CFFF);
//		registerMob(EntityMercenary.class, "Mercenary", "Mercenary", 0x008000, 0x77CFFF);
//		registerMob(EntityZunko.class, "Zunko", "Zunko", 0x008000, 0x77CFFF);
//
//		registerMob(EntityBike.class, "Bike", "Bike", 0x4060FF, 0xEEEEFF);
//		registerMob(EntityTank.class, "Tank", "Tank", 0x006400, 0xEEEEFF);
//		registerMob(EntityNDFTank.class, "NDFTank", "NDFTank", 0x006400, 0xEEEEFF);
		registerMob(EntityEnemyMTT4.class, "EnemyMTT4", "EnemyMTT4", 0x3CB371, 0xFFB500);
		registerMob(EntityEvent1.class, "Event1", "Event1", 0x008000, 0xFFFFFF);
		registerMob(EntityEvent2.class, "Event2", "Event2", 0x008000, 0xFFFFFF);
		registerMob(EntityEvent3.class, "Event3", "Event3", 0x008000, 0xFFFFFF);
		registerMob(EntityLicoris.class, "Licoris", "Licoris", 0x008000, 0xFFFFFF);

		registerMob(EntityMTT1.class, "MTT1", "MTT1");
		registerMob(EntityMTT2.class, "MTT2", "MTT2");
		registerMob(EntityMTT3.class, "MTT3", "MTT3");
		registerMob(EntityMTT4.class, "MTT4", "MTT4");
		registerMob(EntityCFR12.class, "CFR12", "CFR12");

		registerMob(EntityBike.class, "Bike", "Bike");

		registerMob(EntityTF77B.class, "TF77B", "TF77B");
		registerMob(EntityTF78R.class, "TF78R", "TF78R");
		registerMob(EntityTF79P.class, "TF79P", "TF79P");
		registerMob(EntityTF80G.class, "TF80G", "TF80G");
	}

	private static ResourceLocation prefix(String path)
	{
		return new ResourceLocation(Reference.MOD_ID, path);
	}
}
