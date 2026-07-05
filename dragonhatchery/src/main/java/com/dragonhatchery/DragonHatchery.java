package com.dragonhatchery;

import com.dragonhatchery.entity.BabyEnderDragonEntity;
import com.dragonhatchery.entity.ModEntities;
import com.dragonhatchery.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dragon Hatchery
 *
 * Craft an "Ancient Dragonflame Charge" (dragon breath + blaze powder + nether star),
 * then right-click a placed Ender Dragon Egg block while holding it. The egg is consumed
 * and hatches into a tamed, flying Baby Ender Dragon companion bonded to you.
 */
public class DragonHatchery implements ModInitializer {
	public static final String MOD_ID = "dragonhatchery";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerItems();
		ModEntities.registerEntities();

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if (hand != InteractionHand.MAIN_HAND) {
				return InteractionResult.PASS;
			}

			BlockPos pos = hitResult.getBlockPos();
			if (!world.getBlockState(pos).is(Blocks.DRAGON_EGG)) {
				return InteractionResult.PASS;
			}

			ItemStack heldStack = player.getItemInHand(hand);
			if (!heldStack.is(ModItems.ANCIENT_DRAGONFLAME_CHARGE)) {
				return InteractionResult.PASS;
			}

			if (world.isClientSide) {
				return InteractionResult.SUCCESS;
			}

			ServerLevel serverLevel = (ServerLevel) world;
			serverLevel.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

			BabyEnderDragonEntity dragon = new BabyEnderDragonEntity(ModEntities.BABY_ENDER_DRAGON, serverLevel);
			dragon.moveTo(pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5, 0.0F, 0.0F);
			dragon.tameOnHatch(player);
			serverLevel.addFreshEntity(dragon);

			serverLevel.playSound(null, pos, SoundEvents.ENDER_DRAGON_GROWL, SoundSource.NEUTRAL, 1.0F, 1.4F);
			serverLevel.sendParticles(ParticleTypes.DRAGON_BREATH,
					pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
					40, 0.4, 0.4, 0.4, 0.02);

			if (!player.getAbilities().instabuild) {
				heldStack.shrink(1);
			}

			return InteractionResult.SUCCESS;
		});

		LOGGER.info("Dragon Hatchery initialized - the egg awaits a flame.");
	}
}
