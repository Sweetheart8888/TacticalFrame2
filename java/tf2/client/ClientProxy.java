package tf2.client;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.CommonProxy;
import tf2.client.mobrender.RenderBike;
import tf2.client.mobrender.RenderCFR12;
import tf2.client.mobrender.RenderCFRTW;
import tf2.client.mobrender.RenderEnemyMTT4;
import tf2.client.mobrender.RenderEvent1;
import tf2.client.mobrender.RenderEvent2;
import tf2.client.mobrender.RenderLicoris;
import tf2.client.mobrender.RenderMTT1;
import tf2.client.mobrender.RenderMTT2;
import tf2.client.mobrender.RenderMTT3;
import tf2.client.mobrender.RenderMTT4;
import tf2.client.mobrender.RenderTF77B;
import tf2.client.mobrender.RenderTF78R;
import tf2.client.mobrender.RenderTF79P;
import tf2.client.mobrender.RenderTF80G;
import tf2.client.mobrender.RenderTM02;
import tf2.client.mobrender.RenderTM03;
import tf2.client.mobrender.RenderTM04;
import tf2.client.mobrender.RenderTM05;
import tf2.client.mobrender.RenderTM06;
import tf2.client.mobrender.RenderTM07;
import tf2.client.mobrender.RenderTM11;
import tf2.client.mobrender.RenderTM12;
import tf2.client.mobrender.RenderTM17;
import tf2.client.mobrender.RenderTM26A;
import tf2.client.mobrender.RenderTM26B;
import tf2.client.mobrender.RenderTM26C;
import tf2.client.mobrender.RenderTM26D;
import tf2.client.mobrender.RenderTM41;
import tf2.client.model.ModelGynoid;
import tf2.client.model.ModelSoldier;
import tf2.client.render.RenderAreaHeal;
import tf2.client.render.RenderBarrier;
import tf2.client.render.RenderBullet;
import tf2.client.render.RenderBulletBig;
import tf2.client.render.RenderBulletHE;
import tf2.client.render.RenderDimension;
import tf2.client.render.RenderFlat;
import tf2.client.render.RenderFriendShell;
import tf2.client.render.RenderGrenadeHe;
import tf2.client.render.RenderShell;
import tf2.client.render.RenderShield;
import tf2.client.render.RenderShieldRiot;
import tf2.client.render.RenderSoundwave;
import tf2.entity.EntityDimension;
import tf2.entity.mob.enemy.EntityEnemyMTT4;
import tf2.entity.mob.enemy.EntityTM02;
import tf2.entity.mob.enemy.EntityTM03;
import tf2.entity.mob.enemy.EntityTM04;
import tf2.entity.mob.enemy.EntityTM05;
import tf2.entity.mob.enemy.EntityTM06;
import tf2.entity.mob.enemy.EntityTM07;
import tf2.entity.mob.enemy.EntityTM11;
import tf2.entity.mob.enemy.EntityTM12;
import tf2.entity.mob.enemy.EntityTM26A;
import tf2.entity.mob.enemy.EntityTM26B;
import tf2.entity.mob.enemy.EntityTM26C;
import tf2.entity.mob.enemy.EntityTM26D;
import tf2.entity.mob.enemy.EntityTM41;
import tf2.entity.mob.enemy.boss.EntityTM17;
import tf2.entity.mob.frend.EntityBike;
import tf2.entity.mob.frend.EntityCFR12;
import tf2.entity.mob.frend.EntityEvent1;
import tf2.entity.mob.frend.EntityEvent2;
import tf2.entity.mob.frend.EntityEvent3;
import tf2.entity.mob.frend.EntityLicoris;
import tf2.entity.mob.frend.EntityMTT1;
import tf2.entity.mob.frend.EntityMTT2;
import tf2.entity.mob.frend.EntityMTT3;
import tf2.entity.mob.frend.EntityMTT4;
import tf2.entity.mob.frend.EntityTF77B;
import tf2.entity.mob.frend.EntityTF78R;
import tf2.entity.mob.frend.EntityTF79P;
import tf2.entity.mob.frend.EntityTF80G;
import tf2.entity.projectile.EntityBarrier;
import tf2.entity.projectile.enemy.EntityEnemyBullet;
import tf2.entity.projectile.enemy.EntityEnemyBulletHE;
import tf2.entity.projectile.enemy.EntityEnemyGrenade;
import tf2.entity.projectile.enemy.EntityEnemyHowitzer;
import tf2.entity.projectile.enemy.EntityEnemyImpact;
import tf2.entity.projectile.enemy.EntityEnemyMortar;
import tf2.entity.projectile.player.EntityAreaHeal;
import tf2.entity.projectile.player.EntityBullet;
import tf2.entity.projectile.player.EntityBulletBig;
import tf2.entity.projectile.player.EntityBulletCorrosion;
import tf2.entity.projectile.player.EntityFriendBullet;
import tf2.entity.projectile.player.EntityFriendImpact;
import tf2.entity.projectile.player.EntityFriendMortar;
import tf2.entity.projectile.player.EntityFriendShell;
import tf2.entity.projectile.player.EntityFriendSoundwave;
import tf2.entity.projectile.player.EntityGrenade;
import tf2.entity.projectile.player.EntityGrenadeHe;
import tf2.entity.projectile.player.EntityShell;
import tf2.tile.tileentity.TileEntityShield;
import tf2.tile.tileentity.TileEntityShield2;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	public static final KeyBinding keyReload = new KeyBinding("key.tf.reload", Keyboard.KEY_R, "key.categories.tacticalframe");
	public static final KeyBinding keyGetoff = new KeyBinding("key.tf.getoff", Keyboard.KEY_V, "key.categories.tacticalframe");

	public void registerItemRenderer(Item item, int meta, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	@Override
	public void loadEntity()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShield.class, new RenderShield());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShield2.class, new RenderShieldRiot());
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShield3.class, new RenderShieldRiot2());
//
		RenderingRegistry.registerEntityRenderingHandler(EntityDimension.class, RenderDimension::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, RenderBullet::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, RenderBullet::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeHe.class, RenderGrenadeHe::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityShell.class, RenderShell::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAreaHeal.class, RenderAreaHeal::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityBeam.class, RenderBeam::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBulletBig.class, RenderBulletBig::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBulletCorrosion.class, RenderBullet::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityFriendBullet.class, RenderBullet::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityFriendGrenade.class, RenderBullet::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFriendShell.class, RenderFriendShell::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFriendImpact.class, RenderFlat::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFriendSoundwave.class, RenderSoundwave::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBarrier.class, RenderBarrier::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFriendMortar.class, RenderBulletBig::new);


		RenderingRegistry.registerEntityRenderingHandler(EntityEnemyBullet.class, RenderBullet::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityEnemyGrenade.class, RenderBullet::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityEnemyBlade.class, RenderBlade::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityEnemyBulletAP.class, RenderBulletAP::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityEnemyBulletHE.class, RenderBulletHE::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityEnemyMissile.class, RenderMissile2::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityEnemyBulletCube.class, RenderBulletCube::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityEnemyImpact.class, RenderFlat::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityEnemySlashFragment.class, RenderSlashFragment::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityEnemySlashWide.class, RenderSlashWide::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityEnemyHowitzer.class, RenderBulletBig::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityEnemyMortar.class, RenderBulletBig::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityTM02.class, RenderTM02::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTM03.class, RenderTM03::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTM04.class, RenderTM04::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTM05.class, RenderTM05::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTM06.class, RenderTM06::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTM07.class, RenderTM07::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTM11.class, RenderTM11::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTM12.class, RenderTM12::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTM17.class, RenderTM17::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityTM18.class, RenderTM18::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityTM22.class, RenderTM22::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTM26A.class, RenderTM26A::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTM26B.class, RenderTM26B::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTM26C.class, RenderTM26C::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTM26D.class, RenderTM26D::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityTM29.class, RenderTM29::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityTM31.class, RenderTM31::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityTM33.class, RenderTM33::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityTM34.class, RenderTM34::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityTM41.class, RenderTM41::new);
