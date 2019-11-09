package tf2.entity.mob.frend;

import java.util.List;

import com.google.common.base.Predicates;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import tf2.TFItems;
import tf2.TFSoundEvents;
import tf2.entity.mob.ai.EntityAIAttackRangedFriendMecha;
import tf2.entity.projectile.player.EntityFriendSoundwave;

public class EntityTF79P extends EntityGynoid
{
	private static final double defaultDamage = 2;
	private static final double upAttack = 0.055;
	private static final double upArmor = 0.217;
	private static final double upArmorToughness = 0.055;
	private static final double upMaxHealth = 1.76;

	private static final double defaultArmor = 0.0D;
	private static final double defaultArmorToughness = 0.0D;
	private static final double defaultMaxHealth = 20.0D;

	public EntityTF79P(World worldIn)
	{
		super(worldIn, defaultDamage, upAttack, defaultArmor, upArmor, defaultArmorToughness, upArmorToughness, defaultMaxHealth, upMaxHealth);
	}

	protected void initEntityAI()
	{
		super.initEntityAI();
		this.tasks.addTask(1, new EntityAIAttackRangedFriendMecha(this, 1.0D, 1, 30.0F));
	}

    @Override
	public void isUpLevel()
    {
    	super.isUpLevel();
    	this.getUniqueSkill(59);

    	if(this.getMechaLevel() == 19)
    	{
    		this.getInventoryMechaEquipment().setHasSkill(new ItemStack(TFItems.SKILL_ENCHANTSLOW));
    	}

    	if(this.getMechaLevel() == 39)
    	{
    		this.getInventoryMechaEquipment().setHasSkill(new ItemStack(TFItems.SKILL_FULLFIREPOWER));
    	}
    }

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
	{
		double shootX = target.posX - this.posX;
		double shootY = target.posY - this.posY;
		double shootZ = target.posZ - this.posZ;

		if (this.attackTime == 0)
		{
			this.playSound(TFSoundEvents.CUBE, 2.5F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
			float[] wideAngle = { 0, -0.3F, 0.3F};
			 for (int wide = 0; wide < wideAngle.length; ++wide)
	         {
				 double x2 = (shootX * (MathHelper.cos(wideAngle[wide]))) - (shootZ * (MathHelper.sin(wideAngle[wide])));
	             double z2 = (shootX * (MathHelper.sin(wideAngle[wide]))) + (shootZ * (MathHelper.cos(wideAngle[wide])));

				EntityFriendSoundwave entityImpact = new  EntityFriendSoundwave(this.world, this);
	         	entityImpact.setDamage(entityImpact.getDamage() + this.getMechaATK());
	         	entityImpact.shoot(shootX + x2, shootY, shootZ + z2, 1.5F, 0.0F);
	            this.world.spawnEntity(entityImpact);
	         }
		}

		if (this.attackTime <= 0)
		{
			this.attackTime = 60;
		}
	}

    @Override
    public void onLivingUpdate()
    {
		if(this.getMechaLevel() >= 59)
		{
			List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityFriendMecha.class, this.getEntityBoundingBox().grow(32.0D), Predicates.<EntityLivingBase>and(EntitySelectors.NOT_SPECTATING));

			if(!list.isEmpty())
			{
				if(!this.world.isRemote)
				{
					list.forEach( entity ->{entity.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20, 0, false, false));} );
				}
			}

		}

    	super.onLivingUpdate();
    }
}