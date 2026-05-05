package com.pekar.nautilusvsmagma.events;

import net.neoforged.neoforge.common.NeoForge;

public class EventRegistry
{
    public static void registerEvents()
    {
        register((AnimalManager)AnimalManager.instance());
        register(new LivingEntityEvents());
    }

    private static void register(IEventHandler eventHandler)
    {
        NeoForge.EVENT_BUS.register(eventHandler);
    }
}
