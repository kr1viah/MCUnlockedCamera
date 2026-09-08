package org.kr1v.unlockedcamera.client.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.model.HumanoidModel;
import org.joml.Math;
import org.kr1v.unlockedcamera.client.UnlockedCameraConfigManager;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HumanoidModel.class)
public abstract class BipedEntityModelMixin {
    /**
     * @author kr1v
     * @reason prevent arms going crazy when punching
     */
    @ModifyExpressionValue(method = "setupAttackAnimation", at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/geom/ModelPart;xRot:F", opcode = Opcodes.GETFIELD))
    private float unlockedCamera$modifyArmPitch(float pitch) {
        final float PI = (float) Math.PI;
        if (UnlockedCameraConfigManager.getConfig().enabled) {
            float normalizedPitch = ((pitch + PI) % (2 * PI) + (2 * PI)) % (2 * PI) - PI;
            if (normalizedPitch < -PI / 2) {
                return -PI - normalizedPitch;
            } else if (normalizedPitch > PI / 2) {
                return PI - normalizedPitch;
            } else {
                return normalizedPitch;
            }
        }

        return pitch;
    }
}
