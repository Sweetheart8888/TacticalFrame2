package tf2.event;

import java.nio.FloatBuffer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TFItems;
import tf2.items.guns.ItemTFGuns;

public class EventGunRender
{
//	private static final FloatBuffer COLOR_BUFFER = GLAllocation.createDirectFloatBuffer(4);
	@SideOnly(Side.CLIENT)
	public static FloatBuffer setColorBuffer(float p_74521_0_, float p_74521_1_, float p_74521_2_, float p_74521_3_)
	{
		FloatBuffer COLOR_BUFFER = GLAllocation.createDirectFloatBuffer(4);
		COLOR_BUFFER.clear();
		COLOR_BUFFER.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
		COLOR_BUFFER.flip();
		return COLOR_BUFFER;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void rendergun(RenderHandEvent event)
	{
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.player;
		ItemStack itemstack = ((EntityPlayer) (entityplayer)).getHeldItemMainhand();
		ItemStack itemstack1 = ((EntityPlayer) (entityplayer)).getHeldItemOffhand();
		if (itemstack != null && itemstack.getItem() instanceof ItemTFGuns)
		{
			ItemTFGuns gun = (ItemTFGuns) itemstack.getItem();

			if (minecraft.gameSettings.thirdPersonView == 0)
			{

				GlStateManager.pushMatrix();//guns

				GlStateManager.enableRescaleNormal();

				RenderHelper.enableStandardItemLighting();

				if (entityplayer.isSprinting())
				{
					GlStateManager.translate(0.6F, -0.5F, -0.8F);

					GlStateManager.rotate(270F, 0.0F, 1.0F, 0.0F);
					GlStateManager.rotate(-10F, 1.0F, 0.0F, 0.0F);

				}
				//				//				スニーク時
				//				else if (entityplayer.isSneaking() && itemstack.getItem() == TFItems.MOSINNAGANT)
				//				{
				//					GlStateManager.translate(0.0F, -0.25F, -0.2F);
				//					GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
				//				}
				else if (entityplayer.isSneaking() && itemstack.getItem() == TFItems.AK47 ||
						entityplayer.isSneaking() && itemstack.getItem() == TFItems.AK74 ||
						entityplayer.isSneaking() && itemstack.getItem() == TFItems.AK12)
				{
					GlStateManager.translate(0.0F, -0.21F, -0.1F);
					GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
				}
				else if (entityplayer.isSneaking() && itemstack.getItem() == TFItems.M16A1 ||
						entityplayer.isSneaking() && itemstack.getItem() == TFItems.M4A1 ||
						entityplayer.isSneaking() && itemstack.getItem() == TFItems.HK416)
				{
					GlStateManager.translate(0.0F, -0.25F, -0.1F);
					GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
				}
				else if (entityplayer.isSneaking() && itemstack.getItem() == TFItems.FAMAS ||
						entityplayer.isSneaking() && itemstack.getItem() == TFItems.OTS14)
				{
					GlStateManager.translate(0.0F, -0.362F, -0.11F);
					GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
				}
				else if (entityplayer.isSneaking() && itemstack.getItem() == TFItems.EVO3 ||
						entityplayer.isSneaking() && itemstack.getItem() == TFItems.MP7 ||
						entityplayer.isSneaking() && itemstack.getItem() == TFItems.UMP9)
				{
					GlStateManager.translate(0.0F, -0.28F, -0.115F);
					GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
				}
				else if (entityplayer.isSneaking() && itemstack.getItem() == TFItems.UZI)
				{
					GlStateManager.translate(0.0F, -0.18F, -0.45F);
					GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
				}
				else if (entityplayer.isSneaking() && itemstack.getItem() == TFItems.P90)
				{
					GlStateManager.translate(0.0F, -0.242F, -0.2F);
					GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
				}
				else if (entityplayer.isSneaking() && itemstack.getItem() == TFItems.PP19)
				{
					GlStateManager.translate(0.0F, -0.2F, 0.3F);
					GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
				}
				else if (entityplayer.isSneaking() && itemstack.getItem() == TFItems.PPSH41)
				{
					GlStateManager.translate(0.0F, -0.175F, 0.05F);
					GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
				}
				else if (gun.isReload(itemstack))
				{
					GlStateManager.translate(0.85F, -0.3F, -0.75F);

					GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
					GlStateManager.rotate(60F, 1.0F, 0.0F, 1.0F);
				}
				else
				{
					//GL11.glTranslatef(gun.model_x,gun.model_y, gun.model_z);//1.5,-2,-2.5
					GlStateManager.translate(0.85F, -0.4F, -0.75F);

					GlStateManager.rotate(165F, 0.0F, 1.0F, 0.0F);
				} //1e

				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));

				this.rendermat(entityplayer, itemstack, gun);

				GlStateManager.disableRescaleNormal();
				GlStateManager.popMatrix();//gune

			}
		} //item
		if (itemstack1 != null && itemstack1.getItem() instanceof ItemTFGuns)
		{
			ItemTFGuns gun = (ItemTFGuns) itemstack1.getItem();

			if (minecraft.gameSettings.thirdPersonView == 0)
			{
				GlStateManager.pushMatrix();//guns
				GlStateManager.enableRescaleNormal();

				if (entityplayer.isSprinting())
				{
					GlStateManager.translate(-0.6F, -0.5F, -0.9F);
					GlStateManager.rotate(-270F, 0.0F, 1.0F, 0.0F);
					GlStateManager.rotate(-10F, 1.0F, 0.0F, 0.0F);
				}
				//スニーク時
				//				else if (entityplayer.isSneaking() && itemstack1.getItem() == TFItems.MOSINNAGANT)
				//				{
				//					GlStateManager.translate(0.0F, -0.25F, -0.2F);
				//					GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
				//				}
				else if (gun.isReload(itemstack1))
				{
					GlStateManager.translate(-0.9F, -0.3F, -0.75F);
					GlStateManager.rotate(140F, 0.0F, 1.0F, 0.0F);
					GlStateManager.rotate(60F, 1.0F, 0.0F, 1.0F);
					GlStateManager.rotate(-80F, 0.0F, 0.0F, 1.0F);
				}
				else
				{
					GlStateManager.translate(-0.85F, -0.4F, -0.75F);
					GlStateManager.rotate(-165F, 0.0F, 1.0F, 0.0F);
				}

				GlStateManager.pushMatrix();//guns
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));

