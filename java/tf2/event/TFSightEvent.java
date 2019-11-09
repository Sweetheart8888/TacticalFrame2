package tf2.event;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TFItems;
import tf2.items.guns.ItemTFGuns;

public class TFSightEvent
{
	//public String ads;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onEvent(RenderGameOverlayEvent.Text event)
	{
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		ScaledResolution scaledresolution = new ScaledResolution(minecraft);
		int i = scaledresolution.getScaledWidth();
		int j = scaledresolution.getScaledHeight();
		//Entity entity = minecraft.pointedEntity;
		EntityPlayer entityplayer = minecraft.player;
		//EntityPlayer entityplayer = event.player;
		ItemStack itemstack = ((EntityPlayer) (entityplayer)).getHeldItemMainhand();
		minecraft.entityRenderer.setupOverlayRendering();

		Minecraft view = Minecraft.getMinecraft();

		if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
		{
			if (view.gameSettings.thirdPersonView == 0)
			{
				if (itemstack != null && itemstack.getItem() instanceof ItemTFGuns)
				{

					GlStateManager.enableBlend();
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glPushMatrix();// 12
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
					GL11.glPushMatrix();// 13
					GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
							GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
							GlStateManager.DestFactor.ZERO);

					if (entityplayer.isSneaking())
					{
						if (itemstack != null && itemstack.getItem() == TFItems.M24 ||
								itemstack != null && itemstack.getItem() == TFItems.M200 ||
								itemstack != null && itemstack.getItem() == TFItems.XM2010)
						{
							String ads = "tf2:textures/misc/scope1.png";
							GuiIngameForge.renderCrosshairs = false;
							this.renderPumpkinBlur(minecraft, scaledresolution, ads);
						}
						if (itemstack != null && itemstack.getItem() == TFItems.M82A1 ||
								itemstack != null && itemstack.getItem() == TFItems.PGM)
						{
							String ads = "tf2:textures/misc/scope2.png";
							GuiIngameForge.renderCrosshairs = false;
							this.renderPumpkinBlur(minecraft, scaledresolution, ads);
						}
						if (itemstack != null && itemstack.getItem() == TFItems.WA2000)
						{
							String ads = "tf2:textures/misc/scope5.png";
							GuiIngameForge.renderCrosshairs = false;
							this.renderPumpkinBlur(minecraft, scaledresolution, ads);
						}
						if (itemstack != null && itemstack.getItem() == TFItems.PSG1)
						{
							String ads = "tf2:textures/misc/scope6.png";
							GuiIngameForge.renderCrosshairs = false;
							this.renderPumpkinBlur(minecraft, scaledresolution, ads);
						}
						if (itemstack != null && itemstack.getItem() == TFItems.SIG550 ||
								itemstack != null && itemstack.getItem() == TFItems.G36)
						{
							String ads = "tf2:textures/misc/scope7.png";
							GuiIngameForge.renderCrosshairs = false;
							this.renderPumpkinBlur(minecraft, scaledresolution, ads);
						}
						if (itemstack != null && itemstack.getItem() == TFItems.SVD)
						{
							String ads = "tf2:textures/misc/scope4.png";
							GuiIngameForge.renderCrosshairs = false;
							this.renderPumpkinBlur(minecraft, scaledresolution, ads);
						}
						if (itemstack != null && itemstack.getItem() == TFItems.M1A1)
						{
							String ads = "tf2:textures/misc/scopegre1.png";
							GuiIngameForge.renderCrosshairs = false;
							this.renderPumpkinBlur(minecraft, scaledresolution, ads);
						}
						if (itemstack != null && itemstack.getItem() == TFItems.M20 ||
								itemstack != null && itemstack.getItem() == TFItems.MGL140)
						{
							String ads = "tf2:textures/misc/scopegre2.png";
							GuiIngameForge.renderCrosshairs = false;
							this.renderPumpkinBlur(minecraft, scaledresolution, ads);
						}
					} else
					{
						GuiIngameForge.renderCrosshairs = true;
					}

					if (itemstack != null && itemstack.getItem() == TFItems.AK47 ||
							itemstack != null && itemstack.getItem() == TFItems.AK74 ||
							itemstack != null && itemstack.getItem() == TFItems.AK12 ||
							itemstack != null && itemstack.getItem() == TFItems.M16A1 ||
							itemstack != null && itemstack.getItem() == TFItems.M4A1 ||
							itemstack != null && itemstack.getItem() == TFItems.HK416 ||
							itemstack != null && itemstack.getItem() == TFItems.FAMAS ||
							itemstack != null && itemstack.getItem() == TFItems.OTS14 ||
							itemstack != null && itemstack.getItem() == TFItems.SIG550 ||
							itemstack != null && itemstack.getItem() == TFItems.G36)
					{
						if (!entityplayer.isSneaking())
						{
							String ads = "tf2:textures/misc/re_ar.png";
							this.renderCrossHair(minecraft, scaledresolution, ads);
						}
					}
					if (itemstack != null && itemstack.getItem() == TFItems.SPAS12 ||
							itemstack != null && itemstack.getItem() == TFItems.M1014 ||
							itemstack != null && itemstack.getItem() == TFItems.M870)
					{
						if (entityplayer.isSneaking())
						{
							String ads = "tf2:textures/misc/re_sg_ads.png";
							this.renderCrossHair(minecraft, scaledresolution, ads);
						}
						else
						{
							String ads = "tf2:textures/misc/re_spas.png";
							this.renderCrossHair(minecraft, scaledresolution, ads);
						}
					}
					if (itemstack != null && itemstack.getItem() == TFItems.SAIGA12 ||
							itemstack != null && itemstack.getItem() == TFItems.VEPR12 ||
							itemstack != null && itemstack.getItem() == TFItems.AA12)
					{
						if (entityplayer.isSneaking())
						{
							String ads = "tf2:textures/misc/re_saiga_ads.png";
							this.renderCrossHair(minecraft, scaledresolution, ads);
						}
						else
						{
							String ads = "tf2:textures/misc/re_saiga.png";
							this.renderCrossHair(minecraft, scaledresolution, ads);
						}
					}
					if (itemstack != null && itemstack.getItem() == TFItems.UZI ||
							itemstack != null && itemstack.getItem() == TFItems.PPSH41 ||
							itemstack != null && itemstack.getItem() == TFItems.EVO3 ||
							itemstack != null && itemstack.getItem() == TFItems.P90 ||
							itemstack != null && itemstack.getItem() == TFItems.MP7 ||
							itemstack != null && itemstack.getItem() == TFItems.PP19 ||
							itemstack != null && itemstack.getItem() == TFItems.UMP9)
					{
						if (!entityplayer.isSneaking())
						{
							String ads = "tf2:textures/misc/re_smg.png";
							this.renderCrossHair(minecraft, scaledresolution, ads);
						}
					}
					if (itemstack != null && itemstack.getItem() == TFItems.MG42 ||
							itemstack != null && itemstack.getItem() == TFItems.M60E1)
					{
						if (entityplayer.isSneaking())
						{
							String ads = "tf2:textures/misc/re_smg.png";
							this.renderCrossHair(minecraft, scaledresolution, ads);
						}
						else
						{
							String ads = "tf2:textures/misc/re_lmg.png";
							this.renderCrossHair(minecraft, scaledresolution, ads);
						}
					}
//					if (itemstack != null && itemstack.getItem() == TFItems.SCAR ||
//							itemstack != null && itemstack.getItem() == TFItems.M14EBR)
//					{
//						String ads = "tf:textures/misc/re_scar.png";
//						this.renderCrossHair(minecraft, scaledresolution, ads);
//					}
					if (itemstack != null && itemstack.getItem() == TFItems.SVD ||
							itemstack != null && itemstack.getItem() == TFItems.M24 ||
							itemstack != null && itemstack.getItem() == TFItems.M200 ||
							itemstack != null && itemstack.getItem() == TFItems.WA2000 ||
							itemstack != null && itemstack.getItem() == TFItems.M82A1 ||
							itemstack != null && itemstack.getItem() == TFItems.MOSINNAGANT ||
							itemstack != null && itemstack.getItem() == TFItems.XM2010 ||
							itemstack != null && itemstack.getItem() == TFItems.PSG1 ||
							itemstack != null && itemstack.getItem() == TFItems.PGM)
					{
						if (!entityplayer.isSneaking())
						{
							String ads = "tf2:textures/misc/re_m24.png";
							this.renderCrossHair(minecraft, scaledresolution, ads);
						}
					}
					if (itemstack != null && itemstack.getItem() == TFItems.M1A1 ||
							itemstack != null && itemstack.getItem() == TFItems.M20 ||
							itemstack != null && itemstack.getItem() == TFItems.MGL140)
					{
						if (!entityplayer.isSneaking())
						{
							String ads = "tf2:textures/misc/re_spas.png";
							this.renderCrossHair(minecraft, scaledresolution, ads);
						}
					}

					GL11.glPopMatrix();// 13
					GL11.glPopMatrix();// 12
					// GL11.glPopAttrib();//11
					GlStateManager.disableBlend();
				}

				//          		 if(entityplayer.ridingEntity != null && entityplayer.ridingEntity instanceof EntityRobot)
				//          		 {
				//          			 ads = "tf:textures/misc/mecha.png";
				//          			 this.renderMonitor(minecraft,i, j );
				//
				//          			 ads = "tf:textures/misc/hp_bar.png";
				//          			 this.renderHPBar(minecraft,i, j );
				//          		 }
				//          		 if(entityplayer.ridingEntity != null && entityplayer.ridingEntity instanceof EntityRobot)
				//          		 {
				//          			 EntityLivingBase entity = (EntityLivingBase)entityplayer.ridingEntity;
				//
				//          			 if(entity != null && entity instanceof EntityRobot)
				//          			 {
				//          				 int hpi = (int)entity.getHealth();
				//
				//          				 String hp = String.valueOf(hpi);
				//
				//          				 String sph = "";
				//          				 for(int cnt = hp.length() - 1; cnt >= 0; cnt--)
				//          				 {
				//          				     sph = sph + hp.substring(cnt, cnt + 1);
				//          				 }
				//
				//          				 for(int cnt = 0; cnt < hp.length(); cnt++)
				//          				 {
				//          				     ads = "tf:textures/misc/hp_" + (sph.substring(cnt, cnt + 1)) + ".png";
				//          				     this.renderHP(minecraft,i - (cnt * 64), j);
				//          				 }
				//          			 }
				//

			}
		}
	}

	@SideOnly(Side.CLIENT)
	protected void renderPumpkinBlur(Minecraft minecraft, ScaledResolution scaled, String ads)
	{
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableAlpha();
		minecraft.getTextureManager().bindTexture(new ResourceLocation(ads));
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(0.0D, (double) scaled.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
		vertexbuffer.pos((double) scaled.getScaledWidth(), (double) scaled.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
		vertexbuffer.pos((double) scaled.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
		vertexbuffer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@SideOnly(Side.CLIENT)
	protected void renderCrossHair(Minecraft minecraft, ScaledResolution scaled, String ads)
	{
		int x = scaled.getScaledWidth();
		int y = scaled.getScaledHeight();

		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableAlpha();
		minecraft.getTextureManager().bindTexture(new ResourceLocation(ads));
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos((x / 2) - 64, (y / 2) + 64, -90.0D).tex(0.0D, 1.0D).endVertex();
		vertexbuffer.pos((x / 2) + 64, (y / 2) + 64, -90.0D).tex(1.0D, 1.0D).endVertex();
		vertexbuffer.pos((x / 2) + 64, (y / 2) - 64, -90.0D).tex(1.0D, 0.0D).endVertex();
		vertexbuffer.pos((x / 2) - 64, (y / 2) - 64, -90.0D).tex(0.0D, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
