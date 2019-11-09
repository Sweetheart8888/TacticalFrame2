package tf2.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelGynoid extends ModelBase
{
  //fields
    public ModelRenderer bipedHead;
    public ModelRenderer bipedHeadwear;
    public ModelRenderer[] ponytail;
    public ModelRenderer rightSidetail;
    public ModelRenderer leftSidetail;
    public ModelRenderer bipedBody;
    public ModelRenderer bipedRightArm;
    public ModelRenderer bipedLeftArm;
    public ModelRenderer bipedRightLeg;
    public ModelRenderer bipedLeftLeg;

    public boolean isSneak;

    public ModelGynoid()
    {
        this(0.0F);
    }

    public ModelGynoid(float modelSize)
    {
        this(modelSize, 0.0F, 64, 64);
    }

  public ModelGynoid(float modelSize, float p_i1149_2_, int textureWidthIn, int textureHeightIn)
  {
      this.textureWidth = textureWidthIn;
      this.textureHeight = textureHeightIn;
      this.setTextureOffset("head.scale", 0, 0);
      this.setTextureOffset("head.napeof", 42, 16);

      this.bipedHead = new ModelRenderer(this, "head");
      this.bipedHead.addBox("scale", -4F, -8F, -4F, 8, 8, 8);
      this.bipedHead.addBox("napeof", -4F, 0F, 1.0F, 8, 9, 3);
      this.bipedHead.setRotationPoint(0F, 8F + p_i1149_2_, 0F);
      {//tf77b, tf78r
	      this.ponytail = new ModelRenderer[2];
	      this.ponytail[0] = new ModelRenderer(this, 0, 33);
	      this.ponytail[0].addBox(-2F, -1.5F, 0F, 4, 4, 2);
	      this.ponytail[0].setRotationPoint(0F, -6F, 4F);
	      this.ponytail[1] = new ModelRenderer(this, 12, 33);
	      this.ponytail[1].addBox(-1.5F, -1F, 0F, 3, 13, 3);
	      this.ponytail[1].setRotationPoint(0F, -6F, 4F);
      }
      this.bipedHead.addChild(this.ponytail[0]);
      this.bipedHead.addChild(this.ponytail[1]);
      {//tf80g
	      this.rightSidetail = new ModelRenderer(this, 24, 33);
	      this.rightSidetail.addBox(-1.5F, -2.5F, 0F, 3, 13, 3);
	      this.rightSidetail.setRotationPoint(3F, -6F, 4F);

	      this.leftSidetail = new ModelRenderer(this, 24, 33);
	      this.leftSidetail.mirror = true;
	      this.leftSidetail.addBox(-1.5F, -2.5F, 0F, 3, 13, 3);
	      this.leftSidetail.setRotationPoint(-3F, -6F, 4F);
      }
      this.bipedHead.addChild(this.rightSidetail);
      this.bipedHead.addChild(this.leftSidetail);


      this.bipedHeadwear = new ModelRenderer(this, 32, 0);
      this.bipedHeadwear.addBox(-4F, -8F, -4F, 8, 8, 8, modelSize + 0.25F);
      this.bipedHeadwear.setRotationPoint(0F, 8F + p_i1149_2_, 0F);

      this.bipedBody = new ModelRenderer(this, 14, 16);
      this.bipedBody.addBox(-3F, 0F, -2F, 6, 7, 4, modelSize);
      this.bipedBody.setRotationPoint(0F, 8F + p_i1149_2_, 0F);

      this.bipedRightArm = new ModelRenderer(this, 34, 18);
      this.bipedRightArm.addBox(-1.5F, -2F, -1F, 2, 8, 2, modelSize);
      this.bipedRightArm.setRotationPoint(-3.5F, 10F + p_i1149_2_, 0F);

      this.bipedLeftArm = new ModelRenderer(this, 34, 18);
      this.bipedLeftArm.mirror = true;
      this.bipedLeftArm.addBox(-0.5F, -2F, -1F, 2, 8, 2);
      this.bipedLeftArm.setRotationPoint(3.5F, 10F + p_i1149_2_, 0F);


      this.bipedRightLeg = new ModelRenderer(this, 0, 16);
      this.bipedRightLeg.addBox(-1.5F, 0F, -2F, 3, 9, 4, modelSize);
      this.bipedRightLeg.setRotationPoint(-1.5F, 15F + p_i1149_2_, 0F);

      this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
      this.bipedLeftLeg.mirror = true;
      this.bipedLeftLeg.addBox(-1.5F, 0F, -2F, 3, 9, 4, modelSize);
      this.bipedLeftLeg.setRotationPoint(1.5F, 15F + p_i1149_2_, 0F);

  }

  /**
   * Sets the models various rotation angles then renders the model.
   */
  public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
  {
      this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
      GlStateManager.pushMatrix();

          if (entityIn.isSneaking())
          {
              GlStateManager.translate(0.0F, 0.2F, 0.0F);
          }

          this.bipedHead.render(scale);
          this.bipedBody.render(scale);
          this.bipedRightArm.render(scale);
          this.bipedLeftArm.render(scale);
          this.bipedRightLeg.render(scale);
          this.bipedLeftLeg.render(scale);
          this.bipedHeadwear.render(scale);

      GlStateManager.popMatrix();
  }


  /**
   * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
   * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
   * "far" arms and legs can swing at most.
   */
  @Override
  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
  {
      boolean flag = entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getTicksElytraFlying() > 4;
      this.bipedHead.rotateAngleY = netHeadYaw * 0.017453292F;

      if (flag)
      {
          this.bipedHead.rotateAngleX = -((float)Math.PI / 4F);
      }
      else
      {
          this.bipedHead.rotateAngleX = headPitch * 0.017453292F;
      }

      this.bipedBody.rotateAngleY = 0.0F;
      this.bipedRightArm.rotationPointZ = 0.0F;
      this.bipedRightArm.rotationPointX = -3.0F;
      this.bipedLeftArm.rotationPointZ = 0.0F;
      this.bipedLeftArm.rotationPointX = 3.0F;
      float f = 1.0F;

      if (flag)
      {
          f = (float)(entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
          f = f / 0.2F;
          f = f * f * f;
      }

      if (f < 1.0F)
      {
          f = 1.0F;
      }

      this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.0F * limbSwingAmount * 0.5F / f;
      this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount * 0.5F / f;
      this.bipedRightArm.rotateAngleZ = 0.0F + limbSwingAmount * 1.15F;
      this.bipedLeftArm.rotateAngleZ = 0.0F - limbSwingAmount * 1.15F;
      this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
      this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f;
      this.bipedRightLeg.rotateAngleY = 0.0F;
      this.bipedLeftLeg.rotateAngleY = 0.0F;
      this.bipedRightLeg.rotateAngleZ = 0.025F;
      this.bipedLeftLeg.rotateAngleZ = 0.025F;

      this.ponytail[1].rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.15F * limbSwingAmount * 0.5F / f;

      this.rightSidetail.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.15F * limbSwingAmount * 0.25F / f;
      this.leftSidetail.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.15F * limbSwingAmount * 0.25F / f;

      if (this.isRiding)
      {
          this.bipedRightArm.rotateAngleX += -((float)Math.PI / 5F);
          this.bipedRightArm.rotateAngleZ += -0.5F;
          this.bipedLeftArm.rotateAngleX += -((float)Math.PI / 5F);
          this.bipedLeftArm.rotateAngleZ += 0.5F;
          this.bipedRightLeg.rotateAngleX = -1.4137167F;
          this.bipedRightLeg.rotateAngleY = ((float)Math.PI / 10F);
          this.bipedRightLeg.rotateAngleZ = 0.07853982F;
          this.bipedLeftLeg.rotateAngleX = -1.4137167F;
          this.bipedLeftLeg.rotateAngleY = -((float)Math.PI / 10F);
          this.bipedLeftLeg.rotateAngleZ = -0.07853982F;
      }

      if (this.swingProgress > 0.0F)
      {
          EnumHandSide enumhandside = this.getMainHand(entityIn);
          ModelRenderer modelrenderer = this.getArmForSide(enumhandside);
          float f1 = this.swingProgress;
          this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float)Math.PI * 2F)) * 0.2F;

          if (enumhandside == EnumHandSide.LEFT)
          {
              this.bipedBody.rotateAngleY *= -1.0F;
          }

          this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
          this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
          this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
          this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
          this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
          this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
          this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
          f1 = 1.0F - this.swingProgress;
          f1 = f1 * f1;
          f1 = f1 * f1;
          f1 = 1.0F - f1;
          float f2 = MathHelper.sin(f1 * (float)Math.PI);
          float f3 = MathHelper.sin(this.swingProgress * (float)Math.PI) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
          modelrenderer.rotateAngleX = (float)((double)modelrenderer.rotateAngleX - ((double)f2 * 1.2D + (double)f3));
          modelrenderer.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
          modelrenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float)Math.PI) * -0.4F;
      }

      if (this.isSneak)
      {
          this.bipedBody.rotateAngleX = 0.5F;
          this.bipedRightArm.rotateAngleX += 0.4F;
          this.bipedLeftArm.rotateAngleX += 0.4F;
          this.bipedRightLeg.rotationPointZ = 4.0F;
          this.bipedLeftLeg.rotationPointZ = 4.0F;
          this.bipedRightLeg.rotationPointY = 12.0F;
          this.bipedLeftLeg.rotationPointY = 12.0F;
          this.bipedHead.rotationPointY = 9.0F;
      }
      else
      {
          this.bipedBody.rotateAngleX = 0.0F;
          this.bipedRightLeg.rotationPointZ = 0.1F;
          this.bipedLeftLeg.rotationPointZ = 0.1F;
          this.bipedRightLeg.rotationPointY = 15.0F;
          this.bipedLeftLeg.rotationPointY = 15.0F;
          this.bipedHead.rotationPointY = 8.0F;
      }

      this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
      this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
      this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
      this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

      copyModelAngles(this.bipedHead, this.bipedHeadwear);

  }

  public void setModelAttributes(ModelBase model)
  {
      super.setModelAttributes(model);

      if (model instanceof ModelGynoid)
      {
      	ModelGynoid modelbiped = (ModelGynoid)model;
          this.isSneak = modelbiped.isSneak;
      }
  }

  public void setVisible(boolean visible)
  {
      this.bipedHead.showModel = visible;
      this.bipedHeadwear.showModel = visible;
      this.bipedBody.showModel = visible;
      this.bipedRightArm.showModel = visible;
      this.bipedLeftArm.showModel = visible;
      this.bipedRightLeg.showModel = visible;
      this.bipedLeftLeg.showModel = visible;

		for(int i = 0; i < this.ponytail.length; i++)
		{
			this.ponytail[i].showModel = visible;
		}


  }

  public void postRenderArm(float scale, EnumHandSide side)
  {
      this.getArmForSide(side).postRender(scale);
  }

  protected ModelRenderer getArmForSide(EnumHandSide side)
  {
      return side == EnumHandSide.LEFT ? this.bipedLeftArm : this.bipedRightArm;
  }

  protected EnumHandSide getMainHand(Entity entityIn)
  {
      if (entityIn instanceof EntityLivingBase)
      {
          EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
          EnumHandSide enumhandside = entitylivingbase.getPrimaryHand();
          return entitylivingbase.swingingHand == EnumHand.MAIN_HAND ? enumhandside : enumhandside.opposite();
      }
      else
      {
          return EnumHandSide.RIGHT;
      }
  }
}
