package org.kr1v.unlockedcamera.client.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.kr1v.unlockedcamera.client.UnlockedCameraConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    /**
     * @return a
     * @author kr1v
     * @reason implement swimming upside down movement and looking upside down movement
     */
    @ModifyVariable(method = "travel(Lnet/minecraft/util/math/Vec3d;)V", at = @At("HEAD"))
    public Vec3d invertMovement(Vec3d movementInput) {
        float normalizedPitch = ((this.getPitch() + 180) % 360 + 360) % 360 - 180;
        if ((normalizedPitch > 90.0F || normalizedPitch < -90.0F) &&
                ((UnlockedCameraConfigManager.getConfig().shouldInvertMovementSwimming && this.isSwimming())
                        || UnlockedCameraConfigManager.getConfig().shouldInvertMovement)) {
            movementInput = movementInput.multiply(-1, 1, -1);
        }
        return movementInput;
    }
}
