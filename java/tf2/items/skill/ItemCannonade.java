package tf2.items.skill;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TFSoundEvents;
import tf2.entity.projectile.player.EntityShell;

public class ItemCannonade extends ItemSkillBase
{
	private final int coolTime;
	public ItemCannonade(String name, int cooltime)
	{
		super(name);
		this.coolTime = cooltime;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemStackIn = playerIn.getHeldItem(handIn);
		worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, TFSoundEvents.DECISION, SoundCategory.NEUTRAL, 0.7F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
		worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, TFSoundEvents.BAZOOKA, SoundCategory.AMBIENT, 2.5F, 1.0F / (this.itemRand.nextFloat() * 0.4F + 0.8F));
		playerIn.getCooldownTracker().setCooldown(this, this.coolTime);

		Vec3d playerLookVec = playerIn.getLookVec();
		double n = 150.0D;
		double vecX = playerLookVec.x * n;
		double vecY = playerLookVec.y * n;
		double vecZ = playerLookVec.z * n;
		double playerPosX = playerIn.posX;
		double playerPosY = playerIn.posY + 1.8D + playerIn.getYOffset();
		double playerPosZ = playerIn.posZ;

		Vec3d entityPlayer = new Vec3d(playerPosX, playerPosY, playerPosZ);
		Vec3d playerLook = entityPlayer.addVector(vecX, vecY, vecZ);

		RayTraceResult x1 = worldIn.rayTraceBlocks(entityPlayer, playerLook, true);

		if (x1 != null)
		{
			if (!worldIn.isRemote)
			{
				for (int i = 0; i < 20; i++)
                {
					EntityShell entity = new EntityShell(worldIn, playerIn);
					entity.setDamage(entity.getDamage() + 20.0F);
					entity.posX = x1.hitVec.x + ((worldIn.rand.nextDouble() * 30D) - 15D);
					entity.posZ = x1.hitVec.z + ((worldIn.rand.nextDouble() * 30D) - 15D);
					entity.posY = x1.hitVec.y + 50F + (i * 2F);
					entity.shoot(0D, 1D, 0D, 0.15F * i, 10.0F);
					worldIn.spawnEntity(entity);
                }
			}
		}
		else
		{
			if (!worldIn.isRemote)
			{
				for (int i = 0; i < 20; i++)
                {
					EntityShell entity = new EntityShell(worldIn, playerIn);
					entity.setDamage(entity.getDamage() + 20.0F);
					entity.posX = playerPosX + vecX + ((worldIn.rand.nextDouble() * 30D) - 15D);
					entity.posZ = playerPosZ + vecZ + ((worldIn.rand.nextDouble() * 30D) - 15D);
					entity.posY = playerPosY + vecY + 50F + (i * 2F);
					entity.shoot(0D, 1D, 0D, 0.15F * i, 10.0F);
					worldIn.spawnEntity(entity);
                }
			}
		}
		playerIn.addStat(StatList.getObjectUseStats(this));
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		int y1 = this.coolTime;
        int y = y1 / 20;

        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
        {
        	tooltip.add(TextFormatting.BLUE + " " + I18n.translateToLocal("skill.cannonade"));
        }
        else
        {
        	tooltip.add(ChatFormatting.BLUE + " " + ("20") + " " + I18n.translateToLocal("tf.bombdamage"));
        	tooltip.add(TextFormatting.GRAY + " " + (y) + " " + I18n.translateToLocal("tf.cooltime"));
        	tooltip.add(TextFormatting.ITALIC + I18n.translateToLocal("LShift: Expand tooltip."));
        }

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
