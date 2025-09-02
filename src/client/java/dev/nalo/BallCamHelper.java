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

    public static void smoothLookAtEntity(ClientPlayerEntity player, Entity target) {
        if (player == null || target == null)
            return;

        Vec3d playerPos = player.getCameraPosVec(1.0F);
        Vec3d targetPos = target.getBoundingBox().getCenter();

        double dx = targetPos.x - playerPos.x;
        double dy = targetPos.y - playerPos.y;
        double dz = targetPos.z - playerPos.z;

        double distXZ = Math.sqrt(dx * dx + dz * dz);

        float targetYaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0F);
        // 20 degrees offset for the ball to be slightly above view
        float targetPitch = (float) (-Math.toDegrees(Math.atan2(dy, distXZ)) + 20.0F);

        float currentYaw = player.getYaw();
        float currentPitch = player.getPitch();

        // shortest path for yaw
        float deltaYaw = wrapDegrees(targetYaw - currentYaw);
        float deltaPitch = targetPitch - currentPitch;

        // smoothing factor (0 < f <= 1)
        float factor = 0.2F; // lower = slower, smoother

        float newYaw = currentYaw + deltaYaw * factor;
        float newPitch = currentPitch + deltaPitch * factor;

        player.setYaw(newYaw);
        player.setPitch(newPitch);
    }

    private static float wrapDegrees(float value) {
        value = value % 360.0F;
        if (value >= 180.0F)
            value -= 360.0F;
        if (value < -180.0F)
            value += 360.0F;
        return value;
    }
}
