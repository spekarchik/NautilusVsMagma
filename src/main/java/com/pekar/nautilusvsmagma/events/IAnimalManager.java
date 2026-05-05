package com.pekar.nautilusvsmagma.events;

import com.pekar.nautilusvsmagma.events.animal.IAnimal;
import net.minecraft.world.entity.animal.Animal;

import java.util.UUID;

public interface IAnimalManager
{
    IAnimal getAnimal(UUID uuid);
    void addAnimal(Animal animal);
}
