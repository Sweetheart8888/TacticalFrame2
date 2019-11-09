package tf2.items.weapon;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.entity.projectile.player.EntityGrenadeHe;

public class ItemGrenadeHe extends ItemBaseLevelUp
{
	public ItemGrenadeHe(String name)
	{
		super(name);
		this.setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemStackIn = playerIn.getHeldItem(handIn);
		playerIn.getCooldownTracker().setCooldown(this, this.coolTime(itemStackIn));
		worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ENDERPEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if (!worldIn.isRemote)
		{
			EntityGrenadeHe grenade = new EntityGrenadeHe(worldIn, playerIn);
			grenade.setDamage(grenade.getDamage() + this.damage(itemStackIn));
			grenade.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
			worldIn.spawnEntity(grenade);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	}

	public float damage(ItemStack itemstackIn)
	{
		return 20F + ((float)this.getLevel(itemstackIn) * 2.0F);
	}

	public int coolTime(ItemStack itemstackIn)
	{
		return 600 - (this.getLevel(itemstackIn) * 30);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);

		float damage = this.damage(stack);
		int cooltime = this.coolTime(stack);

		tooltip.add(ChatFormatting.GRAY + I18n.translateToLocal(" ") + (damage) + " " + I18n.translateToLocal("tf.damage"));
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			tooltip.add(TextFormatting.GRAY + " " + (cooltime) + " " + I18n.translateToLocal("tf.cooltime"));
		}
		else
		{
			tooltip.add(TextFormatting.ITALIC + I18n.translateToLocal("LShift: Expand tooltip."));
		}
	}
}
