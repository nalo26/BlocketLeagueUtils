package dev.nalo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.Entity;

import org.lwjgl.glfw.GLFW;

import dev.nalo.events.BallCamEvent;

public class BlocketLeagueUtilsClient implements ClientModInitializer {

	public static Entity ballEntity = null;

	@Override
	public void onInitializeClient() {
		WorldRenderEvents.START.register(new BallCamEvent());
	}

	public static KeyBinding ballCamKeyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.blocketleagueutils.ballcam", // translation key (lang file support)
			GLFW.GLFW_KEY_B, // default key: B
			"category.blocketleagueutils" // category in Controls menu
	));

}