				this.rendermat(entityplayer, itemstack1, gun);
				GlStateManager.popMatrix();//gune

				GlStateManager.disableRescaleNormal();
				GlStateManager.popMatrix();//gune

			}
		} //item
			//		if (itemstack != null && itemstack.getItem() instanceof ItemTFWeapon)
			//		{
			//			ItemTFWeapon gun = (ItemTFWeapon)itemstack.getItem();
			//
			//			if(minecraft.gameSettings.thirdPersonView == 0)
			//			{
			//
			//				GlStateManager.pushMatrix();//guns
			//
			//				GlStateManager.enableRescaleNormal();
			//				if(itemstack.getItem() instanceof ItemTFWeapon)
			//				{
			//					float f1 = entityplayer.swingProgress;
			//					GlStateManager.translate(-0.1F, 0F, -f1);
			//					//GL11.glRotatef(f1*30, 1.0F, 0.0F, 1.0F);
			//					GlStateManager.rotate(-f1*140, 1.0F, -0.3F, 0.0F);
			//				}
			//				if(entityplayer.isSprinting())
			//				{
			//					GlStateManager.translate(0.6F, -0.5F, -0.8F);
			//					GlStateManager.rotate(270F, 0.0F, 1.0F, 0.0F);
			//					GlStateManager.rotate(-10F, 1.0F, 0.0F, 0.0F);
			//
			//				}
			//				else
			//				{
			//					GlStateManager.translate(0.85F, -0.5F, -0.75F);
			//					GlStateManager.rotate(165F, 0.0F, 1.0F, 0.0F);
			//				}
			//
			//				GlStateManager.pushMatrix();//guns
			//				//Minecraft.getMinecraft().renderEngine.bindTexture(gun.obj_tex);
			//				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
			//
			//				this.rendermat(entityplayer, itemstack, gun);
			//				GlStateManager.popMatrix();//gune
			//
			//				GlStateManager.disableRescaleNormal();
			//				GlStateManager.popMatrix();//gune
			//			}
			//		}//item
			//		if (itemstack1 != null && itemstack1.getItem() instanceof ItemTFWeapon)
			//		{
			//			ItemTFWeapon gun = (ItemTFWeapon)itemstack1.getItem();
			//
			//			if(minecraft.gameSettings.thirdPersonView == 0)
			//			{
			//				GlStateManager.pushMatrix();//guns
			//				GlStateManager.enableRescaleNormal();
			//				if(itemstack1.getItem() instanceof ItemTFWeapon)
			//				{
			//					float f1 = entityplayer.swingProgress;
			//					GlStateManager.translate(0.1F, 0F, -f1);
			//					//GL11.glRotatef(f1*30, 1.0F, 0.0F, 1.0F);
			//					GlStateManager.rotate(-f1*140, 1.0F, 0.3F, 0.0F);
			//				}
			//				if(entityplayer.isSprinting())
			//				{
			//					GlStateManager.translate(-0.6F, -0.5F, -0.9F);
			//					GlStateManager.rotate(-270F, 0.0F, 1.0F, 0.0F);
			//					GlStateManager.rotate(-10F, 1.0F, 0.0F, 0.0F);
			//				}
			//				else
			//				{
			//					GlStateManager.translate(-0.85F, -0.5F, -0.75F);
			//					GlStateManager.rotate(-165F, 0.0F, 1.0F, 0.0F);
			//				}
			//
			//				GlStateManager.pushMatrix();//guns
			//				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
			//
			//				this.rendermat(entityplayer, itemstack1, gun);
			//				GlStateManager.popMatrix();//gune
			//
			//				GlStateManager.disableRescaleNormal();
			//				GlStateManager.popMatrix();//gune
			//
			//			}
			//		}//item
	}

	private void rendermat(EntityPlayer entityplayer, ItemStack itemstack, ItemTFGuns gun)
	{

		gun.obj_model.renderPart("weapon");
		//		if (itemstack.getItemDamage() != itemstack.getMaxDamage())
		//		{
		//			gun.obj_model.renderPart("mat3");
		//		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderergunthird(RenderLivingEvent.Post event)
	{
		EntityLivingBase entity = (EntityLivingBase) event.getEntity();
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.player;
		ItemStack itemstack = entity.getHeldItemMainhand();
		if (itemstack != null && itemstack.getItem() instanceof ItemTFGuns)
		{//item
			ItemTFGuns gun = (ItemTFGuns) itemstack.getItem();
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) event.getX(), (float) event.getY(), (float) event.getZ());
			GlStateManager.enableRescaleNormal();

			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));

			if (entity != entityplayer && entity.getHealth() > 0.0F)
			{
				GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(180 - entity.renderYawOffset, 0.0F, 1.0F, 0.0F);
				GlStateManager.translate(-0.27F, 1.55F, 0F);
				//GlStateManager.rotate(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
				GlStateManager.translate(0, 0F, 0.50F);
				GlStateManager.scale(0.75F, 0.75F, 0.75F);
				gun.obj_model.renderPart("weapon");
			}
			GlStateManager.disableRescaleNormal();
			GlStateManager.popMatrix();
		} //item
		ItemStack itemstack2 = entity.getHeldItemOffhand();
		if (itemstack2 != null && itemstack2.getItem() instanceof ItemTFGuns)
		{//item
			ItemTFGuns gun = (ItemTFGuns) itemstack2.getItem();
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) event.getX(), (float) event.getY(), (float) event.getZ());
			GlStateManager.enableRescaleNormal();

			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));

			if (entity != entityplayer && entity.getHealth() > 0.0F)
			{
				GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(180 - entity.renderYawOffset, 0.0F, 1.0F, 0.0F);
				GlStateManager.translate(0.27F, 1.55F, 0F);
				//GlStateManager.rotate(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
				GlStateManager.translate(0, 0F, 0.50F);
				GlStateManager.scale(0.75F, 0.75F, 0.75F);
				gun.obj_model.renderPart("weapon");
			}
			GlStateManager.disableRescaleNormal();
			GlStateManager.popMatrix();
		} //item
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void rendergunthird(RenderPlayerEvent.Pre event)
	{
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.player;
		EntityPlayer entityplayer2 = event.getEntityPlayer();

		if (entityplayer == entityplayer2)
		{
			ItemStack itemstack = ((EntityPlayer) (entityplayer)).getHeldItemMainhand();
			ItemStack itemstack1 = ((EntityPlayer) (entityplayer)).getHeldItemOffhand();
			if (itemstack != null && itemstack.getItem() instanceof ItemTFGuns)
			{//item
				ItemTFGuns gun = (ItemTFGuns) itemstack.getItem();
				GlStateManager.pushMatrix();
				GlStateManager.enableRescaleNormal();
				{
					float f5 = 0.0F;
					float f6 = 0.0F;

					if (!entityplayer.isRiding())
					{
						f5 = entityplayer.prevLimbSwingAmount + (entityplayer.limbSwingAmount - entityplayer.prevLimbSwingAmount) * event.getPartialRenderTick();
						f6 = entityplayer.limbSwing - entityplayer.limbSwingAmount * (1.0F - event.getPartialRenderTick());

						if (entityplayer.isChild())
						{
							f6 *= 3.0F;
						}

						if (f5 > 1.0F)
						{
							f5 = 1.0F;
						}
					}

					boolean flag = entityplayer instanceof EntityLivingBase && ((EntityLivingBase) entityplayer).getTicksElytraFlying() > 4;
					float f = 1.0F;
					if (flag)
					{
						f = (float) (entityplayer.motionX * entityplayer.motionX + entityplayer.motionY * entityplayer.motionY + entityplayer.motionZ * entityplayer.motionZ);
						f = f / 0.2F;
						f = f * f * f;
					}
					if (f < 1.0F)
					{
						f = 1.0F;
					}
					float x = MathHelper.cos(f6 * 0.6662F + (float) Math.PI) * 1.0F * f5 * 0.5F / f;
					if (entityplayer.isHandActive() && !gun.isReload(itemstack))
					{
						GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
						GlStateManager.rotate(180 - entityplayer.rotationYawHead, 0.0F, 1.0F, 0.0F);
						if (entityplayer.isSneaking())
						{
							GlStateManager.translate(-0.25F, 1.2F, 0F);
						}
						else
						{
							GlStateManager.translate(-0.25F, 1.45F, 0F);
						}
						GlStateManager.rotate(entityplayer.rotationPitch, 1.0F, 0.0F, 0.0F);
						GlStateManager.translate(0F, 0F, 0.30F);
					}
					else
					{
						GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
						GlStateManager.rotate(180 - entityplayer.renderYawOffset, 0.0F, 1.0F, 0.0F);
						if (entityplayer.isSneaking())
						{
							GlStateManager.translate(-0.36F, 1.15F, 0.0F);
							GlStateManager.rotate(15F, 1.0F, 0.0F, 0.0F);
						}
						else
						{
							GlStateManager.translate(-0.36F, 1.375F, 0.0F);
						}
						GlStateManager.rotate(x * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
						GlStateManager.translate(0F, -0.5F, 0.3F);
						GlStateManager.rotate(70F, 1.0F, 0.0F, 0.0F);

					}
					GlStateManager.scale(0.75F, 0.75F, 0.75F);

				}
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
				gun.obj_model.renderPart("weapon");

				GlStateManager.disableRescaleNormal();
				GlStateManager.popMatrix();
			} //item
			if (itemstack1 != null && itemstack1.getItem() instanceof ItemTFGuns)
			{//item
				ItemTFGuns gun = (ItemTFGuns) itemstack1.getItem();
				GlStateManager.pushMatrix();
				GlStateManager.enableRescaleNormal();

				{
					float f5 = 0.0F;
					float f6 = 0.0F;

					if (!entityplayer.isRiding())
					{
						f5 = entityplayer.prevLimbSwingAmount + (entityplayer.limbSwingAmount - entityplayer.prevLimbSwingAmount) * event.getPartialRenderTick();
						f6 = entityplayer.limbSwing - entityplayer.limbSwingAmount * (1.0F - event.getPartialRenderTick());

						if (entityplayer.isChild())
						{
							f6 *= 3.0F;
						}

						if (f5 > 1.0F)
						{
							f5 = 1.0F;
						}
					}

					boolean flag = entityplayer instanceof EntityLivingBase && ((EntityLivingBase) entityplayer).getTicksElytraFlying() > 4;
					float f = 1.0F;
					if (flag)
					{
						f = (float) (entityplayer.motionX * entityplayer.motionX + entityplayer.motionY * entityplayer.motionY + entityplayer.motionZ * entityplayer.motionZ);
						f = f / 0.2F;
						f = f * f * f;
					}
					if (f < 1.0F)
					{
						f = 1.0F;
					}
					float x = -(MathHelper.cos(f6 * 0.6662F + (float) Math.PI) * 1.0F * f5 * 0.5F / f);
					if (entityplayer.isHandActive() && !gun.isReload(itemstack1))
					{
						GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
						GlStateManager.rotate(-20F, 0.0F, 1.0F, 0.0F);
						GlStateManager.rotate(180 - entityplayer.rotationYawHead, 0.0F, 1.0F, 0.0F);
						if (entityplayer.isSneaking())
						{
							GlStateManager.translate(0.25F, 1.2F, 0F);
						}
						else
						{
							GlStateManager.translate(0.25F, 1.45F, 0F);
						}
						GlStateManager.rotate(entityplayer.rotationPitch, 1.0F, 0.0F, 0.0F);
						GlStateManager.translate(0F, 0F, 0.30F);
					}
					else
					{
						GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
						GlStateManager.rotate(180 - entityplayer.renderYawOffset, 0.0F, 1.0F, 0.0F);
						if (entityplayer.isSneaking())
						{
							GlStateManager.translate(0.36F, 1.15F, 0.0F);
							GlStateManager.rotate(15F, 1.0F, 0.0F, 0.0F);
						}
						else
						{
							GlStateManager.translate(0.36F, 1.375F, 0.0F);
						}
						GlStateManager.rotate(x * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
						GlStateManager.translate(0F, -0.5F, 0.3F);
						GlStateManager.rotate(70F, 1.0F, 0.0F, 0.0F);

					}
					GlStateManager.scale(0.75F, 0.75F, 0.75F);

				}
				//Minecraft.getMinecraft().renderEngine.bindTexture(gun.obj_tex);
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
				gun.obj_model.renderPart("weapon");

				GlStateManager.disableRescaleNormal();
				GlStateManager.popMatrix();
			} //item
				//			if (itemstack != null && itemstack.getItem() instanceof ItemTFWeapon)
				//			{//item
				//				ItemTFWeapon gun = (ItemTFWeapon) itemstack.getItem();
				//				GlStateManager.pushMatrix();
				//				GlStateManager.enableRescaleNormal();
				//					{
				//						float f5 = 0.0F;
				//			            float f6 = 0.0F;
				//
				//			            if (!entityplayer.isRiding())
				//			            {
				//			                f5 = entityplayer.prevLimbSwingAmount + (entityplayer.limbSwingAmount - entityplayer.prevLimbSwingAmount) * event.getPartialRenderTick();
				//			                f6 = entityplayer.limbSwing - entityplayer.limbSwingAmount * (1.0F - event.getPartialRenderTick());
				//
				//			                if (entityplayer.isChild())
				//			                {
				//			                    f6 *= 3.0F;
				//			                }
				//
				//			                if (f5 > 1.0F)
				//			                {
				//			                    f5 = 1.0F;
				//			                }
				//			            }
				//
				//						boolean flag = entityplayer instanceof EntityLivingBase && ((EntityLivingBase)entityplayer).getTicksElytraFlying() > 4;
				//						float f = 1.0F;
				//				        if (flag)
				//				        {
				//				            f = (float)(entityplayer.motionX * entityplayer.motionX + entityplayer.motionY * entityplayer.motionY + entityplayer.motionZ * entityplayer.motionZ);
				//				            f = f / 0.2F;
				//				            f = f * f * f;
				//				        }
				//				        if (f < 1.0F)
				//				        {
				//				            f = 1.0F;
				//				        }
				//
				//				        float x = MathHelper.cos(f6 * 0.6662F + (float)Math.PI) * 1.0F * f5 * 0.5F / f;
				//				        if(entityplayer.swingProgress > 0.0F)
				//						{
				////							float f1 = entityplayer.swingProgress;
				////							float x1 = MathHelper.cos(f1 + (float)Math.PI);
				////							GL11.glTranslatef(x1*2,0, x1*3);
				////							GlStateManager.rotate(180-entityplayer.rotationYawHead, x, 0.0F, 0.0F);
				//						}
				//				        if(entityplayer.isHandActive())
				//				        {
				//				        	GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
				//				        	GlStateManager.rotate(180-entityplayer.rotationYawHead, 0.0F, 1.0F, 0.0F);
				//							if(entityplayer.isSneaking())
				//							{
				//								GlStateManager.translate(-0.25F,1.2F,0F);
				//							}
				//							else
				//							{
				//								GlStateManager.translate(-0.25F,1.45F,0F);
				//							}
				//							GlStateManager.rotate(entityplayer.rotationPitch, 1.0F, 0.0F, 0.0F);
				//							GlStateManager.translate(0F, 0F, 0.30F);
				//				        }
				//				        else
				//				        {
				//				        	GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
				//				        	GlStateManager.rotate(180-entityplayer.renderYawOffset, 0.0F, 1.0F, 0.0F);
				//							if(entityplayer.isSneaking())
				//							{
				//								GlStateManager.translate(-0.36F,1.15F,0.0F);
				//								GlStateManager.rotate(15F, 1.0F, 0.0F, 0.0F);
				//							}
				//							else
				//							{
				//								GlStateManager.translate(-0.36F,1.375F,0.0F);
				//							}
				//		                        GlStateManager.rotate(x * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
				//		                        GlStateManager.translate(0F, -0.5F, 0.3F);
				//								GlStateManager.rotate(70F, 1.0F, 0.0F, 0.0F);
				//
				//				        }
				//				        GlStateManager.scale(0.75F, 0.75F, 0.75F);
				//
				//
				//					}
				//					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
				//					gun.obj_model.renderPart("weapon");
				//
				//					GlStateManager.disableRescaleNormal();
				//					GlStateManager.popMatrix();
				//			}//item
				//			if (itemstack1 != null && itemstack1.getItem() instanceof ItemTFWeapon)
				//			{//item
				//				ItemTFWeapon gun = (ItemTFWeapon) itemstack1.getItem();
				//				GlStateManager.pushMatrix();
				//				GlStateManager.enableRescaleNormal();
				//
				//					{
				//						float f5 = 0.0F;
				//			            float f6 = 0.0F;
				//
				//			            if (!entityplayer.isRiding())
				//			            {
				//			                f5 = entityplayer.prevLimbSwingAmount + (entityplayer.limbSwingAmount - entityplayer.prevLimbSwingAmount) * event.getPartialRenderTick();
				//			                f6 = entityplayer.limbSwing - entityplayer.limbSwingAmount * (1.0F - event.getPartialRenderTick());
				//
				//			                if (entityplayer.isChild())
				//			                {
				//			                    f6 *= 3.0F;
				//			                }
				//
				//			                if (f5 > 1.0F)
				//			                {
				//			                    f5 = 1.0F;
				//			                }
				//			            }
				//
				//						boolean flag = entityplayer instanceof EntityLivingBase && ((EntityLivingBase)entityplayer).getTicksElytraFlying() > 4;
				//						float f = 1.0F;
				//				        if (flag)
				//				        {
				//				            f = (float)(entityplayer.motionX * entityplayer.motionX + entityplayer.motionY * entityplayer.motionY + entityplayer.motionZ * entityplayer.motionZ);
				//				            f = f / 0.2F;
				//				            f = f * f * f;
				//				        }
				//				        if (f < 1.0F)
				//				        {
				//				            f = 1.0F;
				//				        }
				//				        float x = -(MathHelper.cos(f6 * 0.6662F + (float)Math.PI) * 1.0F * f5 * 0.5F / f);
				//				        if(entityplayer.isHandActive())
				//				        {
				//				        	GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
				//				        	GlStateManager.rotate(-20F, 0.0F, 1.0F, 0.0F);
				//				        	GlStateManager.rotate(180-entityplayer.rotationYawHead, 0.0F, 1.0F, 0.0F);
				//							if(entityplayer.isSneaking())
				//							{
				//								GlStateManager.translate(0.25F,1.2F,0F);
				//							}
				//							else
				//							{
				//								GlStateManager.translate(0.25F,1.45F,0F);
				//							}
				//							GlStateManager.rotate(entityplayer.rotationPitch, 1.0F, 0.0F, 0.0F);
				//							GlStateManager.translate(0F, 0F, 0.30F);
				//				        }
				//				        else
				//				        {
				//				        	GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
				//				        	GlStateManager.rotate(180-entityplayer.renderYawOffset, 0.0F, 1.0F, 0.0F);
				//							if(entityplayer.isSneaking())
				//							{
				//								GlStateManager.translate(0.36F,1.15F,0.0F);
				//								GlStateManager.rotate(15F, 1.0F, 0.0F, 0.0F);
				//							}
				//							else
				//							{
				//								GlStateManager.translate(0.36F,1.375F,0.0F);
				//							}
				//		                        GlStateManager.rotate(x * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
				//		                        GlStateManager.translate(0F, -0.5F, 0.3F);
				//								GlStateManager.rotate(70F, 1.0F, 0.0F, 0.0F);
				//
				//				        }
				//				        GlStateManager.scale(0.75F, 0.75F, 0.75F);
				//
				//
				//					}
				//					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
				//					gun.obj_model.renderPart("weapon");
				//
				//					GlStateManager.disableRescaleNormal();
				//					GlStateManager.popMatrix();
				//			}//item
		}

	}
}
