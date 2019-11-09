package tf2.tile.gui;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TFBlocks;
import tf2.TFItems;
import tf2.common.MessageCupola;
import tf2.common.PacketHandler;
import tf2.tile.container.ContainerCupola;
import tf2.tile.tileentity.TileEntityCupola;

@SideOnly(Side.CLIENT)
public class GuiCupola extends GuiContainer
{
	private final static ResourceLocation GUI_BACKGROUND = new ResourceLocation("tf2:textures/gui/container/cupola.png");

	private GuiItemButton itemButton;
	private TileEntityCupola tileEntity;

	public GuiCupola(EntityPlayer inventory, TileEntityCupola tileEntity)
	{
		super(new ContainerCupola(inventory, tileEntity));
		this.tileEntity = tileEntity;
		this.xSize = 176;
		this.ySize = 222;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.allowUserInput = true;
		this.buttonList.add(this.itemButton = new GuiItemButton(1, i + 94, j + 14, true));
		this.buttonList.add(this.itemButton = new GuiItemButton(2, i + 118, j + 14, true));
		this.buttonList.add(this.itemButton = new GuiItemButton(3, i + 142, j + 14, true));

		this.buttonList.add(this.itemButton = new GuiItemButton(4, i + 94, j + 38, true));
		this.buttonList.add(this.itemButton = new GuiItemButton(5, i + 118, j + 38, true));
		this.buttonList.add(this.itemButton = new GuiItemButton(6, i + 142, j + 38, true));

		this.buttonList.add(this.itemButton = new GuiItemButton(7, i + 94, j + 62, true));
		this.buttonList.add(this.itemButton = new GuiItemButton(8, i + 118, j + 62, true));
		this.buttonList.add(this.itemButton = new GuiItemButton(9, i + 142, j + 62, true));
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		PacketHandler.INSTANCE.sendToServer(new MessageCupola(button.id, this.tileEntity.getPos().getX(), this.tileEntity.getPos().getY(), this.tileEntity.getPos().getZ()));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRenderer.drawString(I18n.format(this.tileEntity.getName(), new Object[0]), 8, 6, 0x404040);
		this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 0x404040);
	}

	@Override
	public void drawScreen(int x, int y, float par3)
	{
		this.drawDefaultBackground();
		super.drawScreen(x, y, par3);

		int x1 = (this.width - this.xSize) / 2;
		int y1 = (this.height - this.ySize) / 2;

		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.pushMatrix();
//		GL11.glPushMatrix();
//        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
//        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
//        GL11.glDisable(GL11.GL_LIGHTING);
		itemRender.zLevel = 100.0F;
		itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.IRON_INGOT), x1 + 96, y1 + 16);
		itemRender.renderItemAndEffectIntoGUI(new ItemStack(Blocks.IRON_BLOCK), x1 + 120, y1 + 16);
		itemRender.renderItemAndEffectIntoGUI(new ItemStack(TFBlocks.IRON_FRAME), x1 + 144, y1 + 16);

		itemRender.renderItemAndEffectIntoGUI(new ItemStack(TFItems.IRON_SHIELD), x1 + 96, y1 + 40);
		itemRender.renderItemAndEffectIntoGUI(new ItemStack(TFItems.GEAR_IRON), x1 + 120, y1 + 40);
		itemRender.renderItemAndEffectIntoGUI(new ItemStack(TFBlocks.MACHINE_CHASSIS), x1 + 144, y1 + 40);

		itemRender.renderItemAndEffectIntoGUI(new ItemStack(TFItems.COMPRESS_IRON), x1 + 96, y1 + 64);
		itemRender.renderItemAndEffectIntoGUI(new ItemStack(TFItems.CAN), x1 + 120, y1 + 64);
		itemRender.zLevel = 0.0F;
