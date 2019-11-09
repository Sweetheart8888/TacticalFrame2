package tf2.entity.mob.frend;

import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import tf2.TFItems;
import tf2.entity.mob.ai.EntityAIFollowFriendMecha;
import tf2.entity.mob.ai.EntityAINearestAttackbleTargetFriend;
import tf2.entity.mob.ai.EntityAIWanderFriendMecha;

public abstract class EntityGynoid extends EntityFriendMecha implements IRangedAttackMob
{
//	private InventoryGynoidTrade inventoryGynoidTrade;
//
//	protected static final DataParameter<Integer> GYNOID_TRADE = EntityDataManager.<Integer> createKey(EntityFriendMecha.class, DataSerializers.VARINT);
//	protected static final DataParameter<Integer> GYNOID_TRADE_LIST = EntityDataManager.<Integer> createKey(EntityFriendMecha.class, DataSerializers.VARINT);

	public EntityGynoid(World worldIn, double defaultDamage, double upAttack, double defaultArmor, double upArmor, double defaultArmorToughness, double upArmorToughness, double defaultMaxHealth, double upMaxHealth)
	{
		super(worldIn, (byte)2, (byte)75, defaultDamage, upAttack, defaultArmor, upArmor, defaultArmorToughness, upArmorToughness, defaultMaxHealth, upMaxHealth, false);
		this.setSize(0.6F, 1.5F);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
//		this.dataManager.register(GYNOID_TRADE, Integer.valueOf(0));
//		this.dataManager.register(GYNOID_TRADE_LIST, Integer.valueOf(0));
	}


	protected void initEntityAI()
	{
		super.initEntityAI();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(3, new EntityAILookIdle(this));
		this.tasks.addTask(1, new EntityFriendMecha.EntityAILookAtAccessPlayer(this));
		this.tasks.addTask(2, new EntityAIWanderFriendMecha(this, 1.0D));
		this.tasks.addTask(2, new EntityAIFollowFriendMecha(this, 1.0D, 5.0F, 2.0F));
		this.targetTasks.addTask(2, new EntityAINearestAttackbleTargetFriend(this, 10, true));
	}


    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1.0D);
    }

    @Override
	public void isUpLevel()
    {
    	super.isUpLevel();
//    	if(this.getMechaLevel() == 14)
//    	{
//    		this.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 0.5F, 1.0F);
//    		ItemStack stack = new ItemStack(TFItems.SKILL_ARMEDFORM);
//
//			ITextComponent text = new TextComponentString("[");
//			text.getStyle().setColor(TextFormatting.GREEN);
//			text.getStyle().setColor(TextFormatting.LIGHT_PURPLE);
//			ITextComponent itemName = new TextComponentString(stack.getDisplayName());
//	        text.appendSibling(itemName);
//	        text.appendText("]");
//
//	        String skillText = "skill.get";
//
//	        if (this.getOwner() != null && this.getOwner() instanceof EntityPlayerMP)
//	        {
//	            this.getOwner().sendMessage(new TextComponentTranslation(skillText, new Object[] {this.getDisplayName() , text}));
//	        }
//    	}
    }

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

//		compound.setInteger("gynoidTrade", this.getGynoidTrade());
//		compound.setInteger("gynoidTradeList", this.getGynoidTradeList());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return null;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_PLAYER_DEATH;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundEvents.ENTITY_PLAYER_HURT;
	}

	@Override
	public void setSwingingArms(boolean swingingArms)
	{
	}

	@Override
	public ItemStack getSkillUnique()
	{
		if (this.getMechaLevel() >= 59 && this instanceof EntityTF77B)
		{
			return new ItemStack(TFItems.SKILL_ARMEDFORM_ALPHA);
		}
		if (this.getMechaLevel() >= 59 && this instanceof EntityTF78R)
		{
			return new ItemStack(TFItems.SKILL_ARMEDFORM_BETA);
		}
		if (this.getMechaLevel() >= 59 && this instanceof EntityTF79P)
		{
			return new ItemStack(TFItems.SKILL_ARMEDFORM_GAMMA);
		}
		if (this.getMechaLevel() >= 59 && this instanceof EntityTF80G)
		{
			return new ItemStack(TFItems.SKILL_ARMEDFORM_DELTA);
		}

		return null;
	}
}
