package dev.nalo.events;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import static dev.nalo.BlocketLeagueUtilsClient.ballCamKeyBind;
import static dev.nalo.BlocketLeagueUtilsClient.ballEntity;
import static dev.nalo.BlocketLeagueUtilsClient.CONFIG;

public class HudRenderEvent implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (!CONFIG.showHUD || ballEntity == null)
            return;

        context.drawText(client.textRenderer, "⦿ Ball Cam", 12, 9, 0xFB5454, true);
        context.drawText(client.textRenderer,
                "Press [" + ballCamKeyBind.getBoundKeyLocalizedText().getString() + "] to toggle", 12, 19,
                0xffffff, true);
        context.drawGuiTexture(new Identifier("minecraft:toast/advancement"), 2, 2, 160, 32);
    }

}
