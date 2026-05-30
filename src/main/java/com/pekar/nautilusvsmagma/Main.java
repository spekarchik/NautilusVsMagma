package com.pekar.nautilusvsmagma;

import com.mojang.logging.LogUtils;
import com.pekar.nautilusvsmagma.events.EventRegistry;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;

public class Main implements ModInitializer
{
    public static final String MODID = "nautilusvsmagma";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize()
    {
        EventRegistry.registerEvents();
    }
}
