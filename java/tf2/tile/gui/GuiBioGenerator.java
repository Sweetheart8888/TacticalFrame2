package tf2.tile.gui;

import java.util.ArrayList;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.tile.container.ContainerBioGenerator;
import tf2.tile.tileentity.TileEntityBioGenerator;


@SideOnly(Side.CLIENT)
public class GuiBioGenerator extends GuiContainer
{
	private final static ResourceLocation GUI_BACKGROUND = new ResourceLocation("tf2:textures/gui/container/bio.png");

	private TileEntityBioGenerator tileEntity;

	 public GuiBioGenerator(EntityPlayer inventory, TileEntityBioGenerator tileEntity)
	 {
		 super(new ContainerBioGenerator(inventory, tileEntity));
		 this.tileEntity = tileEntity;
		 this.xSize = 176;
		 this.ySize = 166;
	 }

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRenderer.drawString(I18n.format(this.tileEntity.getName(), new Object[0]), 8, 6, 0x404040);
		this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 0x404040);
	}
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);

		boolean b1 = this.isPointInRegion(98, 14, 13, 58, mouseX, mouseY);
		if (b1)
		{
			int charge = this.tileEntity.cookTime;
			ArrayList<String> list1 = new ArrayList<String>();
			list1.add("Bio Liquid Amount : " + charge);
			this.drawHoveringText(list1, mouseX, mouseY, fontRenderer);
		}
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialRenderTick, int xMouse, int yMouse)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_BACKGROUND);
		int x = (this.width  - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

		int i1;

		if (this.tileEntity.isBurning())
		{
			i1 = this.tileEntity.getBurnTimeRemainingScaled(0, 23);
			this.drawTexturedModalRect(x + 25, y + 49 - i1, 176, 22 - i1, 18, i1 + 1);
			i1 = this.tileEntity.getBurnTimeRemainingScaled(1, 23);
			this.drawTexturedModalRect(x + 43, y + 49 - i1, 176, 22 - i1, 18, i1 + 1);
			i1 = this.tileEntity.getBurnTimeRemainingScaled(2, 23);
			this.drawTexturedModalRect(x + 61, y + 49 - i1, 176, 22 - i1, 18, i1 + 1);
		}

		i1 = this.tileEntity.getProcessProgressScaled(58);
		this.drawTexturedModalRect(x + 98, y + 14 + 58 - i1, 176, 23 + 58 - i1, 13, i1 + 1);
	}
}
