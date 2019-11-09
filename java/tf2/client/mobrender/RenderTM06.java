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
import tf2.entity.mob.enemy.EntityTM06;

@SideOnly(Side.CLIENT)
public class RenderTM06 extends Render
{
	private static final ResourceLocation tftexture = new ResourceLocation("tf2:textures/mob/tm06.png");
	private static final ResourceLocation tfeyes = new ResourceLocation("tf2:textures/mob/tm06_light.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/mob/tm06.obj"));

	public RenderTM06(RenderManager renderManager)
	{
		super(renderManager);
	}

	@Override
	public void doRender(Entity entity, double entityX, double entityY, double entityZ, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityTM06) entity, entityX, entityY, entityZ, p_76986_8_, p_76986_9_);
	}

	public void doRender(EntityTM06 entity, double entityX, double entityY, double entityZ, float p_76986_8_, float p_76986_9_)
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

//				float x = entity.ticksExisted;
//				float t = (float) Math.sin(x / ((float) Math.PI * 5F)) * 0.05F;
//				GlStateManager.translate(0F, t + 0.1F, 0F);

		entity.rotationYaw = entity.rotationYawHead;

		GlStateManager.rotate(180.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);

		tankk.renderPart("body");
		this.renderlegs(entity);
		//start発光処理
		this.bindTexture(tfeyes);
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		float lastx = OpenGlHelper.lastBrightnessX;
		float lasty = OpenGlHelper.lastBrightnessY;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

		tankk.renderPart("body");
		this.renderlegs(entity);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastx, lasty);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		GlStateManager.enableLighting();
		//end

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}

	private void renderlegs(EntityTM06 entity)
	{
		float x = entity.ticksExisted;

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.575F, 0F, 0F);
		GlStateManager.translate(0F, 0F, 0.575F);
		GlStateManager.rotate(x * 35F, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(0.575F, 0F, 0F);
		GlStateManager.translate(0F, 0F, -0.575F);
		tankk.renderPart("propeller");
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(0.575F, 0F, 0F);
		GlStateManager.translate(0F, 0F, 0.575F);
		GlStateManager.rotate(-x * 35F, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(-0.575F, 0F, 0F);
		GlStateManager.translate(0F, 0F, -0.575F);
		tankk.renderPart("propeller2");
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.575F, 0F, 0F);
		GlStateManager.translate(0F, 0F, -0.575F);
		GlStateManager.rotate(-x * 35F, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(0.575F, 0F, 0F);
		GlStateManager.translate(0F, 0F, 0.575F);
		tankk.renderPart("propeller3");
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(0.575F, 0F, 0F);
		GlStateManager.translate(0F, 0F, -0.575F);
		GlStateManager.rotate(x * 35F, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(-0.575F, 0F, 0F);
		GlStateManager.translate(0F, 0F, 0.575F);
		tankk.renderPart("propeller4");
		GlStateManager.popMatrix();

		float f = entity.rotationPitch;
		if (f < 0)
		{
			f = 0;
		}

		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, 0.2549F, 0F);
		GlStateManager.translate(0F, 0F, 0.147F);
		GlStateManager.rotate(f, 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(0.0F, 0F, -0.147F);
		GlStateManager.translate(0.0F, -0.2549F, 0F);
		tankk.renderPart("head");
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, 0.235F, 0F);
		GlStateManager.translate(0F, 0F, -0.0043F);
		GlStateManager.rotate(f, 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(0.0F, 0F, 0.0043F);
		GlStateManager.translate(0.0F, -0.235F, 0F);
		tankk.renderPart("weapon");
		GlStateManager.popMatrix();
	}


	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving)
	{
		return this.tftexture;
	}
}
