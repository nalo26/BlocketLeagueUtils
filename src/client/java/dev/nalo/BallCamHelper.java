package dev.nalo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.DisplayEntity.ItemDisplayEntity;
import net.minecraft.util.math.Vec3d;

public class BallCamHelper {

    public static ItemDisplayEntity findBallEntity() {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;

        if (world == null)
            return null;

        for (Entity entity : world.getEntities()) {
            if (entity instanceof ItemDisplayEntity itemDisplay) {
                // NbtCompound nbt = new NbtCompound();
                // if (itemDisplay.saveNbt(nbt)) {
                // if (nbt.contains("BallBlock")) {
                // return itemDisplay;
                // }

                // Alternative way to check for the BallBlock tag if added by command
                // }
                // if (itemDisplay.getCommandTags().contains("BallBlock")) {
                // return itemDisplay;
                // }

                // Neither of the above methods work
                return itemDisplay; // the only item display in the world is the ball
            }
        }
        return null;
    }

    public static void lookAtEntity(ClientPlayerEntity player, Entity target) {
        if (player == null || target == null)
            return;

        Vec3d playerPos = player.getCameraPosVec(1.0F);
        Vec3d targetPos = target.getBoundingBox().getCenter();

        double dx = targetPos.x - playerPos.x;
        double dy = targetPos.y - playerPos.y;
        double dz = targetPos.z - playerPos.z;

        double distXZ = Math.sqrt(dx * dx + dz * dz);

        float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0F);
        float pitch = (float) (-Math.toDegrees(Math.atan2(dy, distXZ)));

        player.setYaw(yaw);
        player.setPitch(pitch);
    }
}
