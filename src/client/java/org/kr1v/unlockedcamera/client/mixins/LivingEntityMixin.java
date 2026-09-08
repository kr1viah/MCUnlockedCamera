package org.kr1v.unlockedcamera.client.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.kr1v.unlockedcamera.client.UnlockedCameraConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @WrapOperation(method = "jumpFromGround", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;sin(F)F"))
    private float unlockedCamera$sinWrap(float value, Operation<Float> original) {
        int mul = 1;
        if (UnlockedCameraConfigManager.getConfig().enabled) {
            float normalizedPitch = ((((Entity) (Object) this).getXRot() + 180) % 360 + 360) % 360 - 180;
            if ((normalizedPitch < -90 || normalizedPitch > 90) && UnlockedCameraConfigManager.getConfig().shouldInvertMovement) {
                mul = -1;
            }
        }

        return original.call(value) * mul;
    }

    @WrapOperation(method = "jumpFromGround", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;cos(F)F"))
    private float unlockedCamera$cosWrap(float value, Operation<Float> original) {
        int mul = 1;
        if (UnlockedCameraConfigManager.getConfig().enabled) {
            float normalizedPitch = ((((Entity) (Object) this).getXRot() + 180) % 360 + 360) % 360 - 180;
            if ((normalizedPitch < -90 || normalizedPitch > 90) && UnlockedCameraConfigManager.getConfig().shouldInvertMovement) {
                mul = -1;
            }
        }

        return original.call(value) * mul;
    }
}
