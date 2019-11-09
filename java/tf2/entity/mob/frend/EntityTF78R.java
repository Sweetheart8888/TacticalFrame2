package tf2.entity.mob.frend;

import java.util.List;

import com.google.common.base.Predicates;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import tf2.TFItems;

public class EntityTF78R extends EntityGynoid
{
	private static final double defaultDamage = 5;
	private static final double upAttack = 0.083;
	private static final double upArmor = 0.176;
	private static final double upArmorToughness = 0.028;
	private static final double upMaxHealth = 2.1;

	private static final double defaultArmor = 1.0D;
	private static final double defaultArmorToughness = 0.0D;
	private static final double defaultMaxHealth = 20.0D;

	private int jumpTime;

	public EntityTF78R(World worldIn)
	{
		super(worldIn, defaultDamage, upAttack, defaultArmor, upArmor, defaultArmorToughness, upArmorToughness, defaultMaxHealth, upMaxHealth);
	}

	protected void initEntityAI()
	{
		super.initEntityAI();
		this.tasks.addTask(3, new EntityFriendMecha.EntityAIAttackRangedGunFriendMecha(this, 1.25D, 10.0F));
	}

    @Override
	public void isUpLevel()
    {
    	super.isUpLevel();
    	this.getUniqueSkill(59);

    	if(this.getMechaLevel() == 19)
    	{
    		this.getInventoryMechaEquipment().setHasSkill(new ItemStack(TFItems.SKILL_ENCHANTFLAME));
    	}

    	if(this.getMechaLevel() == 39)
    	{
    		this.getInventoryMechaEquipment().setHasSkill(new ItemStack(TFItems.SKILL_FIREPROTECTION));
    	}
    }

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
	{
		double dx = target.posX - this.posX;
		double dy = target.posY - this.posY;
		double dz = target.posZ - this.posZ;

        float distance = this.getDistance(target);

		if (this.attackTime <= 20 && this.attackTime % 4 == 0 && (this.onGround || this.inWater))
		{
			double f2 = MathHelper.sqrt(dx * dx + dz * dz);
			double y1 = Math.max(dy, 0D);
			double y2 = MathHelper.sqrt(y1 * y1);
			double y3 = Math.min(y2, 10D);

			this.motionX = (dx / f2) * MathHelper.sqrt(f2) * 1.1F;
			this.motionZ = (dz / f2) * MathHelper.sqrt(f2) * 1.1F;
			this.motionY = (0.14F * y3) + 0.41F;
			this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.5F, 1.5F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		}

        if(distance < 1.5F)
        {
        	target.attackEntityFrom(DamageSource.causeMobDamage(this), this.getMechaATK());
        }

		if (this.attackTime <= 0)
		{
			this.attackTime = 80;
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
					list.forEach( entity ->{entity.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20, 0, false, false));} );
				}
			}

		}

    	super.onLivingUpdate();
    }

}
