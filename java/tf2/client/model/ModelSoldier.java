package tf2.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSoldier extends ModelBiped
{
    public ModelSoldier()
    {
        this(0.0F, false);
    }

    public ModelSoldier(float modelSize, boolean p_i1168_2_)
    {
    	super(modelSize, 0.0F, 64, 32);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.bipedLeftArm.offsetX = -0.075F;
        this.bipedLeftArm.offsetY = 0.02F;
        this.bipedRightArm.offsetY = 0.02F;
        this.bipedRightArm.rotateAngleX = -1.6F;
        this.bipedRightArm.rotateAngleZ = 1.6F;
        this.bipedLeftArm.rotateAngleX = -1.6F;
        this.bipedLeftArm.rotateAngleY = 0.8F;

    }
}