package dev.nalo.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.api.controller.ItemControllerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static dev.nalo.BlocketLeagueUtilsClient.CONFIG;

public class ScreenFactory implements ConfigScreenFactory<Screen> {

    @Override
    public Screen create(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("category.blocketleagueutils"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("category.blocketleagueutils.config"))
                        .option(Option.<Float>createBuilder()
                                .name(Text.translatable(
                                        "option.blocketleagueutils.camerasmooth.name"))
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
                                .name(Text.translatable(
                                        "option.blocketleagueutils.camerapitchdegree.name"))
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
                                .name(Text.translatable(
                                        "option.blocketleagueutils.showhud.name"))
                                .description(OptionDescription
                                        .of(Text.translatable(
                                                "option.blocketleagueutils.showhud.tooltip")))
                                .binding(true, () -> CONFIG.showHUD,
                                        newValue -> CONFIG.showHUD = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable(
                                        "option.blocketleagueutils.disablethirdpersonfront.name"))
                                .description(OptionDescription.of(
                                        Text.translatable(
                                                "option.blocketleagueutils.disablethirdpersonfront.tooltip")))
                                .binding(true, () -> CONFIG.disableThirdPersonFront,
                                        newValue -> CONFIG.disableThirdPersonFront = newValue)
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
                .save(CONFIG::save)
                .build()
                .generateScreen(parent);
    }

}
