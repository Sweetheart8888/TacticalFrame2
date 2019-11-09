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
import tf2.entity.mob.enemy.EntityTM41;

@SideOnly(Side.CLIENT)
public class RenderTM41 extends Render
{
	private static final ResourceLocation tftexture = new ResourceLocation("tf2:textures/mob/tm41.png");
	private static final ResourceLocation tfeyes = new ResourceLocation("tf2:textures/mob/tm41_light.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/mob/tm41.obj"));

	public RenderTM41(RenderManager renderManager)
	{
		super(renderManager);
	}

	@Override
	public void doRender(Entity entity, double entityX, double entityY, double entityZ, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityTM41) entity, entityX, entityY, entityZ, p_76986_8_, p_76986_9_);
	}

	public void doRender(EntityTM41 entity, double entityX, double entityY, double entityZ, float p_76986_8_, float p_76986_9_)
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
			GlStateManager.color(0.5F, 0.3F, 0.3F, 1F);
		}

		double d0 = entity.posX - entity.prevPosX;
		double d1 = entity.posZ - entity.prevPosZ;
		float f = (float) (d0 * d0 + d1 * d1);
		if (f > 0.1F)
		{
			entity.rotationYaw = entity.rotationYawHead;
		}

		this.renderHead(entity);
		this.renderHigh(entity);
		GlStateManager.rotate(180.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);

		tankk.renderPart("body");

		//start発光処理
		this.bindTexture(tfeyes);
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		float lastx = OpenGlHelper.lastBrightnessX;
		float lasty = OpenGlHelper.lastBrightnessY;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

		tankk.renderPart("body");

		GlStateManager.rotate(-(180.0F - entity.rotationYaw), 0.0F, 1.0F, 0.0F);
		this.renderHead(entity);
		this.renderHigh(entity);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastx, lasty);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		GlStateManager.enableLighting();
		//end

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}

	private void renderHead(EntityTM41 entity)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.0F, 1.5F, 0.0F);
		GlStateManager.rotate(180.0F - entity.rotationYawHead, 0.0F, 1.0F, 0.0F);
		//GlStateManager.rotate(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(0.0F, -1.5F, -0.0F);

		tankk.renderPart("head");
		GlStateManager.popMatrix();


		float f = entity.rotationPitch;
		f = MathHelper.clamp(f, -20F, 10F);

		GlStateManager.pushMatrix();
		GlStateManager.rotate(180.0F - entity.rotationYawHead, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(0F, 1.90F, 0F);
		GlStateManager.translate(0F, 0F, 1.75F);

		GlStateManager.rotate(f, 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(0.0F, 0F, -1.75F);
		GlStateManager.translate(0.0F, -1.90F, 0F);
		tankk.renderPart("weapon1");
		GlStateManager.popMatrix();


	}
	private void renderHigh(EntityTM41 entity)
	{
		float f = entity.rotationPitch;
		f = MathHelper.clamp(f, -90F, 0F);

		GlStateManager.pushMatrix();
		GlStateManager.rotate(180.0F - entity.rotationYawHead, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(0F, 2.86F, 0F);
		GlStateManager.translate(0F, 0F, -0.638F);
		GlStateManager.rotate(f, 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(0.0F, 0F, 0.638F);
		GlStateManager.translate(0.0F, -2.86F, 0F);
		tankk.renderPart("highgun1");
		tankk.renderPart("highgun2");
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving)
	{
		return this.tftexture;
	}
}
