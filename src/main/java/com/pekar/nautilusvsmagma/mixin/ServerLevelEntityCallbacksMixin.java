package com.pekar.nautilusvsmagma.mixin;

import com.pekar.nautilusvsmagma.events.AnimalManager;
import com.pekar.nautilusvsmagma.events.EntityLeaveLevelEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.server.level.ServerLevel$EntityCallbacks")
public class ServerLevelEntityCallbacksMixin
{
    @Shadow
    @Final
    private ServerLevel this$0;

    @Inject(method = "onTrackingEnd(Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"))
    private void nautilusvsmagma$onTrackingEnd(Entity entity, CallbackInfo ci)
    {
        AnimalManager.onEntityLeaveLevelEvent(new EntityLeaveLevelEvent(entity, this$0));
    }
}