//
//		RenderingRegistry.registerEntityRenderingHandler(EntityTF08A.class, RenderTF08A::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityTF08B.class, RenderTF08B::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityTF08C.class, RenderTF08C::new);
//
//		RenderingRegistry.registerEntityRenderingHandler(EntityTF44.class, RenderTF44::new);
//
//		RenderingRegistry.registerEntityRenderingHandler(EntityGiantSpider.class, m -> new RenderGiantSpider<>(m, new ModelSpider(), 1.75F));
//
//		RenderingRegistry.registerEntityRenderingHandler(EntityBike.class, RenderBike::new);
//		RenderingRegistry.registerEntityRenderingHandler(EntityTank.class, RenderTank::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityEvent1.class, m -> new RenderEvent1<>(m, new ModelBiped(), 0.625F));
		RenderingRegistry.registerEntityRenderingHandler(EntityEvent2.class, m -> new RenderEvent2<>(m, new ModelSoldier(), 0.625F));

		RenderingRegistry.registerEntityRenderingHandler(EntityEvent3.class, RenderCFRTW::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityLicoris.class, m -> new RenderLicoris<>(m, new ModelSoldier(), 0.625F));

//		RenderingRegistry.registerEntityRenderingHandler(EntitySoldier.class, m -> new RenderSoldier<>(m, new ModelSoldier(), 0.625F));
//		RenderingRegistry.registerEntityRenderingHandler(EntityMercenary.class, m -> new RenderMercenary<>(m, new ModelSoldier(), 0.625F));
//		RenderingRegistry.registerEntityRenderingHandler(EntityZunko.class, m -> new RenderZunko<>(m, new ModelSoldier(), 0.625F));
//		RenderingRegistry.registerEntityRenderingHandler(EntityNDFTank.class, RenderNDFTank::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityEnemyMTT4.class, RenderEnemyMTT4::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityMTT1.class, RenderMTT1::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityMTT2.class, RenderMTT2::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityMTT3.class, RenderMTT3::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityMTT4.class, RenderMTT4::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityCFR12.class, RenderCFR12::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityBike.class, RenderBike::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityTF77B.class, m -> new RenderTF77B(m, new ModelGynoid(), 0.455F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTF78R.class, m -> new RenderTF78R(m, new ModelGynoid(), 0.455F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTF79P.class, m -> new RenderTF79P(m, new ModelGynoid(), 0.455F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTF80G.class, m -> new RenderTF80G(m, new ModelGynoid(), 0.455F));
	}

