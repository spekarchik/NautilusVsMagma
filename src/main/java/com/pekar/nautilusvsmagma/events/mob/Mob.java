package com.pekar.nautilusvsmagma.events.mob;

public abstract class Mob implements IMob
{
    private int tickCounter = 0;

    @Override
    public boolean every(int throttling)
    {
        tickCounter = (tickCounter + 1) % throttling;
        return tickCounter == 0;
    }
}
