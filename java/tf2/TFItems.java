package tf2;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import tf2.items.ItemAmmo;
import tf2.items.ItemBase;
import tf2.items.ItemCan;
import tf2.items.ItemCanLava;
import tf2.items.ItemCanMilk;
import tf2.items.ItemCanWater;
import tf2.items.ItemDrill;
import tf2.items.ItemGunChip;
import tf2.items.ItemMission;
import tf2.items.ItemParts;
import tf2.items.ItemPowder;
import tf2.items.ItemShieldIron;
import tf2.items.ItemShieldRiot;
import tf2.items.ItemSpawnFriendMecha;
import tf2.items.ItemTFAxe;
import tf2.items.ItemTFPickaxe;
import tf2.items.ItemTFSpade;
import tf2.items.ItemTFSword;
import tf2.items.ItemWorkkit;
import tf2.items.armor.ItemRigidoBody;
import tf2.items.armor.ItemRigidoBoot;
import tf2.items.armor.ItemRigidoHelmet;
import tf2.items.armor.ItemRigidoLeg;
import tf2.items.armor.ItemSteelArmor;
import tf2.items.guns.ItemTFGunsAR;
import tf2.items.guns.ItemTFGunsGL;
import tf2.items.guns.ItemTFGunsHG;
import tf2.items.guns.ItemTFGunsLMG;
import tf2.items.guns.ItemTFGunsSG;
import tf2.items.guns.ItemTFGunsSMG;
import tf2.items.guns.ItemTFGunsSR;
import tf2.items.skill.ItemAreaHeal;
import tf2.items.skill.ItemAssaltForce;
import tf2.items.skill.ItemBuffSkill;
import tf2.items.skill.ItemCannonade;
import tf2.items.skill.ItemCommandAssault;
import tf2.items.skill.ItemCorrosionBullet;
import tf2.items.skill.ItemCurePod;
import tf2.items.skill.ItemDistortion;
import tf2.items.skill.ItemFireBarrage;
import tf2.items.skill.ItemFrontLine;
import tf2.items.skill.ItemGhostDance;
import tf2.items.skill.ItemMobileArmor;
import tf2.items.skill.ItemMortarSupport;
import tf2.items.skill.ItemPrecisionSniper;
import tf2.items.skill.ItemReliefSquad;
import tf2.items.skill.ItemRepairTool;
import tf2.items.skill.ItemResonanceSoul;
import tf2.items.skill.ItemShieldCircle;
import tf2.items.skill.ItemSpreadHowitzer;
import tf2.items.skill.ItemSuppressFormation;
import tf2.items.skill.ItemTeamWork;
import tf2.items.skill.friendskill.ItemMechaSkillBase;
import tf2.items.skill.friendskill.ItemMechaSkillBase.EnumFriendSkillType;
import tf2.items.weapon.ItemCoordJump;
import tf2.items.weapon.ItemGrenadeHe;
import tf2.util.Reference;

