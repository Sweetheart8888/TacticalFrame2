package tf2.items;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.entity.mob.frend.EntityBike;
import tf2.entity.mob.frend.EntityCFR12;
import tf2.entity.mob.frend.EntityFriendMecha;
import tf2.entity.mob.frend.EntityMTT1;
import tf2.entity.mob.frend.EntityMTT2;
import tf2.entity.mob.frend.EntityMTT3;
import tf2.entity.mob.frend.EntityMTT4;
import tf2.entity.mob.frend.EntityTF77B;
import tf2.entity.mob.frend.EntityTF78R;
import tf2.entity.mob.frend.EntityTF79P;
import tf2.entity.mob.frend.EntityTF80G;
import tf2.util.Reference;

public class ItemSpawnFriendMecha extends ItemBase
{
	private static final List<UUID> warnedFails = new ArrayList<>();
	public static Class[] spawnableEntities = {
			EntityCFR12.class,
			EntityMTT1.class,
			EntityMTT2.class,
			EntityMTT3.class,
			EntityMTT4.class,
			EntityTF77B.class,
			EntityTF78R.class,
			EntityTF79P.class,
			EntityTF80G.class,
			EntityBike.class
	};

	public ItemSpawnFriendMecha(String name)
	{
		super(name);

		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public void registerModel()
	{
		for (int i = 0; i < spawnableEntities.length; ++i)
		{
			String name = EntityRegistry.getEntry(spawnableEntities[i]).getName();

			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "spawncore_fm_" + name), "inventory"));
		}
	}


    /**
     * Called when a Block is right-clicked with this Item
     */
	@Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = playerIn.getHeldItem(hand);

        if (worldIn.isRemote)
        {
            return EnumActionResult.SUCCESS;
        }
        else if (!playerIn.canPlayerEdit(pos.offset(facing), facing, itemstack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();


            BlockPos blockpos = pos.offset(facing);
            double d0 = this.getYOffset(worldIn, blockpos);
            //Entity entity = spawnCreature(worldIn, getNamedIdFrom(itemstack), (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + d0, (double)blockpos.getZ() + 0.5D);

        	NBTTagCompound nbt = itemstack.getTagCompound();
        	if(nbt != null)
        	{
        		if(nbt.getInteger("tf.mechaHealth") <= nbt.getInteger("tf.mechaMaxHealth") * 0.25)
        		{

        			return EnumActionResult.FAIL;
        		}
        	}

            Entity entity = spawnCreature(playerIn, worldIn, itemstack, itemstack.getItemDamage(), (double)blockpos.getX() + 0.5D, (double)blockpos.getY(), (double)blockpos.getZ() + 0.5D);

            if (entity != null)
            {
                if (entity instanceof EntityLivingBase && itemstack.hasDisplayName())
                {
                    entity.setCustomNameTag(itemstack.getDisplayName());
                }

                //applyItemEntityDataToEntity(worldIn, player, itemstack, entity);

                if (!playerIn.capabilities.isCreativeMode)
                {
                    itemstack.shrink(1);
                }
            }

//            TFAdvancements.SUMMON_FRIENDMECHA.trigger((EntityPlayerMP) playerIn);
//            if(entity instanceof EntityGynoid)
//            {
//            	TFAdvancements.SUMMON_GYNOID.trigger((EntityPlayerMP) playerIn);
//            }
//            if(entity instanceof EntityMobCF)
//            {
//            	TFAdvancements.SUMMON_RIDEMECHA.trigger((EntityPlayerMP) playerIn);
//            }

            return EnumActionResult.SUCCESS;
        }
    }

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (worldIn.isRemote)
        {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
        }
        else
        {
            RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);

            if (raytraceresult != null && raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK)
            {
                BlockPos blockpos = raytraceresult.getBlockPos();


                if (!(worldIn.getBlockState(blockpos).getBlock() instanceof BlockLiquid))
                {
                    return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
                }
                else if (worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos, raytraceresult.sideHit, itemstack))
                {
                	NBTTagCompound nbt = itemstack.getTagCompound();
                	if(nbt != null)
                	{
                		if(nbt.getInteger("tf.mechaHealth") <= nbt.getInteger("tf.mechaMaxHealth") / 4)
                		{

                			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
                		}
                		else
                		{

                		}
                	}

                	Entity entity = spawnCreature(playerIn, worldIn, itemstack, itemstack.getItemDamage(), (double)blockpos.getX() + 0.5D, (double)blockpos.getY(), (double)blockpos.getZ() + 0.5D);


                    if (entity == null)
                    {
                        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
                    }
                    else
                    {
                        if (entity instanceof EntityLivingBase && itemstack.hasDisplayName())
                        {
                            entity.setCustomNameTag(itemstack.getDisplayName());
                        }

                        //applyItemEntityDataToEntity(worldIn, playerIn, itemstack, entity);

                        if (!playerIn.capabilities.isCreativeMode)
                        {
                            itemstack.shrink(1);
                        }

                        playerIn.addStat(StatList.getObjectUseStats(this));
                        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
                    }
                }
                else
                {
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
                }
            }
            else
            {
                return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
            }
        }
    }

    protected double getYOffset(World p_190909_1_, BlockPos p_190909_2_)
    {
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(p_190909_2_)).expand(0.0D, -1.0D, 0.0D);
        List<AxisAlignedBB> list = p_190909_1_.getCollisionBoxes((Entity)null, axisalignedbb);

        if (list.isEmpty())
        {
            return 0.0D;
        }
        else
        {
            double d0 = axisalignedbb.minY;

            for (AxisAlignedBB axisalignedbb1 : list)
            {
                d0 = Math.max(axisalignedbb1.maxY, d0);
            }

            return d0 - (double)p_190909_2_.getY();
        }
    }

	public static Entity spawnCreature(EntityPlayer player, World world, ItemStack itemstack, int spawnListID, double x, double y, double z)
	{
		Class c = spawnableEntities[spawnListID];
		Entity entity = null;

		NBTTagCompound nbt = itemstack.getTagCompound();

		try
		{
			entity = (Entity) c.getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });

			EntityLiving entityliving = (EntityLiving) entity;
			EntityFriendMecha entityMecha = (EntityFriendMecha)entityliving;
			entity.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
			entityMecha.rotationYawHead = entityMecha.rotationYaw;
			entityMecha.renderYawOffset = entityMecha.rotationYaw;
			entityMecha.onInitialSpawn(null, (IEntityLivingData) null);

			if(nbt != null)
			{
				UUID ownerUuid = UUID.fromString(nbt.getString("tf.mechaOwner"));
				entityMecha.setMechaLevel(nbt.getInteger("tf.mechaLevel"));
				entityMecha.setMechaATK((int) (entityMecha.defaultDamage) + (int)(entityMecha.getMechaLevel() * entityMecha.upDamage));
				entityMecha.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(entityMecha.defaultArmor + (int)(entityMecha.getMechaLevel() * entityMecha.upArmor));
				entityMecha.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(entityMecha.defaultArmorToughness + (int)(entityMecha.getMechaLevel() * entityMecha.upArmorToughness));
				entityMecha.setOwnerUUID(ownerUuid);

				entityMecha.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(entityMecha.defaultMaxHealth + (int)(entityMecha.getMechaLevel() * entityMecha.upMaxHealth));
				entityMecha.heal((float) (nbt.getInteger("tf.mechaHealth")));
				if(nbt.hasKey("tf.mechaSkillA"))
				{
					Item item = Item.REGISTRY.getObject(new ResourceLocation(nbt.getString("tf.mechaSkillA")));

					if(item != null)
					{
						entityMecha.getInventoryMechaEquipment().setSkillAItem(new ItemStack(item));
					}
				}
				if(nbt.hasKey("tf.mechaSkillB"))
				{
					Item item = Item.REGISTRY.getObject(new ResourceLocation(nbt.getString("tf.mechaSkillB")));

					if(item != null)
					{
						entityMecha.getInventoryMechaEquipment().setSkillBItem(new ItemStack(item));
					}
				}
				if(nbt.hasKey("tf.mechaSkillC"))
				{
					Item item = Item.REGISTRY.getObject(new ResourceLocation(nbt.getString("tf.mechaSkillC")));

					if(item != null)
					{
						entityMecha.getInventoryMechaEquipment().setSkillCItem(new ItemStack(item));
					}
				}
			}
			else
			{
				entityMecha.setMechaATK((int) (entityMecha.defaultDamage));
				entityMecha.setOwnerUUID(player.getUniqueID());
				entityMecha.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(entityMecha.defaultMaxHealth + (int)(entityMecha.getMechaLevel() * entityMecha.upMaxHealth));
				entityMecha.heal((float) (entityMecha.getMaxHealth()));
			}

			world.spawnEntity(entity);
			entityMecha.playLivingSound();

		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		} catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}

		return entity;
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack)
	{

        //String s1 = EntityList.getTranslationName(EntityRegistry.getEntry(spawnableEntities[itemStack.getItemDamage()]).getRegistryName());

        String s = ("" + I18n.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
        String s1 = EntityList.getTranslationName(EntityRegistry.getEntry(spawnableEntities[itemStack.getItemDamage()]).getRegistryName());

        if (s1 != null)
        {
            s = s + " " + I18n.translateToLocal("entity." + s1 + ".name");
        }

        return s;
	}

	public static Entity getSpawnCreature(World world, ItemStack itemstack)
	{
		Class c = spawnableEntities[itemstack.getItemDamage()];
		Entity entity = null;

		try
		{
			entity = (Entity) c.getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });

		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		} catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}

		return entity;
	}


	public String getEntityName(ItemStack itemStack)
	{


        String s = ("").trim();
        String s1 = EntityList.getTranslationName(EntityRegistry.getEntry(spawnableEntities[itemStack.getItemDamage()]).getRegistryName());

        if (s1 != null)
        {
            s = s + " " + I18n.translateToLocal("entity." + s1 + ".name");
        }

        return s;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (this.isInCreativeTab(tab))
		{
			for (int i = 0; i < spawnableEntities.length; ++i)
			{
				items.add(new ItemStack(this, 1, i));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		String s = "";

		if (nbt != null)
		{
			//EntityPlayer player = worldIn.getPlayerEntityByUUID(ownerUuid);

			s = "Lv: " + (nbt.getInteger("tf.mechaLevel") + 1);

			tooltip.add(TextFormatting.GRAY + " " + I18n.translateToLocal(s));

			s = "HP: " + (nbt.getInteger("tf.mechaHealth") + ".0 / " + (nbt.getInteger("tf.mechaMaxHealth")) + ".0");

			tooltip.add(TextFormatting.GRAY + " " + I18n.translateToLocal(s));


			if(nbt.hasKey("tf.mechaOwner"))
			{
				UUID ownerUuid = UUID.fromString(nbt.getString("tf.mechaOwner"));

				s = "Owner: " + this.getLastKnownUsername(ownerUuid);

				tooltip.add(TextFormatting.GRAY + " " + I18n.translateToLocal(s));
			}

			if(nbt.hasKey("tf.mechaSkillA") || nbt.hasKey("tf.mechaSkillB") || nbt.hasKey("tf.mechaSkillC"))
			{
				tooltip.add(TextFormatting.GRAY + "");
				s = "Skill: ";
				tooltip.add(TextFormatting.DARK_AQUA + " " + I18n.translateToLocal(s));

				if(nbt.hasKey("tf.mechaSkillA"))
				{
					Item item = Item.REGISTRY.getObject(new ResourceLocation(nbt.getString("tf.mechaSkillA")));
					if(item != null)
					{
						s = item.getItemStackDisplayName(new ItemStack(item));
						tooltip.add(TextFormatting.GRAY + " " + I18n.translateToLocal(s));
					}
				}
				if(nbt.hasKey("tf.mechaSkillB"))
				{
					Item item = Item.REGISTRY.getObject(new ResourceLocation(nbt.getString("tf.mechaSkillB")));
					if(item != null)
					{
						s = item.getItemStackDisplayName(new ItemStack(item));
						tooltip.add(TextFormatting.GRAY + " " + I18n.translateToLocal(s));
					}
				}
				if(nbt.hasKey("tf.mechaSkillC"))
				{
					Item item = Item.REGISTRY.getObject(new ResourceLocation(nbt.getString("tf.mechaSkillC")));
					if(item != null)
					{
						s = item.getItemStackDisplayName(new ItemStack(item));
						tooltip.add(TextFormatting.GRAY + " " + I18n.translateToLocal(s));
					}
				}
			}
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Nonnull
	public static String getLastKnownUsername(UUID uuid)
	{
		String ret = UsernameCache.getLastKnownUsername(uuid);

		if(ret == null && !warnedFails.contains(uuid) && FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{ // see if MC/Yggdrasil knows about it?!
			GameProfile gp = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerProfileCache().getProfileByUUID(uuid);

			if(gp != null)
			{
				ret = gp.getName();
			}
		}

		if(ret == null && !warnedFails.contains(uuid))
		{
			warnedFails.add(uuid);
		}

		return ret != null ? ret : "<???>";
	}
}
