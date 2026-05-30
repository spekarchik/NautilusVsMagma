package com.pekar.nautilusvsmagma.events;

import com.pekar.nautilusvsmagma.Config;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.nautilus.AbstractNautilus;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;

public class LivingEntityEvents
{
    public static void register()
    {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(LivingEntityEvents::allowDamage);
    }

    private static boolean allowDamage(LivingEntity entity, DamageSource damageSource, float amount)
    {
        if (!(entity instanceof AbstractNautilus nautilus) || !(entity.level() instanceof ServerLevel level)) return true;
        var armorItem = nautilus.getBodyArmorItem();
        if (armorItem.isEmpty()) return true;

        if (!damageSource.is(DamageTypes.HOT_FLOOR)) return true;

        if (Config.isMagmaProtectionEnabledForArmor(armorItem))
        {
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

            return false;
        }

        return true;
    }
}
