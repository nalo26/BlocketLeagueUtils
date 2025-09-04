package dev.nalo.config;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.loader.api.FabricLoader;

import static dev.nalo.BlocketLeagueUtils.MOD_ID;

public class ModConfig {
    public float cameraSmooth = 0.2f; // lower = slower, smoother
    public float cameraOffset = 20f; // Pitch offset when ball cam is active to avoid cam hidden behind player
    public boolean showHUD = true;
    public boolean disableThirdPersonFront = true;

    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + ".json");

    public static ModConfig load() {
        if (Files.exists(FILE)) {
            try (Reader reader = Files.newBufferedReader(FILE)) {
                return new Gson().fromJson(reader, ModConfig.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ModConfig(); // defaults
    }

    public void save() {
        try (Writer writer = Files.newBufferedWriter(FILE)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(this, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
