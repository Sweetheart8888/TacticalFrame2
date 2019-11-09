package tf2.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tf2.TFItems;
import tf2.entity.mob.enemy.EntityMobTF;
import tf2.entity.mob.frend.EntityFriendMecha;
import tf2.entity.mob.frend.EntityMobCF;
import tf2.entity.projectile.IEnemyProjectile;
import tf2.entity.projectile.IFriendProjectile;
import tf2.items.guns.ItemTFGunsSG;
import tf2.items.guns.ItemTFGunsSMG;
import tf2.potion.TFPotionPlus;

public class TFHurtEvent
{
	@SubscribeEvent
	public void onHurtEvent(LivingHurtEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		DamageSource damage = event.getSource();
		World world = entity.world;
		float amount = event.getAmount();

		ItemStack itemstack = entity.getHeldItemOffhand();

		if (entity != null)
		{
			if (itemstack != null && itemstack.getItem() == TFItems.IRON_SHIELD)
			{
				amount *= 0.9F;
				event.setAmount(amount);
			}
			else if (itemstack != null && itemstack.getItem() == TFItems.RIOT_SHIELD)
			{
				amount *= 0.8F;
				event.setAmount(amount);
			}

//			if (entity instanceof EntityPlayer)
//			{
//				EntityPlayer player = (EntityPlayer) event.getEntityLiving();
//
//				if (player.getRidingEntity() != null && player.getRidingEntity() instanceof EntityTank)
//				{
//					amount = 0F;
//					event.setCanceled(true);
//				}
//
//			}
		}
	}

