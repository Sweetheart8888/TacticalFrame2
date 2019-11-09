package tf2.items.skill;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
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
import tf2.potion.TFPotionPlus;

public class ItemAssaltForce extends ItemSkillBase
{
	private final int coolTime;

	public ItemAssaltForce(String name, int cooltime)
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

		List entity = worldIn.getEntitiesWithinAABB(EntityPlayer.class, playerIn.getEntityBoundingBox().grow(15.0D));
 		for (int i = 0; i < entity.size(); ++i)
 		{
 			EntityPlayer player = (EntityPlayer)entity.get(i);
 			if (!worldIn.isRemote)
 			{
				if (player.isPotionActive(MobEffects.SPEED))
				{
					PotionEffect potion = player.getActivePotionEffect(MobEffects.SPEED);
					int j = potion.getAmplifier();
					int k = potion.getDuration();

					if (j < 3)
					{
						player.addPotionEffect(new PotionEffect(MobEffects.SPEED, k + 600, 2));
					}
					else
					{
						player.addPotionEffect(new PotionEffect(MobEffects.SPEED, k + 600, j));
					}

				}
				else if (!player.isPotionActive(MobEffects.SPEED))
				{
					player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 600, 2));
				}

				if (player.isPotionActive(TFPotionPlus.MOVE_SHOOTING))
				{
					PotionEffect potion = player.getActivePotionEffect(TFPotionPlus.MOVE_SHOOTING);
					int j = potion.getAmplifier();
					int k = potion.getDuration();
					if (j < 2)
					{
						player.addPotionEffect(new PotionEffect(TFPotionPlus.MOVE_SHOOTING, k + 600, j + 1));
					}
					else
					{
						player.addPotionEffect(new PotionEffect(TFPotionPlus.MOVE_SHOOTING, k + 600, j));
					}

				}
				else if (!player.isPotionActive(TFPotionPlus.MOVE_SHOOTING))
				{
					player.addPotionEffect(new PotionEffect(TFPotionPlus.MOVE_SHOOTING, 1200, 0));
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
        	tooltip.add(TextFormatting.BLUE + " " + I18n.translateToLocal("skill.assaltforce1"));
        	tooltip.add(TextFormatting.BLUE + " " + I18n.translateToLocal("skill.assaltforce2"));
        }
        else
        {
        	tooltip.add(TextFormatting.BLUE + I18n.translateToLocal("effect.moveSpeed") + " III " + "(0:30)");
        	tooltip.add(TextFormatting.BLUE + I18n.translateToLocal("moveshooting") + " " + "(1:00)");
        	tooltip.add(TextFormatting.GRAY + " " + (y) + " " + I18n.translateToLocal("tf.cooltime"));
        	tooltip.add(TextFormatting.ITALIC + I18n.translateToLocal("LShift: Expand tooltip."));
        }

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
