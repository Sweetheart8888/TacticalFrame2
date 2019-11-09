package tf2.entity.mob.frend;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import tf2.TF2Core;
import tf2.TFItems;
import tf2.entity.mob.ai.EntityAIAttackRangedGun;
import tf2.items.ItemSpawnFriendMecha;
import tf2.items.skill.friendskill.ItemMechaSkillBase;
import tf2.tile.InventoryFriendMechaEquipment;
import tf2.util.Reference;

public abstract class EntityFriendMecha extends EntityGolem
{
	private InventoryFriendMechaEquipment inventoryMechaEquipment;
	public final byte slotSize;
	public final byte maxLevel;

	public final double defaultDamage;

	public final double defaultArmor;
	public final double defaultArmorToughness;
	public final double defaultMaxHealth;

	public final double upDamage;
	public final double upArmor;
	public final double upArmorToughness;
	public final double upMaxHealth;

	@Nullable
	private EntityPlayer accessPlayer;

	public final boolean canBeingRidden;

	protected int attackTime;

	private static final DataParameter<String> OWNER_UUID = EntityDataManager.<String> createKey(EntityFriendMecha.class, DataSerializers.STRING);
	private static final DataParameter<String> OWNER_NAME = EntityDataManager.<String> createKey(EntityFriendMecha.class, DataSerializers.STRING);

	private static final DataParameter<Integer> MECHA_ATK = EntityDataManager.<Integer> createKey(EntityFriendMecha.class, DataSerializers.VARINT);

	private static final DataParameter<Integer> MECHA_LEVEL = EntityDataManager.<Integer> createKey(EntityFriendMecha.class, DataSerializers.VARINT);

	protected static final DataParameter<Byte> MECHA_MODE = EntityDataManager.<Byte> createKey(EntityFriendMecha.class, DataSerializers.BYTE);

	private static final List<UUID> warnedFails = new ArrayList<>();

	public EntityFriendMecha(World worldIn, byte maxSlot, byte maxLevel, double defaultDamage, double upDamage, double defaultArmor, double upArmor,
			double defaultArmorToughness, double upArmorToughness, double defaultMaxHealth, double upMaxHealth, boolean canRide)
	{
		super(worldIn);
		this.slotSize = maxSlot;
		this.maxLevel = maxLevel;
		this.defaultDamage = defaultDamage;
		this.upDamage = upDamage;
		this.defaultArmor = defaultArmor;
		this.upArmor = upArmor;
		this.defaultArmorToughness = defaultArmorToughness;
		this.upArmorToughness = upArmorToughness;
		this.defaultMaxHealth = defaultMaxHealth;
		this.upMaxHealth = upMaxHealth;
		this.canBeingRidden = canRide;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(OWNER_UUID, "");
		dataManager.register(OWNER_NAME, "");
		this.dataManager.register(MECHA_ATK, Integer.valueOf(0));
		this.dataManager.register(MECHA_LEVEL, Integer.valueOf(0));
		dataManager.register(MECHA_MODE, Byte.valueOf((byte) 0));

	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(defaultMaxHealth + (int) (this.getMechaLevel() * upMaxHealth));
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(defaultArmor + (int) (this.getMechaLevel() * upArmor));
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(defaultArmorToughness);
	}

	protected void initEntityAI()
	{
		super.initEntityAI();
		this.tasks.addTask(1, new EntityFriendMecha.EntityAIAccessPlayer(this));
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();
		if (this.getMechaMode() == 3 && this.getRidingEntity() == null)
		{
			this.setMechaMode((byte) 0);
		}
		if (this.attackTime > 0)
		{
			--this.attackTime;
		}
		if (!world.isRemote)
		{
			if (this.getAttackTarget() != null && !this.getAttackTarget().isEntityAlive())
			{
				this.setAttackTarget(((EntityLivingBase) null));
			}
		}
	}

