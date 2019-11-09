package tf2.event;

import java.util.Iterator;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tf2.TFConfig;
import tf2.entity.mob.enemy.EntityMobTF;
import tf2.entity.mob.frend.EntityMobCF;
import tf2.entity.projectile.IEnemyProjectile;
import tf2.entity.projectile.IFriendProjectile;
import tf2.items.guns.ItemTFGuns;
import tf2.items.guns.ItemTFGunsHG;
import tf2.items.guns.ItemTFGunsLMG;
import tf2.items.guns.ItemTFGunsSMG;
import tf2.items.guns.ItemTFGunsSR;
import tf2.potion.TFPotionPlus;
import tf2.util.TFWorldConfigManager;

public class TFLivingUpdateEvent
{
	@SubscribeEvent
	public void onWorldEvent(WorldEvent event)
	{
		if(TFConfig.configChange)
		{
			TFWorldConfigManager.saveWorldConfigFile(event.getWorld());
			TFConfig.configChange = false;
		}
	}

	@SubscribeEvent
	public void onRideMobCFUseItemEvent(RightClickItem event)
	{
		if(event.getEntityPlayer().isRiding() && event.getEntityPlayer().getRidingEntity() instanceof EntityMobCF)
		{
			EntityPlayer player = event.getEntityPlayer();

			if(player.getHeldItemMainhand().getItem() instanceof ItemTFGuns)
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onLivingMob(LivingUpdateEvent event)
	{
		EntityLivingBase target = event.getEntityLiving();
		if (target != null && target instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) target;

			ItemStack itemstack = ((EntityPlayer) (player)).getHeldItemMainhand();

			if (itemstack != null && itemstack.getItem() instanceof ItemTFGuns)
			{
				double x = player.motionX;
				double z = player.motionZ;
				double d0 = MathHelper.sqrt((float) (x * x + z * z));

				if (player.isPotionActive(TFPotionPlus.MOVE_SHOOTING))
				{
					PotionEffect potion = player.getActivePotionEffect(TFPotionPlus.MOVE_SHOOTING);
					int i = potion.getAmplifier();

					if (player.isHandActive() && !player.isSneaking())
					{
						if (itemstack.getItem() instanceof ItemTFGunsSMG ||
								itemstack.getItem() instanceof ItemTFGunsHG)
						{
							if (d0 <= 0.14F + (i * 0.01F))
							{
								target.motionX *= 1.65D + (i * 0.01F);
								target.motionZ *= 1.65D + (i * 0.01F);
							}
						}
						else if (itemstack.getItem() instanceof ItemTFGunsLMG ||
								itemstack.getItem() instanceof ItemTFGunsSR)
						{
							if (d0 <= 0.13F + (i * 0.01F))
							{
								target.motionX *= 1.58D + (i * 0.01F);
								target.motionZ *= 1.58D + (i * 0.01F);
							}
						}
						else
						{
							if (d0 <= 0.13F + (i * 0.01F))
							{
								target.motionX *= 1.61D + (i * 0.01F);
								target.motionZ *= 1.61D + (i * 0.01F);
							}
						}

					}
				}
			}
		}

		if (target != null && target.isPotionActive(TFPotionPlus.DEBUFF_GUARD))
		{
			//なぜ分けているのかがわからないため放置
			//if (!target.world.isRemote) return;
			Iterator<PotionEffect> iterator = target.getActivePotionMap().values().iterator();

			while (iterator.hasNext())
			{
				PotionEffect effect = iterator.next();

				if (effect.getPotion().isBadEffect())
				{
					iterator.remove();
				}
			}
		}
	}

	@SubscribeEvent
	public void onTFProjectileImpact(ProjectileImpactEvent event)
	{
		Entity projectile = event.getEntity();
		Entity target = event.getRayTraceResult().entityHit;

		if (target instanceof EntityMobTF && projectile instanceof IEnemyProjectile)
		{
			event.setCanceled(true);
		}

		if ((target instanceof EntityPlayer || (target instanceof EntityGolem && !(target instanceof IMob))) && projectile instanceof IFriendProjectile)
		{
			event.setCanceled(true);
		}
	}
}