public class TFItems
{
	public static final List<Item> ITEMS = new ArrayList<Item>();
	public static final ToolMaterial steel = EnumHelper.addToolMaterial("steel", 2, 1000, 12.0F, 3.0F, 8);
	public static final ToolMaterial rigido = EnumHelper.addToolMaterial("rigido", 3, 2000, 13.0F, 4.0F, 14);
	public static final ArmorMaterial steelArmor = EnumHelper.addArmorMaterial("steelArmor", "tf2:reinforcediron_armor", 40, new int[] { 3, 5, 7, 3 }, 8, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
	public static final ArmorMaterial rigidoArmor = EnumHelper.addArmorMaterial("rigidoArmor", "tf2:rigidoarmor", 120, new int[] { 3, 6, 8, 3 }, 14, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2);

	public static final Item REINFORCED_IRON_INGOT = new ItemBase("reinforced_ironingot");
	public static final Item RIGIDO_INGOT = new ItemBase("rigidoingot");
	public static final Item REIMETAL_INGOT = new ItemBase("reimetalingot");

	public static final Item NITER = new ItemBase("niter");
	public static final Item SULFUR = new ItemBase("sulfur");
	public static final Item FLAMLIGHT = new ItemBase("flamlight");
	public static final Item FLAM_FRAGMENT = new ItemBase("flamfragment");
	public static final Item COKE = new ItemBase("coke");
	public static final Item COALTAR = new ItemBase("coaltar");
	public static final Item PHENOL = new ItemBase("phenol");
	public static final Item WASTE_OIL = new ItemBase("waste_oil");
	public static final Item DIESEL_BOX = new ItemBase("diesel_box");

	public static final Item SCRAP = new ItemBase("scrap");
	public static final Item SCRAP_RUBBER = new ItemBase("scrap_rubber");
	public static final Item RUBBER = new ItemBase("rubber");
	public static final Item REIMETAL = new ItemBase("reimetal");
	public static final Item RIGIDO_PLATE = new ItemBase("rigidoplate");
	public static final Item MECHA_PARTS = new ItemBase("mechaparts");
	public static final Item TECH_PARTS = new ItemBase("techparts");
	public static final Item LEPRE_CUBE = new ItemBase("leprecube");
	public static final Item COMPRESS_IRON = new ItemBase("compressiron");
	public static final Item GEAR_IRON = new ItemBase("gear_iron");
	public static final Item CAN = new ItemCan("can");
	public static final Item CAN_WATER = new ItemCanWater("can_water");
	public static final Item CAN_LAVA = new ItemCanLava("can_lava");
	public static final Item CAN_MILK = new ItemCanMilk("can_milk");
	public static final Item EXPLOSIVE = new ItemBase("explosive");
	public static final Item CRUSH_FILTER = new ItemBase("crushfilter");
	public static final Item PARTICLE_FILTER = new ItemBase("particlefilter");

	public static final Item POWDER = new ItemPowder("powder");
	public static final Item POWDER_IRON = new ItemBase("powder_iron");
	public static final Item IRON_SHIELD = new ItemShieldIron("ironshield");
	public static final Item RIOT_SHIELD = new ItemShieldRiot("riotshield");
	public static final Item DRILL = new ItemDrill("drill").setMaxDamage(250);
	public static final Item DRILL_DIAMOND = new ItemDrill("drill_diamond").setMaxDamage(1000);

	public static final Item WORK_KIT = new ItemWorkkit("workkit");
	public static final Item BOX_SMALL = new ItemAmmo("boxsmall", 200);
	public static final Item BOX_RIFLE = new ItemAmmo("boxrifle", 200);
	public static final Item BOX_SHOT = new ItemAmmo("boxshot", 50);
	public static final Item BOX_SNIPER = new ItemAmmo("boxsniper", 30);
	public static final Item BOX_GRENADE = new ItemAmmo("boxgrenade", 10);

	public static final Item ITEM_LOCK = new ItemBase("item_lock").setMaxStackSize(1);
	public static final Item COORD_JUMP = new ItemCoordJump("coord_jump");
	public static final Item GRENADE_HE = new ItemGrenadeHe("grenade_he");

	public static final Item PARTS = new ItemParts("parts");

	public static final Item UPGRADE_0 = new ItemBase("upgrade_0");
	public static final Item UPGRADE_1 = new ItemBase("upgrade_1");
	public static final Item UPGRADE_2 = new ItemBase("upgrade_2");

	public static final Item DEVELOP_CHIP_0 = new ItemBase("developchip_0");
	public static final Item DEVELOP_CHIP_1 = new ItemBase("developchip_1");
	public static final Item DEVELOP_CHIP_2 = new ItemBase("developchip_2");

	public static final Item GUNCHIP = new ItemGunChip("gunchip");

	public static final Item M1911 = new ItemTFGunsHG("m1911", 8, 6F, 2F, 1F, TFSoundEvents.M1911, 2F, 4F, 3, 18);
	public static final Item DESERTEAGLE = new ItemTFGunsHG("deserteagle", 7, 15F, 2F, 1F, TFSoundEvents.DESERT, 10F, 10F, 15, 20);

	public static final Item UZI = new ItemTFGunsSMG("uzi", 40, 3F, 0.8F, 1F, TFSoundEvents.UZI, 0F, 1.5F, 1 / 2, 24);
	public static final Item PPSH41 = new ItemTFGunsSMG("ppsh41", 70, 3F, 1F, 2.8F, TFSoundEvents.PPSH, 0F, 6F, 1, 42);
	public static final Item P90 = new ItemTFGunsSMG("p90", 50, 3F, 1F, 2.8F, TFSoundEvents.P90, 0F, 1.5F, 1 / 2, 30);
	public static final Item PP19 = new ItemTFGunsSMG("pp19", 60, 3F, 1F, 2.8F, TFSoundEvents.PP19, 0F, 1.5F, 1 / 2, 36);
	public static final Item EVO3 = new ItemTFGunsSMG("evo3", 30, 4F, 1F, 2.8F, TFSoundEvents.EVO, 0F, 2.1F, 1 / 2, 24);
	public static final Item MP7 = new ItemTFGunsSMG("mp7", 30, 4F, 1F, 2.8F, TFSoundEvents.MP7, 0F, 1.5F, 1 / 2, 32);
	public static final Item UMP9 = new ItemTFGunsSMG("ump9", 30, 4F, 1F, 2.8F, TFSoundEvents.UMP9, 0F, 1.8F, 1 / 2, 28);

	public static final Item AK47 = new ItemTFGunsAR("ak47", 30, 5.5F, 1F, 4.5F, TFSoundEvents.AK, 0.5F, 4F, 1, 35);
	public static final Item AK74 = new ItemTFGunsAR("ak74", 30, 5F, 1F, 4.5F, TFSoundEvents.AK, 0.4F, 4F, 1, 30);
	public static final Item AK12 = new ItemTFGunsAR("ak12", 30, 6F, 1F, 4.5F, TFSoundEvents.AK12, 0.5F, 4.5F, 1, 35);
	public static final Item M16A1 = new ItemTFGunsAR("m16a1", 30, 4F, 1F, 4.5F, TFSoundEvents.M16, 0.3F, 2.2F, 1, 27);
	public static final Item M4A1 = new ItemTFGunsAR("m4a1", 30, 4.5F, 1F, 4.5F, TFSoundEvents.M16, 0.3F, 2.6F, 1, 30);
	public static final Item HK416 = new ItemTFGunsAR("hk416", 30, 5F, 1F, 4.5F, TFSoundEvents.HK416, 0.4F, 3F, 1, 36);
	public static final Item SIG550 = new ItemTFGunsAR("sig550", 30, 4F, 1F, 4.5F, TFSoundEvents.SIG550, 0F, 2.1F, 1, 28);
	public static final Item G36 = new ItemTFGunsAR("g36", 30, 4.5F, 1F, 4.5F, TFSoundEvents.G36, 0F, 2.2F, 1, 33);
	public static final Item FAMAS = new ItemTFGunsAR("famas", 25, 4F, 1F, 4.5F, TFSoundEvents.FAMAS, 0F, 2.3F, 1 / 2, 30);
	public static final Item OTS14 = new ItemTFGunsAR("ots14", 30, 4F, 1F, 4.5F, TFSoundEvents.SUPPRESS, 0F, 2.3F, 1 / 2, 36);

	public static final Item M24 = new ItemTFGunsSR("m24", 5, 25F, 2.5F, 20F, TFSoundEvents.M24, 5F, 8F, 26, 30);
	public static final Item M200 = new ItemTFGunsSR("m200", 5, 28F, 2.5F, 20F, TFSoundEvents.M24, 6F, 9F, 28, 31);
	public static final Item XM2010 = new ItemTFGunsSR("xm2010", 5, 30F, 2.5F, 20F, TFSoundEvents.M24, 7F, 10F, 30, 32);
	public static final Item SVD = new ItemTFGunsSR("svd", 10, 14F, 2F, 20F, TFSoundEvents.SVD, 2F, 4.5F, 7, 30);
	public static final Item WA2000 = new ItemTFGunsSR("wa2000", 10, 16F, 2F, 20F, TFSoundEvents.WA2000, 2.5F, 5F, 8, 31);
	public static final Item PSG1 = new ItemTFGunsSR("psg1", 10, 18F, 2F, 20F, TFSoundEvents.PSG1, 3F, 5.5F, 9, 32);
	public static final Item MOSINNAGANT = new ItemTFGunsSR("mosinnagant", 5, 23F, 2.5F, 20F, TFSoundEvents.MOSIN, 4F, 8F, 14, 32);
	public static final Item M82A1 = new ItemTFGunsSR("m82a1", 10, 30F, 2.5F, 20F, TFSoundEvents.M82, 8F, 10F, 20, 45);
	public static final Item PGM = new ItemTFGunsSR("pgm", 5, 35F, 2.5F, 20F, TFSoundEvents.PGM, 8F, 10F, 34, 35);

	public static final Item SPAS12 = new ItemTFGunsSG("spas12", 8, 3F, 0.5F, 12, 8F, TFSoundEvents.SPAS, 8F, 10F, 18, 30);
	public static final Item M1014 = new ItemTFGunsSG("m1014", 8, 3F, 0.5F, 13, 8F, TFSoundEvents.M1014, 8F, 10F, 20, 32);
	public static final Item M870 = new ItemTFGunsSG("m870", 8, 3F, 0.5F, 14, 8F, TFSoundEvents.M870, 8F, 10F, 22, 34);
	public static final Item SAIGA12 = new ItemTFGunsSG("saiga12", 8, 3F, 0.5F, 10, 13F, TFSoundEvents.SAIGA, 4F, 6F, 6, 34);
	public static final Item VEPR12 = new ItemTFGunsSG("vepr12", 8, 3F, 0.5F, 9, 13F, TFSoundEvents.SAIGA, 4F, 6F, 5, 30);
	public static final Item AA12 = new ItemTFGunsSG("aa12", 20, 3F, 0.5F, 8, 13F, TFSoundEvents.AA12, 3F, 6F, 5, 38);

	public static final Item MG42 = new ItemTFGunsLMG("mg42", 100, 5.5F, 1F, 6F, TFSoundEvents.MG42, 0.5F, 6.5F, 1, 60);
	public static final Item M60E1 = new ItemTFGunsLMG("m60e1", 100, 6F, 1F, 6F, TFSoundEvents.MG42, 0.5F, 7F, 1, 60);

	public static final Item M1A1 = new ItemTFGunsGL("m1a1", 1, 40F, 3F, 4D, 8F, TFSoundEvents.BAZOOKA, 15F, 15F, 40, 40);
	public static final Item M20 = new ItemTFGunsGL("m20", 1, 40F, 3F, 3D, 8F, TFSoundEvents.BAZOOKA, 15F, 15F, 30, 30);
	public static final Item MGL140 = new ItemTFGunsGL("mgl140", 6, 40F, 3F, 2.5D, 8F, TFSoundEvents.MGL, 15F, 15F, 20, 50);

	public static final Item SKILL_FULLFIRE = new ItemBuffSkill("fullfire", 1200);
	public static final Item SKILL_ATTITUDECONTROL = new ItemBuffSkill("attitudecontrol", 1200);
	public static final Item SKILL_OVERDRIVE = new ItemBuffSkill("overdrive", 1200);
	public static final Item SKILL_MORTARSUPPORT = new ItemMortarSupport("mortarsupport", 1800);
	public static final Item SKILL_CANNONADE = new ItemCannonade("cannonade", 1800);

	public static final Item SKILL_AVOIDANCE = new ItemBuffSkill("avoidance", 300);
	public static final Item SKILL_GUARDPOINOT = new ItemBuffSkill("guardpoint", 1200);
	public static final Item SKILL_ANTIDISTURB = new ItemBuffSkill("antidisturb", 1200);
	public static final Item SKILL_DISTORTION = new ItemDistortion("distortion", 1200);
	public static final Item SKILL_SHIELDCIRCLE = new ItemShieldCircle("shieldcircle", 600);

	public static final Item SKILL_FRONTLINE = new ItemFrontLine("frontline", 400);
	public static final Item SKILL_TEAMWORK = new ItemTeamWork("teamwork", 600);
	public static final Item SKILL_ASSALTFORCE = new ItemAssaltForce("assaltforce", 1200);
	public static final Item SKILL_RESONANCESOUL = new ItemResonanceSoul("resonancesoul", 4800);
	public static final Item SKILL_SUPPRESSFORMATION = new ItemSuppressFormation("suppressformation", 1200);

	public static final Item SKILL_MEDICALKIT = new ItemBuffSkill("medicalkit", 1200);
	public static final Item SKILL_RECOVERPOINT = new ItemBuffSkill("recoverpoint", 1200);
	public static final Item SKILL_AREAHEAL = new ItemAreaHeal("areaheal", 400);
	public static final Item SKILL_CUREPOD = new ItemCurePod("curepod", 1200);
	public static final Item SKILL_RELIEFSQUAD = new ItemReliefSquad("reliefsquad", 1200);

	public static final Item SKILL_REPAIRTOOL = new ItemRepairTool("repairtool", 2400);

	public static final Item SKILL_CORROSIONBULLET = new ItemCorrosionBullet("corrosionbullet");
	public static final Item SKILL_MOBILEARMOR = new ItemMobileArmor("mobilearmor");
	public static final Item SKILL_PRECISONSNIPER = new ItemPrecisionSniper("precisionsniper");
	public static final Item SKILL_GHOSTDANCE = new ItemGhostDance("ghostdance");
	public static final Item SKILL_FIREBARRAGE = new ItemFireBarrage("firebarrage");
	public static final Item SKILL_BERSERKGLUED = new ItemCommandAssault("berserkglued");
	public static final Item SKILL_SPREADHOWITZER = new ItemSpreadHowitzer("spreadhowitzer");

	public static final Item SKILL_FIREFILLING = new ItemMechaSkillBase("firefilling", EnumFriendSkillType.COMMON);
	public static final Item SKILL_HARDSTRIKE = new ItemMechaSkillBase("hardstrike", EnumFriendSkillType.COMMON);
	public static final Item SKILL_FULLFIREPOWER = new ItemMechaSkillBase("fullfirepower", EnumFriendSkillType.COMMON);
	public static final Item SKILL_ENCHANTFLAME = new ItemMechaSkillBase("enchantflame", EnumFriendSkillType.COMMON);
	public static final Item SKILL_ADDITIONALARMOR_1 = new ItemMechaSkillBase("additionalarmor_1", EnumFriendSkillType.COMMON);
	public static final Item SKILL_ADDITIONALARMOR_2 = new ItemMechaSkillBase("additionalarmor_2", EnumFriendSkillType.COMMON);
	public static final Item SKILL_ADDITIONALARMOR_3 = new ItemMechaSkillBase("additionalarmor_3", EnumFriendSkillType.COMMON);
	public static final Item SKILL_ALLORNOTHING = new ItemMechaSkillBase("allornothing", EnumFriendSkillType.COMMON);
	public static final Item SKILL_FIREPROTECTION = new ItemMechaSkillBase("fireprotection", EnumFriendSkillType.COMMON);
	public static final Item SKILL_ENCHANTWEAKNESS = new ItemMechaSkillBase("enchantweakness", EnumFriendSkillType.COMMON);
	public static final Item SKILL_PROVOCATE = new ItemMechaSkillBase("provocate", EnumFriendSkillType.COMMON);
	public static final Item SKILL_ARTILLERYCOMMAND_TURRET = new ItemMechaSkillBase("artillerycommand_turret", EnumFriendSkillType.COMMON);
	public static final Item SKILL_REPAIRDOUBLING = new ItemMechaSkillBase("repairdoubling", EnumFriendSkillType.COMMON);
	public static final Item SKILL_ENCHANTSLOW = new ItemMechaSkillBase("enchantslow", EnumFriendSkillType.COMMON);
	public static final Item SKILL_AUTOREPAIR = new ItemMechaSkillBase("autorepair", EnumFriendSkillType.COMMON);
	public static final Item SKILL_COMEBACK = new ItemMechaSkillBase("comeback", EnumFriendSkillType.COMMON);

	public static final Item SKILL_RESURRECTION =  new ItemMechaSkillBase("resurrection", EnumFriendSkillType.CONSUME);

	public static final Item SKILL_QUICKRELOAD = new ItemMechaSkillBase("quickreload", EnumFriendSkillType.UNIQUE);
	public static final Item SKILL_SPREADCANNON = new ItemMechaSkillBase("spreadcannon", EnumFriendSkillType.UNIQUE);
	public static final Item SKILL_WIDESPREAD = new ItemMechaSkillBase("widespread", EnumFriendSkillType.UNIQUE);
	public static final Item SKILL_SELFHEALING = new ItemMechaSkillBase("selfhealing", EnumFriendSkillType.UNIQUE);
	public static final Item SKILL_ARMEDFORM_ALPHA = new ItemMechaSkillBase("armedform_alpha", EnumFriendSkillType.UNIQUE);
	public static final Item SKILL_ARMEDFORM_BETA = new ItemMechaSkillBase("armedform_beta", EnumFriendSkillType.UNIQUE);
	public static final Item SKILL_ARMEDFORM_GAMMA = new ItemMechaSkillBase("armedform_gamma", EnumFriendSkillType.UNIQUE);
	public static final Item SKILL_ARMEDFORM_DELTA = new ItemMechaSkillBase("armedform_delta", EnumFriendSkillType.UNIQUE);

	public static final Item SPAWNFM = new ItemSpawnFriendMecha("spawncore_fm").setMaxStackSize(1);

	public static final Item REINFORCED_IRON_SWORD = new ItemTFSword("reinforced_ironsword", steel, new ItemStack(REINFORCED_IRON_INGOT));
	public static final Item REINFORCED_IRON_SHOVEL = new ItemTFSpade("reinforced_ironshovel", steel, new ItemStack(REINFORCED_IRON_INGOT));
	public static final Item REINFORCED_IRON_PICKAXE = new ItemTFPickaxe("reinforced_ironpickaxe", steel, new ItemStack(REINFORCED_IRON_INGOT));
	public static final Item REINFORCED_IRON_AXE = new ItemTFAxe("reinforced_ironaxe", steel, 8F, -3F,new ItemStack(REINFORCED_IRON_INGOT));
	public static final Item RIGIDO_SWORD = new ItemTFSword("rigidosword", rigido, new ItemStack(RIGIDO_INGOT));
	public static final Item RIGIDO_SHOVEL = new ItemTFSpade("rigidoshovel", rigido, new ItemStack(RIGIDO_INGOT));
	public static final Item RIGIDO_PICKAXE = new ItemTFPickaxe("rigidopickaxe", rigido, new ItemStack(RIGIDO_INGOT));
	public static final Item RIGIDO_AXE = new ItemTFAxe("rigidoaxe", rigido, 9F, -3F,new ItemStack(RIGIDO_INGOT));

	public static final Item REINFORCED_IRON_HELMET = new ItemSteelArmor("reinforced_ironhelmet",steelArmor, 0, EntityEquipmentSlot.HEAD);
	public static final Item REINFORCED_IRON_BODY = new ItemSteelArmor("reinforced_ironbody",steelArmor, 0, EntityEquipmentSlot.CHEST);
	public static final Item REINFORCED_IRON_LEG = new ItemSteelArmor("reinforced_ironleg",steelArmor, 0, EntityEquipmentSlot.LEGS);
	public static final Item REINFORCED_IRON_BOOT = new ItemSteelArmor("reinforced_ironboot",steelArmor, 0, EntityEquipmentSlot.FEET);
	public static final Item RIGIDO_HELMET = new ItemRigidoHelmet("rigidohelmet",rigidoArmor, 0, EntityEquipmentSlot.HEAD);
	public static final Item RIGIDO_BODY = new ItemRigidoBody("rigidobody",rigidoArmor, 0, EntityEquipmentSlot.CHEST);
	public static final Item RIGIDO_LEG = new ItemRigidoLeg("rigidoleg",rigidoArmor, 0, EntityEquipmentSlot.LEGS);
	public static final Item RIGIDO_BOOT = new ItemRigidoBoot("rigidoboot",rigidoArmor, 0, EntityEquipmentSlot.FEET);

	public static final Item MISSION_1 = new ItemMission("mission_1",new ResourceLocation(Reference.MOD_ID, "event1"));
	public static final Item MISSION_2 = new ItemMission("mission_2",new ResourceLocation(Reference.MOD_ID, "event2"));
	public static final Item MISSION_3 = new ItemMission("mission_3",new ResourceLocation(Reference.MOD_ID, "event3"));
}