//		GL11.glPopMatrix();
//        GL11.glEnable(GL11.GL_LIGHTING);
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GlStateManager.popMatrix();
        RenderHelper.enableStandardItemLighting();

		if (this.isPointInRegion(96, 16, 16, 16, x, y))
		{
			this.renderToolTip(new ItemStack(Items.IRON_INGOT), x, y);
		}
		if (this.isPointInRegion(120, 16, 16, 16, x, y))
		{
			this.renderToolTip(new ItemStack(Blocks.IRON_BLOCK), x, y);
		}
		if (this.isPointInRegion(144, 16, 16, 16, x, y))
		{
			this.renderToolTip(new ItemStack(TFBlocks.IRON_FRAME), x, y);
		}
		if (this.isPointInRegion(96, 40, 16, 16, x, y))
		{
			this.renderToolTip(new ItemStack(TFItems.IRON_SHIELD), x, y);
		}
		if (this.isPointInRegion(120, 40, 16, 16, x, y))
		{
			this.renderToolTip(new ItemStack(TFItems.GEAR_IRON), x, y);
		}
		if (this.isPointInRegion(144, 40, 16, 16, x, y))
		{
			this.renderToolTip(new ItemStack(TFBlocks.MACHINE_CHASSIS), x, y);
		}

		if (this.isPointInRegion(96, 64, 16, 16, x, y))
		{
			this.renderToolTip(new ItemStack(TFItems.COMPRESS_IRON), x, y);
		}
		if (this.isPointInRegion(120, 64, 16, 16, x, y))
		{
			this.renderToolTip(new ItemStack(TFItems.CAN), x, y);
		}

		this.renderHoveredToolTip(x, y);

		boolean b1 = this.isPointInRegion(16, 16, 32, 112, x, y);
		if (b1)
		{
			int charge = (this.tileEntity.burnStack1 + (this.tileEntity.burnStack2 * 20000));
			ArrayList<String> list1 = new ArrayList<String>();
			list1.add("Melted Iron Amount : " + charge);
			this.drawHoveringText(list1, x, y, fontRenderer);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialRenderTick, int xMouse, int yMouse)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_BACKGROUND);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

		//this.drawTexturedModalRect(x + 143, y + 63, 176, 161, 18, 18);

		int i1;

		if (this.tileEntity.isBurning())
		{
			i1 = this.tileEntity.getBurnTimeRemainingScaled(13);
			this.drawTexturedModalRect(x + 59, y + 37 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
		}

		i1 = this.tileEntity.getStackProgressScaled(112);
		this.drawTexturedModalRect(x + 16, y + 16 + 111 - i1, 176, 31 + 111 - i1, 32, i1 + 1);

		i1 = this.tileEntity.getCookProgressScaled(32);
		this.drawTexturedModalRect(x + 53, y + 105, 176, 14, i1 + 1, 17);

	}

	@SideOnly(Side.CLIENT)
	static class GuiItemButton extends GuiButton
	{
		private final boolean field_146157_o;
		private boolean field_146142_r;

		public GuiItemButton(int p_i1095_1_, int p_i1095_2_, int p_i1095_3_, boolean p_i1095_4_)
		{
			super(p_i1095_1_, p_i1095_2_, p_i1095_3_, 20, 20, "");
			this.field_146157_o = p_i1095_4_;
		}

		/**
		 * Draws this button to the screen.
		 */
		public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
		{
			if (this.visible)
			{
				p_146112_1_.getTextureManager().bindTexture(GUI_BACKGROUND);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				boolean flag = p_146112_2_ >= this.x && p_146112_3_ >= this.y && p_146112_2_ < this.x + this.width && p_146112_3_ < this.y + this.height;
				int l = 176;
				int k = 222;

				if (!this.enabled)
				{
					l += this.width * 2;
				}
				else if (flag)
				{
					l += this.width * 1;
				}

				if (!this.field_146157_o)
				{
					k += this.height;
				}

				this.drawTexturedModalRect(this.x, this.y, l, k, this.width, this.height);
			}
		}

		public void func_146140_b(boolean p_146140_1_)
		{
			this.field_146142_r = p_146140_1_;
		}
	}
}
