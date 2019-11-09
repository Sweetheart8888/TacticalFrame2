package tf2.entity.mob.frend;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import tf2.TF2Core;
import tf2.TFItems;
import tf2.TFSoundEvents;
import tf2.entity.mob.ai.EntityAIAttackRangedGun;
import tf2.entity.mob.enemy.EntityTM06;
import tf2.entity.projectile.player.EntityFriendBullet;
import tf2.util.TFAdvancements;

public class EntityEvent2 extends EntityMobNPC implements IRangedAttackMob
{
	private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getScoreName(), BossInfo.Color.RED, BossInfo.Overlay.NOTCHED_6));

	public int count;
	public int maxCount;
	private static final DataParameter<Integer> COUNT = EntityDataManager.<Integer> createKey(EntityEvent2.class, DataSerializers.VARINT);

	protected int eventTime2;
	private static final DataParameter<Integer> EVENT_TIME2 = EntityDataManager.<Integer> createKey(EntityEvent2.class, DataSerializers.VARINT);

	public int attackTime;

	public EntityEvent2(World worldIn)
	{
		super(worldIn);
		this.setSize(0.6F, 1.8F);
		this.count = 10;
		this.maxCount = count;
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackRangedGun(this, 1.0D, 15.0F));
		this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityTM06.class, true));
		this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(COUNT, Integer.valueOf(0));
		this.dataManager.register(EVENT_TIME2, Integer.valueOf(1));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(15.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
	}

	@Override
	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
	{
		livingdata = super.onInitialSpawn(difficulty, livingdata);

		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(TFItems.HK416));
		return livingdata;
	}


	public ITextComponent getScoreName()
    {
        return new TextComponentTranslation("tf.mission.potential", new Object[] {});
    }
	@Override
	protected SoundEvent getAmbientSound()
	{
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundEvents.ENTITY_PLAYER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_PLAYER_DEATH;
	}

	@Override
	protected boolean canDespawn()
	{
		return false;
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();

		if (this.attackTime > 0)
		{
			--this.attackTime;
		}

		if (!world.isRemote)
		{
			this.setCount();
			this.setEventTime2();
		}
		else
		{
			this.count = this.dataManager.get(COUNT).intValue();
			this.eventTime2 = this.dataManager.get(EVENT_TIME2).intValue();
		}
		if (this.count <= 0)
		{
			++this.eventTime2;
			this.isClear();
		}

		this.isMission();

		if (this.eventTime > 800 && this.count > 0)
		{
			this.bossInfo.setVisible(true);
		}
		else
		{
			this.bossInfo.setVisible(false);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setInteger("Count", this.count);
		compound.setInteger("EventTime2", this.eventTime2);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		this.count = compound.getInteger("Count");
		this.eventTime2 = compound.getInteger("EventTime2");
	}

	public void setEventTime2()
	{
		this.dataManager.set(EVENT_TIME2, new Integer(this.eventTime2));
	}

	public void setCount()
	{
		this.dataManager.set(COUNT, new Integer(this.count));
	}

	public int getCount()
	{
		return this.dataManager.get(COUNT);
	}

	public int getMaxCount()
	{
		return this.maxCount;
	}

	@Override
	public void addTrackingPlayer(EntityPlayerMP player)
	{
		super.addTrackingPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	@Override
	public void removeTrackingPlayer(EntityPlayerMP player)
	{
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	protected void updateAITasks()
	{
		super.updateAITasks();
		this.bossInfo.setPercent((float) this.getCount() / this.getMaxCount());
	}

	public void isChat(String text, Object... args)
	{
		List k = this.world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(30.0D));
		for (int u = 0; u < k.size(); ++u)
		{
			EntityPlayer playerall = (EntityPlayer) k.get(u);
			if(!this.world.isRemote)
			{
				playerall.sendMessage(new TextComponentTranslation(text, args));
			}
		}
	}

	public void isMission()
	{
		if (this.eventTime == 200)
		{
			this.isChat("tf.mission2.txt1");
		}
		if (this.eventTime == 300)
		{
			this.isChat("tf.mission2.txt2");
		}
		if (this.eventTime == 400)
		{
			this.isChat("tf.mission2.txt3");
		}
		if (this.eventTime == 500)
		{
			this.isChat("tf.mission2.txt4");
		}
		if (this.eventTime == 700)
		{
			this.isChat("tf.mission2.txt5");
		}
		if (this.eventTime == 800)
		{
			this.isChat("tf.mission2.txt6");
		}

		if (this.count > 0)
		{
			if (this.eventTime >= 900 && this.eventTime <= 4000)
			{
				if (this.ticksExisted % 200 == 0)
				{
					EntityTM06 var7 = new EntityTM06(this.world);
					this.isSpawn(var7, 20F);
				}
			}

			if (this.eventTime > 4400)
			{
				this.setDead();
			}
		}
	}

	public void isClear()
	{
		if (this.eventTime2 == 100)
		{
			this.isChat("tf.mission2.txt7");
		}
		if (this.eventTime2 == 200)
		{
			this.isChat("tf.mission2.txt8");
		}
		if (this.eventTime2 == 400)
		{
			this.isChat("tf.mission2.txt9");
		}
		if (this.eventTime2 == 500)
		{
			this.isChat("tf.mission2.txt10");

			List<EntityPlayerMP> k = this.world.getEntitiesWithinAABB(EntityPlayerMP.class, this.getEntityBoundingBox().grow(40.0D));
			for (int u = 0; u < k.size(); ++u)
			{
				EntityPlayerMP playerall = k.get(u);
				TFAdvancements.MISSION_02.trigger(playerall);
			}

			TF2Core.config.getCategory("all").get("tf.config.tier0").set(true);
			TF2Core.syncConfig();
		}
		if (this.eventTime2 > 600)
		{
			this.setDead();
		}
	}

	protected void isSpawn(EntityLivingBase var1, float range)
	{
		if (!this.world.isRemote)
		{
			double t = this.rand.nextDouble() * 2 * Math.PI;

			var1.posX = this.posX + 0.5D + range * Math.sin(t);
			var1.posZ = this.posZ + 0.5D + range * Math.cos(t);
			var1.posY = this.posY + 15 + (10 * (Math.random() - 0.5));

			int i = MathHelper.floor(var1.posX);
			int j = MathHelper.floor(var1.posY);
			int k = MathHelper.floor(var1.posZ);

			BlockPos blockpos = new BlockPos(i, j, k);

			if (world.isBlockLoaded(blockpos))
			{
				boolean flag1 = false;

				while (!flag1 && j > 0)
				{
					BlockPos blockpos1 = blockpos.down();
					IBlockState iblockstate = world.getBlockState(blockpos1);

					if (iblockstate.getMaterial().blocksMovement())
					{
						flag1 = true;
					}
					else
					{
						--var1.posY;
						--j;
						blockpos = blockpos1;
					}
				}

				if (flag1)
				{
					var1.setPosition((double) i, (double) j, (double) k);
					var1.setLocationAndAngles((double) i, (double) j, (double) k, this.rand.nextFloat() * 360.0F, 0.0F);
				}
			}
			this.world.spawnEntity(var1);
		}
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
	{
		double var3 = target.posX - this.posX;
		double var8 = target.posY - this.posY - 0.3F;
		double var5 = target.posZ - this.posZ;

		if (this.attackTime <= 24 && this.attackTime % 4 == 0)
		{
			EntityFriendBullet var7 = new EntityFriendBullet(this.world, this);
			var7.setDamage(var7.getDamage() + 1.0D);
			this.playSound(TFSoundEvents.HK416, 2.3F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
			var7.shoot(var3, var8, var5, 2.0F, 3.0F);
			this.world.spawnEntity(var7);
		}
		if (this.attackTime <= 0)
		{
			this.attackTime = 80;
		}
	}

	@Override
	public void setSwingingArms(boolean swingingArms)
	{}
}
