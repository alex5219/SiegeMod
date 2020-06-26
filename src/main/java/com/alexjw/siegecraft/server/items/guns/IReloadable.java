package com.alexjw.siegecraft.server.items.guns;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IReloadable {
    void attemptReload(EntityPlayer entityPlayer, ItemStack itemStack);
}
