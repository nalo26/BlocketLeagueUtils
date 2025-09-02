package dev.nalo.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.nalo.BlocketLeagueUtilsClient.ballEntity;;

@Mixin(Mouse.class)
public class MouseMixin {
    @Inject(method = "updateMouse", at = @At("HEAD"), cancellable = true)
    private void updateMouse(CallbackInfo ci) {
        // If ball cam activated & player curently in game (no screen)
        if (ballEntity != null && MinecraftClient.getInstance().currentScreen == null) {
            ci.cancel();
        }
    }
}
