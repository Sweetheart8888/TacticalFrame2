package tf2.client.mobrender;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;
import tf2.entity.mob.enemy.EntityTM11;

@SideOnly(Side.CLIENT)
public class RenderTM11 extends Render
{
	private static final ResourceLocation tftexture = new ResourceLocation("tf2:textures/mob/tm11.png");
	private static final ResourceLocation tfeyes = new ResourceLocation("tf2:textures/mob/tm11_light.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/mob/tm11.obj"));

	public RenderTM11(RenderManager renderManager)
	{
		super(renderManager);
	}

	public void doRender(Entity entity, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityTM11) entity, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	public void doRender(EntityTM11 entity, double entityX, double entityY, double entityZ, float p_76986_8_, float p_76986_9_)
	{
		this.bindEntityTexture(entity);

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) entityX, (float) entityY, (float) entityZ);
		GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
		GlStateManager.enableRescaleNormal();
		if (entity.deathTime > 0)
		{
			GlStateManager.color(0.1F, 0.1F, 0.1F, 1F);
		}
		if (entity.hurtTime > 0)
		{
			GlStateManager.color(0.3F, 0.3F, 0.5F, 1F);
		}

		double d0 = entity.posX - entity.prevPosX;
		double d1 = entity.posZ - entity.prevPosZ;
		float f = (float) (d0 * d0 + d1 * d1);
		if (f > 0F)
		{
			float x = entity.ticksExisted;
			float t = (float) Math.sin(x / ((float) Math.PI * 0.9662F)) * 0.1F;
			if (t < 0)
			{
				t = -(t);
			}
			GlStateManager.translate(0F, t, 0F);
		}
		this.renderHead(entity);
		GlStateManager.rotate(180.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);

		this.renderlegs(entity);
		tankk.renderPart("body");

		this.bindTexture(tfeyes);
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		float lastx = OpenGlHelper.lastBrightnessX;
		float lasty = OpenGlHelper.lastBrightnessY;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		tankk.renderPart("body");
		this.renderlegs(entity);
		GlStateManager.rotate(-(180.0F - entity.rotationYaw), 0.0F, 1.0F, 0.0F);
		this.renderHead(entity);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastx, lasty);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		GlStateManager.enableLighting();

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();

	}

	private void renderlegs(EntityTM11 entity)
	{
		float x = entity.ticksExisted;

		//足の前後移動
		float t1 = (float) Math.sin(x / ((float) Math.PI * 0.9662F)) * 0.2F;

		//足が上がる高さ
		float t2 = (float) Math.sin(x / ((float) Math.PI * 0.9662F)) * 0.05F;
		if (t2 < 0)
		{
			t2 = -(t2);
		}

		float t3 = (float) Math.sin(x / ((float) Math.PI * 0.9662F)) * 30F;

		double d0 = entity.posX - entity.prevPosX;
		double d1 = entity.posZ - entity.prevPosZ;
		float f = (float) (d0 * d0 + d1 * d1);
		if (f > 0F)
		{
			this.renderleg(t2, t3, t1);
		}
		else
		{
			GlStateManager.pushMatrix();
			tankk.renderPart("rightThigh");
			tankk.renderPart("rightLeg");
			tankk.renderPart("leftThigh");
			tankk.renderPart("leftLeg");
			GlStateManager.popMatrix();
		}
	}

	private void renderleg(float i0, float iii2, float ii)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, 1.5F, 0.0F);
		//GlStateManager.translate(0F, 0.0F, 0.02F);
		GlStateManager.rotate((iii2), 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(0F, -1.5F, 0F);
		//GlStateManager.translate(0F, 0F, -0.02F);
		tankk.renderPart("rightLeg");
		tankk.renderPart("rightThigh");
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, 1.5F, 0.0F);
		// GlStateManager.translate(0F, 0.0F, 0.02F);
		GlStateManager.rotate(-(iii2), 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(0F, -1.5F, 0F);
		// GlStateManager.translate(0F, 0F, -0.02F);
		tankk.renderPart("leftLeg");
		tankk.renderPart("leftThigh");
		GlStateManager.popMatrix();
	}

	private void renderHead(EntityTM11 entity)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.0F, 0F, 0.08F);
		GlStateManager.rotate(180.0F - entity.rotationYawHead, 0.0F, 1.0F, 0.0F);
		//GlStateManager.rotate(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(0.0F, 0F, -0.08F);

		tankk.renderPart("head");
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving)
	{
		return this.tftexture;
	}
}
