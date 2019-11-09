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
import tf2.entity.mob.frend.EntityEvent3;

@SideOnly(Side.CLIENT)
public class RenderCFRTW extends Render
{
	private static final ResourceLocation tftexture = new ResourceLocation("tf2:textures/mob/cfrtw.png");
	private static final ResourceLocation tfeyes = new ResourceLocation("tf2:textures/mob/cfrtw_light.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/mob/cfrtw.obj"));

	public RenderCFRTW(RenderManager renderManager)
	{
		super(renderManager);
	}

	public void doRender(Entity entity, double entityX, double entityY, double entityZ, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityEvent3) entity, entityX, entityY, entityZ, p_76986_8_, p_76986_9_);
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

	public void doRender(EntityEvent3 entity, double entityX, double entityY, double entityZ, float p_76986_8_, float p_76986_9_)
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

		float f4 = MathHelper.wrapDegrees(entity.rotationYawHead - entity.rotationYaw);

		double d3 = Math.abs(f4);

		if (d3 > 70F)
		{
			entity.rotationYaw = this.interpolateRotation(entity.rotationYaw, entity.rotationYawHead, 0.05F);
		}

		double d0 = entity.posX - entity.prevPosX;
		double d1 = entity.posZ - entity.prevPosZ;
		float f = (float) (d0 * d0 + d1 * d1);
		if (f > 0F)
		{
			entity.rotationYaw = entity.rotationYawHead;
		}

		this.renderhead(entity);

		GlStateManager.rotate(180.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);

		this.renderlegs(entity);

		this.bindTexture(tfeyes);

		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		float lastx = OpenGlHelper.lastBrightnessX;
		float lasty = OpenGlHelper.lastBrightnessY;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

		this.renderlegs(entity);

		GlStateManager.rotate(-(180.0F - entity.rotationYaw), 0.0F, 1.0F, 0.0F);
		this.renderhead(entity);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastx, lasty);

		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		GlStateManager.enableLighting();

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();

	}

	private void renderhead(EntityEvent3 entity)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef(0F, 3.84F, 0F);
		GL11.glRotatef(180.0F - entity.rotationYawHead, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0F, -3.84F, 0F);
		tankk.renderPart("head");
		GL11.glPopMatrix();
	}

	private void renderlegs(EntityEvent3 entity)
	{
		float x = entity.ticksExisted;
		//足の曲がる最大角度角度
		float t = (float) Math.sin(x / ((float) Math.PI * 0.6662F)) * 50F;

		//足が上がる高さ
		float t1 = (float) Math.sin(x / ((float) Math.PI * 0.6662F)) * 0.25F;
		if (t1 < 0)
		{
			t1 = -(t1);
		}
		//ひざ下の曲がり方
		float t2 = (float) Math.sin(x / ((float) Math.PI * 0.6662F)) * 50F;
		//足の前後移動
		float t3 = (float) Math.sin(x / ((float) Math.PI * 0.6662F)) * 0.5F;

		double d0 = entity.posX - entity.prevPosX;
		double d1 = entity.posZ - entity.prevPosZ;
		float f = (float) (d0 * d0 + d1 * d1);
		if (f > 0F)
		{
			GlStateManager.pushMatrix();
			GL11.glTranslatef(0F, 4.00F, 0F);
			GL11.glRotatef(30F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0F, -4.00F, 0F);
			//tankk.renderPart("waist");
			tankk.renderPart("body");
			{
				GlStateManager.pushMatrix();
				GlStateManager.translate(0F, 3.80F, 0F);
				GlStateManager.translate(0F, 0F, -0.655F);
				GlStateManager.rotate(-20F, 1.0F, 0.0F, 0.0F);
				GlStateManager.translate(0F, 0F, 0.655F);
				GlStateManager.translate(0F, -3.80F, 0F);
				tankk.renderPart("right_Backweapon");
				tankk.renderPart("left_Backweapon");
				GlStateManager.popMatrix();
			}

			{
				GlStateManager.pushMatrix();
				GlStateManager.translate(0F, 3.96F, 0F);
				GlStateManager.translate(-0.909F, 0F, 0F);
				GlStateManager.rotate(15F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(-20F, 0.0F, 0.0F, 1.0F);
				GlStateManager.translate(0.909F, 0F, 0F);
				GlStateManager.translate(0F, -3.96F, 0F);
				tankk.renderPart("right_Arm");
				GlStateManager.translate(0F, 3.36F, 0F);
				GlStateManager.rotate(-60F, 1.0F, 0.0F, 0.0F);
				GlStateManager.translate(0F, -3.36F, 0F);
				tankk.renderPart("right_Armfore");
				tankk.renderPart("right_Weapon");
				GlStateManager.popMatrix();
			}
			{
				GlStateManager.pushMatrix();
				GlStateManager.translate(0F, 3.96F, 0F);
				GlStateManager.translate(0.909F, 0F, 0F);
				GlStateManager.rotate(15F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(20F, 0.0F, 0.0F, 1.0F);
				GlStateManager.translate(-0.909F, 0F, 0F);
				GlStateManager.translate(0F, -3.96F, 0F);
				tankk.renderPart("left_Arm");
				GlStateManager.translate(0F, 3.36F, 0F);
				GlStateManager.rotate(-60F, 1.0F, 0.0F, 0.0F);
				GlStateManager.translate(0F, -3.36F, 0F);
				tankk.renderPart("left_Armfore");
				tankk.renderPart("left_Weapon");
				GlStateManager.popMatrix();
			}
			{
				GlStateManager.pushMatrix();
				GlStateManager.translate(0F, 2.80F, 0F);
				GlStateManager.translate(0.35F, 0F, 0F);
				GlStateManager.rotate(15F, 0F, 0F, 1F);
				GlStateManager.translate(-0.35F, 0F, 0F);
				GlStateManager.translate(0F, -2.80F, 0F);

				tankk.renderPart("left_Thighs");
				tankk.renderPart("left_Leg");
				tankk.renderPart("left_Ankle");
				GlStateManager.popMatrix();
			}
			{
				GlStateManager.pushMatrix();
				GlStateManager.translate(0F, 2.80F, 0F);
				GlStateManager.translate(-0.35F, 0F, 0F);
				GlStateManager.rotate(-15F, 0F, 0F, 1F);
				GlStateManager.translate(0.35F, 0F, 0F);
				GlStateManager.translate(0F, -2.80F, 0F);

				tankk.renderPart("right_Thighs");
				tankk.renderPart("right_Leg");
				tankk.renderPart("right_Ankle");
				GlStateManager.popMatrix();
			}


			GlStateManager.popMatrix();


		} else
		{
			GlStateManager.pushMatrix();
			tankk.renderPart("body");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 3.80F, 0F);
			GlStateManager.translate(0F, 0F, -0.655F);
			GlStateManager.rotate(-20F, 1.0F, 0.0F, 0.0F);
			GlStateManager.translate(0F, 0F, 0.655F);
			GlStateManager.translate(0F, -3.80F, 0F);
			tankk.renderPart("right_Backweapon");
			tankk.renderPart("left_Backweapon");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 2.80F, 0F);
			GlStateManager.translate(-0.35F, 0F, 0F);
			GlStateManager.rotate(-10F, 0.0F, 0.0F, 1.0F);
			GlStateManager.translate(0.35F, 0F, 0F);
			GlStateManager.translate(0F, -2.80F, 0F);
			tankk.renderPart("right_Thighs");
			tankk.renderPart("right_Leg");
			tankk.renderPart("right_Ankle");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 2.80F, 0F);
			GlStateManager.translate(0.35F, 0F, 0F);
			GlStateManager.rotate(10F, 0.0F, 0.0F, 1.0F);
			GlStateManager.translate(-0.35F, 0F, 0F);
			GlStateManager.translate(0F, -2.80F, 0F);
			tankk.renderPart("left_Thighs");
			tankk.renderPart("left_Leg");
			tankk.renderPart("left_Ankle");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 3.96F, 0F);
			GlStateManager.translate(-0.909F, 0F, 0F);
			GlStateManager.rotate(15F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(-20F, 0.0F, 0.0F, 1.0F);
			GlStateManager.translate(0.909F, 0F, 0F);
			GlStateManager.translate(0F, -3.96F, 0F);
			tankk.renderPart("right_Arm");
			GlStateManager.translate(0F, 3.36F, 0F);
			GlStateManager.rotate(-40F, 1.0F, 0.0F, 0.0F);
			GlStateManager.translate(0F, -3.36F, 0F);
			tankk.renderPart("right_Armfore");
			tankk.renderPart("right_Weapon");
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 3.96F, 0F);
			GlStateManager.translate(0.909F, 0F, 0F);
			GlStateManager.rotate(15F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(20F, 0.0F, 0.0F, 1.0F);
			GlStateManager.translate(-0.909F, 0F, 0F);
			GlStateManager.translate(0F, -3.96F, 0F);
			tankk.renderPart("left_Arm");
			GlStateManager.translate(0F, 3.36F, 0F);
			GlStateManager.rotate(-40F, 1.0F, 0.0F, 0.0F);
			GlStateManager.translate(0F, -3.36F, 0F);
			tankk.renderPart("left_Armfore");
			tankk.renderPart("left_Weapon");
			GlStateManager.popMatrix();
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving)
	{
		return this.tftexture;
	}
}
