package dev.nalo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.Entity;

import org.lwjgl.glfw.GLFW;

import dev.nalo.events.BallCamEvent;
import dev.nalo.events.HudRenderEvent;
import dev.nalo.config.ModConfig;

public class BlocketLeagueUtilsClient implements ClientModInitializer {

	public static ModConfig CONFIG;
	public static Entity ballEntity = null;

	@Override
	public void onInitializeClient() {
		CONFIG = ModConfig.load();
		WorldRenderEvents.START.register(new BallCamEvent());
		HudRenderCallback.EVENT.register(new HudRenderEvent());
	}

	public static KeyBinding ballCamKeyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.blocketleagueutils.ballcam", // translation key (lang file support)
			GLFW.GLFW_KEY_B, // default key: B
			"category.blocketleagueutils" // category in Controls menu
	));

}