	@Override
	public void setDead()
	{
		int mechaID = 0;
		Boolean dropedItem = false;

		Class mechaClass = this.getClass();

		for (mechaID = 0; mechaID < ItemSpawnFriendMecha.spawnableEntities.length; mechaID++)
		{
			if (ItemSpawnFriendMecha.spawnableEntities[mechaID] == mechaClass)
			{
				break;
			}
		}

		ItemStack itemstack = new ItemStack(TFItems.SPAWNFM, 1, mechaID);

		NBTTagCompound nbt = itemstack.getTagCompound();
		if (nbt == null)
		{
			nbt = new NBTTagCompound();

			nbt.setInteger("tf.mechaLevel", this.getMechaLevel());
			nbt.setInteger("tf.mechaHealth", (int) this.getHealth());
			nbt.setInteger("tf.mechaMaxHealth", (int) this.getMaxHealth());

			nbt.setString("tf.mechaOwner", this.dataManager.get(OWNER_UUID));
			itemstack.setTagCompound(nbt);
		}

		if (!world.isRemote && !nbt.getString("tf.mechaOwner").isEmpty())
		{
			if (this.inventoryMechaEquipment != null)
			{
				if (!this.inventoryMechaEquipment.getSkillAItem().isEmpty())
				{
					Item item = Item.REGISTRY.getObject(this.inventoryMechaEquipment.getSkillAItem().getItem().getRegistryName());
					if (item != null)
					{
						nbt.setString("tf.mechaSkillA", item.getRegistryName().toString());
					}
					else
					{
						nbt.removeTag("tf.mechaSkillA");
					}
				}
				if (!this.inventoryMechaEquipment.getSkillBItem().isEmpty())
				{
					Item item = Item.REGISTRY.getObject(this.inventoryMechaEquipment.getSkillBItem().getItem().getRegistryName());
					if (item != null)
					{
						nbt.setString("tf.mechaSkillB", item.getRegistryName().toString());
					}
					else
					{
						nbt.removeTag("tf.mechaSkillB");
					}
				}
				if (!this.inventoryMechaEquipment.getSkillCItem().isEmpty())
				{
					Item item = Item.REGISTRY.getObject(this.inventoryMechaEquipment.getSkillCItem().getItem().getRegistryName());
					if (item != null)
					{
						nbt.setString("tf.mechaSkillC", item.getRegistryName().toString());
					}
					else
					{
						nbt.removeTag("tf.mechaSkillC");
					}
				}
			}

			if(this.getInventoryMechaEquipment().getHasSkill(TFItems.SKILL_COMEBACK))
			{
				MinecraftServer minecraftserver = this.getServer();
				for(EntityPlayerMP player : minecraftserver.getPlayerList().getPlayers())
				{
					if(this.getOwner() == player)
					{
						player.entityDropItem(itemstack, 0);
						dropedItem = true;
					}
				}

			}
			if(!dropedItem)
			{
				this.entityDropItem(itemstack, 0);
			}
		}

		super.setDead();
	}

	//味方のダメージを受けないように要修正
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{

		Entity entity = source.getTrueSource();

		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;

			if (player.capabilities.isCreativeMode)
			{
				return super.attackEntityFrom(source, amount);
			}
			return false;
		}
		if (entity instanceof EntityMobNPC)
		{
			return false;
		}
		if (entity instanceof EntityFriendMecha)
		{
			return false;
		}

