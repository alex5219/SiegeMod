package com.alexjw.siegecraft.server.event;

import com.alexjw.siegecraft.Siege;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = Siege.MODID)
public class RappelEvent {

    @SubscribeEvent
    public static void onPlayerCollide(TickEvent.PlayerTickEvent event) {

    }
}
