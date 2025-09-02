package dev.nalo.events;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

import dev.nalo.BallCamHelper;
import static dev.nalo.BlocketLeagueBallCamClient.ballEntity;
import static dev.nalo.BlocketLeagueBallCamClient.ballCamKeyBind;

public class BallCamEvent implements WorldRenderEvents.Start {

    @Override
    public void onStart(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        if (player == null || client.world == null) {
            ballEntity = null;
            return;
        }

        if (ballCamKeyBind.wasPressed()) {
            if (ballEntity != null) { // Disable ball cam
                ballEntity = null;
                player.setYaw(player.getBodyYaw());
                player.setPitch(0);
            } else { // Enable ball cam
                ballEntity = BallCamHelper.findBallEntity();
                // client.player.sendMessage(Text.literal("Looking at the ball!"));
            }
        }

        if (ballEntity == null)
            return;
        BallCamHelper.lookAtEntity(client.player, ballEntity);
    }
}