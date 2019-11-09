package tf2.items.skill;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import tf2.TF2Core;
import tf2.items.ItemBase;
import tf2.util.Reference;

public class ItemSkillBase  extends ItemBase
{
	private final String tex;

	public ItemSkillBase(String name)
	{
		super(name);
		this.setMaxStackSize(1);
		this.setCreativeTab(TF2Core.tabstfSkills);
		this.tex = name;
	}
	@Override
	public void registerModel()
	{
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "skill/" + tex), "inventory"));
	}
}
