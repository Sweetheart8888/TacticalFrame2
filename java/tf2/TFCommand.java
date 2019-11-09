package tf2;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import tf2.util.TFWorldConfigManager;

public class TFCommand extends CommandBase
{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "tf";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    /**
     * Gets the usage string for the command.
     */
    public String getUsage(ICommandSender sender)
    {
        return "tf.commands.usage";
    }

    /**
     * Callback for when the command is executed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length >= 1)
        {
        	String s = args.length > 0 ? args[0] : "";
        	String s1 = args.length > 1 ? args[1] : "";
            World world = server.getEntityWorld();
            WorldInfo worldinfo = world.getWorldInfo();

            if ("destroy".equalsIgnoreCase(args[0]))
            {
            	if(args.length == 2)
            	{
	            	if (("true".equalsIgnoreCase(args[1]) || ("false".equalsIgnoreCase(args[1]))))
	            			{
	            		TF2Core.config.getCategory("all").get("tf.config.destroy").set(args[1]);
	            		TF2Core.syncConfig();
	            		notifyCommandListener(sender, this, "tf.commands.success", new Object[] {s, TF2Core.CONFIG.blockDestroy});
	            	}
	            	else
	            	{
	            		throw new CommandException("commands.generic.boolean.invalid", new Object[] {s1});
	            	}
            	}
            	else if(args.length > 2)
            	{
            		throw new CommandException("tf.commands.destroy.usage", new Object[0]);
            	}
            	else
            	{
            		s = "Block Destroy";
            		notifyCommandListener(sender, this, "tf.commands.stat.usage", new Object[] {s, TF2Core.CONFIG.blockDestroy});
            	}
            }
            else if ("irongen".equalsIgnoreCase(args[0]))
            {
            	if(args.length == 2)
            	{
	            	if (("true".equalsIgnoreCase(args[1]) || ("false".equalsIgnoreCase(args[1]))))
	            			{
	            		TF2Core.config.getCategory("all").get("tf.config.iron").set(args[1]);
	            		TF2Core.syncConfig();
	            		notifyCommandListener(sender, this, "tf.commands.success", new Object[] {s, TF2Core.CONFIG.ironGenerate});
	            	}
	            	else
	            	{
	            		throw new CommandException("commands.generic.boolean.invalid", new Object[] {s1});
	            	}
            	}
            	else if(args.length > 2)
            	{
            		throw new CommandException("tf.commands.irongen.usage", new Object[0]);
            	}
            	else
            	{
            		s = "More Iron Ore Generate";
            		notifyCommandListener(sender, this, "tf.commands.stat.usage", new Object[] {s, TF2Core.CONFIG.ironGenerate});
            	}
            }
            else if ("multi".equalsIgnoreCase(args[0]))
            {
            	if(args.length == 2)
            	{
	            	if (("true".equalsIgnoreCase(args[1]) || ("false".equalsIgnoreCase(args[1]))))
	            			{
	            		TF2Core.config.getCategory("all").get("tf.config.multi").set(args[1]);
	            		TF2Core.syncConfig();
	            		notifyCommandListener(sender, this, "tf.commands.success", new Object[] {s, TF2Core.CONFIG.multiMission});
	            	}
	            	else
	            	{
	            		throw new CommandException("commands.generic.boolean.invalid", new Object[] {s1});
	            	}
            	}
            	else if(args.length > 2)
            	{
            		throw new CommandException("tf.commands.multi.usage", new Object[0]);
            	}
            	else
            	{
            		s = "Multi Mission";
            		notifyCommandListener(sender, this, "tf.commands.stat.usage", new Object[] {s, TF2Core.CONFIG.multiMission});
            	}
            }
            else if ("tier0".equalsIgnoreCase(args[0]))
            {
            	if(args.length == 2)
            	{
	            	if (("true".equalsIgnoreCase(args[1]) || ("false".equalsIgnoreCase(args[1]))))
	            			{
	            		TF2Core.config.getCategory("all").get("tf.config.tier0").set(args[1]);
	            		TF2Core.syncConfig();
	            		notifyCommandListener(sender, this, "tf.commands.success", new Object[] {s, TF2Core.CONFIG.spawnMobTMtier0});
	            		TFWorldConfigManager.saveWorldConfigFile(world);
	            	}
	            	else
	            	{
	            		throw new CommandException("commands.generic.boolean.invalid", new Object[] {s1});
	            	}
            	}
            	else if(args.length > 2)
            	{
            		throw new CommandException("tf.commands.tier.usage", new Object[0]);
            	}
            	else
            	{
            		s = "Mecha Tier.0";
            		notifyCommandListener(sender, this, "tf.commands.stat.usage", new Object[] {s, TF2Core.CONFIG.spawnMobTMtier0});
            	}
            }
            else if ("tier1".equalsIgnoreCase(args[0]))
            {
            	if(args.length == 2)
            	{
	            	if (("true".equalsIgnoreCase(args[1]) || ("false".equalsIgnoreCase(args[1]))))
	            			{
	            		TF2Core.config.getCategory("all").get("tf.config.tier1").set(args[1]);
	            		TF2Core.syncConfig();
	            		notifyCommandListener(sender, this, "tf.commands.success", new Object[] {s, TF2Core.CONFIG.spawnMobTMtier1});
	            		TFWorldConfigManager.saveWorldConfigFile(world);
	            	}
	            	else
	            	{
	            		throw new CommandException("commands.generic.boolean.invalid", new Object[] {s1});
	            	}
            	}
            	else if(args.length > 2)
            	{
            		throw new CommandException("tf.commands.tier.usage", new Object[0]);
            	}
            	else
            	{
            		s = "Mecha Tier.1";
            		notifyCommandListener(sender, this, "tf.commands.stat.usage", new Object[] {s, TF2Core.CONFIG.spawnMobTMtier1});
            	}
            }
            else if ("tier2".equalsIgnoreCase(args[0]))
            {
            	if(args.length == 2)
            	{
	            	if (("true".equalsIgnoreCase(args[1]) || ("false".equalsIgnoreCase(args[1]))))
	            			{
	            		TF2Core.config.getCategory("all").get("tf.config.tier2").set(args[1]);
	            		TF2Core.syncConfig();
	            		notifyCommandListener(sender, this, "tf.commands.success", new Object[] {s, TF2Core.CONFIG.spawnMobTMtier2});
	            		TFWorldConfigManager.saveWorldConfigFile(world);
	            	}
	            	else
	            	{
	            		throw new CommandException("commands.generic.boolean.invalid", new Object[] {s1});
	            	}
            	}
            	else if(args.length > 2)
            	{
            		throw new CommandException("tf.commands.tier.usage", new Object[0]);
            	}
            	else
            	{
            		s = "Mecha Tier.2";
            		notifyCommandListener(sender, this, "tf.commands.stat.usage", new Object[] {s, TF2Core.CONFIG.spawnMobTMtier2});
            	}
            }
            else if ("tier3".equalsIgnoreCase(args[0]))
            {
            	if(args.length == 2)
            	{
	            	if (("true".equalsIgnoreCase(args[1]) || ("false".equalsIgnoreCase(args[1]))))
	            			{
	            		TF2Core.config.getCategory("all").get("tf.config.tier3").set(args[1]);
	            		TF2Core.syncConfig();

	            		notifyCommandListener(sender, this, "tf.commands.success", new Object[] {s, TF2Core.CONFIG.spawnMobTMtier3});
	            		TFWorldConfigManager.saveWorldConfigFile(world);
	            	}
	            	else
	            	{
	            		throw new CommandException("commands.generic.boolean.invalid", new Object[] {s1});
	            	}
            	}
            	else if(args.length > 2)
            	{
            		throw new CommandException("tf.commands.tier.usage", new Object[0]);
            	}
            	else
            	{
            		s = "Mecha Tier.3";
            		notifyCommandListener(sender, this, "tf.commands.stat.usage", new Object[] {s, TF2Core.CONFIG.spawnMobTMtier3});
            	}
            }
            else
            {
                throw new WrongUsageException("tf.commands.false", new Object[] {s});
            }
        }
        else
        {
        	notifyCommandListener(sender, this, "tf.commands.usage", new Object[] {});
        }
    }

    /**
     * Get a list of options for when the user presses the TAB key
     */
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
    	if(args.length == 1)
    	{
    		return getListOfStringsMatchingLastWord(args, new String[] {"destroy", "irongen", "multi", "tier0", "tier1", "tier2", "tier3"});
    	}
        if(args.length == 2)
        {
        	return getListOfStringsMatchingLastWord(args, new String[] {"true", "false"});
        }
        return  Collections.emptyList();
    }
}