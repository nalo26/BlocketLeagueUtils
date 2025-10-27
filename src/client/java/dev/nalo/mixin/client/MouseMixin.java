package dev.nalo.mixin.client;

import static dev.nalo.BlocketLeagueUtilsClient.ballCamEnabled;
import static dev.nalo.BlocketLeagueUtilsClient.ballEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;

@Mixin(Mouse.class)
public class MouseMixin {
    @Shadow
    private double cursorDeltaX;
    @Shadow
    private double cursorDeltaY;

    @Inject(method = "updateMouse", at = @At("HEAD"), cancellable = true)
    private void updateMouse(CallbackInfo ci) {
        // If ball cam activated & player curently in game (no screen)
        if (ballCamEnabled && ballEntity != null && MinecraftClient.getInstance().currentScreen == null) {
            this.cursorDeltaX = 0.0;
            this.cursorDeltaY = 0.0;
            ci.cancel();
        }
    }
}