//	@Override
//    public void registerFluidBlockRendering(Block block, String name)
//    {
//        final ModelResourceLocation fluidLocation = new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":fluids", name);
//        // use a custom state mapper which will ignore the LEVEL property
//        ModelLoader.setCustomStateMapper(block, new StateMapperBase()
//        {
//            @Override
//            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
//            {
//                return fluidLocation;
//            }
//        });
//    }

	@Override
	public EntityPlayer getClientPlayer()
	{
		return Minecraft.getMinecraft().player;
	}

	@Override
	public void registerClientInformation()
	{
		ClientRegistry.registerKeyBinding(keyReload);
		ClientRegistry.registerKeyBinding(keyGetoff);
	}

	@Override
	public boolean reload()
	{
		return Keyboard.isCreated() && Keyboard.isKeyDown(keyReload.getKeyCode());
	}

	@Override
	public boolean getoff()
	{
		return Keyboard.isCreated() && Keyboard.isKeyDown(keyGetoff.getKeyCode());
	}

	@Override
	public boolean shift()
	{
		return Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown();
	}

	@Override
	public boolean jumped()
	{
		return Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown();
	}

	@Override
	public boolean rightclick()
	{
		return Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown();
	}

	@Override
	public boolean leftclick()
	{
		return Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown();
	}

	@Override
	public boolean frontmove()
	{
		return Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown();
	}

	@Override
	public boolean backmove()
	{
		return Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown();
	}

	@Override
	public boolean rightmove()
	{
		return Minecraft.getMinecraft().gameSettings.keyBindRight.isKeyDown();
	}

	@Override
	public boolean leftmove()
	{
		return Minecraft.getMinecraft().gameSettings.keyBindLeft.isKeyDown();
	}

	@Override
	public <T extends TileEntity> void setCustomTileEntitySpecialRenderer(Item itemBlock, Class<T> tileEntityClass)
	{
		ForgeHooksClient.registerTESRItemStack(itemBlock, 0, tileEntityClass);
	}
}
