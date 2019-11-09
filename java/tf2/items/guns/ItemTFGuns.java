package tf2.items.guns;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import objmodel.IModelCustom;
import tf2.TF2Core;
import tf2.potion.TFPotionPlus;

public abstract class ItemTFGuns extends ItemTFGunsDummy {


	/*
	 * リロードのシーケンス
	 * 0x0000	:射撃状態
	 * 0x1000	:リロード開始
	 * 0x2000	:マガジンリリース、下位24bitはリロード時の残弾
	 * 0x8000	:リロード完了
	 */
	public static int IFNValFire		= 0x0000;
	public static int IFNValReloadStart	= 0x1000;
	public static int IFNValReleaseMag	= 0x2000;
	public static int IFNValReloadEnd	= 0x8000;

	public IModelCustom obj_model = null;//AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/model/ar.obj"));
    public String obj_tex = "gvclib:textures/model/ar.png";

	public ItemTFGuns()
	{
		super();
		setMaxDamage(0);
		this.setMaxStackSize(1);
		this.setCreativeTab(TF2Core.tabstfGuns);
	}

	public int getLevel(ItemStack itemstackIn)
	{
		int level = 0;
		NBTTagCompound nbt = itemstackIn.getTagCompound();

    	if(nbt != null && nbt.hasKey("tf.level"))
    	{
    		//if
    		level = nbt.getInteger("tf.level");
    	}
		return level;
	}

