package tf2.client;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import objmodel.AdvancedModelLoader;
import tf2.TFItems;
import tf2.items.guns.ItemTFGuns;

public class GunRenderSetup
{
	public static void gunSetup(FMLPreInitializationEvent event)
	{
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.AK47;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/ak47.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/ak47.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.M16A1;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/m16a1.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/m16a1.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.AK74;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/ak74.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/ak74.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.M4A1;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/m4a1.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/m4a1.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.AK12;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/ak12.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/ak12.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.HK416;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/hk416.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/hk416.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.SIG550;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/sig550.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/sig550.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.G36;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/g36.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/g36.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.FAMAS;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/famas.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/famas.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.OTS14;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/ots14.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/ots14.png";
		}

		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.M1911;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/m1911.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/m1911.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.DESERTEAGLE;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/deserteagle.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/deserteagle.png";
		}

		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.UZI;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/uzi.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/uzi.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.EVO3;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/evo3.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/evo3.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.PPSH41;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/ppsh41.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/ppsh41.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.P90;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/p90.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/p90.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.MP7;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/mp7.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/mp7.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.PP19;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/pp19.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/pp19.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.UMP9;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/ump9.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/ump9.png";
		}

		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.M24;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/m24.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/m24.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.SVD;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/svd.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/svd.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.M200;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/m200.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/m200.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.WA2000;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/wa2000.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/wa2000.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.MOSINNAGANT;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/mosinnagant.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/mosinnagant.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.M82A1;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/m82a1.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/m82a1.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.XM2010;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/xm2010.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/xm2010.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.PSG1;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/psg1.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/psg1.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.PGM;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/pgm.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/pgm.png";
		}

		//		{
		//			ItemTFGuns gun = (ItemTFGuns) TFItems.SCAR;
		//			if (event.getSide().isClient())
		//			{
		//				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/scar.obj"));
		//			}
		//			gun.obj_tex = "tf2:textures/items/guns/scar.png";
		//		}
		//		{
		//			ItemTFGuns gun = (ItemTFGuns) TFItems.M14EBR;
		//			if (event.getSide().isClient())
		//			{
		//				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/m14ebr.obj"));
		//			}
		//			gun.obj_tex = "tf2:textures/items/guns/m14ebr.png";
		//		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.SPAS12;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/spas12.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/spas12.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.SAIGA12;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/saiga12.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/saiga12.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.M1014;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/m1014.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/m1014.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.VEPR12;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/vepr12.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/vepr12.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.M870;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/m870.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/m870.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.AA12;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/aa12.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/aa12.png";
		}

		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.MG42;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/mg42.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/mg42.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.M60E1;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/m60e1.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/m60e1.png";
		}

		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.M1A1;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/m1a1.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/m1a1.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.M20;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/m20.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/m20.png";
		}
		{
			ItemTFGuns gun = (ItemTFGuns) TFItems.MGL140;
			if (event.getSide().isClient())
			{
				gun.obj_model = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/items/guns/mgl140.obj"));
			}
			gun.obj_tex = "tf2:textures/items/guns/mgl140.png";
		}
	}
}
