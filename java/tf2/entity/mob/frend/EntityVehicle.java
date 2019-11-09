package tf2.entity.mob.frend;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import tf2.TF2Core;
import tf2.common.MessageKeyPressed;
import tf2.common.PacketHandler;

public abstract class EntityVehicle extends EntityFriendMecha
{
	public boolean serverFrontmove;
	public boolean serverBackmove;
	public boolean serverLeftmove;
	public boolean serverRightmove;
	public boolean serverBoost;
	public boolean serverGetoff;
	public boolean serverShift;

	public EntityVehicle(World worldIn, double defaultDamage, double defaultArmor, double defaultArmorToughness, double defaultMaxHealth)
	{
		super(worldIn, (byte)0, (byte)0, defaultDamage, 0, defaultArmor, 0, defaultArmorToughness,
				0, defaultMaxHealth, 0, true);
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
	public boolean shouldDismountInWater(Entity rider)
	{
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (source.damageType == "starve" || source.damageType == "inWall" || source.damageType == "flyIntoWall" || source.damageType == "cactus" || source.damageType == "inFire" || source.damageType == "onFire" || source.damageType == "hotFloor"
				|| source.damageType == "fall")
		{
			return false;
		}

		Entity entity = source.getTrueSource();
		if (entity instanceof EntityPlayer)
		{
			return this.isBeingRidden() ? false : super.attackEntityFrom(source, amount);
		}
		//		if (entity instanceof EntityMobFriend)
		//		{
		//			return false;
		//		}
		if (entity instanceof EntityVehicle)
		{
			return false;
		}
		return this.isBeingRidden() && entity != null && this.isRidingOrBeingRiddenBy(entity) ? false : super.attackEntityFrom(source, amount);
	}

	@Override
	public boolean isPotionApplicable(PotionEffect potioneffectIn)
	{
		return false;
	}


	@Override
	public boolean canBeSteered()
	{
		Entity entity = this.getControllingPassenger();
		return entity instanceof EntityLivingBase;
	}

	@Override
	@Nullable
	public Entity getControllingPassenger()
	{
		return this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
	}

	@Override
	public boolean canPassengerSteer()
	{
		Entity entity = this.getControllingPassenger();
		return entity instanceof EntityPlayer ? ((EntityPlayer) entity).isUser() : !this.world.isRemote;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		//this.maxBoostStack = 40;

		if (this.canBeSteered() && this.getControllingPassenger() != null && this.getHealth() > 0.0F)
		{
			EntityPlayer entitylivingbase = (EntityPlayer) this.getControllingPassenger();

			boolean jump = TF2Core.proxy.jumped();
			boolean front = TF2Core.proxy.frontmove();
			boolean back = TF2Core.proxy.backmove();
			boolean left = TF2Core.proxy.leftmove();
			boolean right = TF2Core.proxy.rightmove();
			boolean shift = TF2Core.proxy.shift();
			boolean getoff = TF2Core.proxy.getoff();

			if (getoff)
			{
				PacketHandler.INSTANCE.sendToServer(new MessageKeyPressed(10, this.getEntityId()));
				this.serverGetoff = true;
			}
			if (front)
			{
				PacketHandler.INSTANCE.sendToServer(new MessageKeyPressed(17, this.getEntityId()));
				this.serverFrontmove = true;
			}
			if (back)
			{
				PacketHandler.INSTANCE.sendToServer(new MessageKeyPressed(18, this.getEntityId()));
				this.serverBackmove = true;
			}
			if (left)
			{
				PacketHandler.INSTANCE.sendToServer(new MessageKeyPressed(15, this.getEntityId()));
				this.serverLeftmove = true;
			}
			if (right)
			{
				PacketHandler.INSTANCE.sendToServer(new MessageKeyPressed(16, this.getEntityId()));
				this.serverRightmove = true;
			}
			if (jump)
			{
				PacketHandler.INSTANCE.sendToServer(new MessageKeyPressed(13, this.getEntityId()));
				this.serverBoost = true;
			}
			if (shift)
			{
				PacketHandler.INSTANCE.sendToServer(new MessageKeyPressed(14, this.getEntityId()));
				this.serverShift = true;
			}

			if (this.serverFrontmove)
			{
				this.onFrontMove(this.world, entitylivingbase);
				this.serverFrontmove = false;
			}
			if (this.serverBackmove)
			{
				this.onBackMove(this.world, entitylivingbase);
				this.serverBackmove = false;
			}
			if (this.serverLeftmove)
			{
				this.onLeftMove(this.world, entitylivingbase);
				this.serverLeftmove = false;
			}
			if (this.serverRightmove)
			{
				this.onRightMove(this.world, entitylivingbase);
				this.serverRightmove = false;
			}
			if (this.serverGetoff)
			{
				if (!this.getPassengers().isEmpty())
				{
					this.getPassengers().get(0).setSneaking(true);
				}
				this.serverGetoff = false;
			}
			if (this.serverBoost)
			{
				this.onJumped(this.world, entitylivingbase);
				this.serverBoost = false;
			}
			if (this.serverShift)
			{
				this.onShift(world, entitylivingbase);
				this.serverShift = false;
			}
		}
	}

	public void onFrontMove(World world, EntityPlayer player)
	{}

	public void onBackMove(World world, EntityPlayer player)
	{}

	public void onLeftMove(World world, EntityPlayer player)
	{}

	public void onRightMove(World world, EntityPlayer player)
	{}

	public void onJumped(World world, EntityPlayer player)
	{}

	public void onShift(World world, EntityPlayer player)
	{}

	@Override
	public void onLivingUpdate()
	{
		if (!this.getPassengers().isEmpty())
		{
			if (this.getPassengers().get(0).isSneaking())
			{
				this.getPassengers().get(0).setSneaking(false);
			}
		}
		super.onLivingUpdate();
	}
}
