package com.krontixz.classicfeatures;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class ClassicFeaturesMod implements ModInitializer {

    @Override
    public void onInitialize() {
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (oldPlayer.getName().getString().equals("Notch")) {
                ItemEntity apple = new ItemEntity(
                    oldPlayer.getServerWorld(),
                    oldPlayer.getX(),
                    oldPlayer.getY(),
                    oldPlayer.getZ(),
                    new ItemStack(Items.APPLE)
                );
                oldPlayer.getServerWorld().spawnEntity(apple);
            }
        });

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!world.isClient() && hand == Hand.MAIN_HAND && entity instanceof SquidEntity) {
                ItemStack heldItem = player.getStackInHand(hand);
                if (heldItem.isOf(Items.BUCKET)) {
                    heldItem.decrement(1);
                    player.giveItemStack(new ItemStack(Items.MILK_BUCKET));
                    player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                    return ActionResult.SUCCESS;
                }
            }
            return ActionResult.PASS;
        });
    }
}
