package tf2;

import net.minecraftforge.common.config.Configuration;

public class TFConfig
{

	public static boolean configChange= false;

	public boolean spawnMobTMtier0 = false;
	public boolean spawnMobTMtier1 = false;
	public boolean spawnMobTMtier2 = false;
	public boolean spawnMobTMtier3 = false;
	public boolean blockDestroy = true;
	public boolean ironGenerate = true;
	public boolean multiMission = false;
	public int spawnRatetier1 = 20;
	public int spawnRatetier2 = 18;
	public int spawnRatetier3 = 12;
	public int spawnRateneter = 18;

	public void init(Configuration config)
	{
//		this.spawnMobTMtier1 = config.get("SpawnSetting", "Spawn type.TM Tier.1", true).getBoolean(true);
//		this.spawnMobTMtier2 = config.get("SpawnSetting", "Spawn type.TM Tier.2", false).getBoolean(false);
//		this.spawnMobTFtier3 = config.get("SpawnSetting", "Spawn type.TF Tier.3", false).getBoolean(false);

//		this.blockDestroy = config.get("TerrainSetting", "Block Destroy", true).getBoolean(true);
//		this.ironGenerate = config.get("TerrainSetting", "Iron Generate", true).getBoolean(true);

//		this.multiMission = config.get("EventSetting", "Multi Misson", false).getBoolean(false);

//		this.spawnRatetier1 = config.getInt("Type.TM Tier.1", "Mob Spawn Rate", 20, 1, 250, "Spawn Rate 1~250");
//		this.spawnRatetier2 = config.getInt("Type.TM Tier.2", "Mob Spawn Rate", 18, 1, 250, "Spawn Rate 1~250");
//		this.spawnRatetier3 = config.getInt("Type.TF Tier.3", "Mob Spawn Rate", 12, 1, 250, "Spawn Rate 1~250");
//		this.spawnRateneter = config.getInt("Type.Nether", "Mob Spawn Rate", 18, 1, 250, "Spawn Rate 1~250");

		this.spawnMobTMtier0 = config.getBoolean("tf.config.tier0", "all", true, "True if Tier.0 Mecha are allowed to spawn");
		this.spawnMobTMtier1 = config.getBoolean("tf.config.tier1", "all", true, "True if Tier.1 Mecha are allowed to spawn");
		this.spawnMobTMtier2 = config.getBoolean("tf.config.tier2", "all", false, "True if Tier.2 Mecha are allowed to spawn");
		this.spawnMobTMtier3 = config.getBoolean("tf.config.tier3", "all", false, "True if Tier.3 Mecha are allowed to spawn");
		this.blockDestroy = config.getBoolean("tf.config.destroy", "all", true, "True if block destroy with Explosive");
		this.ironGenerate = config.getBoolean("tf.config.iron", "all", true, "True if Magnetite ore and Hematite ore generate");
		this.multiMission = config.getBoolean("tf.config.multi", "all", false, "True if more enemies with multi missions");
		this.spawnRatetier1= config.getInt("tf.config.rate1", "all", 20, 1, 250, "The more this number, the more enemies will spawn");
		this.spawnRatetier2= config.getInt("tf.config.rate2", "all", 18, 1, 250, "The more this number, the more enemies will spawn");
		this.spawnRatetier3= config.getInt("tf.config.rate3", "all", 12, 1, 250, "The more this number, the more enemies will spawn");
		this.spawnRateneter= config.getInt("tf.config.ratenether", "all", 18, 1, 250, "The more this number, the more enemies will spawn");
	}

}
