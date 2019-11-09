package tf2.items.weapon;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.items.ItemBase;

public class ItemCoordJump extends ItemBase
{
	public ItemCoordJump(String name)
	{
		super(name);
		this.setMaxStackSize(1);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		pos = pos.offset(facing);
		ItemStack itemstack = player.getHeldItem(hand);

		BlockPos blockpos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());

		if (!player.canPlayerEdit(pos, facing, itemstack))
		{
			return EnumActionResult.FAIL;
		}
		else
		{
			if (worldIn.isAirBlock(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ())) &&
					worldIn.isAirBlock(pos))
			{
				//worldIn.playSound(player, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5F, itemRand.nextFloat() * 0.1F + 0.9F);

				NBTTagCompound nbt = itemstack.getTagCompound();
				if (nbt == null)
				{
					nbt = new NBTTagCompound();

					String dimName = worldIn.provider.getDimensionType().getName();
					int dim = worldIn.provider.getDimension();
					int x = pos.getX();
					int y = pos.getY();
					int z = pos.getZ();
					nbt.setString("tf.tp.dimname", dimName);
					nbt.setInteger("tf.tp.dim", dim);
					nbt.setInteger("tf.tpX", x);
					nbt.setInteger("tf.tpY", y);
					nbt.setInteger("tf.tpZ", z);
					itemstack.setTagCompound(nbt);

					return EnumActionResult.SUCCESS;
				}
			}
		}
		return EnumActionResult.PASS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemStackIn = playerIn.getHeldItem(handIn);
		NBTTagCompound nbt = itemStackIn.getTagCompound();

		//worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);

		if (!worldIn.isRemote && nbt != null)
		{
			playerIn.getCooldownTracker().setCooldown(this, 200);

			int x = nbt.getInteger("tf.tpX");
			int y = nbt.getInteger("tf.tpY");
			int z = nbt.getInteger("tf.tpZ");

			int D = 0;

			if (nbt.hasKey("tf.tp.dim"))
			{
				D = nbt.getInteger("tf.tp.dim");
			}
			if (worldIn.provider.getDimension() == D)
			{
				this.teleportTo(playerIn, x+0.5F, y, z+0.5F);
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	}

	private boolean teleportTo(EntityPlayer playerIn, double x, double y, double z)
	{
		EnderTeleportEvent event = new EnderTeleportEvent(playerIn, x, y, z, 0);
		if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
		boolean flag = playerIn.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());

		if (flag)
		{
			playerIn.world.playSound((EntityPlayer) null, playerIn.prevPosX, playerIn.prevPosY, playerIn.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, playerIn.getSoundCategory(), 1.0F, 1.0F);
			//playerIn.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
		}

		return flag;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		String s = "None";

		if (nbt != null)
		{
				int x = nbt.getInteger("tf.tpX");
				int y = nbt.getInteger("tf.tpY");
				int z = nbt.getInteger("tf.tpZ");
				String D = "Unknown";
				if (nbt.hasKey("tf.tp.dimname"))
				{
					D = nbt.getString("tf.tp.dimname");
				}
				s = x + ", " + y + ", " + z + " / " + D;
		}
		tooltip.add(TextFormatting.GRAY + " " + I18n.translateToLocal(s));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
