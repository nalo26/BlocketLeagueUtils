package dev.nalo.events;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import static dev.nalo.BlocketLeagueUtilsClient.ballEntity;
import static dev.nalo.BlocketLeagueUtilsClient.CONFIG;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;

public class BallMarkerRendererEvent implements WorldRenderEvents.AfterEntities {

    private static final int CIRCLE_SEGMENTS = 64;

    @Override
    public void afterEntities(WorldRenderContext context) {
        if (!CONFIG.showBallMarker || ballEntity == null || ballEntity.isRemoved())
            return;

        renderBallGroundMarker(context);
    }

    private void renderBallGroundMarker(WorldRenderContext context) {
        MatrixStack matrices = context.matrixStack();
        Vec3d camPos = context.camera().getPos();

        double ex = ballEntity.getX();
        double ez = ballEntity.getZ();
        double ceilY = ballEntity.getWorld().getTopY(Heightmap.Type.WORLD_SURFACE, (int) ex, (int) ez);
        double floorY = raycastFloorY(ballEntity.getWorld(), ballEntity.getPos()) + 0.01; // avoid z-fighting
        double eyRatio = (ballEntity.getY() - (floorY + 2)) / (ceilY - (floorY + 2)); // 2 = ball radius

        float oOuterR = CONFIG.ballMarkerRadius + CONFIG.ballMarkerThickness * 0.5f;
        float oInnerR = CONFIG.ballMarkerRadius - CONFIG.ballMarkerThickness * 0.5f;
        float iOuterR = oInnerR * (1.0f - (float) eyRatio) - CONFIG.ballMarkerThickness;
        float iInnerR = oInnerR * (1.0f - (float) eyRatio) - CONFIG.ballMarkerThickness * 2;
        if (oInnerR < 0)
            oInnerR = 0;
        if (iOuterR < CONFIG.ballMarkerThickness)
            iOuterR = CONFIG.ballMarkerThickness;
        if (iInnerR < 0)
            iInnerR = 0;

        // Position relative to camera
        matrices.push();
        matrices.translate(ex - camPos.x, floorY - camPos.y, ez - camPos.z);

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.enableDepthTest();

        Matrix4f mat = matrices.peek().getPositionMatrix();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);

        // Outer circle vertices
        for (int i = 0; i <= CIRCLE_SEGMENTS; i++) {
            double angle = 2 * Math.PI * i / CIRCLE_SEGMENTS;
            float cos = (float) Math.cos(angle);
            float sin = (float) Math.sin(angle);

            float xOuter = cos * oOuterR;
            float zOuter = sin * oOuterR;
            float xInner = cos * oInnerR;
            float zInner = sin * oInnerR;

            // Outer vertex
            buffer.vertex(mat, xOuter, 0, zOuter)
                    .color(CONFIG.ballMarkerColor.getRGB())
                    .next();
            // Inner vertex
            buffer.vertex(mat, xInner, 0, zInner)
                    .color(CONFIG.ballMarkerColor.getRGB())
                    .next();
        }

        // Inner circle vertices (relative to ball height)
        for (int i = 0; i <= CIRCLE_SEGMENTS; i++) {
            double angle = 2 * Math.PI * i / CIRCLE_SEGMENTS;
            float cos = (float) Math.cos(angle);
            float sin = (float) Math.sin(angle);

            float xOuter = cos * iOuterR;
            float zOuter = sin * iOuterR;
            float xInner = cos * iInnerR;
            float zInner = sin * iInnerR;

            // Outer vertex
            buffer.vertex(mat, xOuter, 0, zOuter)
                    .color(CONFIG.ballMarkerColor.getRGB())
                    .next();
            // Inner vertex
            buffer.vertex(mat, xInner, 0, zInner)
                    .color(CONFIG.ballMarkerColor.getRGB())
                    .next();
        }

        tessellator.draw();
        matrices.pop();
    }

    private double raycastFloorY(World world, Vec3d startPos) {
        Vec3d endPos = startPos.add(0, -256, 0); // cast down 256 blocks
        BlockHitResult hit = world.raycast(new RaycastContext(
                startPos,
                endPos,
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                ShapeContext.absent()));

        if (hit.getType() == HitResult.Type.BLOCK) {
            return hit.getPos().y;
        }

        // fallback if nothing hit (usual flatworld height)
        return 56.0d;
    }

}
