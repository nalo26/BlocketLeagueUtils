package dev.nalo.config;

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

import static dev.nalo.BlocketLeagueUtils.MOD_ID;

public class ModConfig {
    public float cameraSmooth = 0.2f; // lower = slower, smoother
    public float cameraOffset = 20f; // Pitch offset when ball cam is active to avoid cam hidden behind player
    public boolean showHUD = true;
    public boolean disableThirdPersonFront = true;

    // Saving only the string ID to JSON
    public String ballItemId = "minecraft:target";
    public transient Item ballItem = Registries.ITEM.get(new Identifier(ballItemId));

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

        // Convert string ID back to Item
        config.ballItem = Registries.ITEM.get(new Identifier(config.ballItemId));
        return config;
    }

    public void save() {
        // Update ID from Item before saving
        Identifier id = Registries.ITEM.getId(ballItem);
        if (id != null)
            ballItemId = id.toString();

        try (Writer writer = Files.newBufferedWriter(FILE)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(this, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
