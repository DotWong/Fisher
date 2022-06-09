package com.dot.fisher;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.*;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.item.*;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(FisherMod.MOD_ID)
public class FisherMod
{
    public static final String MOD_ID = "fishermod";
    private Minecraft mc;
    private long nextTime;


    public FisherMod() {
        mc = Minecraft.getInstance();
        nextTime = -1L;

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (/*mc.world != null && */mc.player != null) {
                

                if (event.player.getMainHandItem().getItem() == Items.FISHING_ROD) {
                    if ( (nextTime > 0) && (System.currentTimeMillis() > nextTime) ) {
                        // reset timer
                        nextTime = -1L;

                        // right click
                        KeyMapping.click(mc.options.keyUse.getKey());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlaySound(PlaySoundEvent event) {
        LocalPlayer p = mc.player;

        if (p != null)
        {
            // sound name
            if ( !p.getMainHandItem().isEmpty() && (p.getMainHandItem().getItem() == Items.FISHING_ROD) && event.getName().equals("entity.fishing_bobber.splash") ) {
                // right click
                KeyMapping.click(mc.options.keyUse.getKey());

                // timer set after 1 sec
                nextTime = System.currentTimeMillis() + 1000L;
            }
        }
    }
}
