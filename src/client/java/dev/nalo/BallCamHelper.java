package dev.nalo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.DisplayEntity.ItemDisplayEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import static dev.nalo.BlocketLeagueUtilsClient.CONFIG;

public class BallCamHelper {

    public static ItemDisplayEntity findBallEntity() {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;
        Identifier ball_item_id = Registries.ITEM.getId(CONFIG.ballItem);

        if (world == null)
            return null;

        for (Entity entity : world.getEntities()) {
            if (entity instanceof ItemDisplayEntity itemDisplay) {
                NbtCompound nbt = new NbtCompound();
                if (!itemDisplay.saveNbt(nbt))
                    continue;
                if (!nbt.contains("item"))
                    continue;
                NbtCompound itemNbt = nbt.getCompound("item");
                if (itemNbt.contains("id") && itemNbt.getString("id").equals(ball_item_id.toString()))
                    return itemDisplay;
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
        // offset for the ball to be slightly above view
        float targetPitch = (float) (-Math.toDegrees(Math.atan2(dy, distXZ)) + CONFIG.cameraOffset);

        float currentYaw = player.getYaw();
        float currentPitch = player.getPitch();

        // shortest path for yaw
        float deltaYaw = wrapDegrees(targetYaw - currentYaw);
        float deltaPitch = targetPitch - currentPitch;

        float newYaw = currentYaw + deltaYaw * CONFIG.cameraSmooth;
        float newPitch = currentPitch + deltaPitch * CONFIG.cameraSmooth;

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
