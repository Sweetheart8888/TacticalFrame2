package tf2.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import tf2.TF2Core;

public class TFWorldConfigManager
{

	public static void createWorldConfigFile(World world)
	{
		TF2Core.loadConfig();

		File dirWorld = new File(Loader.instance().getConfigDir(),"tacticalframe" + File.separatorChar + "world");

		if(!dirWorld.isDirectory())
		{
			dirWorld.delete();
			dirWorld.mkdirs();
		}

		File fileWorld = new File(dirWorld, world.getWorldInfo().getWorldName() + ".tf");
//		if (/*!fileWorld.exists() ||*/ !fileWorld.isFile())
//		{
		TF2Core.config.getCategory("all").get("tf.config.tier0").set(false);
		TF2Core.config.getCategory("all").get("tf.config.tier1").set(false);
		TF2Core.config.getCategory("all").get("tf.config.tier2").set(false);
		TF2Core.config.getCategory("all").get("tf.config.tier3").set(false);


			try
			{
				fileWorld.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(fileWorld));
				bw.write("#TacticalFrame2 読み込み専用ファイル");	bw.newLine();
				bw.write("#このファイルは絶対に消さないでください");	bw.newLine();
				bw.newLine();
				bw.write("tier0=" + String.valueOf(false));	bw.newLine();
				bw.write("tier1=" + String.valueOf(false));	bw.newLine();
				bw.write("tier2=" + String.valueOf(false));	bw.newLine();
				bw.write("tier3=" + String.valueOf(false));	bw.newLine();
				bw.close();
			} catch (FileNotFoundException ex)
			{
				ex.printStackTrace();
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}

			TF2Core.syncConfig();
//		}
	}

	public static void saveWorldConfigFile(World world)
	{
		File dirWorld = new File(Loader.instance().getConfigDir(),"tacticalframe" + File.separatorChar + "world");
		File fileWorld = new File(dirWorld, world.getWorldInfo().getWorldName() + ".tf");
		Boolean change = false;

		if (!fileWorld.exists() || !fileWorld.isFile())
		{
			System.out.println("ファイルが存在しません");

			createWorldConfigFile(world);
		}

		try {
			if(fileWorld.canRead())
			{
				BufferedReader br = new BufferedReader(new FileReader(fileWorld));
				String str;
				while ((str = br.readLine()) != null)
				{
					String[] args = str.split("=");

					if(args.length != 0)
					{
						switch(args[0])
						{
							case "tier0":
								if(!args[1].equals(TF2Core.config.getCategory("all").get("tf.config.tier0").getString()))
								{
									change = true;
								}
								break;
							case "tier1":
								if(!args[1].equals(TF2Core.config.getCategory("all").get("tf.config.tier1").getString()))
								{
									change = true;
								}
								break;
							case "tier2":
								if(!args[1].equals(TF2Core.config.getCategory("all").get("tf.config.tier2").getString()))
								{
									change = true;
								}
								break;
							case "tier3":
								if(!args[1].equals(TF2Core.config.getCategory("all").get("tf.config.tier3").getString()))
								{
									change = true;
								}
								break;
						}
					}
				}
				br.close();
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		if(change)
		{
			changeWorldConfigFile(world);
		}

		TF2Core.syncConfig();
	}

	private static void changeWorldConfigFile(World world)
	{
		TF2Core.loadConfig();

		File dirWorld = new File(Loader.instance().getConfigDir(),"tacticalframe" + File.separatorChar + "world");

		if(!dirWorld.isDirectory())
		{
			dirWorld.delete();
			dirWorld.mkdirs();
		}

		File fileWorld = new File(dirWorld, world.getWorldInfo().getWorldName() + ".tf");

		try
		{
			fileWorld.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileWorld));
			bw.write("#TacticalFrame2 読み込み専用ファイル");	bw.newLine();
			bw.write("#このファイルは絶対に消さないでください");	bw.newLine();
			bw.newLine();
			bw.write("tier0=" + String.valueOf(TF2Core.config.getCategory("all").get("tf.config.tier0").getString()));	bw.newLine();
			bw.write("tier1=" + String.valueOf(TF2Core.config.getCategory("all").get("tf.config.tier1").getString()));	bw.newLine();
			bw.write("tier2=" + String.valueOf(TF2Core.config.getCategory("all").get("tf.config.tier2").getString()));	bw.newLine();
			bw.write("tier3=" + String.valueOf(TF2Core.config.getCategory("all").get("tf.config.tier3").getString()));	bw.newLine();
			bw.close();
		} catch (FileNotFoundException ex)
		{
			ex.printStackTrace();
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
		TF2Core.syncConfig();
	}

	public static void loadWorldConfigFile(World world)
	{
		TF2Core.loadConfig();

		File dirWorld = new File(Loader.instance().getConfigDir(),"tacticalframe" + File.separatorChar + "world");

		if(!dirWorld.isDirectory())
		{
			dirWorld.delete();
			dirWorld.mkdirs();
		}

		File fileWorld = new File(dirWorld, world.getWorldInfo().getWorldName() + ".tf");

		if(fileWorld.canRead())
		{
			try
			{
				BufferedReader br = new BufferedReader(new FileReader(fileWorld));
				String str;
				while ((str = br.readLine()) != null)
				{
					String[] args = str.split("=");

					if(args.length != 0)
					{
						switch(args[0])
						{
							case "tier0":
								TF2Core.config.getCategory("all").get("tf.config.tier0").set(Boolean.parseBoolean(args[1]));
							case "tier1":
								TF2Core.config.getCategory("all").get("tf.config.tier1").set(Boolean.parseBoolean(args[1]));
								break;
							case "tier2":
								TF2Core.config.getCategory("all").get("tf.config.tier2").set(Boolean.parseBoolean(args[1]));
								break;
							case "tier3":
								TF2Core.config.getCategory("all").get("tf.config.tier3").set(Boolean.parseBoolean(args[1]));
								break;
						}
					}
				}
				br.close();

			} catch (FileNotFoundException ex)
			{
				ex.printStackTrace();
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		TF2Core.syncConfig();
	}
}
