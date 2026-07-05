package com.dragonhatchery.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import javax.annotation.Nullable;

/**
 * A small tamed dragon companion, hatched from a vanilla Ender Dragon Egg.
 * Flies freely, follows its owner, can be told to sit, and can be healed with food.
 */
public class BabyEnderDragonEntity extends TamableAnimal {

	public BabyEnderDragonEntity(EntityType<? extends BabyEnderDragonEntity> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new FlyingMoveControl(this, 15, true);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
		this.noCulling = true;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return TamableAnimal.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 30.0D)
				.add(Attributes.FLYING_SPEED, 0.6D)
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.FOLLOW_RANGE, 48.0D)
				.add(Attributes.ATTACK_DAMAGE, 4.0D);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0D, 8.0F, 3.0F, true));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);
		navigation.setCanOpenDoors(false);
		navigation.setCanFloat(true);
		return navigation;
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return stack.is(Items.ROTTEN_FLESH) || stack.is(Items.CHICKEN) || stack.is(Items.COOKED_CHICKEN);
	}

	@Nullable
	@Override
	public BabyEnderDragonEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
		return null;
	}

	/** Called right after the entity is spawned from a hatched egg. */
	public void tameOnHatch(Player player) {
		this.tame(player);
		this.setOwnerUUID(player.getUUID());
		this.setPersistenceRequired();
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (this.isTame() && this.isOwnedBy(player)) {
			if (!this.level().isClientSide) {
				if (isFood(stack) && this.getHealth() < this.getMaxHealth()) {
					this.heal(4.0F);
					if (!player.getAbilities().instabuild) {
						stack.shrink(1);
					}
					return InteractionResult.SUCCESS;
				}
				this.setOrderedToSit(!this.isOrderedToSit());
			}
			return InteractionResult.sidedSuccess(this.level().isClientSide);
		}

		return super.mobInteract(player, hand);
	}

	/** Used by the client renderer to animate wing flapping. */
	public float getFlapProgress(float partialTick) {
		return this.tickCount + partialTick;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENDER_DRAGON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.ENDER_DRAGON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENDER_DRAGON_DEATH;
	}

	@Override
	public float getVoicePitch() {
		return 1.5F;
	}
}
