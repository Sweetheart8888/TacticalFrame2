package tf2.client.mobrender;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;
import tf2.entity.mob.frend.EntityCFR12;


@SideOnly(Side.CLIENT)
public class RenderCFR12 extends Render
{
	private static final ResourceLocation tftexture = new ResourceLocation("tf2:textures/mob/cfr12.png");
	private static final ResourceLocation tfeyes = new ResourceLocation("tf2:textures/mob/cfr12eye.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/mob/cfr12.obj"));

	public RenderCFR12(RenderManager renderManager)
	{
		super(renderManager);
	}

	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityCFR12) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
	 private float interpolateRotation(float p_77034_1_, float p_77034_2_, float p_77034_3_)
	    {
	        float f3;

	        for (f3 = p_77034_2_ - p_77034_1_; f3 < -180.0F; f3 += 360.0F)
	        {
	            ;
	        }

	        while (f3 >= 180.0F)
	        {
	            f3 -= 360.0F;
	        }

	        return p_77034_1_ + p_77034_3_ * f3;
	    }
	public void doRender(EntityCFR12 p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.bindEntityTexture(p_76986_1_);

		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		if(p_76986_1_.deathTime > 0)
		{
			GlStateManager.color(0.1F, 0.1F, 0.1F, 1F);
		}

        float f4 = MathHelper.wrapDegrees(p_76986_1_.rotationYawHead - p_76986_1_.rotationYaw);

        double d3 = Math.abs(f4);

        if(d3 > 70F)
        {
        	p_76986_1_.rotationYaw = this.interpolateRotation(p_76986_1_.rotationYaw, p_76986_1_.rotationYawHead, 0.05F);
        }

        double d0 = p_76986_1_.posX - p_76986_1_.prevPosX;
		double d1 = p_76986_1_.posZ - p_76986_1_.prevPosZ;
		float f = (float)(d0 * d0 + d1 * d1);
		if(f > 0F)
		{
			p_76986_1_.rotationYaw = p_76986_1_.rotationYawHead;

			float k = 180.0F - p_76986_1_.rotationYaw;

			GL11.glRotatef((float)Math.cos(k), 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-((float)Math.sin(k)), 0.0F, 0.0F, 1.0F);
		}

		//this.renderhead(p_76986_1_);

		GL11.glRotatef(180.0F - p_76986_1_.rotationYaw, 0.0F, 1.0F, 0.0F);

		this.renderlegs(p_76986_1_);

		this.bindTexture(tfeyes);

		GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);

        float lastx = OpenGlHelper.lastBrightnessX;
        float lasty = OpenGlHelper.lastBrightnessY;

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

        this.renderlegs(p_76986_1_);

        GL11.glRotatef(-(180.0F - p_76986_1_.rotationYaw), 0.0F, 1.0F, 0.0F);
        //this.renderhead(p_76986_1_);

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastx, lasty);

        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

        GL11.glEnable(GL11.GL_LIGHTING);

		//GL11.glRotatef(-(180.0F - p_76986_8_), 0.0F, 1.0F, 0.0F);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);

		GL11.glPopMatrix();


	}

	private void renderhead(EntityCFR12 p_76986_1_)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef(0F, 3.84F, 0F);
		GL11.glRotatef(180.0F - p_76986_1_.rotationYawHead, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(p_76986_1_.rotationPitch, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0F, -3.84F, 0F);
		tankk.renderPart("head");
		GL11.glPopMatrix();
	}
	private void renderlegs(EntityCFR12 p_76986_1_)
	{
		float x = p_76986_1_.ticksExisted;
		//足の曲がる最大角度角度
		float t = (float)Math.sin(x / ((float)Math.PI * 0.6662F)) * 50F;

		//足が上がる高さ
		float t1 = (float)Math.sin(x / ((float)Math.PI * 0.6662F)) * 0.25F;
		if(t1 < 0)
		{
			t1 = -(t1);
		}
		//ひざ下の曲がり方
		float t2 = (float)Math.sin(x / ((float)Math.PI * 0.6662F)) * 50F;
		//足の前後移動
		float t3 = (float)Math.sin(x / ((float)Math.PI * 0.6662F)) * 0.5F;

		double d0 = p_76986_1_.posX - p_76986_1_.prevPosX;
		double d1 = p_76986_1_.posZ - p_76986_1_.prevPosZ;
		float f = (float)(d0 * d0 + d1 * d1);
		if(f > 0F)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef(0F, 0F, 0.44F);
			tankk.renderPart("rightarm");
			tankk.renderPart("leftarm");
			this.renderweapon(p_76986_1_);

			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslatef(0F, 2.61F, 0F);
			GL11.glRotatef(30F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0F, -2.61F, 0F);
			tankk.renderPart("waist");
			tankk.renderPart("body");
			tankk.renderPart("head");
			GL11.glPopMatrix();

			//rightleg
			GL11.glPushMatrix();
			GL11.glTranslatef(0F, 2.61F, 0F);
			GL11.glRotatef(-15F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0F, -2.61F, 0F);

			GL11.glPushMatrix();
			GL11.glRotatef(30F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0F, 0.221F, 0F);
			GL11.glTranslatef(0F, 0F, -0.4F);
			tankk.renderPart("rightleg");
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslatef(0F, 2.61F, 0F);
			GL11.glRotatef(-30F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0F, -2.61F, 0F);
			tankk.renderPart("rightthighs");
			GL11.glPopMatrix();

			GL11.glPopMatrix();

			//leftleg
			GL11.glPushMatrix();
			GL11.glTranslatef(0F, 2.61F, 0F);
			GL11.glRotatef(15F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0F, -2.61F, 0F);

			GL11.glPushMatrix();
			GL11.glRotatef(30F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0F, 0.221F, 0F);
			GL11.glTranslatef(0F, 0F, -0.4F);
			tankk.renderPart("leftleg");
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslatef(0F, 2.61F, 0F);
			GL11.glRotatef(-30F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0F, -2.61F, 0F);
			tankk.renderPart("leftthighs");
			GL11.glPopMatrix();

			GL11.glPopMatrix();
		}
		else
		{
			GL11.glPushMatrix();
			tankk.renderPart("rightarm");
			tankk.renderPart("leftarm");
			tankk.renderPart("waist");
			tankk.renderPart("body");
			tankk.renderPart("head");
			this.renderweapon(p_76986_1_);
			GL11.glPopMatrix();


			GL11.glPushMatrix();
			GL11.glTranslatef(0F, 2.61F, 0F);
			GL11.glRotatef(-8F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0F, -2.61F, 0F);
			tankk.renderPart("rightthighs");
			tankk.renderPart("rightleg");
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslatef(0F, 2.61F, 0F);
			GL11.glRotatef(8F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0F, -2.61F, 0F);
			tankk.renderPart("leftthighs");
			tankk.renderPart("leftleg");
			GL11.glPopMatrix();
		}
	}

	private void renderweapon(EntityCFR12 par1)
	{
		if(par1.isBeingRidden() && par1.canBeSteered())
		{
			EntityLivingBase entitylivingbase = (EntityLivingBase)par1.getControllingPassenger();
			ItemStack itemstack = entitylivingbase.getHeldItemMainhand();

			GL11.glPushMatrix();
			if(itemstack != null)
			{
				tankk.renderPart("weapon");
			}
//			if(itemstack != null && itemstack.getItem() == TFCore.gun_gatling)
//			{
//				tankk.renderPart("weapon2");
//			}
//			else if(itemstack != null && itemstack.getItem() == TFCore.gun_rocket)
//			{
//				tankk.renderPart("weapon3");
//			}

			GL11.glPopMatrix();
		}
		else
		{
			GL11.glPushMatrix();
			tankk.renderPart("weapon");
			GL11.glPopMatrix();
		}

	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving)
	{
		return this.tftexture;
	}
}
