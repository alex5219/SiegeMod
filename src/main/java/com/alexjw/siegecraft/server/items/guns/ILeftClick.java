package com.alexjw.siegecraft.server.items.guns;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ILeftClick {
    boolean onLeftClick(ItemStack itemStack, EntityPlayer entityPlayer);

    void onServerFire(ItemStack itemStack, EntityPlayer entityPlayer);
}
