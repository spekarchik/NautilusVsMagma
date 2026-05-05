package com.pekar.nautilusvsmagma.events.animal;

import com.pekar.nautilusvsmagma.events.mob.Mob;
import net.minecraft.world.entity.animal.Animal;

public class ModAnimal extends Mob implements IAnimal
{
    private final Animal entity;

    public ModAnimal(Animal entity)
    {
        this.entity = entity;
    }

    @Override
    public Animal getEntity()
    {
        return entity;
    }
}
