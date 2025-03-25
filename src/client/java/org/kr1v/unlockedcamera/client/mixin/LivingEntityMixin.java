package org.kr1v.unlockedcamera.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.kr1v.unlockedcamera.client.UnlockedCameraConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @WrapOperation(
            method = "jump",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;sin(F)F"))
    public float sinWrap(float value, Operation<Float> original) {
        float normalizedPitch = ((this.getPitch() + 180) % 360 + 360) % 360 - 180;
        if ((normalizedPitch < -90 || normalizedPitch > 90) && UnlockedCameraConfigManager.getConfig().shouldInvertMovement)
            return original.call(-value);
        else return original.call(value);
    }
    @WrapOperation(
            method = "jump",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;cos(F)F"))
    public float cosWrap(float value, Operation<Float> original) {
        float normalizedPitch = ((this.getPitch() + 180) % 360 + 360) % 360 - 180;
        if ((normalizedPitch < -90 || normalizedPitch > 90) && UnlockedCameraConfigManager.getConfig().shouldInvertMovement)
            return -original.call(value);
        else return original.call(value);
    }
}