		return super.attackEntityFrom(source, amount);
	}

	public InventoryFriendMechaEquipment getInventoryMechaEquipment()
	{
		if (this.inventoryMechaEquipment == null)
		{
			this.inventoryMechaEquipment = new InventoryFriendMechaEquipment(this);
		}

		return this.inventoryMechaEquipment;
	}

	public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		ItemStack itemstack = player.getHeldItem(hand);
		boolean flag = itemstack.getItem() == Items.NAME_TAG;
		boolean skill = itemstack.getItem() instanceof ItemMechaSkillBase;

		boolean installer0 = itemstack.getItem() == TFItems.UPGRADE_0;
		boolean installer1 = itemstack.getItem() == TFItems.UPGRADE_1;
		boolean installer2 = itemstack.getItem() == TFItems.UPGRADE_2;

		if (flag)
		{
			itemstack.interactWithEntity(player, this, hand);
			return true;
		}
		else if (installer0)
		{
			if (!this.world.isRemote && this.getMechaLevel() < this.maxLevel - 1)
			{
				if (this.getMechaLevel() < 29)
				{
					itemstack.shrink(1);
					this.isUpLevel();
				}
			}
			return true;
		}
		else if (installer1)
		{
			if (!this.world.isRemote && this.getMechaLevel() < this.maxLevel - 1)
			{
				if (this.getMechaLevel() >= 29 && this.getMechaLevel() < 59)
				{
					itemstack.shrink(1);
					this.isUpLevel();
				}
			}
			return true;
		}
		else if (installer2)
		{
			if (!this.world.isRemote && this.getMechaLevel() < this.maxLevel - 1)
			{
				if (this.getMechaLevel() >= 59 && this.getMechaLevel() < 99)
				{
					itemstack.shrink(1);
					this.isUpLevel();
				}
			}
			return true;
		}
		else
		{
			if (!this.world.isRemote)
			{

				if (this.canBeingRidden && player.isSneaking() && player.getCachedUniqueIdString().equals(this.getOwnerUUID().toString()))
				{
					player.startRiding(this);
					return true;
				}
				else
				{
					if(player.isSneaking() && player.getCachedUniqueIdString().equals(this.getOwnerUUID().toString()))
					{
						switch(this.getMechaMode())
						{
							case 0: this.setMechaMode((byte)1);	break;
							case 1: this.setMechaMode((byte)2);	break;
							case 2: this.setMechaMode((byte)0);	break;
						}
						this.playSound(SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, 0.5F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
						return true;
					}
					this.setAccessPlayer(player);
					//if(this.getOwner() != null)s
					if(this.getOwnerUUID() != null)
					{
						player.openGui(TF2Core.INSTANCE, TF2Core.guiTurret, this.getEntityWorld(), this.getEntityId(), 0, 0);
					}
					return false;
				}
			}
			return true;
		}
	}

	@Override
	public boolean getLeashed()
	{
		return false;
	}

	public void isUpLevel()
	{
		if (!this.world.isRemote)
		{
			this.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		}

		this.setMechaLevel(this.getMechaLevel() + 1);
		this.setMechaATK((int) (this.defaultDamage + this.getMechaLevel() * upDamage));
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(defaultMaxHealth + (int) (this.getMechaLevel() * upMaxHealth));
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(defaultArmor + (int) (this.getMechaLevel() * upArmor));
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(defaultArmorToughness + (int) (this.getMechaLevel() * upArmorToughness));
		this.heal((int) upMaxHealth + 1);
	}

	public abstract ItemStack getSkillUnique();

	@Override
	public boolean isPotionApplicable(PotionEffect potioneffectIn)
	{
		Potion potion = potioneffectIn.getPotion();

		if (potion == MobEffects.ABSORPTION ||
				potion == MobEffects.HEALTH_BOOST ||
				potion == MobEffects.REGENERATION ||
				potion == MobEffects.INSTANT_HEALTH)
		{
			return false;
		}

		return super.isPotionApplicable(potioneffectIn);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		if (getOwnerUUID() != null)
		{
			compound.setString("ownerUUID", getOwnerUUID().toString());
		}

		compound.setInteger("mechaATK", (Integer) this.getMechaATK());

		compound.setInteger("mechaLevel", (Integer) this.getMechaLevel());

		compound.setByte("mechaMode", this.getMechaMode());

		compound.setTag(Reference.ENTITY_MECHA_EQUIPMENT, this.getInventoryMechaEquipment().writeInventoryToNBT());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		if (compound.hasKey("ownerUUID"))
		{
			setOwnerUUID(UUID.fromString(compound.getString("ownerUUID")));
		}

		if (compound.hasKey("mechaATK", 99))
		{
			this.setMechaATK(compound.getInteger("mechaATK"));
		}

		if (compound.hasKey("mechaLevel", 99))
		{
			this.setMechaLevel(compound.getInteger("mechaLevel"));
		}

		if (compound.hasKey("mechaMode", 99))
		{
			this.setMechaMode(compound.getByte("mechaMode"));
		}

		this.getInventoryMechaEquipment().readInventoryFromNBT(compound.getTagList(Reference.ENTITY_MECHA_EQUIPMENT, 10));
	}

	public EntityPlayer getOwner()
	{
		return this.getOwnerUUID() == null ? null : world.getPlayerEntityByUUID(this.getOwnerUUID());
	}

	public String getOwnerName()
	{
		return dataManager.get(OWNER_NAME);
	}

	public UUID getOwnerUUID()
	{
		if (dataManager.get(OWNER_UUID).isEmpty())
		{
			return null;
		}

		return UUID.fromString(dataManager.get(OWNER_UUID));
	}

	public void setOwnerUUID(UUID uuid)
	{
		dataManager.set(OWNER_UUID, uuid.toString());
		dataManager.set(OWNER_NAME, this.getLastKnownUsername(uuid));
	}

	public int getMechaATK()
	{
		return (int) this.dataManager.get(MECHA_ATK);
	}

	public void setMechaATK(int amount)
	{
		this.dataManager.set(MECHA_ATK, Integer.valueOf(amount));
	}

	public int getMechaLevel()
	{
		return (int) this.dataManager.get(MECHA_LEVEL);
	}

	public void setMechaLevel(int level)
	{
		this.dataManager.set(MECHA_LEVEL, Integer.valueOf(level));
	}

	public void setMechaMode(byte mode)
	{
		this.dataManager.set(MECHA_MODE, Byte.valueOf(mode));
	}

	public byte getMechaMode()
	{
		return this.dataManager.get(MECHA_MODE);
	}

	protected void setSkillEmptySlot(ItemStack stack)
	{
		if (!world.isRemote)
		{
			this.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 0.5F, 1.0F);
			if (this.getInventoryMechaEquipment() != null)
			{
				if (this.getInventoryMechaEquipment().getSkillAItem().isEmpty()
						&& this.getInventoryMechaEquipment().getSkillBItem().getItem() != stack.getItem()
						&& this.getInventoryMechaEquipment().getSkillCItem().getItem() != stack.getItem())
				{
					this.getInventoryMechaEquipment().setSkillAItem(stack);
				}
				else if (this.getInventoryMechaEquipment().getSkillBItem().isEmpty()
						&& this.getInventoryMechaEquipment().getSkillAItem().getItem() != stack.getItem()
						&& this.getInventoryMechaEquipment().getSkillCItem().getItem() != stack.getItem()
						&& this.slotSize >= 2)
				{
					this.getInventoryMechaEquipment().setSkillBItem(stack);
				}
				else if (this.getInventoryMechaEquipment().getSkillCItem().isEmpty()
						&& this.getInventoryMechaEquipment().getSkillAItem().getItem() != stack.getItem()
						&& this.getInventoryMechaEquipment().getSkillBItem().getItem() != stack.getItem()
						&& this.slotSize >= 3)
				{
					this.getInventoryMechaEquipment().setSkillBItem(stack);
				}
				else
				{
					this.entityDropItem(stack, 0);
				}
			}
			else
			{
				this.entityDropItem(stack, 0);
			}

			ITextComponent text = new TextComponentString("[");
			text.getStyle().setColor(TextFormatting.GREEN);
			if (stack.getItem().getRarity(stack) == EnumRarity.UNCOMMON || stack.getItem().getRarity(stack) == EnumRarity.EPIC)
			{
				text.getStyle().setColor(TextFormatting.LIGHT_PURPLE);
			}

			ITextComponent itemName = new TextComponentString(stack.getDisplayName());
			text.appendSibling(itemName);
			text.appendText("]");

			String skillText = "skill.get";

			if (this.getOwner() != null && this.getOwner() instanceof EntityPlayerMP)
			{
				this.getOwner().sendMessage(new TextComponentTranslation(skillText, new Object[] { this.getDisplayName(), text }));
			}
		}
	}

	public void getUniqueSkill(int level)
	{
		ItemStack stack = this.getSkillUnique();

		if (this.getMechaLevel() == level && !stack.isEmpty())
		{
			this.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 0.5F, 1.0F);

			ITextComponent text = new TextComponentString("[");
			text.getStyle().setColor(TextFormatting.GREEN);
			text.getStyle().setColor(TextFormatting.LIGHT_PURPLE);

			ITextComponent itemName = new TextComponentString(stack.getDisplayName());
			text.appendSibling(itemName);
			text.appendText("]");

			String skillText = "skill.get";

			if (this.getOwner() != null && this.getOwner() instanceof EntityPlayerMP)
			{
				this.getOwner().sendMessage(new TextComponentTranslation(skillText, new Object[] { this.getDisplayName(), text }));
			}
		}
	}

	@Nonnull
	public static String getLastKnownUsername(UUID uuid)
	{
		String ret = UsernameCache.getLastKnownUsername(uuid);

		if (ret == null && !warnedFails.contains(uuid) && FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{ // see if MC/Yggdrasil knows about it?!
			GameProfile gp = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerProfileCache().getProfileByUUID(uuid);

			if (gp != null)
			{
				ret = gp.getName();
			}
		}

		if (ret == null && !warnedFails.contains(uuid))
		{
			warnedFails.add(uuid);
		}

		return ret != null ? ret : "<???>";
	}

	public void setAccessPlayer(@Nullable EntityPlayer player)
	{
		this.accessPlayer = player;
	}

	@Nullable
	public EntityPlayer getAccessPlayer()
	{
		return this.accessPlayer;
	}

	public boolean isAccessing()
	{
		return this.accessPlayer != null;
	}

	private class EntityAIAccessPlayer extends EntityAIBase
	{
		private final EntityFriendMecha mecha;

		public EntityAIAccessPlayer(EntityFriendMecha mechaIn)
		{
			this.mecha = mechaIn;
			this.setMutexBits(5);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute()
		{
			if (!this.mecha.isEntityAlive())
			{
				return false;
			}
			else if (this.mecha.isInWater())
			{
				return false;
			}
			else if (!this.mecha.onGround)
			{
				return false;
			}
			else if (this.mecha.velocityChanged)
			{
				return false;
			}
			else
			{
				EntityPlayer entityplayer = this.mecha.getAccessPlayer();

				if (entityplayer == null)
				{
					return false;
				}
				else if (this.mecha.getDistanceSq(entityplayer) > 16.0D)
				{
					return false;
				}
				else
				{
					return entityplayer.openContainer != null;
				}
			}
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting()
		{
			this.mecha.getNavigator().clearPath();
		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted by another one
		 */
		public void resetTask()
		{
			this.mecha.setAccessPlayer((EntityPlayer) null);
		}
	}

	public class EntityAILookAtAccessPlayer extends EntityAIWatchClosest
	{
		private final EntityFriendMecha mecha;

		public EntityAILookAtAccessPlayer(EntityFriendMecha villagerIn)
		{
			super(villagerIn, EntityPlayer.class, 8.0F);
			this.mecha = villagerIn;
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute()
		{
			if (this.mecha.isAccessing())
			{
				this.closestEntity = this.mecha.getAccessPlayer();
				return true;
			}
			else
			{
				return false;
			}
		}
	}

	public class EntityAIAttackRangedGunFriendMecha extends EntityAIAttackRangedGun
	{
		/** The entity the AI instance has been applied to */
		private final EntityLiving entityHost;

		public EntityAIAttackRangedGunFriendMecha(IRangedAttackMob attacker, double movespeed, float maxAttackDistanceIn) {
			super(attacker, movespeed, maxAttackDistanceIn);
			this.entityHost = (EntityLiving) attacker;
			this.setMutexBits(4);
		}

		@Override
		public boolean shouldExecute()
		{
			if(this.entityHost instanceof EntityFriendMecha)
			{
				EntityFriendMecha mecha = (EntityFriendMecha) this.entityHost;
				if(mecha.getOwner() != null && mecha.getMechaMode() == 1)
				{
					return mecha.getDistanceSq(mecha.getOwner()) < 420.0D;
				}
				if(mecha.getHomePosition() != null && mecha.getMechaMode() == 2)
				{
					return mecha.getDistanceSq(mecha.getHomePosition()) < 420.0D;
				}
			}
			return super.shouldExecute();
		}

		@Override
		public void resetTask()
		{
			if(this.entityHost instanceof EntityFriendMecha)
			{
				EntityFriendMecha mecha = (EntityFriendMecha) this.entityHost;

				if(mecha.getOwner() != null && mecha.getMechaMode() == 1)
				{
                    int i = MathHelper.floor(mecha.getOwner().posX) - 2;
                    int j = MathHelper.floor(mecha.getOwner().posZ) - 2;
                    int k = MathHelper.floor(mecha.getOwner().getEntityBoundingBox().minY);

                    for (int l = 0; l <= 4; ++l)
                    {
                        for (int i1 = 0; i1 <= 4; ++i1)
                        {
                            if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.isTeleportFriendlyBlock(i, j, k, l, i1))
                            {
                                mecha.setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), mecha.rotationYaw, mecha.rotationPitch);
                                mecha.getNavigator().clearPath();
                                return;
                            }
                        }
                    }
				}
				if(mecha.getHomePosition() != null && mecha.getMechaMode() == 2)
				{
                    int i = MathHelper.floor(mecha.getHomePosition().getX()) - 2;
                    int j = MathHelper.floor(mecha.getHomePosition().getZ()) - 2;
                    int k = MathHelper.floor(mecha.getHomePosition().getY());

                    for (int l = 0; l <= 4; ++l)
                    {
                        for (int i1 = 0; i1 <= 4; ++i1)
                        {
                            if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.isTeleportFriendlyBlock(i, j, k, l, i1))
                            {
                                mecha.setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), mecha.rotationYaw, mecha.rotationPitch);
                                mecha.getNavigator().clearPath();
                                return;
                            }
                        }
                    }
				}
			}
		}

	    protected boolean isTeleportFriendlyBlock(int x, int p_192381_2_, int y, int p_192381_4_, int p_192381_5_)
	    {
	        BlockPos blockpos = new BlockPos(x + p_192381_4_, y - 1, p_192381_2_ + p_192381_5_);
	        IBlockState iblockstate = entityHost.world.getBlockState(blockpos);
	        return iblockstate.getBlockFaceShape(entityHost.world, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID && iblockstate.canEntitySpawn(this.entityHost) && entityHost.world.isAirBlock(blockpos.up()) && entityHost.world.isAirBlock(blockpos.up(2));
	    }
	}
}
