package com.pekar.nautilusvsmagma;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;

import java.io.IOException;

public class Main implements ModInitializer
{
    public static final String MODID = "nautilusvsmagma";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize()
    {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        var configPath = FabricLoader.getInstance()
                .getConfigDir()
                .resolve("enchantonce-common.toml");

        try
        {
            Config.SPEC.load(configPath);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to load config", e);
        }
    }
}
