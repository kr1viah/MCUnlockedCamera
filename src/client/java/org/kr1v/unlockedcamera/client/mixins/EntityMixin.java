package org.kr1v.unlockedcamera.client.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.kr1v.unlockedcamera.client.UnlockedCameraConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract float getXRot();

    @Shadow
    public abstract float getYRot();

    @Shadow
    public abstract void setYRot(float yaw);

    @Shadow
    public abstract void setXRot(float pitch);

    @Shadow
    public float yRotO;

    @Shadow
    public float xRotO;

    @Shadow
    @Nullable
    public abstract Entity getVehicle();

    /**
     * @author kr1v
     * @reason prevent pitch from clamping internally
     */
    @WrapOperation(method = "absRotateTo", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F"))
    private float unlockedCameras$normalizePitch(float value, float min, float max, Operation<Float> original, @Local(argsOnly = true, ordinal = 1) float pitch) {
        if (UnlockedCameraConfigManager.getConfig().enabled) {
            return ((pitch + 180) % 360 + 360) % 360 - 180;
        } else {
            return value;
        }
    }

    /**
     * @author kr1v
     * @reason invert horizontal mouse movement if upside down
     */
    @Inject(method = "turn", at = @At("HEAD"), cancellable = true)
    private void unlockedCamera$changeLookDirection(double cursorDeltaX, double cursorDeltaY, CallbackInfo ci) {
        if (UnlockedCameraConfigManager.getConfig().enabled) {
            ci.cancel(); // TODO/NOTE: I tried improving this code using a inject, could of been done better but should be fine for now
            float f = (float) cursorDeltaY * 0.15F;
            float g = (float) cursorDeltaX * 0.15F;

            // TODO/NOTE: __ This is the snippet thats most important __
            float normalizedPitch = ((this.getXRot() + 180) % 360 + 360) % 360 - 180;
            if ((normalizedPitch > 90 || normalizedPitch < -90) && UnlockedCameraConfigManager.getConfig().shouldInvertMouse) {
                this.setYRot(this.getYRot() - g);
                this.yRotO -= g;
            } else {
                this.setYRot(this.getYRot() + g);
                this.yRotO += g;
            }
            // TODO/NOTE: __ This is the snippet thats most important __

            this.setXRot(this.getXRot() + f);
            this.xRotO += f;
            if (this.getVehicle() != null) {
                this.getVehicle().onPassengerTurned((Entity) (Object) this);
            }
        }
    }

    /**
     * @author kr1v
     * @reason prevent pitch from clamping
     */
    @WrapOperation(method = "setXRot", at = @At(value = "INVOKE", target = "Ljava/lang/Math;clamp(FFF)F"))
    private float unlockedCamera$unlockPitch(float value, float min, float max, Operation<Float> original, @Local(argsOnly = true) float pitch) {
        if (UnlockedCameraConfigManager.getConfig().enabled) {
            return pitch;
        } else {
            return original.call(value, min, max);
        }
    }
}