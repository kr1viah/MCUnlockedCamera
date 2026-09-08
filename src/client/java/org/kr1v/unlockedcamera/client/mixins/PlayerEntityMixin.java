package org.kr1v.unlockedcamera.client.mixins;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.kr1v.unlockedcamera.client.UnlockedCameraConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Player.class)
public abstract class PlayerEntityMixin {
    /**
     * @author kr1v
     * @reason implement swimming upside down movement and looking upside down movement
     */
    @ModifyVariable(method = "travel(Lnet/minecraft/world/phys/Vec3;)V", at = @At("HEAD"), argsOnly = true)
    public Vec3 unlockedCamera$invertMovement(Vec3 movementInput) {
        if (UnlockedCameraConfigManager.getConfig().enabled) {
            Player thiz = (Player) (Object) this;
            float normalizedPitch = ((thiz.getXRot() + 180) % 360 + 360) % 360 - 180;
            if ((normalizedPitch > 90.0F || normalizedPitch < -90.0F) &&
                    ((UnlockedCameraConfigManager.getConfig().shouldInvertMovementSwimming && thiz.isSwimming()) ||
                            UnlockedCameraConfigManager.getConfig().shouldInvertMovement)) {
                movementInput = movementInput.multiply(-1, 1, -1);
            }
        }

        return movementInput;
    }
}
