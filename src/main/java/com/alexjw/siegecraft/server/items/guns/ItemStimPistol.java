package com.alexjw.siegecraft.server.items.guns;

import com.alexjw.siegecraft.network.MessageShoot;
import com.alexjw.siegecraft.network.SiegeNetworkHandler;
import com.alexjw.siegecraft.server.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemStimPistol extends ItemWeapon {
    public ItemStimPistol() {
        super("doc_stim_pistol", 0.35f, 10, ModItems.itemStim);
    }

    public boolean onLeftClick(ItemStack itemStack, EntityPlayer entityPlayer) {
        SiegeNetworkHandler.wrapper.sendToServer(new MessageShoot());
        return true;
    }
}