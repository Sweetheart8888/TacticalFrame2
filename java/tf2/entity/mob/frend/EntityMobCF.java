package tf2.entity.mob.frend;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import tf2.TF2Core;
import tf2.common.MessageKeyPressed;
import tf2.common.PacketHandler;

public class EntityMobCF extends EntityFriendMecha
{
	public boolean serverLeftclick;
	public boolean serverRightclick;
	public boolean serverBoost;
	public boolean serverGetoff;
	public boolean serverShift;

	public int limitLeftclick;
	public int limitRightclick;
	public int limitShift;

	public int stackLeftAmmo;
	public int maxStackLeftAmmo;
	public int reloadLeftAmmo;
	public int reloadTimeLeftAmmo;

	public int stackRightAmmo;
	public int maxStackRightAmmo;
	public int reloadRightAmmo;
	public int reloadTimeRightAmmo;

	public int maxBoostStack;
	public int boostStack;
	public int disBoost;

	public int cooltimeLeftclick;
	public int cooltimeRightclick;
	public int cooltimeShift;

	private static final DataParameter<Integer> LEFT_AMMO = EntityDataManager.<Integer> createKey(EntityMobCF.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> RIGHT_AMMO = EntityDataManager.<Integer> createKey(EntityMobCF.class, DataSerializers.VARINT);

	public EntityMobCF(World worldIn, byte slotSize, byte maxLevel, double defaultDamage, double upAttack, double defaultArmor, double upArmor, double defaultArmorToughness, double upArmorToughness, double defaultMaxHealth, double upMaxHealth,
			int maxBoostStackIn, int limitLeftclickIn, int limitRightclickIn, int limitShiftIn, int maxStackLeftAmmoIn, int maxStackRightAmmoIn, int reloadTimeLeftIn, int reloadTimeRightIn)
	{
		super(worldIn, slotSize, maxLevel, defaultDamage, upAttack, defaultArmor, upArmor, defaultArmorToughness, upArmorToughness, defaultMaxHealth, upMaxHealth, true);
		this.maxBoostStack = maxBoostStackIn;
		this.limitLeftclick = limitLeftclickIn;
		this.limitRightclick = limitRightclickIn;
		this.limitShift = limitShiftIn;
		this.maxStackLeftAmmo = maxStackLeftAmmoIn - 1;
		this.maxStackRightAmmo = maxStackRightAmmoIn - 1;
		this.reloadTimeLeftAmmo = reloadTimeLeftIn;
		this.reloadTimeRightAmmo = reloadTimeRightIn;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.disBoost = 199;
	}

	@Override
	protected void initEntityAI()
	{}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(LEFT_AMMO, Integer.valueOf(0));
		this.dataManager.register(RIGHT_AMMO, Integer.valueOf(1));
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();

		if (!world.isRemote)
		{
			this.setLeftAmmo();
			this.setRightAmmo();
		}
		else
		{
			this.stackLeftAmmo = this.dataManager.get(LEFT_AMMO).intValue();
			this.stackRightAmmo = this.dataManager.get(RIGHT_AMMO).intValue();
		}

		int boostRegen = 10;
		if (this.boostStack < this.maxBoostStack)
		{
			if (this.ticksExisted % boostRegen == 0)
			{
				++this.boostStack;
			}
		}
	}

	public void setLeftAmmo()
	{
		this.dataManager.set(LEFT_AMMO, new Integer(this.stackLeftAmmo));
	}

	public void setRightAmmo()
	{
		this.dataManager.set(RIGHT_AMMO, new Integer(this.stackRightAmmo));
	}

	@Override
	protected boolean canDespawn()
	{
		return false;
	}

	@Override
	public void setInWeb()
	{}

