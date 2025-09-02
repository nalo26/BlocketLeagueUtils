package dev.nalo.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.client.option.Perspective;

@Mixin(Perspective.class)
public abstract class PerspectiveMixin {

    @Overwrite
    public Perspective next() {
        Perspective self = (Perspective) (Object) this;
        return switch (self) {
            case FIRST_PERSON -> Perspective.THIRD_PERSON_BACK;
            case THIRD_PERSON_BACK, THIRD_PERSON_FRONT -> Perspective.FIRST_PERSON;
        };
    }
}