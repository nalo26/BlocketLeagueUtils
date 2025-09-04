package dev.nalo.mixin.client;

import static dev.nalo.BlocketLeagueUtilsClient.CONFIG;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.client.option.Perspective;

@Mixin(Perspective.class)
public abstract class PerspectiveMixin {

    /**
     * Disable third person front view if configured
     */
    @Overwrite
    public Perspective next() {
        Perspective self = (Perspective) (Object) this;
        if (CONFIG.disableThirdPersonFront) {
            return switch (self) {
                case FIRST_PERSON -> Perspective.THIRD_PERSON_BACK;
                case THIRD_PERSON_BACK, THIRD_PERSON_FRONT -> Perspective.FIRST_PERSON;
            };
        }
        // (recoded) Default behavior if not disabled
        return switch (self) {
            case FIRST_PERSON -> Perspective.THIRD_PERSON_BACK;
            case THIRD_PERSON_BACK -> Perspective.THIRD_PERSON_FRONT;
            case THIRD_PERSON_FRONT -> Perspective.FIRST_PERSON;
        };
    }
}