	@SubscribeEvent
	public void onHurtPassiveEvent(LivingHurtEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		DamageSource damage = event.getSource();
		float amount = event.getAmount();

		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;

			if (player != null)
			{
				for (int inv = 9; inv < 18; inv++)
				{
					ItemStack itemstack = player.inventory.getStackInSlot(inv);

					if (itemstack != null && itemstack.getItem() == TFItems.SKILL_AVOIDANCE)
					{
						if (player.getCooldownTracker().getCooldown(itemstack.getItem(), 0) == 0)
						{
							player.addPotionEffect(new PotionEffect(TFPotionPlus.DAMAGE_CANCEL, 60, 0));
							player.getCooldownTracker().setCooldown(itemstack.getItem(), 300);
							event.setCanceled(true);
						}
					}
					if (itemstack != null && itemstack.getItem() == TFItems.SKILL_DISTORTION)
					{
						if (player.getCooldownTracker().getCooldown(itemstack.getItem(), 0) == 0)
						{
							player.addPotionEffect(new PotionEffect(TFPotionPlus.DISABLE_CHANCE, 800, 1));
							player.getCooldownTracker().setCooldown(itemstack.getItem(), 1200);
						}
					}
				}
				for (int inv = 9; inv < 18; inv++)
				{
					ItemStack itemstack = player.inventory.getStackInSlot(inv);
					if (itemstack != null && itemstack.getItem() == TFItems.SKILL_MOBILEARMOR)
					{
						if (damage instanceof EntityDamageSource)
						{
							ItemStack main = entity.getHeldItemMainhand();
							if (main != null && main.getItem() instanceof ItemTFGunsSG)
							{
								if (amount <= 1.0F)
								{
									player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.NEUTRAL, 0.5F, 1.0F / (entity.getRNG().nextFloat() * 0.4F + 0.8F));
									event.setCanceled(true);
									break;
								}
								else if (amount > 1.0F && amount < 5.0F)
								{
									amount = 1.0F;
									event.setAmount(amount);
									break;
								}
								else
								{
									amount -= 4.0F;
									event.setAmount(amount);
									break;
								}
							}
						}
					}
				}
			}
		}
	}

	//ダメージ発生そのものをなくしたい時はLivingAttackEventを使う
	@SubscribeEvent
	public void onLivingAttackEvent(LivingAttackEvent event)
	{
		EntityLivingBase target = event.getEntityLiving();
		DamageSource damage = event.getSource();
		float amount = event.getAmount();

		if((damage.getTrueSource() instanceof EntityMobTF || damage.getImmediateSource() instanceof IEnemyProjectile) && target instanceof EntityMobTF)
		{
			event.setCanceled(true);
		}
		if((damage.getTrueSource() instanceof EntityFriendMecha || damage.getImmediateSource() instanceof IFriendProjectile) && (target instanceof EntityPlayer || (target instanceof EntityGolem && !(target instanceof IMob))))
		{
			event.setCanceled(true);
		}

		if (target != null && !target.world.isRemote && target instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) target;

			for (int inv = 9; inv < 18; inv++)
			{
				ItemStack itemstack = player.inventory.getStackInSlot(inv);
				if (itemstack != null && itemstack.getItem() == TFItems.SKILL_GHOSTDANCE)
				{
					if (damage instanceof EntityDamageSource)
					{
						ItemStack main = player.getHeldItemMainhand();
						if (main != null && main.getItem() instanceof ItemTFGunsSMG)
						{
							float k = target.world.rand.nextFloat() * 10.0F;
							if (4.0D < k)
							{
								event.setCanceled(true);
							}
						}
					}
				}
			}

//			if (player.getRidingEntity() != null && player.getRidingEntity() instanceof EntityBike)
//			{
//				if (damage.damageType.equals("fall") || damage.damageType.equals("inWall") || damage.damageType.equals("flyIntoWall"))
//				{
//					event.setCanceled(true);
//				}
//			}
//			//			 if(player.getRidingEntity() != null && player.getRidingEntity() instanceof EntityTruck)
//			//			 {
//			//				 if(damage.damageType.equals("fall") || damage.damageType.equals("inWall") || damage.damageType.equals("flyIntoWall"))
//			//				 {
//			//					 event.setCanceled(true);
//			//				 }
//			//			 }

			if (player.getRidingEntity() != null && player.getRidingEntity() instanceof EntityMobCF)
			{
				player.getRidingEntity().attackEntityFrom(damage, amount);

				if (this.attackEntity(damage) == true)
				{
					player.getRidingEntity().hurtResistantTime = 0;
				}
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onLivingPotionEvent(LivingAttackEvent event)
	{
		EntityLivingBase target = event.getEntityLiving();
		DamageSource damage = event.getSource();
		float amount = event.getAmount();

		if (target != null && !target.world.isRemote)
		{
			if (target.isPotionActive(TFPotionPlus.DAMAGE_CANCEL))
			{
				event.setCanceled(true);
			}
			if (target.isPotionActive(TFPotionPlus.RESISTANCE_DOT))
			{
				PotionEffect potion = target.getActivePotionEffect(TFPotionPlus.RESISTANCE_DOT);
				int i = potion.getAmplifier();

				if (amount <= 2F + ((float) i * 2F))
				{
					event.setCanceled(true);
				}
			}
			if (target.isPotionActive(TFPotionPlus.DISABLE_CHANCE))
			{
				PotionEffect potion = target.getActivePotionEffect(TFPotionPlus.DISABLE_CHANCE);
				int i = potion.getAmplifier();

				float par = 5.0F - (1.0F + (float) i);

				if (par < target.world.rand.nextFloat() * 5.0F)
				{
					event.setCanceled(true);
				}
			}

			// defeatedcrowさんのHeatAndClimateModの寒暖ダメーシに対応
			if (target.isPotionActive(TFPotionPlus.RESISTANCE_HEAT))
			{
				if (damage.damageType.equals("dcs_heat") || damage.damageType.equals("heat"))
				{
					event.setCanceled(true);
				}
				if (damage.isFireDamage())
				{
					event.setCanceled(true);
				}
			}
			if (target.isPotionActive(TFPotionPlus.RESISTANCE_COLD))
			{
				if (damage.damageType.equals("dcs_cold"))
				{
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public void onHurtPotionEvent(LivingHurtEvent event)
	{
		EntityLivingBase target = event.getEntityLiving();
		DamageSource damage = event.getSource();
		float amount = event.getAmount();

		if (target != null && !target.world.isRemote)
		{
			if (target.isPotionActive(TFPotionPlus.VULNERABILITY))
			{
				PotionEffect potion = target.getActivePotionEffect(TFPotionPlus.VULNERABILITY);
				int i = potion.getAmplifier();

				float f = 1.15F + (i * 0.15F);

				amount *= f;
				event.setAmount(amount);
			}
		}
	}

	public boolean attackEntity(DamageSource var1)
	{
		if (var1.damageType.equals("bullet"))
		{
			return true;
		}
		if (var1.damageType.equals("bomb"))
		{
			return true;
		}
		if (var1.damageType.equals("grenade"))
		{
			return true;
		}
		if (var1.damageType.equals("heat"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
