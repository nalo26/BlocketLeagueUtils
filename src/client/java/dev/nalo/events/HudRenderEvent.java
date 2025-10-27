package dev.nalo.events;

import static dev.nalo.BlocketLeagueUtilsClient.CONFIG;
import static dev.nalo.BlocketLeagueUtilsClient.ballCamEnabled;
import static dev.nalo.BlocketLeagueUtilsClient.ballCamKeyBind;
import static dev.nalo.BlocketLeagueUtilsClient.ballEntity;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class HudRenderEvent implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (!CONFIG.showHUD || !ballCamEnabled || ballEntity == null || client.getDebugHud().shouldShowDebugHud())
            return;

        context.drawText(client.textRenderer, "⦿ Ball Cam", 12, 9, 0xFB5454, true);
        context.drawText(client.textRenderer,
                "Press [" + ballCamKeyBind.getBoundKeyLocalizedText().getString() + "] to toggle", 12, 19,
                0xffffff, true);
        context.drawGuiTexture(new Identifier("minecraft:toast/advancement"), 2, 2, 160, 32);
    }

}