	@Override
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		// リロード完了
		reloadMagazin(stack, worldIn, (EntityPlayer)entityLiving);
		return stack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityLivingBase entityplayer, int i)
	{
		// リロード中止
		cancelReload(itemstack, IFNValReloadEnd);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		ItemStack itemStackIn = playerIn.getHeldItem(handIn);
		int li = getReload(itemStackIn);
		if (li <= IFNValFire)
		{
			if (canReload(itemStackIn, playerIn))
			{
				// リロード
				if (isEmpty(itemStackIn))
				{
					releaseMagazin(itemStackIn, worldIn, playerIn);
					TF_Helper.updateCheckinghSlot(playerIn, itemStackIn);
				}
			}
		}

		playerIn.setActiveHand(handIn);
		return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    }


	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {

		if (world.isRemote)
		{
			if (TF2Core.proxy.getClientPlayer() != entity)
			{
				// クライアントの保持しているプレーヤー以外は処理する必要がない
				return;
			}
			if (isReload(itemstack))
			{
				// リロード中でも終了
				return;
			}
			if (entity instanceof EntityPlayer)
			{
				EntityPlayer lep = (EntityPlayer)entity;
				if (lep.getHeldItemMainhand() != itemstack)
				{
					// 今手に持っていなければ終了
					return;
				}
			}
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack)
	{
		// リロード時は時間を変更
		int li = getReload(itemstack);
		if ((li >= IFNValReloadStart) && (li & 0xf000) < IFNValReloadEnd)
		{
			return reloadTime(itemstack);
		}
		else
		{
			return super.getMaxItemUseDuration(itemstack);
		}
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack)
	{
		// リロード時は構えが違う
		return isReload(itemstack) ? EnumAction.NONE : EnumAction.BOW;
	}

	protected void cancelReload(ItemStack itemstack, int force)
	{
		if (getReload(itemstack) >= force)
		{
			// リロードのキャンセル
			setReload(itemstack, IFNValFire);
		}
	}

	protected boolean canReload(ItemStack itemstack, EntityPlayer entityplayer)
	{
		// リロードが可能かどうかの判定（エンチャント対応）
		if (entityplayer.capabilities.isCreativeMode) return true;

		for (int ll = 0; ll < 36; ll++)
		{
			ItemStack is = entityplayer.inventory.getStackInSlot(ll);
			if (isConformityBullet(is)) return true;
		}
//		for (ItemStack is : entityplayer.inventory.mainInventory)
//		{
//			if (isConformityBullet(is)) return true;
//		}
		return false;
	}

	protected boolean isEmpty(ItemStack itemstack)
	{
		// 残弾ゼロ
		return itemstack.getItemDamage() >= getMaxDamage(itemstack);
	}

	protected void releaseMagazin(ItemStack itemstack, World world, Entity entity)
	{
		// マガジンをリリースしたときの動作、残弾を記録
		setReload(itemstack, (IFNValReleaseMag | (itemstack.getItemDamage() & 0x0fff)));
		itemstack.setItemDamage(getMaxDamage(itemstack));
	}

	protected void reloadMagazin(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		// マガジンを入れたときの動作
		{
			// リロード
			if (entityplayer == null || entityplayer.capabilities.isCreativeMode)
			{
				itemstack.setItemDamage(0);
			}
			else
			{
				// インベントリから弾薬を減らす
				int lk = getReload(itemstack);
				lk = (lk > 0) ? lk & 0x0fff : 0;
				itemstack.setItemDamage(lk);
				for (int ll = 0; lk > 0 && ll < 36; ll++) {
				//for (int ll = 0; lk > 0 && ll < entityplayer.inventory.mainInventory.; ll++) {
					ItemStack lis = entityplayer.inventory.getStackInSlot(ll);
					if (isConformityBullet(lis)) {
						for (;lk > 0 && lis.getCount() > 0;) {
							setAmmo(itemstack, lk--, lis);
							itemstack.setItemDamage(itemstack.getItemDamage() - 1);
							lis.damageItem(1, entityplayer);

							if (lis.isEmpty())
	                        {
	                            entityplayer.inventory.deleteStack(itemstack);
	                        }
//							if (lis.stackSize <= 0)
//							{
//								entityplayer.inventory.getStackInSlot(ll) = null;
//							}
						}
					}
				}
			}
		}
		setReload(itemstack, IFNValReloadEnd);
		clearBolt(itemstack);
		TF_Helper.updateCheckinghSlot(entityplayer, itemstack);
	}

	// リロードにかかる時間
	public abstract int reloadTime(ItemStack itemstack);

	public abstract Item getBullet(ItemStack itemstack);

	public final double calRecoil(EntityPlayer entityplayer)
	{
		double k = 1.0D;
		if (entityplayer.isPotionActive(TFPotionPlus.RECOIL_SUPPRESS))
		{
			PotionEffect potion = entityplayer.getActivePotionEffect(TFPotionPlus.RECOIL_SUPPRESS);
			int i = potion.getAmplifier();

			k = 1.0D - (0.3D + (i * 0.3D));
			if(k < 0)
			{
				k = 0D;
			}
		}
		return k;
	}
	/**
	 * 使用出来る弾薬かどうかの判定
	 */
	public abstract boolean isConformityBullet(ItemStack pItemstack);

	// littleMaidMobはこのメソッドを参照して特殊動作を行います
	public boolean isWeaponReload(ItemStack itemstack, EntityPlayer entityplayer)
	{
		// リロード実行するべきか？
		cancelReload(itemstack, IFNValReloadEnd);
		return isEmpty(itemstack) && canReload(itemstack, entityplayer);
	}

	public boolean isWeaponFullAuto(ItemStack itemstack)
	{
		// フルオート武器か？
		// （右クリックした時点で射撃開始されるもの）
		return false;
	}

	/**
	 * リロードカウンタ読み取り
	 */
	public int getReload(ItemStack pItemstack) {
		checkTags(pItemstack);
		return pItemstack.getTagCompound().getInteger("Reload");
	}

	/**
	 * リロードカウンタのセット
	 */
	public void setReload(ItemStack pItemstack, int pValue) {
		checkTags(pItemstack);
		NBTTagCompound lnbt = pItemstack.getTagCompound();
		lnbt.setInteger("Reload", pValue);
	}

	/**
	 * リロード中かね？
	 */
	public boolean isReload(ItemStack pItemstack) {
		return getReload(pItemstack) >= IFNValReloadStart;
	}

	// 連射用のタイミング回路
	/**
	 * 連射タイミングの設定。
	 * 1=50ms、20=1000ms=1s。
	 */
	public byte getCycleCount(ItemStack pItemstack) {
		return (byte)1;
	}

	protected void resetBolt(ItemStack pItemstack) {
		checkTags(pItemstack);
		pItemstack.getTagCompound().setByte("Bolt", getCycleCount(pItemstack));
	}

	protected void clearBolt(ItemStack pItemstack) {
		checkTags(pItemstack);
		pItemstack.getTagCompound().setByte("Bolt", (byte)0);
	}

	/**
	 * 発射タイミングの確認
	 */
	protected boolean cycleBolt(ItemStack pItemstack)
	{
		checkTags(pItemstack);
		NBTTagCompound lnbt = pItemstack.getTagCompound();
		byte lb = lnbt.getByte("Bolt");
		if (lb <= 0) {
//			if (pReset) resetBolt(pItemstack);
			return true;
		} else {
			lnbt.setByte("Bolt", --lb);
			return false;
		}
	}

	protected int getBolt(ItemStack pItemstack) {
		checkTags(pItemstack);
		NBTTagCompound lnbt = pItemstack.getTagCompound();
		return lnbt.getByte("Bolt");
	}

	/**
	 * マガジンに弾を込める
	 */
	public void setAmmo(ItemStack pGun, int pIndex, ItemStack pAmmo)
	{
		if (!pGun.getTagCompound().hasKey("Ammo")) {
			pGun.getTagCompound().setTag("Ammo", new NBTTagCompound());
		}
		NBTTagCompound lnbt = pGun.getTagCompound().getCompoundTag("Ammo");

		lnbt.setLong(Integer.toString(pIndex), Item.getIdFromItem(pAmmo.getItem()));

		lnbt.setInteger(Integer.toString(pIndex) + "d", pAmmo.getItemDamage());
	}

	/**
	 * 装弾されている弾を取り出す
	 */
	public ItemStack getAmmo(ItemStack pGun, int pIndex)
	{
		NBTTagCompound lnbt = pGun.getTagCompound().getCompoundTag("Ammo");
		String lid = lnbt.getString(Integer.toString(pIndex));
		int ldam = lnbt.getInteger(Integer.toString(pIndex) + "d");
		return lid==null|| lid.isEmpty()? null: new ItemStack((Item)Item.getByNameOrId(lid), 1, ldam);
	}

	public void clearAmmo(ItemStack pGun, int pIndex)
	{
		NBTTagCompound lnbt = pGun.getTagCompound().getCompoundTag("Ammo");
		String ls = Integer.toString(pIndex);
		lnbt.removeTag(ls);
		lnbt.removeTag(ls + "d");
	}

	public boolean checkTags(ItemStack pitemstack)
	{
		// NBTTagの初期化
		if (pitemstack.hasTagCompound()) {
			return true;
		}
		NBTTagCompound ltags = new NBTTagCompound();
		pitemstack.setTagCompound(ltags);
		ltags.setInteger("Reload", 0x0000);
		ltags.setByte("Bolt", (byte)0);
		NBTTagCompound lammo = new NBTTagCompound();
		for (int li = 0; li < getMaxDamage(pitemstack); li++) {
//			lammo.setLong(Integer.toString(li), 0L);
			lammo.setString(Integer.toString(li), "");
			lammo.setInteger(Integer.toString(li) + "d", 0);
		}
		ltags.setTag("Ammo", lammo);
		return false;
	}

}
