package tf2.tile.gui;

import java.io.IOException;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.common.MessageFriendMecha;
import tf2.common.PacketHandler;
import tf2.entity.mob.frend.EntityFriendMecha;
import tf2.entity.mob.frend.EntityMTT1;
import tf2.entity.mob.frend.EntityMTT2;
import tf2.entity.mob.frend.EntityMTT3;
import tf2.entity.mob.frend.EntityMobCF;
import tf2.entity.mob.frend.EntityVehicle;
import tf2.tile.container.ContainerFriendMechaInventory;

public class GuiEntityFriendMechaInventory  extends GuiContainer
{
	private static final ResourceLocation RES_MECHA_INVENTORY = new ResourceLocation("tf2:textures/gui/mecha_inventory.png");

	private EntityFriendMecha entityMecha;
	private EntityPlayer entityPlayer;
	private GuiEntityFriendMechaInventory.ChangeButton buttonChange;
	private GuiEntityFriendMechaInventory.ChangeButton buttonRide;
	private GuiEntityFriendMechaInventory.ChangeButton buttonItem;
//	private GuiEntityFriendMechaInventory.ChangeButton buttonTrade;
	private GuiEntityFriendMechaInventory.ChangeButton buttonLevel;

	public GuiEntityFriendMechaInventory(EntityFriendMecha entityMecha, EntityPlayer entityPlayer)
	{
		super(new ContainerFriendMechaInventory(entityMecha, entityPlayer));

		this.ySize = 199;
		this.entityMecha = entityMecha;
		this.entityPlayer = entityPlayer;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		int buttonPosX = ((this.width - this.xSize) / 2) + 137;
		int buttonPosY = ((this.height - this.ySize) / 2) + 85;

		if(this.entityPlayer == this.entityMecha.getOwner() || this.entityPlayer.capabilities.isCreativeMode)
		{
			if(!this.entityMecha.canBeingRidden)
			{
				this.buttonChange = (GuiEntityFriendMechaInventory.ChangeButton) this.addButton(new GuiEntityFriendMechaInventory.ChangeButton(0, buttonPosX, buttonPosY));
			}
			else
			{
				buttonPosX = ((this.width - this.xSize) / 2) + 153;
				buttonPosY = ((this.height - this.ySize) / 2) + 85;
				this.buttonRide = (GuiEntityFriendMechaInventory.ChangeButton) this.addButton(new GuiEntityFriendMechaInventory.ChangeButton(1, buttonPosX, buttonPosY));
			}

			buttonPosX = ((this.width - this.xSize) / 2) + 158;
			buttonPosY = ((this.height - this.ySize) / 2) + 17;
			this.buttonItem = (GuiEntityFriendMechaInventory.ChangeButton) this.addButton(new GuiEntityFriendMechaInventory.ChangeButton(2, buttonPosX, buttonPosY));

			buttonPosX = ((this.width - this.xSize) / 2) + 147;
			buttonPosY = ((this.height - this.ySize) / 2) + 17;

			if(this.entityMecha.getMechaLevel() < this.entityMecha.maxLevel - 1)
			{
				this.buttonLevel = (GuiEntityFriendMechaInventory.ChangeButton) this.addButton(new GuiEntityFriendMechaInventory.ChangeButton(6, buttonPosX, buttonPosY));
			}
		}
//		if(this.entityMecha instanceof EntityGynoid)
//		{
//			buttonPosX = ((this.width - this.xSize) / 2) + 8;
//			buttonPosY = ((this.height - this.ySize) / 2) + 28;
//			this.buttonTrade = (GuiEntityFriendMechaInventory.ChangeButton) this.addButton(new GuiEntityFriendMechaInventory.ChangeButton(5, buttonPosX, buttonPosY));
//		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float renderPartialTicks, int xMouse, int yMouse)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(RES_MECHA_INVENTORY);

		int originPosX = (this.width - this.xSize) / 2;
		int originPosY = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(originPosX, originPosY, 0, 0, this.xSize, this.ySize);

		if(!this.entityMecha.canBeingRidden)
		{
			this.drawTexturedModalRect((originPosX + 153), (originPosY + 85), 203, 104, 15, 10);
		}
		else
		{
			this.drawTexturedModalRect((originPosX + 137), (originPosY + 85), 184, 104, 15, 10);
		}

		if(!(this.entityMecha instanceof EntityVehicle))
		{
			this.drawTexturedModalRect((originPosX + 62), (originPosY + 85), 184, 4, 18, 18);
		}

		for(int i =0; i < this.entityMecha.slotSize && i < 3; i++)
		{
			this.drawTexturedModalRect((originPosX + 82 + (i * 18)), (originPosY + 85), 184, 4, 18, 18);
		}

		int entityPosX = (originPosX + 33);
		int entityPosY = (originPosY + 92);

		int scale= (int)(37 - this.entityMecha.height * 5);

		if(entityMecha instanceof EntityMobCF || entityMecha instanceof EntityMTT3)
		{
			scale =12;
		}
		if(entityMecha instanceof EntityMTT1 || entityMecha instanceof EntityMTT2)
		{
			scale =15;
		}
		if( entityMecha instanceof EntityVehicle)
		{
			scale = 20;
		}

		GuiInventory.drawEntityOnScreen(entityPosX, entityPosY, scale, (float) (entityPosX - xMouse), (float) ((entityPosY / 2) - yMouse), this.entityMecha);

		entityPosX = (originPosX + 6);
		entityPosY = (originPosY + 28);

//		if(this.entityMecha instanceof EntityGynoid)
//		{
//			itemRender.renderItemAndEffectIntoGUI(this.entityMecha, new ItemStack(TFItems.GEM), entityPosX, entityPosY);
//		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int xMouse, int yMouse)
	{

		CharSequence owner = this.entityMecha.getOwnerName().length() > 14 ? this.entityMecha.getOwnerName().subSequence(0, 14) : this.entityMecha.getOwnerName();

		int atk = this.entityMecha.getMechaATK();
//		if(this.entityMecha instanceof EntityGynoid)
//		{
//			if(this.entityMecha.getMechaLevel() < 14)
//			{
//				atk = 0;
//			}
//			else
//			{
//				atk = this.entityMecha.getMechaATK();
//
//				if(this.entityMecha instanceof EntityTF77B)
//				{
//					atk = this.entityMecha.getMechaATK() + 3;
//				}
//				if(this.entityMecha instanceof EntityTF78R)
//				{
//					atk = this.entityMecha.getMechaATK() + 5;
//				}
//				if(this.entityMecha instanceof EntityTF79P)
//				{
//					atk = this.entityMecha.getMechaATK() + 2;
//				}
//				if(this.entityMecha instanceof EntityTF80G)
//				{
//					atk = this.entityMecha.getMechaATK() + 4;
//				}
//
//			}
//		}
		String mode = "";
		switch(this.entityMecha.getMechaMode())
		{
		case 0: mode = "Idle"; break;
		case 1: mode = "Follow"; break;
		case 2: mode = "Free"; break;
		case 3: mode = "Riding"; break;
		}

		int color = 0x00DDCC;

		if(this.entityMecha.getMechaLevel() >=  this.entityMecha.maxLevel - 1)
		{
			color = 0xF3F781;
		}

		String toughness = "";
		if(this.entityMecha.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getBaseValue() >= 1)
		{
			toughness = " (+" + (int)this.entityMecha.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getBaseValue() + ")";
		}

		this.fontRenderer.drawString(this.entityPlayer.inventory.getDisplayName().getUnformattedText(), 8, 106, 4210752);

		this.fontRenderer.drawString(this.entityMecha.getDisplayName().getUnformattedText(), 8, 18, 0x00DDCC);
		this.fontRenderer.drawString("Lv: " + (this.entityMecha.getMechaLevel() + 1), 113, 18, color);

		this.fontRenderer.drawString("Status: " + mode, 63, 30, 0x00EEDD);
		this.fontRenderer.drawString("Health: " + (int)this.entityMecha.getHealth() + ".0 / " + this.entityMecha.getMaxHealth(), 63, 39, 0x00DDCC);
		this.fontRenderer.drawString("Attack: " + atk + ".0", 63, 48, 0x00DDCC);
		this.fontRenderer.drawString("Armor: " + this.entityMecha.getEntityAttribute(SharedMonsterAttributes.ARMOR).getAttributeValue() + toughness, 63, 57, 0x00DDCC);
		this.fontRenderer.drawString("Owner: " + owner, 63, 66, 0x00DDCC);


	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);

		int originPosX = (this.width - this.xSize) / 2;
		int originPosY = (this.height - this.ySize) / 2;

		if(entityMecha.getSkillUnique() != null)
		{
			itemRender.renderItemAndEffectIntoGUI(entityMecha.getSkillUnique(), originPosX + 63, originPosY + 86);

			if (this.isPointInRegion(63, 86, 16, 16, mouseX, mouseY))
			{
				this.renderToolTip(entityMecha.getSkillUnique(), mouseX, mouseY);
			}
		}

		this.renderHoveredToolTip(mouseX, mouseY);


//		if(entityMecha instanceof EntityGynoid)
//		{
//			if (this.isPointInRegion(6, 28, 14, 14, mouseX, mouseY))
//			{
//				this.drawHoveringText(Lists.newArrayList(I18n.format("entity.gynoidReady", new Object[0])), mouseX, mouseY);
//			}
//		}

		if(entityMecha.getOwner() == entityPlayer || this.entityPlayer.capabilities.isCreativeMode)
		{
			if(this.entityMecha.canBeingRidden)
			{
				if (this.isPointInRegion(153, 86, 13, 8, mouseX, mouseY))
				{
					this.drawHoveringText(Lists.newArrayList(I18n.format("entity.mechaRide", new Object[0])), mouseX, mouseY);
				}
			}
			else
			{
				if (this.isPointInRegion(138, 86, 13, 8, mouseX, mouseY))
				{
					this.drawHoveringText(Lists.newArrayList(I18n.format("entity.mechaMode", new Object[0])), mouseX, mouseY);
				}
			}

			if (this.isPointInRegion(159, 18, 8, 8, mouseX, mouseY))
			{
				this.drawHoveringText(Lists.newArrayList(I18n.format("entity.mechaItem", new Object[0])), mouseX, mouseY);
			}

			if (this.isPointInRegion(148, 18, 7, 7, mouseX, mouseY) && this.entityMecha.getMechaLevel() < this.entityMecha.maxLevel - 1)
			{
				this.drawHoveringText(Lists.newArrayList(I18n.format("entity.mechaLevel", new Object[0])), mouseX, mouseY);
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button == this.buttonChange || button == this.buttonRide || button == this.buttonItem /* || button == this.buttonTrade */ || button == this.buttonLevel)
		{
			PacketHandler.INSTANCE.sendToServer(new MessageFriendMecha(this.entityMecha, button.id));

			((ChangeButton) button).mouseClicked();
		}
	}

	@SideOnly(Side.CLIENT)
	private static class ChangeButton extends GuiButton
	{
		private int buttonTextureY;

		public ChangeButton(int buttonId, int x, int y)
		{
			super(buttonId, x, y, buttonId == 2 ? 10 : buttonId == 6 ? 9 : buttonId == 5 ? 16 : 15, buttonId == 5 ? 16 : buttonId == 6 ? 9 : 10, "");

			this.buttonTextureY = 56;

		}

		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
		{
			if (this.visible)
			{
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

				mc.getTextureManager().bindTexture(RES_MECHA_INVENTORY);

                this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

				int buttonTex = 0;
				int buttonHover = 0;

				switch (id)
				{
				case 0:
					buttonTex = 184;
					break;
				case 1:
					buttonTex = 203;
					break;
				case 2:
					buttonTex = 222;
					break;
				case 5:
					this.buttonTextureY = 26;
					buttonTex = 184;
					break;
				case 6:
					buttonTex = 236;
					break;
				}

                if (this.hovered)
                {
                	buttonHover = 16;
                	if(id == 5)
                	{
                		buttonHover = 0;
                	}
                }

				this.drawTexturedModalRect(this.x, this.y, buttonTex, this.buttonTextureY + buttonHover, this.width, this.height);
			}
		}

		@Override
		public void mouseReleased(int mouseX, int mouseY)
		{
			this.buttonTextureY = 56;
		}

		// TODO /* ======================================== MOD START =====================================*/

		public void mouseClicked()
		{
			this.buttonTextureY = 72;
		}

		public int getButtonId(int id)
		{
			return id == 3 ? 10 : id == 5 ? 16 : 15;
		}

	}
}
