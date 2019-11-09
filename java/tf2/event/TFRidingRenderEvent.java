package tf2.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.entity.mob.frend.EntityCFR12;
import tf2.entity.mob.frend.EntityMobCF;

public class TFRidingRenderEvent
{
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onEvent(RenderGameOverlayEvent.Text event)
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
				GlStateManager.pushMatrix();

				GlStateManager.enableBlend();
				//GL11.glPushMatrix();
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				//GL11.glPushMatrix();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
						GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
						GlStateManager.DestFactor.ZERO);

//				if (entityplayer.getRidingEntity() != null && entityplayer.getRidingEntity() instanceof EntityTank)
//				{
//					String ads = "tf:textures/misc/hp_bar.png";
//					this.renderTex(minecraft, scaledresolution, ads, 96, 32, 160, 0);
//
//					EntityTank tank = (EntityTank) entityplayer.getRidingEntity();
//					if (tank.cooltime >= tank.ammo1)
//					{
//						String ads2 = "tf:textures/misc/ok.png";
//						this.renderTex(minecraft, scaledresolution, ads2, 32, 32, 144, 32);
//					}
//					else
//					{
//						String ads2 = "tf:textures/misc/no.png";
//						this.renderTex(minecraft, scaledresolution, ads2, 32, 32, 144, 32);
//					}
//
//					String ads3 = "tf:textures/misc/sub.png";
//					this.renderTex(minecraft, scaledresolution, ads3, 32, 32, 176, 32);
//				}
//				if (entityplayer.getRidingEntity() != null && entityplayer.getRidingEntity() instanceof EntityTank)
//				{
//					EntityLivingBase entity = (EntityLivingBase) entityplayer.getRidingEntity();
//
//					if (entity != null && entity instanceof EntityTank)
//					{
//						int hpi = (int) entity.getHealth();
//
//						String hp = String.valueOf(hpi);
//
//						String sph = "";
//						for (int cnt = hp.length() - 1; cnt >= 0; cnt--)
//						{
//							sph = sph + hp.substring(cnt, cnt + 1);
//						}
//
//						for (int cnt = 0; cnt < hp.length(); cnt++)
//						{
//							String ads = "tf:textures/misc/hp_" + (sph.substring(cnt, cnt + 1)) + ".png";
//							this.renderHP(minecraft, scaledresolution, ads, 32, 32, 0 - (cnt * 32) - 57, 0);
//						}
//					}
//				}
				if (entityplayer.getRidingEntity() != null && entityplayer.getRidingEntity() instanceof EntityCFR12)
				{
					EntityMobCF cf = (EntityMobCF) entityplayer.getRidingEntity();

					String ads = "tf2:textures/misc/ndf12_left.png";
					this.renderHP(minecraft, scaledresolution, ads, 32, 32, -120, 32);

					String ads2 = "tf2:textures/misc/ndf12_right.png";
					this.renderHP(minecraft, scaledresolution, ads2, 32, 32, -120, 64);


					if (cf.cooltimeShift >= cf.limitShift)
					{
						String ads3 = "tf2:textures/misc/ndf_shift_ok.png";
						this.renderHP(minecraft, scaledresolution, ads3, 32, 32, -56, 32);
					}
					else
					{
						String ads3 = "tf2:textures/misc/ndf_shift_no.png";
						this.renderHP(minecraft, scaledresolution, ads3, 32, 32, -56, 32);
					}

					String ads3 = "tf2:textures/misc/sub2.png";
					this.renderHP(minecraft, scaledresolution, ads3, 32, 32, -56, 64);
				}
				if (entityplayer.getRidingEntity() != null && entityplayer.getRidingEntity() instanceof EntityMobCF)
				{
					EntityMobCF cf = (EntityMobCF) entityplayer.getRidingEntity();
					int hpi = cf.maxStackLeftAmmo - cf.stackLeftAmmo + 1;
					if(hpi > 0)
					{
						String hp = String.valueOf(hpi);

						String sph = "";
						for (int cnt = hp.length() - 1; cnt >= 0; cnt--)
						{
							sph = sph + hp.substring(cnt, cnt + 1);
						}

						for (int cnt = 0; cnt < hp.length(); cnt++)
						{
							String ads = "tf2:textures/misc/ammo_" + (sph.substring(cnt, cnt + 1)) + ".png";
							this.renderHP(minecraft, scaledresolution, ads, 8, 12, 0 - (cnt * 8) - 64, 40);
						}

						String ads2 = "tf2:textures/misc/ammo_batu.png";
						this.renderHP(minecraft, scaledresolution, ads2, 8, 12, -88, 40);
					}
					else
					{
						String ads2 = "tf2:textures/misc/ammo_no.png";
						this.renderHP(minecraft, scaledresolution, ads2, 32, 12, -88, 40);
					}

					int hpi2 = cf.maxStackRightAmmo - cf.stackRightAmmo + 1;
					if(hpi2 > 0)
					{
						String hp = String.valueOf(hpi2);

						String sph = "";
						for (int cnt = hp.length() - 1; cnt >= 0; cnt--)
						{
							sph = sph + hp.substring(cnt, cnt + 1);
						}

						for (int cnt = 0; cnt < hp.length(); cnt++)
						{
							String ads = "tf2:textures/misc/ammo_" + (sph.substring(cnt, cnt + 1)) + ".png";
							this.renderHP(minecraft, scaledresolution, ads, 8, 12, 0 - (cnt * 8) - 64, 72);
						}

						String ads2 = "tf2:textures/misc/ammo_batu.png";
						this.renderHP(minecraft, scaledresolution, ads2, 8, 12, -88, 72);
					}
					else
					{
						String ads2 = "tf2:textures/misc/ammo_no.png";
						this.renderHP(minecraft, scaledresolution, ads2, 32, 12, -88, 72);
					}
				}
				if (entityplayer.getRidingEntity() != null && entityplayer.getRidingEntity() instanceof EntityMobCF)
				{
					EntityLivingBase entity = (EntityLivingBase) entityplayer.getRidingEntity();

					if (entity != null && entity instanceof EntityMobCF)
					{
						int hpi = (int) entity.getHealth();

						String hp = String.valueOf(hpi);

						String sph = "";
						for (int cnt = hp.length() - 1; cnt >= 0; cnt--)
						{
							sph = sph + hp.substring(cnt, cnt + 1);
						}

						for (int cnt = 0; cnt < hp.length(); cnt++)
						{
							String ads = "tf2:textures/misc/hp_" + (sph.substring(cnt, cnt + 1)) + ".png";
							this.renderHP(minecraft, scaledresolution, ads, 32, 32, 0 - (cnt * 32) - 57, 0);
						}
					}
				}
				if (entityplayer.getRidingEntity() != null && entityplayer.getRidingEntity() instanceof EntityMobCF)
				{
					String ads0 = "tf2:textures/misc/hp_bar.png";
					this.renderHP(minecraft, scaledresolution, ads0, 96, 32, -120, 0);

					String ads = "tf2:textures/misc/boost_under.png";
					this.renderBoostUnder(minecraft, scaledresolution, ads, 12, 170, 10, 0);

					EntityMobCF cf = (EntityMobCF) entityplayer.getRidingEntity();
					int boost = cf.boostStack;
					int boostMax = cf.maxBoostStack;

					int y = boostMax + (160 - boostMax);
					float k = 160F / boostMax;
					float x = y - ((boostMax - boost) * k);

					String ads2 = "tf2:textures/misc/boost.png";
					this.renderBoost(minecraft, scaledresolution, ads2, 5, (int) x, 10, 0);

					if (cf.disBoost < 200)
					{
						ads = "tf2:textures/misc/boost_under_no.png";
						this.renderBoostUnder(minecraft, scaledresolution, ads, 12, 170, 10, 0);

						ads2 = "tf2:textures/misc/boost_no.png";
						this.renderBoost(minecraft, scaledresolution, ads2, 5, (int) x, 10, 0);

						String ads3 = "tf2:textures/misc/recharge.png";
						this.renderBoostUnder(minecraft, scaledresolution, ads3, 49, 9, 8, 0);
					}

					String ads4 = "tf2:textures/misc/compass.png";
					this.renderCompass(minecraft, scaledresolution, ads4, 128, 16, 0, 10);

					int yaw = (int) cf.rotationYaw;
					if (yaw > 360)
					{
						yaw -= 360;
					}
					else if (yaw < 0)
					{
						yaw += 360;
					}
					if (0 <= yaw && yaw <= 50)
					{
						fontrenderer.drawString("South", (int) (i / 2) - yaw - 8, 9, 0xFFFFFF);
					}
					if (40 <= yaw && yaw <= 140)
					{
						fontrenderer.drawString("West", (int) (i / 2) - yaw + (90 - 8), 9, 0xFFFFFF);
					}
					if (130 <= yaw && yaw <= 230)
					{
						fontrenderer.drawString("North", (int) (i / 2) - yaw + (180 - 8), 9, 0xFFFFFF);
					}
					if (220 <= yaw && yaw <= 320)
					{
						fontrenderer.drawString("East", (int) (i / 2) - yaw + (270 - 8), 9, 0xFFFFFF);
					}
					if (310 <= yaw && yaw < 360)
					{
						fontrenderer.drawString("South", (int) (i / 2) - yaw + (360 - 8), 9, 0xFFFFFF);
					}

					int posY = (int) cf.posY;
					fontrenderer.drawString("--" + posY + "--", (int) (i / 2) - 10, 19, 0xFFFFFF);
				}

				//GL11.glPopMatrix();
				//GL11.glPopMatrix();
				GlStateManager.disableBlend();

				GlStateManager.popMatrix();
			}
		}
	}

	//ロボット騎乗時のプレイヤー表示を消す
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderEvent(RenderPlayerEvent.Pre event)
	{
		EntityPlayer entity = event.getEntityPlayer();

		if (entity.getRidingEntity() != null && entity.getRidingEntity() instanceof EntityMobCF)
		{
			event.setCanceled(true);
		}
		//		  if(entity.getRidingEntity() != null && entity.getRidingEntity() instanceof EntityTruck)
		//		  {
		//			  event.setCanceled(true);
		//		  }
//		if (entity.getRidingEntity() != null && entity.getRidingEntity() instanceof EntityTank)
//		{
//			event.setCanceled(true);
//		}
	}

	private void renderStart()
	{
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableAlpha();
	}

	private void renderEnd()
	{
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@SideOnly(Side.CLIENT)
	protected void renderHP(Minecraft minecraft, ScaledResolution scaled, String ads, int texX, int texY, int posX, int posY)
	{
		int x = scaled.getScaledWidth();
		int y = scaled.getScaledHeight();

		this.renderStart();
		minecraft.getTextureManager().bindTexture(new ResourceLocation(ads));
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(x + posX, (y / 2) + (texY / 2) + posY, -90.0D).tex(0.0D, 1.0D).endVertex();
		vertexbuffer.pos(x + texX + posX, (y / 2) + (texY / 2) + posY, -90.0D).tex(1.0D, 1.0D).endVertex();
		vertexbuffer.pos(x + texX + posX, (y / 2) - (texY / 2) + posY, -90.0D).tex(1.0D, 0.0D).endVertex();
		vertexbuffer.pos(x + posX, (y / 2) - (texY / 2) + posY, -90.0D).tex(0.0D, 0.0D).endVertex();
		tessellator.draw();
		this.renderEnd();
	}

	//texでtextureのサイズ,posで位置
	@SideOnly(Side.CLIENT)
	protected void renderBoost(Minecraft minecraft, ScaledResolution scaled, String ads, int texX, int texY, int posX, int posY)
	{
		int x = scaled.getScaledWidth();
		int y = scaled.getScaledHeight();

		this.renderStart();
		minecraft.getTextureManager().bindTexture(new ResourceLocation(ads));
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(2 + posX, (y / 2) + (texY / 2) + posY, 90.0D).tex(0.0D, 1.0D).endVertex();
		vertexbuffer.pos(2 + texX + posX, (y / 2) + (texY / 2) + posY, 90.0D).tex(1.0D, 1.0D).endVertex();
		vertexbuffer.pos(2 + texX + posX, (y / 2) - (texY / 2) + posY, 90.0D).tex(1.0D, 0.0D).endVertex();
		vertexbuffer.pos(2 + posX, (y / 2) - (texY / 2) + posY, 90.0D).tex(0.0D, 0.0D).endVertex();
		tessellator.draw();
		this.renderEnd();
	}

	@SideOnly(Side.CLIENT)
	protected void renderBoostUnder(Minecraft minecraft, ScaledResolution scaled, String ads, int texX, int texY, int posX, int posY)
	{
		int x = scaled.getScaledWidth();
		int y = scaled.getScaledHeight();

		this.renderStart();
		minecraft.getTextureManager().bindTexture(new ResourceLocation(ads));
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos(0 + posX, (y / 2) + (texY / 2) + posY, -90.0D).tex(0.0D, 1.0D).endVertex();
		vertexbuffer.pos(texX + posX, (y / 2) + (texY / 2) + posY, -90.0D).tex(1.0D, 1.0D).endVertex();
		vertexbuffer.pos(texX + posX, (y / 2) - (texY / 2) + posY, -90.0D).tex(1.0D, 0.0D).endVertex();
		vertexbuffer.pos(0 + posX, (y / 2) - (texY / 2) + posY, -90.0D).tex(0.0D, 0.0D).endVertex();
		tessellator.draw();
		this.renderEnd();
	}

	@SideOnly(Side.CLIENT)
	protected void renderCompass(Minecraft minecraft, ScaledResolution scaled, String ads, int texX, int texY, int posX, int posY)
	{
		int x = scaled.getScaledWidth();
		int y = scaled.getScaledHeight();

		this.renderStart();
		minecraft.getTextureManager().bindTexture(new ResourceLocation(ads));
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos((x / 2) - (texX / 2) + posX, texY + posY, -90.0D).tex(0.0D, 1.0D).endVertex();
		vertexbuffer.pos((x / 2) + (texX / 2) + posX, texY + posY, -90.0D).tex(1.0D, 1.0D).endVertex();
		vertexbuffer.pos((x / 2) + (texX / 2) + posX, 0 + posY, -90.0D).tex(1.0D, 0.0D).endVertex();
		vertexbuffer.pos((x / 2) - (texX / 2) + posX, 0 + posY, -90.0D).tex(0.0D, 0.0D).endVertex();
		tessellator.draw();
		this.renderEnd();
	}

//	@SideOnly(Side.CLIENT)
//	protected void renderBullet(Minecraft minecraft, ScaledResolution scaled, String ads, int texX, int texY, int posX, int posY)
//	{
//		int x = scaled.getScaledWidth();
//		int y = scaled.getScaledHeight();
//
//		this.renderStart();
//		minecraft.getTextureManager().bindTexture(new ResourceLocation(ads));
//		Tessellator tessellator = Tessellator.getInstance();
//		BufferBuilder vertexbuffer = tessellator.getBuffer();
//		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
//		vertexbuffer.pos(x - (texX / 2) + posX, y + (texY / 2) + posY, -90.0D).tex(0.0D, 1.0D).endVertex();
//		vertexbuffer.pos(x + (texX / 2) + posX, y + (texY / 2) + posY, -90.0D).tex(1.0D, 1.0D).endVertex();
//		vertexbuffer.pos(x + (texX / 2) + posX, y - (texY / 2) + posY, -90.0D).tex(1.0D, 0.0D).endVertex();
//		vertexbuffer.pos(x - (texX / 2) + posX, y - (texY / 2) + posY, -90.0D).tex(0.0D, 0.0D).endVertex();
//		tessellator.draw();
//		this.renderEnd();
//	}
}
