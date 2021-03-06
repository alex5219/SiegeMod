package com.alexjw.siegecraft.server.entity.operator;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityThatcher extends EntityDummy {
    protected ItemStack[] dummyInventory;

    public EntityThatcher(World worldIn) {
        super(worldIn);
    }

    public EntityThatcher(World worldIn, ItemStack[] containerPlayer) {
        super(worldIn);
        this.dummyInventory = containerPlayer;
    }

    public ItemStack[] getDummyInventory() {
        return dummyInventory;
    }

    public void setDummyInventory(ItemStack[] dummyInventory) {
        this.dummyInventory = dummyInventory;
    }
}
