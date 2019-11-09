package tf2;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tf2.util.Reference;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class TFSoundEvents {

    public static final SoundEvent M16 = createEvent("gun.m16");
    public static final SoundEvent AK = createEvent("gun.ak");
    public static final SoundEvent AA12 = createEvent("gun.aa12");
    public static final SoundEvent AK12 = createEvent("gun.ak12");
    public static final SoundEvent BAZOOKA = createEvent("gun.bazooka");
    public static final SoundEvent BEAM = createEvent("gun.beam");
    public static final SoundEvent DESERT = createEvent("gun.desert");
    public static final SoundEvent EVO = createEvent("gun.evo");
    public static final SoundEvent FAMAS = createEvent("gun.famas");
    public static final SoundEvent G36 = createEvent("gun.g36");
    public static final SoundEvent HK416 = createEvent("gun.hk416");
    public static final SoundEvent M14 = createEvent("gun.m14");
    public static final SoundEvent M24 = createEvent("gun.m24");
    public static final SoundEvent M82 = createEvent("gun.m82");
    public static final SoundEvent M870 = createEvent("gun.m870");
    public static final SoundEvent M1014 = createEvent("gun.m1014");
    public static final SoundEvent M1911 = createEvent("gun.m1911");
    public static final SoundEvent MG42 = createEvent("gun.mg42");
    public static final SoundEvent MGL = createEvent("gun.mgl");
    public static final SoundEvent MOSIN = createEvent("gun.mosin");
    public static final SoundEvent MP7 = createEvent("gun.mp7");
    public static final SoundEvent P90 = createEvent("gun.p90");
    public static final SoundEvent PGM = createEvent("gun.pgm");
    public static final SoundEvent PP19 = createEvent("gun.pp19");
    public static final SoundEvent PPSH = createEvent("gun.ppsh");
    public static final SoundEvent PSG1 = createEvent("gun.psg1");
    public static final SoundEvent SAIGA = createEvent("gun.saiga");
    public static final SoundEvent SCAR = createEvent("gun.scar");
    public static final SoundEvent SIG550 = createEvent("gun.sig550");
    public static final SoundEvent SPAS = createEvent("gun.spas");
    public static final SoundEvent SVD = createEvent("gun.svd");
    public static final SoundEvent UMP9 = createEvent("gun.ump9");
    public static final SoundEvent UZI = createEvent("gun.uzi");
    public static final SoundEvent VOLT = createEvent("gun.volt");
    public static final SoundEvent WA2000 = createEvent("gun.wa2000");
    public static final SoundEvent SUPPRESS = createEvent("gun.suppress");

    public static final SoundEvent GATLING = createEvent("gun.gatling");

    public static final SoundEvent ANALYZE = createEvent("tf.analyze");
    public static final SoundEvent TM_SAY = createEvent("tf.tmsay");
    public static final SoundEvent TM_DEATH = createEvent("tf.tmdeath");

    public static final SoundEvent TF_SAY = createEvent("tf.tfsay");
    public static final SoundEvent TF_DEATH = createEvent("tf.tfdeath");

    public static final SoundEvent TF_STEP = createEvent("tf.tfstep");
    public static final SoundEvent TF_TANK = createEvent("tf.tftank");

    public static final SoundEvent BIKE = createEvent("tf.bike");

    public static final SoundEvent RELOAD = createEvent("gun.reload");

    public static final SoundEvent CHEINSAW = createEvent("weapon.cheinsaw");
    public static final SoundEvent BLADE = createEvent("weapon.blade");
    public static final SoundEvent CUBE = createEvent("weapon.cube");

    public static final SoundEvent DECISION = createEvent("system.decision");
    public static final SoundEvent QUAKE = createEvent("system.quake");

    private static SoundEvent createEvent(String sound)
    {
		ResourceLocation name = new ResourceLocation(Reference.MOD_ID, sound);
		return new SoundEvent(name).setRegistryName(name);
	}
    @SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
		evt.getRegistry().register(M16);
		evt.getRegistry().register(AK);
		evt.getRegistry().register(AA12);
		evt.getRegistry().register(AK12);
		evt.getRegistry().register(BAZOOKA);
		evt.getRegistry().register(BEAM);
		evt.getRegistry().register(DESERT);
		evt.getRegistry().register(EVO);
		evt.getRegistry().register(FAMAS);
		evt.getRegistry().register(HK416);
		evt.getRegistry().register(M14);
		evt.getRegistry().register(M24);
		evt.getRegistry().register(M82);
		evt.getRegistry().register(M870);
		evt.getRegistry().register(M1014);
		evt.getRegistry().register(M1911);
		evt.getRegistry().register(MG42);
		evt.getRegistry().register(MGL);
		evt.getRegistry().register(MOSIN);
		evt.getRegistry().register(MP7);
		evt.getRegistry().register(P90);
		evt.getRegistry().register(PGM);
		evt.getRegistry().register(PP19);
		evt.getRegistry().register(PPSH);
		evt.getRegistry().register(PSG1);
		evt.getRegistry().register(SAIGA);
		evt.getRegistry().register(SCAR);
		evt.getRegistry().register(SIG550);
		evt.getRegistry().register(SPAS);
		evt.getRegistry().register(SVD);
		evt.getRegistry().register(UZI);
		evt.getRegistry().register(VOLT);
		evt.getRegistry().register(WA2000);

		evt.getRegistry().register(GATLING);

		evt.getRegistry().register(ANALYZE);
		evt.getRegistry().register(TM_SAY);
		evt.getRegistry().register(TM_DEATH);
		evt.getRegistry().register(TF_SAY);
		evt.getRegistry().register(TF_DEATH);
		evt.getRegistry().register(TF_STEP);
		evt.getRegistry().register(TF_TANK);
		evt.getRegistry().register(RELOAD);
		evt.getRegistry().register(CHEINSAW);
		evt.getRegistry().register(BLADE);
		evt.getRegistry().register(CUBE);
		evt.getRegistry().register(BIKE);

		evt.getRegistry().register(DECISION);
		evt.getRegistry().register(QUAKE);
	}
    public TFSoundEvents() {
	}
}