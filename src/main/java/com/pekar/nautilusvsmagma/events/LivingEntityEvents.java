package com.pekar.nautilusvsmagma.events;

import com.pekar.nautilusvsmagma.Config;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.animal.nautilus.AbstractNautilus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class LivingEntityEvents implements IEventHandler
{
    @SubscribeEvent
    public void onLivingDamage(LivingIncomingDamageEvent event)
    {
        var entity = event.getEntity();
        if (!(entity instanceof AbstractNautilus nautilus) || !(entity.level() instanceof ServerLevel level)) return;
        var armorItem = nautilus.getBodyArmorItem();
        if (armorItem.isEmpty()) return;

        var damageSource = event.getSource();
        if (!damageSource.is(DamageTypes.HOT_FLOOR)) return;

        if (Config.isMagmaProtectionEnabledForArmor(armorItem))
        {
            event.setCanceled(true);

            var managedNautilus = AnimalManager.instance().getAnimal(nautilus.getUUID());
            if (managedNautilus == null)
            {
                AnimalManager.instance().addAnimal(nautilus);
                managedNautilus = AnimalManager.instance().getAnimal(nautilus.getUUID());
            }

            if (managedNautilus != null)
            {
                if (managedNautilus.every(20))
                {
                    var pos = nautilus.blockPosition();
                    level.sendParticles(
                            ParticleTypes.END_ROD,
                            pos.getX(), pos.getY(), pos.getZ(),
                            10,
                            0.5, 0.5, 0.5,
                            0.1
                    );

                    level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }
        }
    }
}