package org.kr1v.unlockedcamera.client.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.kr1v.unlockedcamera.client.UnlockedCameraConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    /**
     * @author kr1v
     * @reason implement swimming upside down movement and looking upside down movement
     */
    @Overwrite
    public void travel(Vec3d movementInput) {
        float normalizedPitch = ((this.getPitch() + 180) % 360 + 360) % 360 - 180;
        if (this.hasVehicle()) {
            super.travel(movementInput);
        } else {
            if ((normalizedPitch > 90.0F || normalizedPitch < -90.0F) &&
                    ((UnlockedCameraConfigManager.getConfig().shouldInvertMovementSwimming) && this.isSwimming())
                    || UnlockedCameraConfigManager.getConfig().shouldInvertMovement) {
                movementInput = movementInput.multiply(-1, 1, -1);
            }

            if (this.isSwimming()) {
                double d = getRotationVector().y;
                double e = d < -0.2 ? 0.085 : 0.06;
                if (d <= 0.0 || this.jumping || !this.world.getBlockState(new BlockPos(MathHelper.floor(this.getX()), MathHelper.floor(this.getY() + 1.0 - 0.1), MathHelper.floor(this.getZ()))).getFluidState().isEmpty()) {
                    Vec3d vec3d = this.getVelocity();
                    this.setVelocity(vec3d.add(0.0, (d - vec3d.y) * e, 0.0));
                }
            }
            if (((PlayerEntity)(Object)this).getAbilities().flying) {
                double d = this.getVelocity().y;
                super.travel(movementInput);
                this.setVelocity(this.getVelocity().withAxis(Direction.Axis.Y, d * 0.6));
            } else {
                super.travel(movementInput);
            }
        }
    }
}
