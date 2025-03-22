package org.kr1v.unlockedcamera.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Arm;
import org.joml.Math;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> extends AnimalModel<T> implements ModelWithArms, ModelWithHead {

    @Unique
    private static final float pi = (float)Math.PI;
    /**
     * @author kr1v
     * @reason prevent arms going crazy when punching
     */
    @ModifyExpressionValue(method = "animateArms",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/model/ModelPart;pitch:F",
                    opcode = Opcodes.GETFIELD
            ))
    protected float Injected(float pitch) {
        float normalizedPitch = ((pitch + pi) % (2 * pi) + (2 * pi)) % (2 * pi) - pi;
        if (normalizedPitch < -pi/2) return -pi-normalizedPitch;
        else if (normalizedPitch > pi/2) return pi-normalizedPitch;
        return normalizedPitch;
    }

    // functions for extending stuff

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {

    }

    /**
     * Gets the head model part.
     *
     * @return the head
     */
    @Override
    public ModelPart getHead() {
        return null;
    }

}
