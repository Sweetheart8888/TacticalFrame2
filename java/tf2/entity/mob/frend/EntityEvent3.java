package tf2.entity.mob.frend;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
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
import net.minecraft.world.World;
import tf2.TF2Core;
import tf2.TFItems;
import tf2.TFSoundEvents;
import tf2.entity.EntityDimension;
import tf2.entity.mob.ai.EntityAIAttackRangedGun;
import tf2.entity.mob.enemy.EntityTM02;
import tf2.entity.mob.enemy.EntityTM04;
import tf2.entity.mob.enemy.EntityTM05;
import tf2.entity.mob.enemy.EntityTM06;
import tf2.entity.mob.enemy.EntityTM07;
import tf2.entity.mob.enemy.EntityTM11;
import tf2.entity.projectile.player.EntityFriendBullet;
import tf2.util.TFAdvancements;

public class EntityEvent3 extends EntityMobNPC implements IRangedAttackMob
{
	private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getScoreName(), BossInfo.Color.RED, BossInfo.Overlay.NOTCHED_6));

	public int count;
	public int maxCount;
	private static final DataParameter<Integer> COUNT = EntityDataManager.<Integer> createKey(EntityEvent3.class, DataSerializers.VARINT);

	protected int eventTime2;
	private static final DataParameter<Integer> EVENT_TIME2 = EntityDataManager.<Integer> createKey(EntityEvent3.class, DataSerializers.VARINT);

	public int attackTime;

	public EntityEvent3(World worldIn)
	{
		super(worldIn);
		this.setSize(2.1F, 4.3F);
		this.count = 12;
		this.maxCount = count;
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackRangedGun(this, 1.0D, 20.0F));
		this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityMob.class, true));
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
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
	}

	public ITextComponent getScoreName()
    {
        return new TextComponentTranslation("tf.mission.potential", new Object[] {});
    }
	@Override
	protected SoundEvent getAmbientSound()
	{
		return TFSoundEvents.TM_SAY;
	}
	@Override
	protected SoundEvent getDeathSound()
	{
		return TFSoundEvents.TM_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundEvents.ENTITY_IRONGOLEM_HURT;
	}
	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{}
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
		if (this.eventTime == 100)
		{
			EntityLicoris var7 = new EntityLicoris(this.world);
			var7.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(TFItems.HK416));
			this.isSpawn(var7, 3F);
		}
		if (this.eventTime == 200)
		{
			this.isChat("tf.mission3.txt1");
		}
		if (this.eventTime == 300)
		{
			this.isChat("tf.mission3.txt2");
		}
		if (this.eventTime == 400)
		{
			this.isChat("tf.mission3.txt3");
		}
		if (this.eventTime == 500)
		{
			this.isChat("tf.mission3.txt4");
		}
		if (this.eventTime == 700)
		{
			this.isChat("tf.mission3.txt5");
		}
		if (this.eventTime == 900)
		{
			this.isChat("tf.mission3.txt6");
		}

		if (this.count > 0)
		{
			if (this.eventTime >= 900 && this.eventTime <= 4000)
			{
				if (this.ticksExisted % 200 == 0)
				{
					EntityTM02 var7 = new EntityTM02(this.world);
					this.isSpawn(var7, 25F);
				}
			}
			if (this.eventTime == 4350)
			{
				List var71 = this.world.getEntitiesWithinAABB(EntityLicoris.class, this.getEntityBoundingBox().grow(40.0D));
				int var31;
				for (var31 = 0; var31 < var71.size(); ++var31)
				{
					EntityLicoris var8 = (EntityLicoris) var71.get(var31);
					var8.setDead();
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
			this.isChat("tf.mission3.txt7");
		}
		if (this.eventTime2 == 300)
		{
			this.isChat("tf.mission3.txt8");
		}
		if (this.eventTime2 == 500)
		{
			List var71 = this.world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(40.0D));
			int var31;
			for (var31 = 0; var31 < var71.size(); ++var31)
			{
				EntityPlayer var8 = (EntityPlayer) var71.get(var31);
				var8.playSound(TFSoundEvents.QUAKE, 1.0F, 1.0F);
			}
		}

		if (this.eventTime2 >= 500 && this.eventTime2 <= 1000)
		{
			List var71 = this.world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(40.0D));
			int var31;
			for (var31 = 0; var31 < var71.size(); ++var31)
			{
				EntityPlayer var8 = (EntityPlayer) var71.get(var31);

				if (this.eventTime % 2 == 0)
				{
					var8.rotationPitch += 2F;
				}
				if (this.eventTime % 2 == 1)
				{
					var8.rotationPitch -= 2F;
				}
			}
		}

		if (this.eventTime2 == 550)
		{
			this.isChat("tf.mission3.txt9");
		}
		if (this.eventTime2 == 650)
		{
			this.isChat("tf.mission3.txt10");
		}
		if (this.eventTime2 == 800)
		{
			this.isChat("tf.mission3.txt11");

			if (!this.world.isRemote)
			{
				EntityDimension var7 = new EntityDimension(this.world);
				var7.setPosition((double) this.posX - 5D, (double) this.posY + 40D, (double) this.posZ - 5D);
				this.world.spawnEntity(var7);
			}
			List var71 = this.world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(40.0D));
			int var31;
			for (var31 = 0; var31 < var71.size(); ++var31)
			{
				EntityPlayer var8 = (EntityPlayer) var71.get(var31);
				var8.rotationPitch = -90F;
			}
		}
		if (this.eventTime2 == 900)
		{
			this.isChat("tf.mission3.txt12");
		}
		if (this.eventTime2 == 1000)
		{
			this.isChat("tf.mission3.txt13");

			EntityTM02 var7 = new EntityTM02(this.world);
			this.isSpawn(var7, 25F);

			EntityTM04 var8 = new EntityTM04(this.world);
			this.isSpawn(var8, 25F);

			EntityTM11 var9 = new EntityTM11(this.world);
			this.isSpawn(var9, 25F);
		}
		if (this.eventTime2 == 1200)
		{
			this.isChat("tf.mission3.txt14");
		}
		if (this.eventTime2 == 1300)
		{
			this.isChat("tf.mission3.txt15");
		}

		if (this.eventTime2 >= 1000 && this.eventTime2 <= 3600)
		{

			if (this.ticksExisted % 150 == 0)
			{
				this.isSpawn(this.randomEntity(), 25F);
			}
			if (TF2Core.CONFIG.multiMission)
			{
				if (this.ticksExisted % 80 == 0)
				{
					this.isSpawn(this.randomEntity(), 25F);
				}
			}
		}

		if (this.eventTime2 == 3700)
		{
			this.isChat("tf.mission3.txt16");

			List var71 = this.world.getEntitiesWithinAABB(EntityMob.class, this.getEntityBoundingBox().grow(40.0D));
			int var31;
			for (var31 = 0; var31 < var71.size(); ++var31)
			{
				EntityMob var8 = (EntityMob) var71.get(var31);
				var8.attackEntityFrom(DamageSource.OUT_OF_WORLD, 100.0F);
			}
		}
		if (this.eventTime2 == 3800)
		{
			this.isChat("tf.mission3.txt17");
		}
		if (this.eventTime2 == 4000)
		{
			this.isChat("tf.mission3.txt18");

			List<EntityPlayerMP> k = this.world.getEntitiesWithinAABB(EntityPlayerMP.class, this.getEntityBoundingBox().grow(40.0D));
			for (int u = 0; u < k.size(); ++u)
			{
				EntityPlayerMP playerall = k.get(u);
				TFAdvancements.MISSION_03.trigger(playerall);
			}

			TF2Core.config.getCategory("all").get("tf.config.tier1").set(true);
			TF2Core.syncConfig();
		}
		if (this.eventTime2 == 4200)
		{
			this.isChat("tf.mission3.txt19");
		}
		if (this.eventTime2 == 4250)
		{
			List var71 = this.world.getEntitiesWithinAABB(EntityLicoris.class, this.getEntityBoundingBox().grow(40.0D));
			int var31;
			for (var31 = 0; var31 < var71.size(); ++var31)
			{
				EntityLicoris var8 = (EntityLicoris) var71.get(var31);
				var8.setDead();
			}
		}
		if (this.eventTime2 > 4300)
		{
			this.setDead();
		}
	}

	public EntityLivingBase randomEntity()
    {
        Random random = new Random();
        int i = random.nextInt(6);

        switch (i)
        {
            case 0:
                return new EntityTM02(this.world);
            case 1:
                return new EntityTM04(this.world);
            case 2:
                return new EntityTM05(this.world);
            case 3:
                return new EntityTM06(this.world);
            case 4:
                return new EntityTM07(this.world);
            case 5:
                return new EntityTM11(this.world);

            default:
                return new EntityTM04(this.world);
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
		double var8 = target.posY - this.posY - 1.9D;
		double var5 = target.posZ - this.posZ;

		if (this.attackTime <= 50 && this.attackTime % 10 == 0)
		{
			EntityFriendBullet var7 = new EntityFriendBullet(this.world, this);
			var7.setDamage(var7.getDamage() + 7.0D);
			this.playSound(TFSoundEvents.AK, 2.3F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
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
