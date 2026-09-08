package org.kr1v.unlockedcamera.client;

import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class UnlockedCameraConfigScreen implements ModMenuApi {
    public static ConfigBuilder getConfigBuilder() {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(Minecraft.getInstance().screen).setTitle(Component.translatable("unlockedcamera.title")).setSavingRunnable(UnlockedCameraConfigManager::save);
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("unlockedcamera.group.general"));
        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("unlockedcamera.enabled"), UnlockedCameraConfigManager.getConfig().enabled)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("unlockedcamera.enabled.description"))
                .setSaveConsumer(newValue -> UnlockedCameraConfigManager.getConfig().enabled = newValue)
                .build());
        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("unlockedcamera.shouldInvertMovement"), UnlockedCameraConfigManager.getConfig().shouldInvertMovement)
                .setDefaultValue(false)
                .setTooltip(Component.translatable("unlockedcamera.shouldInvertMovement.description"))
                .setSaveConsumer(newValue -> UnlockedCameraConfigManager.getConfig().shouldInvertMovement = newValue)
                .build());
        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("unlockedcamera.shouldInvertMovementSwimming"), UnlockedCameraConfigManager.getConfig().shouldInvertMovementSwimming)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("unlockedcamera.shouldInvertMovementSwimming.description"))
                .setSaveConsumer(newValue -> UnlockedCameraConfigManager.getConfig().shouldInvertMovementSwimming = newValue)
                .build());
        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("unlockedcamera.shouldInvertMouse"), UnlockedCameraConfigManager.getConfig().shouldInvertMouse)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("unlockedcamera.shouldInvertMouse.description"))
                .setSaveConsumer(newValue -> UnlockedCameraConfigManager.getConfig().shouldInvertMouse = newValue)
                .build());
        return builder;
    }
}