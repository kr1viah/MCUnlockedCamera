package org.kr1v.unlockedcamera.client.mixin;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.kr1v.unlockedcamera.client.UnlockedCameraConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    protected abstract float getJumpVelocity();
    /**
     * @author kr1v
     * @reason make jumping while looking upside down speed you up instead of slow you down
     */
    @VisibleForTesting
    @Overwrite
    public void jump() {

        float f = this.getJumpVelocity();
        if (!(f <= 1.0E-5F)) {
            Vec3d vec3d = this.getVelocity();
            this.setVelocity(vec3d.x, Math.max(f, vec3d.y), vec3d.z);

            if (this.isSprinting()) {
                float normalizedPitch = ((this.getPitch() + 180) % 360 + 360) % 360 - 180;
                if ((normalizedPitch < -90 || normalizedPitch > 90) && UnlockedCameraConfigManager.getConfig().shouldInvertMovement) {
                    float g = this.getYaw() * (float) (Math.PI / 180.0);
                    this.addVelocity(new Vec3d(-MathHelper.sin(g) * -0.2, 0.0, MathHelper.cos(g) * -0.2));
                } else {
                    float g = this.getYaw() * (float) (Math.PI / 180.0);
                    this.addVelocity(new Vec3d(-MathHelper.sin(g) * 0.2, 0.0, MathHelper.cos(g) * 0.2));
                }
            }

            this.velocityDirty = true;
        }
    }
}