	//水中で騎乗が解除されるかどうか
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
		if (entity instanceof EntityMobCF)
		{
			return false;
		}
		return this.isBeingRidden() && entity != null && this.isRidingOrBeingRiddenBy(entity) ? false : super.attackEntityFrom(source, amount);
	}

	@Override
	public boolean isPotionApplicable(PotionEffect potioneffectIn)
	{
		if (potioneffectIn.getPotion() == MobEffects.POISON || potioneffectIn.getPotion() == MobEffects.WITHER)
		{
			return false;
		}
		return super.isPotionApplicable(potioneffectIn);
	}

	//	@Override
	//	public boolean processInteract(EntityPlayer player, EnumHand hand)
	//	{
	//		if (!super.processInteract(player, hand))
	//		{
	//			if (!this.world.isRemote && this.getPassengers().size() < 2 && player.isSneaking())
	//			{
	//				player.startRiding(this);
	//				return true;
	//			}
	//			else
	//			{
	//				return false;
	//			}
	//		}
	//		else
	//		{
	//			return true;
	//		}
	//	}

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

	//搭乗者の位置を常に前方に置く
	@Override
	public void updatePassenger(Entity passenger)
	{
		if (this.isPassenger(passenger))
		{
			passenger.setPosition(this.calPosX(passenger), this.calPosY(passenger), this.calPosZ(passenger));
		}
	}

	public double calPosX(Entity passenger)
	{
		double ix = 0;
		float f1 = passenger.rotationYaw * (2 * (float) Math.PI / 360);
		ix -= MathHelper.sin(f1) * 1.1D;
		return this.posX + ix;
	}

	public double calPosY(Entity passenger)
	{
		return this.posY + this.getMountedYOffset() + passenger.getYOffset();
	}

	public double calPosZ(Entity passenger)
	{
		double iz = 0;
		float f1 = passenger.rotationYaw * (2 * (float) Math.PI / 360);
		iz += MathHelper.cos(f1) * 1.1D;
		return this.posZ + iz;
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
			boolean left = TF2Core.proxy.leftclick();
			boolean right = TF2Core.proxy.rightclick();
			boolean shift = TF2Core.proxy.shift();
			boolean getoff = TF2Core.proxy.getoff();

			if (getoff)
			{
				PacketHandler.INSTANCE.sendToServer(new MessageKeyPressed(10, this.getEntityId()));
				this.serverGetoff = true;
			}
			if (left)
			{
				PacketHandler.INSTANCE.sendToServer(new MessageKeyPressed(11, this.getEntityId()));
				this.serverLeftclick = true;
			}
			if (right)
			{
				PacketHandler.INSTANCE.sendToServer(new MessageKeyPressed(12, this.getEntityId()));
				this.serverRightclick = true;
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

			if (this.cooltimeLeftclick < this.limitLeftclick)
			{
				++this.cooltimeLeftclick;
			}
			if (this.cooltimeRightclick < this.limitRightclick)
			{
				++this.cooltimeRightclick;
			}
			if (this.cooltimeShift < this.limitShift)
			{
				++this.cooltimeShift;
			}

			if (this.stackLeftAmmo > this.maxStackLeftAmmo)
			{
				++this.reloadLeftAmmo;
			}
			if (this.reloadLeftAmmo > this.reloadTimeLeftAmmo)
			{
				this.stackLeftAmmo = 0;
				this.reloadLeftAmmo = 0;
			}

			if (this.stackRightAmmo > this.maxStackRightAmmo)
			{
				++this.reloadRightAmmo;
			}
			if (this.reloadRightAmmo > this.reloadTimeRightAmmo)
			{
				this.stackRightAmmo = 0;
				this.reloadRightAmmo = 0;
			}

			if (this.disBoost < 200)
			{
				++this.disBoost;
			}
			if (this.boostStack <= 0)
			{
				this.disBoost = 0;
			}

			if (this.serverLeftclick)
			{
				if (this.cooltimeLeftclick >= this.limitLeftclick && this.stackLeftAmmo <= this.maxStackLeftAmmo)
				{
					this.onLeftClick(this.world, entitylivingbase);
					this.cooltimeLeftclick = 0;
					++this.stackLeftAmmo;
				}
				this.serverLeftclick = false;
			}
			if (this.serverRightclick)
			{
				if (this.cooltimeRightclick >= this.limitRightclick && this.stackRightAmmo <= this.maxStackRightAmmo)
				{
					this.onRightClick(this.world, entitylivingbase);
					this.cooltimeRightclick = 0;
					++this.stackRightAmmo;
				}
				this.serverRightclick = false;
			}
			if (this.serverBoost)
			{
				if (this.boostStack > 0 && this.disBoost >= 200)
				{
					this.onJumped(this.world, entitylivingbase);
					--this.boostStack;
				}
				this.serverBoost = false;
			}
			if (this.serverGetoff)
			{
				if (!this.getPassengers().isEmpty())
				{
					this.getPassengers().get(0).setSneaking(true);
				}
				this.serverGetoff = false;
			}

			if (this.serverShift)
			{
				if (cooltimeShift >= limitShift)
				{
					this.onShift(this.world, entitylivingbase);
					this.cooltimeShift = 0;
				}
				this.serverShift = false;
			}
		}
	}

	public void onLeftClick(World world, EntityPlayer player)
	{}

	public void onRightClick(World world, EntityPlayer player)
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

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setInteger("BoostStack", this.boostStack);
		compound.setInteger("StackLeftAmmo", this.stackLeftAmmo);
		compound.setInteger("StackRightAmmo", this.stackRightAmmo);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		this.boostStack = compound.getInteger("BoostStack");
		this.stackLeftAmmo = compound.getInteger("StackLeftAmmo");
		this.stackRightAmmo = compound.getInteger("StackRightAmmo");
	}

	@Override
	public ItemStack getSkillUnique()
	{
		return null;
	}
}
