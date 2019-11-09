package tf2.event;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tf2.TFItems;
import tf2.TFSoundEvents;
import tf2.entity.EntityItemSpawnFriendMecha;
import tf2.entity.mob.frend.EntityFriendMecha;
import tf2.entity.mob.frend.EntityMTT1;
import tf2.entity.mob.frend.EntityMTT2;
import tf2.entity.mob.frend.EntityMTT3;
import tf2.entity.mob.frend.EntityMTT4;
import tf2.potion.TFPotionPlus;

public class TFMechaSkillEvent
{
	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent event)
	{
		if(event.getWorld().isRemote || event.isCanceled() || event.getEntity().isDead)
		{
			return;
		}

		if(event.getEntity().getClass() == EntityItem.class)
		{
			EntityItem item = (EntityItem)event.getEntity();
			if(!item.getItem().isEmpty() && item.getItem().getItem() == TFItems.SPAWNFM)
			{
				EntityItemSpawnFriendMecha core = new EntityItemSpawnFriendMecha(item.world, item.posX, item.posY, item.posZ, item.getItem());
				core.motionX = item.motionX;
				core.motionY = item.motionY;
				core.motionZ = item.motionZ;
				core.setPickupDelay(30);
				core.setNoDespawn();
				core.world.spawnEntity(core);

				item.setItem(ItemStack.EMPTY);
			}
		}
	}

	//Damageはメカの防御値のみを参照するため、Playerは不要
	@SubscribeEvent
	public void mechaHurtEvent(LivingHurtEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		DamageSource damage = event.getSource();
		float amount = event.getAmount();

		if (entity instanceof EntityFriendMecha)
		{
			EntityFriendMecha mecha = (EntityFriendMecha) entity;

			if (mecha != null)
			{
				if (mecha.getInventoryMechaEquipment().getHasSkill(TFItems.SKILL_ALLORNOTHING))
				{
					if(0.8F < mecha.world.rand.nextFloat())
					{
						amount *= 0.5F;
						event.setAmount(amount);
					}
				}

				if (mecha.getInventoryMechaEquipment().getHasSkill(TFItems.SKILL_ADDITIONALARMOR_1))
				{
					amount *= 0.9F;
					event.setAmount(amount);
				}
				if (mecha.getInventoryMechaEquipment().getHasSkill(TFItems.SKILL_ADDITIONALARMOR_2))
				{
					amount *= 0.8F;
					event.setAmount(amount);
				}
				if (mecha.getInventoryMechaEquipment().getHasSkill(TFItems.SKILL_ADDITIONALARMOR_3))
				{
					amount *= 0.7F;
					event.setAmount(amount);
				}
			}
		}
	}

	//騎乗時のPlayerにも効果を与える
	@SubscribeEvent
	public void mechaAttackEvent(LivingAttackEvent event)
	{
		EntityLivingBase target = event.getEntityLiving();
		DamageSource damage = event.getSource();
		float amount = event.getAmount();

		if (damage.getTrueSource() instanceof EntityFriendMecha)
		{
			EntityFriendMecha mecha = (EntityFriendMecha)damage.getTrueSource();
			this.selectPlayerOrMecha(mecha, target, event, damage);
		}
		if (damage.getTrueSource() instanceof EntityPlayer && damage.getTrueSource().isRiding() && damage.getTrueSource().getRidingEntity() instanceof EntityFriendMecha)
		{
			EntityFriendMecha mecha = (EntityFriendMecha)damage.getTrueSource().getRidingEntity();
			this.selectPlayerOrMecha(mecha, target, event, damage);
		}


		if (target instanceof EntityFriendMecha)
		{
			EntityFriendMecha mecha = (EntityFriendMecha) target;
			this.selectPlayerOrMecha(mecha, target, event, damage);
		}
		if (target instanceof EntityPlayer && target.isRiding() && target.getRidingEntity() instanceof EntityFriendMecha)
		{
			EntityFriendMecha mecha = (EntityFriendMecha) target.getRidingEntity();
			this.selectPlayerOrMecha(mecha, target, event, damage);
		}
	}

	public void selectPlayerOrMecha(EntityFriendMecha mecha, EntityLivingBase target, LivingAttackEvent event, DamageSource damage)
	{
		if (mecha != null)
		{
			if (mecha.getInventoryMechaEquipment().getHasSkill(TFItems.SKILL_ENCHANTFLAME))
			{
				if (target != null && !target.world.isRemote && !(target instanceof EntityPlayer || (target instanceof EntityGolem && !(target instanceof IMob))))
				{
					target.addPotionEffect(new PotionEffect(TFPotionPlus.HEAT, 200, 0));
				}
			}
			if (mecha.getInventoryMechaEquipment().getHasSkill(TFItems.SKILL_ENCHANTSLOW))
			{
				if (target != null && !target.world.isRemote && !(target instanceof EntityPlayer || (target instanceof EntityGolem && !(target instanceof IMob))))
				{
					target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 0));
				}
			}
			if (mecha.getInventoryMechaEquipment().getHasSkill(TFItems.SKILL_ENCHANTWEAKNESS))
			{
				if (target != null && !target.world.isRemote && !(target instanceof EntityPlayer || (target instanceof EntityGolem && !(target instanceof IMob))))
				{
					target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200, 0));
				}
			}
			if (mecha.getInventoryMechaEquipment().getHasSkill(TFItems.SKILL_PROVOCATE))
			{
				if (target != null && target instanceof EntityMob)
				{
					EntityMob mob = (EntityMob)target;
					mob.setAttackTarget(mecha);
					mob.setLastAttackedEntity(mecha);
				}
			}

			if (mecha.getInventoryMechaEquipment().getHasSkill(TFItems.SKILL_FIREPROTECTION))
			{
				if (damage.isFireDamage())
				{
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public void mechaLivingEvent(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		if (entity != null && entity instanceof EntityFriendMecha)
		{
			EntityFriendMecha mecha = (EntityFriendMecha) entity;

			if (mecha != null)
			{
				if (mecha.getInventoryMechaEquipment().getHasSkill(TFItems.SKILL_AUTOREPAIR))
				{
					if(mecha.ticksExisted % 300 == 0 && !mecha.world.isRemote)
					{
						mecha.heal(2F);
					}
				}

				if (mecha.getInventoryMechaEquipment().getHasSkill(TFItems.SKILL_HARDSTRIKE))
				{
					if(mecha.ticksExisted % 300 == 0 && !mecha.world.isRemote)
					{
						mecha.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 300, 0, true, false));

						EntityPlayer player = (EntityPlayer)mecha.getControllingPassenger();
						if(player != null && !player.world.isRemote)
						{
							player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 300, 0, true, false));
						}
					}
				}
				if (mecha.getInventoryMechaEquipment().getHasSkill(TFItems.SKILL_FIREFILLING))
				{
					if(mecha.ticksExisted % 300 == 0 && !mecha.world.isRemote)
					{
						mecha.addPotionEffect(new PotionEffect(TFPotionPlus.SHOOTING, 300, 0, true, false));

						EntityPlayer player = (EntityPlayer)mecha.getControllingPassenger();
						if(player != null && !player.world.isRemote)
						{
							player.addPotionEffect(new PotionEffect(TFPotionPlus.SHOOTING, 300, 0, true, false));
						}
					}
				}

				if (mecha.getInventoryMechaEquipment().getHasSkill(TFItems.SKILL_FULLFIREPOWER))
				{
					if(mecha.ticksExisted % 400 == 0 && !mecha.world.isRemote && 0.5F < mecha.world.rand.nextFloat())
					{
						mecha.addPotionEffect(new PotionEffect(TFPotionPlus.SHOOTING, 200, 2, true, false));

						EntityPlayer player = (EntityPlayer)mecha.getControllingPassenger();
						if(player != null && !player.world.isRemote)
						{
							player.addPotionEffect(new PotionEffect(TFPotionPlus.SHOOTING, 200, 2, true, false));
						}
					}
				}

				if (mecha.getInventoryMechaEquipment().getHasSkill(TFItems.SKILL_ARTILLERYCOMMAND_TURRET))
				{
					if(mecha.ticksExisted % 300 == 0 && !mecha.world.isRemote)
					{
						List var7 = mecha.world.getEntitiesWithinAABB(EntityFriendMecha.class, mecha.getEntityBoundingBox().grow(8.0D));
				 		for (int var3 = 0; var3 < var7.size(); ++var3)
				 		{
				 			EntityFriendMecha friend = (EntityFriendMecha)var7.get(var3);

				 			if (friend instanceof EntityMTT1 || friend instanceof EntityMTT2 || friend instanceof EntityMTT3 || friend instanceof EntityMTT4)
				        	{
				 				if(!friend.world.isRemote)
								{
				 					friend.addPotionEffect(new PotionEffect(TFPotionPlus.SHOOTING_SUPPORT, 300, 0, true, false));
								}
				        	}
				 		}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void mechaHealEvent(LivingHealEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		float amount = event.getAmount();

		if (entity instanceof EntityFriendMecha)
		{
			EntityFriendMecha mecha = (EntityFriendMecha) entity;

			if (mecha != null)
			{
				if (mecha.getInventoryMechaEquipment().getHasSkill(TFItems.SKILL_REPAIRDOUBLING))
				{
					amount *= 2.0F;
					event.setAmount(amount);
				}
			}
		}
	}

	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		if (entity instanceof EntityFriendMecha)
		{
			EntityFriendMecha mecha = (EntityFriendMecha) entity;
			if (mecha != null)
			{
				if (mecha.getInventoryMechaEquipment().getHasSkill(TFItems.SKILL_RESURRECTION))
				{
	    			if(mecha.getInventoryMechaEquipment().getSkillAItem().getItem() == TFItems.SKILL_RESURRECTION)
	    			{
	    				mecha.getInventoryMechaEquipment().setSkillAItem(ItemStack.EMPTY);
	    			}
	    			else if(mecha.getInventoryMechaEquipment().getSkillBItem().getItem() == TFItems.SKILL_RESURRECTION)
	    			{
	    				mecha.getInventoryMechaEquipment().setSkillBItem(ItemStack.EMPTY);
	    			}
	    			else if(mecha.getInventoryMechaEquipment().getSkillCItem().getItem() == TFItems.SKILL_RESURRECTION)
	    			{
	    				mecha.getInventoryMechaEquipment().setSkillCItem(ItemStack.EMPTY);
	    			}
	    			mecha.playSound(TFSoundEvents.DECISION, 0.7F, 1.0F / (mecha.getRNG().nextFloat() * 0.4F + 0.8F));
	    			mecha.setHealth(mecha.getMaxHealth());
					event.setCanceled(true);
				}
			}
		}
	}
}
