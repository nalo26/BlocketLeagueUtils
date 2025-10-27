package dev.nalo.config;

import static dev.nalo.BlocketLeagueUtils.MOD_ID;

import java.awt.Color;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ModConfig {
    public boolean disableThirdPersonFront = true;

    public float cameraSmooth = 0.2f; // lower = slower, smoother
    public float cameraOffset = 20f; // Pitch offset when ball cam is active to avoid cam hidden behind player
    public boolean showHUD = true;
    public String ballItemId = "minecraft:target"; // Saving only the string ID to JSON
    public transient Item ballItem = Registries.ITEM.get(new Identifier(ballItemId));

    public boolean showBallMarker = true;
    public float ballMarkerRadius = 2f;
    public float ballMarkerThickness = 0.2f;
    public String ballMarkerColorHex = "#FFFFFF"; // Saving only the string hex to JSON
    public transient Color ballMarkerColor = Color.WHITE;

    // The string identifier saved to JSON
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + ".json");

    public static ModConfig load() {
        ModConfig config;
        if (Files.exists(FILE)) {
            try (Reader reader = Files.newBufferedReader(FILE)) {
                config = new Gson().fromJson(reader, ModConfig.class);
            } catch (Exception e) {
                e.printStackTrace();
                config = new ModConfig();
            }
        } else
            config = new ModConfig();

        // Update transient fields after loading
        config.ballItem = Registries.ITEM.get(new Identifier(config.ballItemId));
        try {
            config.ballMarkerColor = Color.decode(config.ballMarkerColorHex);
        } catch (NumberFormatException e) {
            config.ballMarkerColor = Color.WHITE;
            config.ballMarkerColorHex = "#FFFFFF";
        }
        return config;
    }

    public void save() {
        // Update string identifiers before saving
        Identifier id = Registries.ITEM.getId(ballItem);
        if (id != null)
            ballItemId = id.toString();
        ballMarkerColorHex = String.format("#%06X", (0xFFFFFF & ballMarkerColor.getRGB()));

        try (Writer writer = Files.newBufferedWriter(FILE)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(this, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
