package tf2.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import tf2.TF2Core;
import tf2.util.Reference;

public class GuiTFConfig extends GuiConfig {
    public GuiTFConfig(GuiScreen parent) {
        super(parent, new ConfigElement(TF2Core.config.getCategory("all")).getChildElements(), Reference.MOD_ID, false, false, "TacticalFrame2 Config");
        titleLine2 = TF2Core.config.getConfigFile().getAbsolutePath();
    }

   @Override
    public void onGuiClosed(){
        super.onGuiClosed();
    }
}