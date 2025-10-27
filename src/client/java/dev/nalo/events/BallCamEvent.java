package dev.nalo.events;

import static dev.nalo.BlocketLeagueUtilsClient.ballCamEnabled;
import static dev.nalo.BlocketLeagueUtilsClient.ballCamKeyBind;
import static dev.nalo.BlocketLeagueUtilsClient.ballEntity;

import dev.nalo.BallCamHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;;

public class BallCamEvent implements WorldRenderEvents.Start {

    @Override
    public void onStart(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        if (player == null || client.world == null) {
            ballEntity = null;
            return;
        }

        if (ballCamKeyBind.wasPressed())
            ballCamEnabled = !ballCamEnabled;

        if (ballEntity == null || ballEntity.isRemoved())
            ballEntity = BallCamHelper.findBallEntity();

        if (!ballCamEnabled || ballEntity == null)
            return;

        BallCamHelper.smoothLookAtEntity(client.player, ballEntity);
    }
}