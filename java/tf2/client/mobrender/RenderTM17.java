package tf2.client.mobrender;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;
import tf2.entity.mob.enemy.boss.EntityTM17;

@SideOnly(Side.CLIENT)
public class RenderTM17 extends Render
{
	private static final ResourceLocation tftexture = new ResourceLocation("tf2:textures/mob/tm17.png");
	private static final ResourceLocation tfeyes = new ResourceLocation("tf2:textures/mob/tm17eye.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/mob/tm17.obj"));

	public RenderTM17(RenderManager renderManager)
	{
		super(renderManager);
	}

	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityTM17)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
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
	public void doRender(EntityTM17 p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.bindEntityTexture(p_76986_1_);

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
		GlStateManager.enableRescaleNormal();
		if(p_76986_1_.deathTime > 0)
		{
			GlStateManager.color(0.1F, 0.1F, 0.1F, 1F);
		}
		if (p_76986_1_.hurtTime > 0)
        {
			GlStateManager.color(0.2F, 0.5F, 0.2F, 1F);
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
			GlStateManager.translate(0F, -0.4F, 0F);
			p_76986_1_.rotationYaw = p_76986_1_.rotationYawHead;

			float k = 180.0F - p_76986_1_.rotationYaw;
		}

		this.renderhead(p_76986_1_);
		GlStateManager.rotate(180.0F - p_76986_1_.rotationYaw, 0.0F, 1.0F, 0.0F);


		tankk.renderPart("body");
		tankk.renderPart("west");
		this.renderlegs(p_76986_1_);


		this.bindTexture(tfeyes);
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
        float lastx = OpenGlHelper.lastBrightnessX;
        float lasty = OpenGlHelper.lastBrightnessY;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        tankk.renderPart("body");
        tankk.renderPart("west");
        this.renderlegs(p_76986_1_);

        GlStateManager.rotate(-(180.0F - p_76986_1_.rotationYaw), 0.0F, 1.0F, 0.0F);
        this.renderhead(p_76986_1_);

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastx, lasty);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.enableLighting();
        GlStateManager.disableRescaleNormal();

		GlStateManager.popMatrix();
	}


	private void renderhead(EntityTM17 p_76986_1_)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, 2.5F, 0F);
		GlStateManager.rotate(180.0F - p_76986_1_.rotationYawHead, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(0F, -2.5F, 0F);
		tankk.renderPart("head");
		GlStateManager.popMatrix();
	}

	private void renderlegs(EntityTM17 p_76986_1_)
	{

		double d0 = p_76986_1_.posX - p_76986_1_.prevPosX;
		double d1 = p_76986_1_.posZ - p_76986_1_.prevPosZ;
		float f = (float)(d0 * d0 + d1 * d1);
		if(f > 0.0F)
		{
			GlStateManager.pushMatrix();
			GL11.glTranslatef(0F, 1.545F, 0F);
			GL11.glTranslatef(-0.78F, 0F, 0F);
			GL11.glTranslatef(0F, 0F, 0.702F);
			GL11.glRotatef(-8F, 0F, 0F, 1F);
			GL11.glRotatef(-8F, 1F, 0F, 0F);
			GL11.glTranslatef(0F, 0F, -0.702F);
			GL11.glTranslatef(0.78F, 0F, 0F);
			GL11.glTranslatef(0F, -1.545F, 0F);
			tankk.renderPart("rightarm1");
			tankk.renderPart("rightarm2");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GL11.glTranslatef(0F, 1.545F, 0F);
			GL11.glTranslatef(0.78F, 0F, 0F);
			GL11.glTranslatef(0F, 0F, 0.702F);
			GL11.glRotatef(8F, 0F, 0F, 1F);
			GL11.glRotatef(-8F, 1F, 0F, 0F);
			GL11.glTranslatef(0F, 0F, -0.702F);
			GL11.glTranslatef(-0.78F, 0F, 0F);
			GL11.glTranslatef(0F, -1.545F, 0F);
			tankk.renderPart("leftarm1");
			tankk.renderPart("leftarm2");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GL11.glTranslatef(0F, 1.545F, 0F);
			GL11.glTranslatef(-0.78F, 0F, 0F);
			GL11.glTranslatef(0F, 0F, -0.702F);
			GL11.glRotatef(-8F, 0F, 0F, 1F);
			GL11.glRotatef(8F, 1F, 0F, 0F);
			GL11.glTranslatef(0F, 0F, 0.702F);
			GL11.glTranslatef(0.78F, 0F, 0F);
			GL11.glTranslatef(0F, -1.545F, 0F);
			tankk.renderPart("rightleg1");
			tankk.renderPart("rightleg2");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GL11.glTranslatef(0F, 1.545F, 0F);
			GL11.glTranslatef(0.78F, 0F, 0F);
			GL11.glTranslatef(0F, 0F, -0.702F);
			GL11.glRotatef(8F, 0F, 0F, 1F);
			GL11.glRotatef(8F, 1F, 0F, 0F);
			GL11.glTranslatef(0F, 0F, 0.702F);
			GL11.glTranslatef(-0.78F, 0F, 0F);
			GL11.glTranslatef(0F, -1.545F, 0F);
			tankk.renderPart("leftleg1");
			tankk.renderPart("leftleg2");
			GlStateManager.popMatrix();
		}
		else
		{
			GlStateManager.pushMatrix();
			tankk.renderPart("rightarm1");
			tankk.renderPart("leftarm1");
			tankk.renderPart("rightarm2");
			tankk.renderPart("leftarm2");

			tankk.renderPart("rightleg1");
			tankk.renderPart("leftleg1");
			tankk.renderPart("rightleg2");
			tankk.renderPart("leftleg2");
			GlStateManager.popMatrix();
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving)
	{
		return this.tftexture;
	}
}
