package tf2.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TFItems;
import tf2.entity.mob.frend.EntityMobCF;
import tf2.items.guns.ItemTFGuns;

public class TFZoomEvent
{
	@SideOnly(Side.CLIENT)
    @SubscribeEvent
	  public void renderfov(FOVUpdateEvent event)
	  {
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.player;
		ItemStack itemstack = ((EntityPlayer) (entityplayer)).getHeldItemMainhand();

		if(itemstack != null && itemstack.getItem() == TFItems.M24 ||
				itemstack != null && itemstack.getItem() == TFItems.M200||
				itemstack != null && itemstack.getItem() == TFItems.M82A1 ||
				itemstack != null && itemstack.getItem() == TFItems.XM2010 ||
				itemstack != null && itemstack.getItem() == TFItems.PGM)
		{
			if(entityplayer.isSneaking())
			{
				event.setNewfov(event.getFov()/ 5F);
			}
		}
		if(itemstack != null && itemstack.getItem() == TFItems.WA2000 ||
				itemstack != null && itemstack.getItem() == TFItems.PSG1)
		{
			if(entityplayer.isSneaking())
			{
				event.setNewfov(event.getFov()/ 4F);
			}
		}
		if(itemstack != null && itemstack.getItem() == TFItems.SVD ||
				itemstack != null && itemstack.getItem() == TFItems.SIG550 ||
				itemstack != null && itemstack.getItem() == TFItems.G36)
		{
			if(entityplayer.isSneaking())
			{
				event.setNewfov(event.getFov()/ 3F);
			}
		}
		if(itemstack != null && itemstack.getItem() == TFItems.MOSINNAGANT
				 )
		{
			if(entityplayer.isSneaking())
			{
				event.setNewfov(event.getFov()/ 2F);
			}
		}
		if(itemstack != null && itemstack.getItem() == TFItems.AK47 ||
				itemstack != null && itemstack.getItem() == TFItems.AK74||
				itemstack != null && itemstack.getItem() == TFItems.AK12 ||
				itemstack != null && itemstack.getItem() == TFItems.M16A1 ||
				itemstack != null && itemstack.getItem() == TFItems.M4A1 ||
				itemstack != null && itemstack.getItem() == TFItems.HK416 ||
				itemstack != null && itemstack.getItem() == TFItems.FAMAS ||
				itemstack != null && itemstack.getItem() == TFItems.OTS14)
		{
			if(entityplayer.isSneaking())
			{
				event.setNewfov(event.getFov()/ 1.3F);
			}
		}
	  }

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onBulletRenderEvent(RenderGameOverlayEvent.Text event)
	{
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		ScaledResolution scaledresolution = new ScaledResolution(minecraft);
		int i = scaledresolution.getScaledWidth();
		int j = scaledresolution.getScaledHeight();
		EntityPlayer entityplayer = minecraft.player;
		ItemStack itemstack = ((EntityPlayer) (entityplayer)).getHeldItemMainhand();
		minecraft.entityRenderer.setupOverlayRendering();
		FontRenderer fontrenderer = minecraft.fontRenderer;
		minecraft.fontRenderer.setUnicodeFlag(minecraft.isUnicode());

		Minecraft view = Minecraft.getMinecraft();

		if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
		{
			if (view.gameSettings.thirdPersonView == 0)
			{
				if (itemstack != null && itemstack.getItem() instanceof ItemTFGuns)
				{
					ItemTFGuns gun = (ItemTFGuns) itemstack.getItem();

					int iii = 0;
					InventoryPlayer playerInv = entityplayer.inventory;
					GlStateManager.pushMatrix();
					GlStateManager.color(1F, 1F, 1F);
					GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
							GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
							GlStateManager.DestFactor.ZERO);
					minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
					String d = String.format("%1$3d", itemstack.getMaxDamage() - itemstack.getItemDamage());
					String d1 = String.format("%1$3d", itemstack.getMaxDamage());
					for (int is = 0; is < 36; ++is)
					{
						ItemStack itemi = playerInv.getStackInSlot(is);
						if (itemi != null && itemi.getItem() == gun.getBullet(itemstack))
						{
							iii = iii + (itemi.getMaxDamage() - itemi.getItemDamage());
						}
					}
					String d2 = String.format("%1$3d", iii);
					if(!(entityplayer.isRiding() && entityplayer.getRidingEntity() instanceof EntityMobCF))
					{
						fontrenderer.drawString(d + " /" + d1, i - 70, j - 30 + 0, 0xFFFFFF);
						fontrenderer.drawString("x " + d2, i - 50, j - 45 + 0, 0xFFFFFF);
						iii = 0;
						if (gun.getBullet(itemstack) != null)
						{
							RenderItem renderitem = minecraft.getRenderItem();
							renderitem.renderItemIntoGUI(new ItemStack(gun.getBullet(itemstack)), i - 68, j - 49);
						}
					}
					GlStateManager.popMatrix();
				}
//				if (itemstack != null && itemstack.getItem() instanceof ItemTFWeaponGun)
//				{
//					ItemTFWeaponGun gun = (ItemTFWeaponGun) itemstack.getItem();
//
//					int iii = 0;
//					InventoryPlayer playerInv = entityplayer.inventory;
//
//					GL11.glPushMatrix();
//					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//					GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
//							GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
//							GlStateManager.DestFactor.ZERO);
//					minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
//					String d = String.format("%1$3d", itemstack.getMaxDamage() - itemstack.getItemDamage());
//					String d1 = String.format("%1$3d", itemstack.getMaxDamage());
//					for (int is = 0; is < 36; ++is)
//					{
//						ItemStack itemi = playerInv.getStackInSlot(is);
//						if (itemi != null && itemi.getItem() == gun.getBullet(itemstack))
//						{
//							iii = iii + (itemi.getMaxDamage() - itemi.getItemDamage());
//						}
//					}
//					String d2 = String.format("%1$3d", iii);
//					fontrenderer.drawString(d + " /" + d1, i - 70, j - 30 + 0, 0xFFFFFF);
//					fontrenderer.drawString("x " + d2, i - 50, j - 45 + 0, 0xFFFFFF);
//					iii = 0;
//					if (gun.getBullet(itemstack) != null)
//					{
//						RenderItem renderitem = minecraft.getRenderItem();
//						renderitem.renderItemIntoGUI(new ItemStack(gun.getBullet(itemstack)), i - 68, j - 49);
//					}
//					GL11.glPopMatrix();
//				}
			}
		}
	}
}
