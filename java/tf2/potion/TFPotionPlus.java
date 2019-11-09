package tf2.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TFDamageSource;
import tf2.util.Reference;

public class TFPotionPlus extends Potion
{
	public static Potion FULLFIRE;
	public static Potion ACTIVATION;
	public static Potion RESISTANCE_COLD;
	public static Potion RESISTANCE_HEAT;
	public static Potion RESISTANCE_DOT;
	public static Potion DISABLE_CHANCE;
	public static Potion MOVE_SHOOTING;
	public static Potion DAMAGE_CANCEL;
	public static Potion VULNERABILITY;
	public static Potion HEAT;
	public static Potion SHOOTING;
	public static Potion SHOOTING_SUPPORT;
	public static Potion RECOIL_SUPPRESS;
	public static Potion DEBUFF_GUARD;

	protected static final ResourceLocation potiontex = new ResourceLocation("tf2:textures/gui/potion.png");
	private final int iconID;
	private int statusIconIndex = -1;

	public static void potionSetup()
	{
		FULLFIRE = new TFPotionPlus(false, 0xFFEE00, 0).setPotionName("fullfire").setBeneficial();
		ForgeRegistries.POTIONS.register(FULLFIRE.setRegistryName(Reference.MOD_ID, "fullfire"));
		ACTIVATION = new TFPotionPlus(false, 0xFFD700, 1).setPotionName("activation").setBeneficial();
		ForgeRegistries.POTIONS.register(ACTIVATION.setRegistryName(Reference.MOD_ID, "activation"));
		RESISTANCE_COLD = new TFPotionPlus(false, 0x5070FF, 2).setPotionName("resistance_cold").setBeneficial();
		ForgeRegistries.POTIONS.register(RESISTANCE_COLD.setRegistryName(Reference.MOD_ID, "resistance_cold"));
		RESISTANCE_HEAT = new TFPotionPlus(false, 0xFF7050, 3).setPotionName("resistance_heat").setBeneficial();
		ForgeRegistries.POTIONS.register(RESISTANCE_HEAT.setRegistryName(Reference.MOD_ID, "resistance_heat"));
		MOVE_SHOOTING = new TFPotionPlus(false, 0x7080FF, 4).setPotionName("moveshooting").setBeneficial();
		ForgeRegistries.POTIONS.register(MOVE_SHOOTING.setRegistryName(Reference.MOD_ID, "moveshooting"));
		RESISTANCE_DOT = new TFPotionPlus(false, 0xDDDDFF, 5).setPotionName("resistance_dot").setBeneficial();
		ForgeRegistries.POTIONS.register(RESISTANCE_DOT.setRegistryName(Reference.MOD_ID, "resistance_dot"));
		DISABLE_CHANCE = new TFPotionPlus(false, 0x0055DD, 6).setPotionName("disablechance").setBeneficial();
		ForgeRegistries.POTIONS.register(DISABLE_CHANCE.setRegistryName(Reference.MOD_ID, "disablechance"));
		DEBUFF_GUARD = new TFPotionPlus(false, 0xFFECEF, 7).setPotionName("debuff_guard").setBeneficial();
		ForgeRegistries.POTIONS.register(DEBUFF_GUARD.setRegistryName(Reference.MOD_ID, "debuff_guard"));
		DAMAGE_CANCEL = new TFPotionPlus(false, 0xCCCCFF, 8).setPotionName("damagecancel").setBeneficial();
		ForgeRegistries.POTIONS.register(DAMAGE_CANCEL.setRegistryName(Reference.MOD_ID, "damagecancel"));
		VULNERABILITY = new TFPotionPlus(true, 0xEE6666, 9).setPotionName("vulnerability");
		ForgeRegistries.POTIONS.register(VULNERABILITY.setRegistryName(Reference.MOD_ID, "vulnerability"));
		HEAT = new TFPotionPlus(true, 0xFF6666, 10).setPotionName("heat");
		ForgeRegistries.POTIONS.register(HEAT.setRegistryName(Reference.MOD_ID, "heat"));
		SHOOTING = new TFPotionPlus(false, 0xFFA500, 11).setPotionName("shooting").setBeneficial();
		ForgeRegistries.POTIONS.register(SHOOTING.setRegistryName(Reference.MOD_ID, "shooting"));
		SHOOTING_SUPPORT = new TFPotionPlus(false, 0xADFF2F, 12).setPotionName("shooting_support").setBeneficial();
		ForgeRegistries.POTIONS.register(SHOOTING_SUPPORT.setRegistryName(Reference.MOD_ID, "shooting_support"));
		RECOIL_SUPPRESS = new TFPotionPlus(false, 0xADFF2F, 13).setPotionName("recoil_suppress").setBeneficial();
		ForgeRegistries.POTIONS.register(RECOIL_SUPPRESS.setRegistryName(Reference.MOD_ID, "recoil_suppress"));
	}
	public TFPotionPlus(boolean isBadEffect, int liquidColor, int icon)
	{
        super(isBadEffect, liquidColor);
        this.iconID = icon;
    }

	@Override
    public boolean isInstant()
	{
        return false;
    }

	@Override
	public boolean isReady(int duration, int amplifier)
    {
        if (this == TFPotionPlus.ACTIVATION)
        {
            return  duration % 80 == 0;
        }
//        else if (this == TF2Core.HOT_SPRING)
//        {
//            int k = 50 >> amplifier;
//
//            if (k > 0)
//            {
//                return duration % k == 0;
//            }
//            else
//            {
//                return true;
//            }
//        }
        else if (this == TFPotionPlus.HEAT)
        {
            int j = 50 >> amplifier;

            if (j > 0)
            {
                return duration % j == 0;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return true;
        }
    }


	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier)
    {
        if (this == TFPotionPlus.ACTIVATION)
        {
            if (entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth())
            {
                entityLivingBaseIn.heal((float)amplifier + 1.0F);
            }
        }
//        else if (this == TF2Core.HOT_SPRING)
//        {
//            if (entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth())
//            {
//                entityLivingBaseIn.heal(1.0F);
//            }
//        }
        else if (this == TFPotionPlus.HEAT)
        {
            if (entityLivingBaseIn.getHealth() > 1.0F)
            {
                entityLivingBaseIn.attackEntityFrom(TFDamageSource.HEAT, 1.0F);
                entityLivingBaseIn.hurtResistantTime = 0;
            }
        }
    }

	 @Override
	    public boolean hasStatusIcon(){
	        return true;
	    }

	 @Override
	    public int getStatusIconIndex(){
	        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));//ポーションのアイコンのテクスチャの場所を指定する。
	        return super.getStatusIconIndex();
	    }


		@Override
	    @SideOnly(Side.CLIENT)
	    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc)
		{
	        mc.getTextureManager().bindTexture(potiontex);
	        mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, (iconID % 14) * 18, (iconID / 14) * 18, 18, 18);
	    }
		@Override
		@SideOnly(Side.CLIENT)
	    public void renderHUDEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc, float alpha)
		{
			mc.getTextureManager().bindTexture(potiontex);
	        mc.ingameGUI.drawTexturedModalRect(x + 3, y + 3, (iconID % 14) * 18, (iconID / 14) * 18, 18, 18);
		}


}
