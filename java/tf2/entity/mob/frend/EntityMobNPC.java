package tf2.entity.mob.frend;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMobNPC extends EntityGolem
{
	public int deathTicks;
	protected int eventTime;
	private static final DataParameter<Integer> EVENT_TIME = EntityDataManager.<Integer> createKey(EntityMobNPC.class, DataSerializers.VARINT);

	public EntityMobNPC(World par1World)
	{
		super(par1World);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(EVENT_TIME, Integer.valueOf(0));
	}
	@Override
	protected boolean canDespawn()
	{
		return false;
	}

	@Override
	public void setInWeb()
	{}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		Entity entity = source.getTrueSource();
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			return !player.capabilities.isCreativeMode ? false : super.attackEntityFrom(source, amount);
		}
		if(entity instanceof EntityMobNPC)
		{
			return false;
		}
		if(entity instanceof EntityFriendMecha)
		{
			return false;
		}
		return super.attackEntityFrom(source, amount);
	}

	//--------ここからアニメーション関係
	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();

		if (!world.isRemote)
		{
			this.setEventTime();
		}
		else
		{
			this.eventTime = this.dataManager.get(EVENT_TIME).intValue();
		}
		++this.eventTime;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setInteger("EventTime", this.eventTime);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		this.eventTime = compound.getInteger("EventTime");
	}

	public void setEventTime()
	{
		this.dataManager.set(EVENT_TIME, new Integer(this.eventTime));
	}
}
