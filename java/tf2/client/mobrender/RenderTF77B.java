package tf2.client.mobrender;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;
import tf2.client.model.ModelGynoid;
import tf2.entity.mob.frend.EntityTF77B;

@SideOnly(Side.CLIENT)
public class RenderTF77B extends RenderGynoid<EntityTF77B>
{
	private static final ResourceLocation DEFAULT_RES_LOC = new ResourceLocation("tf2:textures/mob/tf77b.png");
	private static final ResourceLocation WEAPON_RES_LOC = new ResourceLocation("tf2:textures/mob/tf77b_weapon.png");
	private static final ResourceLocation WEAPON_LIGHT_RES_LOC = new ResourceLocation("tf2:textures/mob/tf77b_weapon_light.png");
	private static final IModelCustom WEAPON_OBJ = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/mob/tf77b_weapon.obj"));

	public RenderTF77B(RenderManager renderManagerIn, ModelGynoid modelBipedIn, float shadowSize)
	{
		super(renderManagerIn, modelBipedIn, shadowSize);
	}

	public void doRender(EntityTF77B entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		this.layerModel.ponytail[0].showModel = true;
		this.layerModel.ponytail[1].showModel = true;
		this.layerModel.rightSidetail.showModel = false;
		this.layerModel.leftSidetail.showModel = false;

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
	@Override
    protected ResourceLocation getEntityTexture(EntityTF77B entity)
    {
        return DEFAULT_RES_LOC;
    }

	@Override
    protected ResourceLocation getEntityWeaponTexture()
    {
        return WEAPON_RES_LOC;
    }

	@Override
    protected ResourceLocation getEntityWeaponLightTexture()
    {
        return WEAPON_LIGHT_RES_LOC;
    }

	@Override
    protected IModelCustom getEntityWeaponObj()
    {
		return WEAPON_OBJ;
    }
}