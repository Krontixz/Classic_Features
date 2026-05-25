package com.krontixz.classicfeatures.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class ClassicFeaturesClient implements ClientModInitializer {

    private static KeyBinding spawnCloneKey;
    public static final Identifier CLONE_SPAWN_PACKET = Identifier.of("classic_features", "spawn_clone");

    public record CloneSpawnPayload() implements CustomPayload {
        public static final Id<CloneSpawnPayload> ID = new Id<>(CLONE_SPAWN_PACKET);
        @Override
        public Id<CloneSpawnPayload> getId() { return ID; }
    }

    @Override
    public void onInitializeClient() {
        spawnCloneKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.classic_features.spawn_clone",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "category.classic_features.general"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (spawnCloneKey.wasPressed()) {
                if (client.player != null) {
                    ClientPlayNetworking.send(new CloneSpawnPayload());
                }
            }
        });
    }
}
