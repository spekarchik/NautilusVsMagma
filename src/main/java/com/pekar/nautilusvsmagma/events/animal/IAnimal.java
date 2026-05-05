package com.pekar.nautilusvsmagma.events.animal;

import com.pekar.nautilusvsmagma.events.mob.IMob;
import net.minecraft.world.entity.animal.Animal;

public interface IAnimal extends IMob
{
    Animal getEntity();
}
