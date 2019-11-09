package tf2.items.guns;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TFItems;
import tf2.TFSoundEvents;
import tf2.entity.projectile.player.EntityBullet;
import tf2.potion.TFPotionPlus;
import tf2.util.IHasModel;
import tf2.util.Reference;

public class ItemTFGunsHG extends ItemTFGuns implements IHasModel
{
	private final float damage;
	private final float levelup;
	private final float group;
	private final SoundEvent sound;
	private final float recoil;
	private final float recoilrand;
	private final int cycle;
	private final int reload;
	private final String tex;

	public ItemTFGunsHG(String name, int magazineSize, float bulletDamage, float levelUp, float bulletGrouping, SoundEvent gunSound, float gunRecoil, float gunRecoilRondom, int cooldown, int reloadTime)
	{
		super();
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		TFItems.ITEMS.add(this);

		this.setMaxDamage(magazineSize);
		this.damage = bulletDamage;
		this.levelup = levelUp;
		this.group = bulletGrouping;
		this.sound = gunSound;
		this.recoil = gunRecoil;
		this.recoilrand = gunRecoilRondom;
		this.cycle = cooldown;
		this.reload = reloadTime;
		this.tex = name;
	}

	@Override
	public void registerModel()
	{
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "gun/" + tex), "inventory"));
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityLivingBase entity, int i)
	{
		if (!isReload(itemstack))
		{
			// リロード中ではない
			if (entity != null && entity instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer) entity;

				if (itemstack.getItemDamage() < this.getMaxDamage(itemstack))
				{
					int j = getMaxItemUseDuration(itemstack) - i;
					float f = (float) j / 20F;
					f = (f * f + f * 2.0F) / 3F;
					if (f > 1.0F)
					{
						f = 1.0F;
					}
					fireBullet(itemstack, world, entityplayer);
					entityplayer.getCooldownTracker().setCooldown(this, this.coolTime(itemstack));

				}
				else
				{
					// 弾切れ音(このMODでは追加しない)
					//world.playSoundAtEntity(entityplayer, string, 1.0F, 1.0F);
				}
			}
		}
		super.onPlayerStoppedUsing(itemstack, world, entity, i);
	}

	//弾の発射
	protected void fireBullet(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		ItemStack lis = getAmmo(itemstack, itemstack.getMaxDamage() - itemstack.getItemDamage());
		if (lis == null)
		{
			lis = new ItemStack(getBullet(itemstack), 1, 0);
		}
		boolean lflag = getFire(itemstack, world, entityplayer);

		if (lflag)
		{
			clearAmmo(itemstack, itemstack.getMaxDamage() - itemstack.getItemDamage());
			if (!(entityplayer.isPotionActive(TFPotionPlus.FULLFIRE)))
			{
				itemstack.damageItem(1, entityplayer);
			}
		}
	}

	//弾の設定
	public boolean getFire(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		// 発射
		if (!world.isRemote)
		{
			EntityBullet bullet = null;

			bullet = new EntityBullet(world, (EntityLivingBase) entityplayer);

			if (bullet != null)
			{
				//銃弾のダメージ
				bullet.setDamage(bullet.getDamage() + this.damage(itemstack));

				for (int inv = 9; inv < 18; inv++)
				{
					ItemStack itemstack2 = entityplayer.inventory.getStackInSlot(inv);
					if (itemstack2 != null && itemstack2.getItem() == TFItems.SKILL_BERSERKGLUED)
					{
						int k = itemstack.getItemDamage();
						bullet.setDamage(bullet.getDamage() + (double) k);
						break;
					}
				}

				float b = 1.0F;
				if (entityplayer.isSneaking())
				{
					b = 0.0F;
				}
				//弾の初期弾速と集弾性の下限値
				bullet.setHeadingFromThrower(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, 2.0F, 0.0F);
				//弾の弾速と集団性
				bullet.shoot(bullet.motionX, bullet.motionY, bullet.motionZ, 2.5F, this.group * b);

				world.spawnEntity(bullet);
			}
		}
		world.playSound(entityplayer, entityplayer.posX, entityplayer.posY, entityplayer.posZ, this.sound, SoundCategory.AMBIENT, 2.0F, 1.0F / (this.itemRand.nextFloat() * 0.4F + 0.8F));

		getRecoil(itemstack, world, entityplayer);
		return !entityplayer.capabilities.isCreativeMode;
	}

	public float damage(ItemStack itemstackIn)
	{
		return this.damage + ((float) this.getLevel(itemstackIn) * this.levelup);
	}

	//反動
	public final void getRecoil(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		entityplayer.rotationPitch -= ((this.recoil + itemRand.nextFloat() * this.recoilrand) * this.calRecoil(entityplayer));
	}

	// 弾薬の種類
	@Override
	public Item getBullet(ItemStack itemstack)
	{
		return TFItems.BOX_SMALL;
	}

	// 弾薬の判定
	@Override
	public boolean isConformityBullet(ItemStack pItemstack)
	{
		if (pItemstack != null && pItemstack.getItem() == TFItems.BOX_SMALL)
		{
			return true;
		}
		return false;
	}

	//リロード時間
	@Override
	public int reloadTime(ItemStack itemstack)
	{
		return this.reload;
	}

	public int coolTime(ItemStack itemstack)
	{
		return this.cycle;
	}

	@Override
	public void reloadMagazin(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		// リロード音
		world.playSound(entityplayer, entityplayer.posX, entityplayer.posY, entityplayer.posZ, TFSoundEvents.RELOAD, SoundCategory.AMBIENT, 1.5F, 1.0F);
		super.reloadMagazin(itemstack, world, entityplayer);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		double i = this.damage(stack);
		int x = this.getMaxDamage(stack);
		double y = this.reloadTime(stack) / 20;
		float w = this.recoil + this.recoilrand;
		int z = this.coolTime(stack);

		tooltip.add(ChatFormatting.BLUE + I18n.translateToLocal(" ") + (i) + " " + I18n.translateToLocal("tf.bulletdamage"));

		if (this.getLevel(stack) == 10)
		{
			tooltip.add(TextFormatting.YELLOW + " " + I18n.translateToLocal("Lv: Max"));
		}
		else
		{
			tooltip.add(TextFormatting.GRAY + " " + I18n.translateToLocal("Lv: " + (this.getLevel(stack) + 1)));
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			tooltip.add(TextFormatting.GRAY + " " + (x) + " " + I18n.translateToLocal("tf.ammoloaded"));
			tooltip.add(TextFormatting.GRAY + " " + (y) + " " + I18n.translateToLocal("tf.reloadtime"));
			tooltip.add(TextFormatting.GRAY + " " + (w) + " " + I18n.translateToLocal("tf.recoil"));
			tooltip.add(TextFormatting.GRAY + " " + (z) + " " + I18n.translateToLocal("tf.cooltime"));
			tooltip.add(TextFormatting.GRAY + " " + I18n.translateToLocal("tf.bullettype") + " " + I18n.translateToLocal("tf.small"));
		}
		else
		{
			tooltip.add(TextFormatting.ITALIC + I18n.translateToLocal("LShift: Expand tooltip."));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
