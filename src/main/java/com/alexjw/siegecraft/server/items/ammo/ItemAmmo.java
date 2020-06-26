package com.alexjw.siegecraft.server.items.ammo;

import com.alexjw.siegecraft.Siege;
import com.alexjw.siegecraft.SiegeTabs;
import com.alexjw.siegecraft.server.items.ModItems;
import net.minecraft.item.Item;

public class ItemAmmo extends Item {
    private int clipSize;
    private int reloadTime;

    public ItemAmmo(String unlocalizedName, int clipSize, int reloadTime) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(Siege.MODID, unlocalizedName);
        this.setCreativeTab(SiegeTabs.tabGadgets);
        this.clipSize = clipSize;
        this.reloadTime = reloadTime;
        ModItems.ITEMS.add(this);
    }

    public int getClipSize() {
        return clipSize;
    }

    public int getReloadTime() {
        return reloadTime;
    }
}
