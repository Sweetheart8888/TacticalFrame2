package tf2.items.skill;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TFSoundEvents;
import tf2.entity.projectile.EntityBarrier;

public class ItemShieldCircle extends ItemSkillBase
{
	private final int coolTime;

	private int amount = 4;
    private int life = 10;
    private int ticks = 300;
    private double distance = 1.4D;

    private int maxAmount;

	public ItemShieldCircle(String name, int cooltime)
	{
		super(name);
		this.coolTime = cooltime;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemStackIn = playerIn.getHeldItem(handIn);
		worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, TFSoundEvents.DECISION, SoundCategory.NEUTRAL, 0.7F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
		playerIn.getCooldownTracker().setCooldown(this, this.coolTime);

		if (!worldIn.isRemote)
		 {
			for(int i = 4;i >= 1;i--)
			{
				if(maxAmount < i)
				{
					maxAmount = i;
				}

		EntityBarrier barrier = new EntityBarrier(playerIn.world, playerIn, life, ticks, maxAmount, i, distance);
    	barrier.posY = playerIn.posY + playerIn.height / 4;
    	barrier.posX = playerIn.posX - Math.sin((barrier.ticksExisted + (45 / maxAmount) * i) * Math.PI / 22.5D) * distance;
		barrier.posZ = playerIn.posZ - Math.cos((barrier.ticksExisted + (45 / maxAmount) * i) * Math.PI / 22.5D) * distance;
		playerIn.world.spawnEntity(barrier);

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
        	tooltip.add(TextFormatting.BLUE + " " + I18n.translateToLocal("skill.shieldcircle_0"));
        	tooltip.add(TextFormatting.BLUE + " " + I18n.translateToLocal("skill.shieldcircle_1"));

        }
        else
        {
        	tooltip.add(TextFormatting.GRAY + " " + (y) + " " + I18n.translateToLocal("tf.cooltime"));
        	tooltip.add(TextFormatting.ITALIC + I18n.translateToLocal("LShift: Expand tooltip."));
        }

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
