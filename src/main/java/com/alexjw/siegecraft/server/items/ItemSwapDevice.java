package com.alexjw.siegecraft.server.items;

import com.alexjw.siegecraft.Siege;
import com.alexjw.siegecraft.SiegeTabs;
import com.alexjw.siegecraft.server.helper.SiegeHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemSwapDevice extends Item {
    public ItemSwapDevice() {
        super();
        this.setUnlocalizedName("swap_device");
        this.setRegistryName(Siege.MODID, "swap_device");
        this.setCreativeTab(SiegeTabs.tabGadgets);
        this.setMaxStackSize(1);
        ModItems.ITEMS.add(this);
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
        ItemStack itemstack = player.getHeldItem(handIn);
        SiegeHelper.stopDroning(player, world);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }
}