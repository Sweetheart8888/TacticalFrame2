package tf2.items.guns;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
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

public class ItemTFGunsSMG extends ItemTFGuns implements IHasModel
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

	public ItemTFGunsSMG(String name, int magazineSize, float bulletDamage, float levelUp, float bulletGrouping, SoundEvent gunSound, float gunRecoil, float gunRecoilRondom, int fireRate, int reloadTime)
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
		this.cycle = fireRate;
		this.reload = reloadTime;
		this.tex = name;
	}

	@Override
	public void registerModel()
	{
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "gun/" + tex), "inventory"));
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
	{
		if (!isReload(itemstack))
		{
			// リロードしてない時のみ動作
			boolean lflag = cycleBolt(itemstack);

			if (entity != null && entity instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer) entity;
				if (entityplayer.isHandActive() && itemstack == entityplayer.getHeldItemMainhand())
				{
					// 射撃中なら実行
					if (lflag)
					{
						// 発射
						int lj = getReload(itemstack);
						int li = getMaxDamage(itemstack) - itemstack.getItemDamage();
						if (li > 0)
						{
							// 発射
							fireBullet(itemstack, world, entityplayer);
							resetBolt(itemstack);
							TF_Helper.updateCheckinghSlot(entityplayer, itemstack);
						}
						else
						{
							// 弾切れ
							if (canReload(itemstack, entityplayer))
							{
								entityplayer.stopActiveHand();
							}
						}
					}
					else
					{
						TF_Helper.updateCheckinghSlot(entityplayer, itemstack);
					}
				}
			}
		}
		super.onUpdate(itemstack, world, entity, i, flag);
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

				float b = 1.0F;
				if (entityplayer.isSneaking())
				{
					b = 0.0F;
				}

				//弾の初期弾速と集弾性の下限値
				bullet.setHeadingFromThrower(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, 2.0F, 0.0F);
				//弾の弾速と集団性
				bullet.shoot(bullet.motionX, bullet.motionY, bullet.motionZ, 2.75F, group * b);

				world.spawnEntity(bullet);
			}
		}
		world.playSound(entityplayer, entityplayer.posX, entityplayer.posY, entityplayer.posZ, this.sound, SoundCategory.AMBIENT, 2.4F, 1.0F / (this.itemRand.nextFloat() * 0.4F + 0.8F));

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

	//連射速度
	@Override
	public byte getCycleCount(ItemStack pItemstack)
	{
		return (byte) cycle;
	}

	//リロード時間
	@Override
	public int reloadTime(ItemStack itemstack)
	{
		return this.reload;
	}

	@Override
	public void reloadMagazin(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		// リロード音
		world.playSound(entityplayer, entityplayer.posX, entityplayer.posY, entityplayer.posZ, TFSoundEvents.RELOAD, SoundCategory.AMBIENT, 1.5F, 1.0F);
		super.reloadMagazin(itemstack, world, entityplayer);
	}

	//フルオートかどうか
	@Override
	public boolean isWeaponFullAuto(ItemStack itemstack)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		double i = this.damage(stack);
		int x = this.getMaxDamage(stack);
		double y = this.reloadTime(stack) / 20;
		float w = this.recoil + this.recoilrand;
		byte z = this.getCycleCount(stack);

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
			tooltip.add(TextFormatting.GRAY + " " + (z) + "r/t" + " " + I18n.translateToLocal("tf.firerate"));
			tooltip.add(TextFormatting.GRAY + " " + I18n.translateToLocal("tf.bullettype") + " " + I18n.translateToLocal("tf.small"));
		}
		else
		{
			tooltip.add(TextFormatting.ITALIC + I18n.translateToLocal("LShift: Expand tooltip."));
		}

		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
