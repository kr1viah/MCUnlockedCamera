package org.kr1v.unlockedcamera.client;

import org.lwjgl.glfw.GLFW;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

public class UnlockedCameraKeybindManager {
    private static KeyMapping toggleCameraUnlockedKey;

    public static void initializeKeybind() {
        toggleCameraUnlockedKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                    "unlockedcamera.toggleCameraUnlockedKey",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_UNKNOWN,
                    "unlockedcamera.key"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (toggleCameraUnlockedKey.wasPressed()) {
                UnlockedCameraConfigManager.getConfig().enabled = !UnlockedCameraConfigManager.getConfig().enabled;
            }
        });
    }
}
