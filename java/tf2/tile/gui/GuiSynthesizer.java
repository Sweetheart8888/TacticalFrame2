package tf2.tile.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.tile.container.ContainerSynthesizer;
import tf2.tile.tileentity.TileEntitySynthesizer;

@SideOnly(Side.CLIENT)
public class GuiSynthesizer extends GuiContainer
{
	private final static ResourceLocation GUI_BACKGROUND = new ResourceLocation("tf2:textures/gui/container/synthesizer.png");
	private TileEntitySynthesizer tileEntity;

	public GuiSynthesizer(EntityPlayer inventory, TileEntitySynthesizer tileEntity)
	{
		super(new ContainerSynthesizer(inventory, tileEntity));
		this.tileEntity = tileEntity;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRenderer.drawString(I18n.format(this.tileEntity.getName(), new Object[0]), 8, 6, 0x404040);
		this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialRenderTick, int xMouse, int yMouse)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_BACKGROUND);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

		int i1;

		if (this.tileEntity.isBurning())
		{
			i1 = this.tileEntity.getBurnTimeRemainingScaled(14);
			this.drawTexturedModalRect(x + 49, y + 36 + 13 - i1, 176, 13 - i1, 24, i1 + 1);

			i1 = this.tileEntity.getCookProgressScaled(24);
			this.drawTexturedModalRect(x + 90, y + 35, 176, 14, i1 + 1, 19);
		}
	}
}