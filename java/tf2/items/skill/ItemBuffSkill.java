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
import tf2.TFItems;
import tf2.TFSoundEvents;
import tf2.potion.TFPotionPlus;


public class ItemBuffSkill extends ItemSkillBase
{
	private final int coolTime;


	public ItemBuffSkill(String name, int cooltime)
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
			if (itemStackIn.getItem() == TFItems.SKILL_ANTIDISTURB)
			{
				playerIn.addPotionEffect(new PotionEffect(TFPotionPlus.RESISTANCE_DOT, 1200, 0));
			}
			if (itemStackIn.getItem() == TFItems.SKILL_FULLFIRE)
			{
				playerIn.addPotionEffect(new PotionEffect(TFPotionPlus.FULLFIRE, 200, 0));
			}
			if (itemStackIn.getItem() == TFItems.SKILL_ATTITUDECONTROL)
			{
				playerIn.addPotionEffect(new PotionEffect(TFPotionPlus.RECOIL_SUPPRESS, 600, 0));
			}
			if (itemStackIn.getItem() == TFItems.SKILL_AVOIDANCE)
			{
				playerIn.addPotionEffect(new PotionEffect(TFPotionPlus.DAMAGE_CANCEL, 120, 0));
			}
			if (itemStackIn.getItem() == TFItems.SKILL_GUARDPOINOT)
			{
				playerIn.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 600, 2));
			}
			if (itemStackIn.getItem() == TFItems.SKILL_OVERDRIVE)
			{
				playerIn.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 200, 2));
			}
			if (itemStackIn.getItem() == TFItems.SKILL_MEDICALKIT)
			{
				playerIn.heal(20.0F);
			}
			if (itemStackIn.getItem() == TFItems.SKILL_RECOVERPOINT)
			{
				playerIn.addPotionEffect(new PotionEffect(TFPotionPlus.ACTIVATION, 1200, 0));
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

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			if (stack.getItem() == TFItems.SKILL_ANTIDISTURB)
			{
				tooltip.add(TextFormatting.BLUE + " " + I18n.translateToLocal("skill.antidisturb"));
			}
			if (stack.getItem() == TFItems.SKILL_FULLFIRE)
			{
				tooltip.add(TextFormatting.BLUE + " " + I18n.translateToLocal("skill.fullfire"));
			}
			if (stack.getItem() == TFItems.SKILL_GUARDPOINOT)
			{
				tooltip.add(TextFormatting.BLUE + " " + I18n.translateToLocal("skill.guardpoint"));
			}
			if (stack.getItem() == TFItems.SKILL_OVERDRIVE)
			{
				tooltip.add(TextFormatting.BLUE + " " + I18n.translateToLocal("skill.overdrive"));
			}
			if (stack.getItem() == TFItems.SKILL_AVOIDANCE)
			{
				tooltip.add(TextFormatting.GRAY + " " + I18n.translateToLocal("skill.hotbar"));
	        	tooltip.add(TextFormatting.BLUE + " : " + I18n.translateToLocal("skill.avoidance"));
	        	tooltip.add(TextFormatting.GRAY + " " + I18n.translateToLocal("skill.inventory"));
	        	tooltip.add(TextFormatting.BLUE + " : " + I18n.translateToLocal("skill.avoidance2"));
			}
			if (stack.getItem() == TFItems.SKILL_RECOVERPOINT)
			{
				tooltip.add(TextFormatting.BLUE + " " + I18n.translateToLocal("skill.recoverpoint"));
			}
			if (stack.getItem() == TFItems.SKILL_ATTITUDECONTROL)
			{
				tooltip.add(TextFormatting.BLUE + " " + I18n.translateToLocal("skill.attitudecontrol"));
			}
			if (stack.getItem() == TFItems.SKILL_MEDICALKIT)
			{
				tooltip.add(TextFormatting.BLUE + I18n.translateToLocal("skill.medicalkit"));
			}
		}
		else
		{
			if (stack.getItem() == TFItems.SKILL_ANTIDISTURB)
			{
				tooltip.add(TextFormatting.BLUE + I18n.translateToLocal("resistance_dot") + " " + "(1:00)");
			}
			if (stack.getItem() == TFItems.SKILL_GUARDPOINOT)
			{
				tooltip.add(TextFormatting.BLUE + I18n.translateToLocal("effect.resistance") + " III " + "(0:30)");
			}
			if (stack.getItem() == TFItems.SKILL_AVOIDANCE)
			{
				tooltip.add(TextFormatting.BLUE + I18n.translateToLocal("damagecancel") + " " + "(0:06)");
			}
			if (stack.getItem() == TFItems.SKILL_FULLFIRE)
			{
				tooltip.add(TextFormatting.BLUE + I18n.translateToLocal("fullfire") + " " + "(0:10)");
			}
			if (stack.getItem() == TFItems.SKILL_OVERDRIVE)
			{
				tooltip.add(TextFormatting.BLUE + I18n.translateToLocal("effect.damageBoost") + " III " + "(0:10)");
			}
			if (stack.getItem() == TFItems.SKILL_ATTITUDECONTROL)
			{
				tooltip.add(TextFormatting.BLUE + I18n.translateToLocal("recoil_suppress") + " " + "(0:30)");
			}
			if (stack.getItem() == TFItems.SKILL_RECOVERPOINT)
			{
				tooltip.add(TextFormatting.BLUE + I18n.translateToLocal("activation") + " " + "(1:00)");
			}
			if (stack.getItem() == TFItems.SKILL_MEDICALKIT)
			{
				tooltip.add(TextFormatting.BLUE + I18n.translateToLocal("effect.heal") + " +20");
			}

			tooltip.add(TextFormatting.GRAY + " " + (y) + " " + I18n.translateToLocal("tf.cooltime"));
			tooltip.add(TextFormatting.ITALIC + I18n.translateToLocal("LShift: Expand tooltip."));
		}

		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
