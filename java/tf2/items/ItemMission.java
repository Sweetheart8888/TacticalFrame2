package tf2.items;

import net.minecraft.advancements.Advancement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import tf2.TFItems;
import tf2.entity.mob.frend.EntityEvent1;
import tf2.entity.mob.frend.EntityEvent2;
import tf2.entity.mob.frend.EntityEvent3;

public class ItemMission extends ItemBase
{
	private final ResourceLocation resource;
	public ItemMission(String name, ResourceLocation resourceIn)
	{
		super(name);
		this.resource = resourceIn;
		this.setMaxStackSize(1);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		pos = pos.offset(facing);
		ItemStack itemstack = player.getHeldItem(hand);

		if (!player.canPlayerEdit(pos, facing, itemstack))
		{
			return EnumActionResult.FAIL;
		}
		else
		{
			if(player instanceof EntityPlayerMP)
			{
				EntityPlayerMP playerMP = (EntityPlayerMP) player;

				Advancement adv = playerMP.getServerWorld().getAdvancementManager().getAdvancement(this.resource);

				if(playerMP.getAdvancements().getProgress(adv).isDone())
				{
					playerMP.sendStatusMessage(new TextComponentTranslation("tf.mission.already", new Object[0]), true);
					return EnumActionResult.FAIL;
				}
			}

			if (!worldIn.isRemote)
			{
				if (itemstack.getItem() == TFItems.MISSION_1)
				{
					EntityEvent1 entity = new EntityEvent1(worldIn);
					entity.setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
					entity.setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
					worldIn.spawnEntity(entity);
				}
				if (itemstack.getItem() == TFItems.MISSION_2)
				{
					EntityEvent2 entity = new EntityEvent2(worldIn);
					entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(TFItems.HK416));
					entity.setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
					entity.setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
					worldIn.spawnEntity(entity);
				}
				if (itemstack.getItem() == TFItems.MISSION_3)
				{
					EntityEvent3 entity = new EntityEvent3(worldIn);
					entity.setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
					entity.setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
					worldIn.spawnEntity(entity);
				}
			}

			if (!player.capabilities.isCreativeMode)
			{
				itemstack.shrink(1);
			}
			return EnumActionResult.SUCCESS;
		}
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
//	{
//		if (stack.getItem() == TFItems.MISSION_3)
//		{
//			tooltip.add(TextFormatting.RED + I18n.translateToLocal("tf.build_road"));
//		}
//		super.addInformation(stack, worldIn, tooltip, flagIn);
//	}
}
