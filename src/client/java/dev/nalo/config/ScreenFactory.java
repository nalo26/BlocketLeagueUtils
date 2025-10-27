package dev.nalo.config;

import static dev.nalo.BlocketLeagueUtilsClient.CONFIG;

import java.awt.Color;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.ItemControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ScreenFactory implements ConfigScreenFactory<Screen> {

    @Override
    public Screen create(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("category.blocketleagueutils"))
                .category(ConfigCategory.createBuilder()
                        // Ball Cam settings
                        .name(Text.translatable("category.blocketleagueutils.config.ballcam"))
                        .option(Option.<Float>createBuilder()
                                .name(Text.translatable("option.blocketleagueutils.camerasmooth.name"))
                                .description(OptionDescription.of(
                                        Text.translatable(
                                                "option.blocketleagueutils.camerasmooth.tooltip")))
                                .binding(0.2f, () -> CONFIG.cameraSmooth,
                                        newValue -> CONFIG.cameraSmooth = newValue)
                                .controller(opt -> FloatSliderControllerBuilder
                                        .create(opt)
                                        .range(0f, 1f)
                                        .step(0.01f))
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Text.translatable("option.blocketleagueutils.camerapitchdegree.name"))
                                .description(OptionDescription.of(
                                        Text.translatable(
                                                "option.blocketleagueutils.camerapitchdegree.tooltip")))
                                .binding(20f, () -> CONFIG.cameraOffset,
                                        newValue -> CONFIG.cameraOffset = newValue)
                                .controller(opt -> FloatSliderControllerBuilder
                                        .create(opt)
                                        .range(-90f, 90f)
                                        .step(1f))
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("option.blocketleagueutils.showhud.name"))
                                .description(OptionDescription
                                        .of(Text.translatable(
                                                "option.blocketleagueutils.showhud.tooltip")))
                                .binding(true, () -> CONFIG.showHUD,
                                        newValue -> CONFIG.showHUD = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Item>createBuilder()
                                .name(Text.translatable("option.blocketleagueutils.ballitem.name"))
                                .description(OptionDescription
                                        .of(Text.translatable("option.blocketleagueutils.ballitem.tooltip")))
                                .binding(Registries.ITEM.get(new Identifier("minecraft:target")),
                                        () -> CONFIG.ballItem,
                                        newValue -> CONFIG.ballItem = newValue)
                                .controller(ItemControllerBuilder::create)
                                .build())
                        .build())

                .category(ConfigCategory.createBuilder()
                        // Ball Marker settings
                        .name(Text.translatable("category.blocketleagueutils.config.ballmarker"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("option.blocketleagueutils.showballmarker.name"))
                                .description(OptionDescription
                                        .of(Text.translatable("option.blocketleagueutils.showballmarker.tooltip")))
                                .binding(true, () -> CONFIG.showBallMarker,
                                        newValue -> CONFIG.showBallMarker = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Text.translatable("option.blocketleagueutils.ballmarkerradius.name"))
                                .description(OptionDescription
                                        .of(Text.translatable("option.blocketleagueutils.ballmarkerradius.tooltip")))
                                .binding(2f, () -> CONFIG.ballMarkerRadius,
                                        newValue -> CONFIG.ballMarkerRadius = newValue)
                                .controller(opt -> FloatSliderControllerBuilder
                                        .create(opt)
                                        .range(0.1f, 10f)
                                        .step(0.1f))
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Text.translatable("option.blocketleagueutils.ballmarkerthickness.name"))
                                .description(OptionDescription
                                        .of(Text.translatable("option.blocketleagueutils.ballmarkerthickness.tooltip")))
                                .binding(0.2f, () -> CONFIG.ballMarkerThickness,
                                        newValue -> CONFIG.ballMarkerThickness = newValue)
                                .controller(opt -> FloatSliderControllerBuilder
                                        .create(opt)
                                        .range(0.05f, 5f)
                                        .step(0.05f))
                                .build())
                        .option(Option.<Color>createBuilder()
                                .name(Text.translatable("option.blocketleagueutils.ballmarkercolor.name"))
                                .description(OptionDescription
                                        .of(Text.translatable("option.blocketleagueutils.ballmarkercolor.tooltip")))
                                .binding(Color.WHITE, () -> CONFIG.ballMarkerColor,
                                        newValue -> CONFIG.ballMarkerColor = newValue)
                                .controller(ColorControllerBuilder::create)
                                .build())
                        .build())

                .category(ConfigCategory.createBuilder()
                        // QoL settings
                        .name(Text.translatable("category.blocketleagueutils.config.qol"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("option.blocketleagueutils.disablethirdpersonfront.name"))
                                .description(OptionDescription.of(
                                        Text.translatable(
                                                "option.blocketleagueutils.disablethirdpersonfront.tooltip")))
                                .binding(true, () -> CONFIG.disableThirdPersonFront,
                                        newValue -> CONFIG.disableThirdPersonFront = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .build())
                .save(CONFIG::save)
                .build()
                .generateScreen(parent);
    }

}
