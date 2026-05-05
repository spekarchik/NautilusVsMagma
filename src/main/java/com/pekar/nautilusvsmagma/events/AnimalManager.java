package com.pekar.nautilusvsmagma.events;

import com.pekar.nautilusvsmagma.events.animal.IAnimal;
import com.pekar.nautilusvsmagma.events.animal.ModAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AnimalManager implements IAnimalManager, IEventHandler
{
    private final Map<UUID, IAnimal> animals = new ConcurrentHashMap<>();
    private static final IAnimalManager instance;

    static
    {
        instance = new AnimalManager();
    }

    public static IAnimalManager instance()
    {
        return instance;
    }

    @Override
    public IAnimal getAnimal(UUID uuid)
    {
        return animals.get(uuid);
    }

    @Override
    public void addAnimal(Animal entity)
    {
        IAnimal animal = new ModAnimal(entity);
        animals.put(animal.getEntity().getUUID(), animal);
    }

    @SubscribeEvent
    public void onEntityLeaveLevelEvent(EntityLeaveLevelEvent event)
    {
        if (event.getLevel().isClientSide()) return;

        var entity = event.getEntity();
        animals.remove(entity.getUUID());
    }
}
