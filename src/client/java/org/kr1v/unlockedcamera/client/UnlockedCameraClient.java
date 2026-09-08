package org.kr1v.unlockedcamera.client;

import net.fabricmc.api.ClientModInitializer;

public class UnlockedCameraClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        UnlockedCameraConfigManager.initializeConfig();

        UnlockedCameraKeybindManager.initializeKeybind();
    }
}
