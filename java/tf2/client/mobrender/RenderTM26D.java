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
import tf2.entity.mob.enemy.EntityTM26D;

@SideOnly(Side.CLIENT)
public class RenderTM26D extends Render
{
	private static final ResourceLocation tftexture = new ResourceLocation("tf2:textures/mob/tm26d.png");
	private static final ResourceLocation tfeyes = new ResourceLocation("tf2:textures/mob/tm26deye.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/mob/tm26d.obj"));

	public RenderTM26D(RenderManager renderManager)
	{
		super(renderManager);
	}

	public void doRender(Entity entity, double entityX, double entityY, double entityZ, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityTM26D) entity, entityX, entityY, entityZ, p_76986_8_, p_76986_9_);
	}

	public void doRender(EntityTM26D entity, double entityX, double entityY, double entityZ, float p_76986_8_, float p_76986_9_)
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
			GlStateManager.color(0.5F, 0.5F, 0.3F, 1F);
		}

		double d0 = entity.posX - entity.prevPosX;
		double d1 = entity.posZ - entity.prevPosZ;
		float f = (float) (d0 * d0 + d1 * d1);
		if (f > 0F)
		{
			if(!(entity.getAnimationTime() > 0))
			{
				float x = entity.ticksExisted;
				float t = (float) Math.sin(x / ((float) Math.PI * 0.9662F)) * 0.1F;
				if (t < 0)
				{
					t = -(t);
				}
				GlStateManager.translate(0F, t, 0F);
			}
		}
		entity.rotationYaw = entity.rotationYawHead;

		GlStateManager.rotate(180.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);

		if (entity.getAnimationTime() > 0 && entity.getAnimationNumber() == 2)
		{
			GlStateManager.translate(0F, 0.1F, 0F);
			GlStateManager.rotate(10F, 1.0F, 0.0F, 0.0F);
		}
		if (entity.getAnimationTime() > 0  && entity.getAnimationTime() < 5 && entity.getAnimationNumber() == 2)
		{
			GlStateManager.rotate(entity.animationTime * 40.0F, 0.0F, 1.0F, 0.0F);
		}

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

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastx, lasty);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		GlStateManager.enableLighting();

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}

	private void renderlegs(EntityTM26D entity)
	{
		float x = entity.ticksExisted;
		float t = (float) Math.sin(x / ((float) Math.PI * 0.6662F)) * 40F;

		//足の前後移動
		float t1 = (float) Math.sin(x / ((float) Math.PI * 0.6662F)) * 0.55F;

		//足が上がる高さ
		float t2 = (float) Math.sin(x / ((float) Math.PI * 0.6662F)) * 0.15F;
		if (t2 < 0)
		{
			t2 = -(t2);
		}

		float t3 = (float) Math.sin(x / ((float) Math.PI * 0.6662F)) * 50F;

		double d0 = entity.posX - entity.prevPosX;
		double d1 = entity.posZ - entity.prevPosZ;
		float f = (float) (d0 * d0 + d1 * d1);

		if (entity.getAnimationTime() > 0 && entity.getAnimationNumber() == 1)
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 2.364F, 0.0F);
			GlStateManager.translate(-0.617F, 0.0F, 0.0F);
			GlStateManager.translate(0F, 0F, -0.426F);
			if(entity.getAnimationTime() >= 45)
			{
				GlStateManager.rotate((entity.getAnimationTime() - 50) * 22F, 1.0F, 0.0F, 0.0F);
			}
			else if(entity.getAnimationTime() >= 3)
			{
				GlStateManager.rotate(-110F, 1.0F, 0.0F, 0.0F);
			}
			else
			{
				GlStateManager.rotate(-entity.getAnimationTime() * 55F, 1.0F, 0.0F, 0.0F);
			}
			GlStateManager.translate(0F, 0F, 0.426F);
			GlStateManager.translate(0.617F, 0F, 0.0F);
			GlStateManager.translate(0F, -2.364F, 0F);
			tankk.renderPart("rightarm");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			tankk.renderPart("leftarm");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 1.68F, 0F);
			GlStateManager.rotate(-15F, 0.0F, 0.0F, 1.0F);
			GlStateManager.translate(0F, -1.68F, 0F);
			tankk.renderPart("rightthighs");
			tankk.renderPart("rightleg");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 1.68F, 0F);
			GlStateManager.rotate(15F, 0.0F, 0.0F, 1.0F);
			GlStateManager.translate(0F, -1.68F, 0F);
			tankk.renderPart("leftthighs");
			tankk.renderPart("leftleg");
			GlStateManager.popMatrix();
		}
		else if (entity.getAnimationTime() > 0 && entity.getAnimationNumber() == 2)
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 2.364F, 0.0F);
			GlStateManager.translate(-0.617F, 0.0F, 0.0F);
			GlStateManager.translate(0F, 0F, -0.426F);
			GlStateManager.rotate(20F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(-100F, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(0F, 0F, 0.426F);
			GlStateManager.translate(0.617F, 0F, 0.0F);
			GlStateManager.translate(0F, -2.364F, 0F);
			tankk.renderPart("rightarm");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			tankk.renderPart("leftarm");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 1.68F, 0F);
			GlStateManager.rotate(20F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(-15F, 0.0F, 0.0F, 1.0F);
			GlStateManager.translate(0F, -1.68F, 0F);
			tankk.renderPart("rightthighs");
			tankk.renderPart("rightleg");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 1.68F, 0F);
			GlStateManager.rotate(15F, 0.0F, 0.0F, 1.0F);
			GlStateManager.translate(0F, -1.68F, 0F);
			tankk.renderPart("leftthighs");
			tankk.renderPart("leftleg");
			GlStateManager.popMatrix();
		}
		else if (entity.getAnimationTime() > 0 && entity.getAnimationNumber() == 3)
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 2.364F, 0.0F);
			GlStateManager.translate(-0.655F, 0.0F, 0.0F);
			GlStateManager.translate(0F, 0F, -0.47F);
			GlStateManager.rotate(20F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(-90F, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(0F, 0F, 0.47F);
			GlStateManager.translate(0.655F, 0F, 0.0F);
			GlStateManager.translate(0F, -2.364F, 0F);
			tankk.renderPart("rightarm");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			tankk.renderPart("leftarm");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 1.68F, 0F);
			GlStateManager.rotate(20F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(-15F, 0.0F, 0.0F, 1.0F);
			GlStateManager.translate(0F, -1.68F, 0F);
			tankk.renderPart("rightthighs");
			tankk.renderPart("rightleg");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 1.68F, 0F);
			GlStateManager.rotate(15F, 0.0F, 0.0F, 1.0F);
			GlStateManager.translate(0F, -1.68F, 0F);
			tankk.renderPart("leftthighs");
			tankk.renderPart("leftleg");
			GlStateManager.popMatrix();
		}

		else if (f > 0F)
		{
			this.renderobj(0F, t, 1.0F);
			this.renderleg(t2, t3, t1);
		}
		else
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 2.364F, 0.0F);
			GlStateManager.translate(-0.617F, 0.0F, 0.0F);
			GlStateManager.translate(0F, 0F, -0.426F);
			GlStateManager.rotate(-30F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(-40F, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(0F, 0F, 0.426F);
			GlStateManager.translate(0.617F, 0F, 0.0F);
			GlStateManager.translate(0F, -2.364F, 0F);
			tankk.renderPart("rightarm");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			tankk.renderPart("leftarm");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 1.68F, 0F);
			GlStateManager.rotate(-15F, 0.0F, 0.0F, 1.0F);
			GlStateManager.translate(0F, -1.68F, 0F);
			tankk.renderPart("rightthighs");
			tankk.renderPart("rightleg");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 1.68F, 0F);
			GlStateManager.rotate(10F, 0.0F, 0.0F, 1.0F);
			GlStateManager.translate(0F, -1.68F, 0F);
			tankk.renderPart("leftthighs");
			tankk.renderPart("leftleg");
			GlStateManager.popMatrix();
		}
	}

	private void renderleg(float i0, float iii2, float ii)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, 0F, ii);
		GlStateManager.translate(0F, i0, 0F);
		GlStateManager.translate(0F, 1.68F, 0F);
		GlStateManager.rotate(-(iii2) + 30F, 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(0F, -1.68F, 0F);
		tankk.renderPart("rightleg");
		tankk.renderPart("rightthighs");
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, 0F, -(ii));
		GlStateManager.translate(0F, i0, 0F);
		GlStateManager.translate(0F, 1.68F, 0F);
		GlStateManager.rotate(iii2 + 30F, 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(0F, -1.68F, 0F);
		tankk.renderPart("leftleg");
		tankk.renderPart("leftthighs");
		GlStateManager.popMatrix();
	}

	private void renderobj(float i0, float iii2, float ii)
	{
		GlStateManager.pushMatrix();
		tankk.renderPart("rightarm");
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		tankk.renderPart("leftarm");
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving)
	{
		return this.tftexture;
	}
}
