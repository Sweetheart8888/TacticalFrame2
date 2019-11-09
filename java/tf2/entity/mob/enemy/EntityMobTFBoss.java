package tf2.entity.mob.enemy;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityMobTFBoss extends EntityMobTF
{
	private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS));

	public int healthCount;
	private static final DataParameter<Integer> HEALTH_COUNT = EntityDataManager.<Integer> createKey(EntityMobTFBoss.class, DataSerializers.VARINT);

	public byte animationNumber;
	public int animationTime;

	private static final DataParameter<Byte> ANIMATION_NUMBER = EntityDataManager.<Byte> createKey(EntityMobTFBoss.class, DataSerializers.BYTE);
	private static final DataParameter<Integer> ANIMATION_TIME = EntityDataManager.<Integer> createKey(EntityMobTFBoss.class, DataSerializers.VARINT);

	public EntityMobTFBoss(World par1World, int healthCountIn)
    {
        super(par1World);
        this.healthCount = healthCountIn;
    }

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(HEALTH_COUNT, Integer.valueOf(0));
		this.dataManager.register(ANIMATION_NUMBER, Byte.valueOf((byte)0));
		this.dataManager.register(ANIMATION_TIME, Integer.valueOf(0));
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();
		if (!world.isRemote)
		{
			this.setHealthCount();
			this.setAnimationTime();
		}
		else
		{
			this.healthCount = this.dataManager.get(HEALTH_COUNT).intValue();
			this.animationTime = this.dataManager.get(ANIMATION_TIME).intValue();
		}

		if (this.animationTime > 0)
		{
			--this.animationTime;
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setByte("AnimationNumber", this.animationNumber);
		compound.setInteger("AnimationTime", this.animationTime);
		compound.setInteger("HealthCount", this.healthCount);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		this.animationNumber = compound.getByte("AnimationNumber");
		this.animationTime = compound.getInteger("AnimationTime");
		this.healthCount = compound.getInteger("HealthCount");
	}

	public void setAnimetion(byte number, int animationTimeIn)
	{
		this.animationTime = animationTimeIn;
		this.dataManager.set(ANIMATION_NUMBER, Byte.valueOf(number));
	}

	public void setHealthCount()
	{
		this.dataManager.set(HEALTH_COUNT, new Integer(this.healthCount));
	}
	public void setAnimationTime()
	{
		this.dataManager.set(ANIMATION_TIME, new Integer(this.animationTime));
	}
	public int getAnimationTime()
	{
		return this.dataManager.get(ANIMATION_TIME);
	}
	public byte getAnimationNumber()
	{
		return this.dataManager.get(ANIMATION_NUMBER);
	}
	public int getHealthCount()
	{
		return this.dataManager.get(HEALTH_COUNT);
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
		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
	}

	@Override
	protected boolean canDespawn()
	{
		return false;
	}

//	@Override
//	protected SoundEvent getDeathSound()
//	{
//		if(this.healthCount > 0)
//		{
//			return SoundEvents.ENTITY_IRONGOLEM_HURT;
//		}
//		else
//		{
//			return TFSoundEvents.TF_DEATH;
//		}
//	}

	@Override
	protected void onDeathUpdate()
	{
		if(this.healthCount == 5)
		{
			bossInfo.setColor(BossInfo.Color.BLUE);
		}
		else if(this.healthCount == 4)
		{
			bossInfo.setColor(BossInfo.Color.GREEN);
		}
		else if(this.healthCount == 3)
		{
			bossInfo.setColor(BossInfo.Color.YELLOW);
		}
		else if(this.healthCount == 2)
		{
			bossInfo.setColor(BossInfo.Color.RED);
		}
		else if(this.healthCount == 1)
		{
			bossInfo.setColor(BossInfo.Color.PINK);
		}
		if(this.healthCount > 0)
		{
			--this.healthCount;
			this.setHealth(this.getMaxHealth());
			this.onDeathCountEvent();
			return;
		}
		else
		{
			this.onDeathMotion();
		}
	}

	protected void onDeathCountEvent(){}

	protected void onDeathMotion()
	{
		++this.deathTicks;

		if (this.deathTicks >= 50 && this.deathTicks <= 70)
		{
			float f = (this.rand.nextFloat() - 0.5F) * 4.0F;
			float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F;
			float f2 = (this.rand.nextFloat() - 0.5F) * 4.0F;
			this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + (double) f, this.posY + 2.0D + (double) f1, this.posZ + (double) f2, 0.0D, 0.0D, 0.0D, new int[0]);
		}

		if (this.deathTicks % 4 == 0 && this.deathTicks <= 50)
		{
			float f = (this.rand.nextFloat() - 0.5F) * 4.0F;
			float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F;
			float f2 = (this.rand.nextFloat() - 0.5F) * 4.0F;
			this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + (double) f, this.posY + 2.0D + (double) f1, this.posZ + (double) f2, 0.0D, 0.0D, 0.0D, new int[0]);
			this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.5F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		}

		if (this.deathTicks == 70 && !this.world.isRemote)
		{
			this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, this.experienceValue));
			this.setDead();
		}
	}
}
