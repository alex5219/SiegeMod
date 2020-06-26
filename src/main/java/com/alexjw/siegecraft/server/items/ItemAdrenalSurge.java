package com.alexjw.siegecraft.server.items;

import com.alexjw.siegecraft.Siege;
import com.alexjw.siegecraft.SiegeTabs;
import com.alexjw.siegecraft.network.MessageAdrenalSurge;
import com.alexjw.siegecraft.network.SiegeNetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemAdrenalSurge extends Item {
    public ItemAdrenalSurge() {
        super();
        this.setUnlocalizedName("adrenal_surge");
        this.setRegistryName(Siege.MODID, "adrenal_surge");
        this.setCreativeTab(SiegeTabs.tabGadgets);
        ModItems.ITEMS.add(this);
    }

    public boolean shouldCauseReequipAnimation(ItemStack itemStack, ItemStack itemStack1, boolean b) {
        return false;
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityPlayer, EnumHand enumHand) {
        ItemStack itemStack = entityPlayer.getHeldItem(enumHand);
        if (!world.isRemote) {
            if (!entityPlayer.getCooldownTracker().hasCooldown(this)) {
                if (!entityPlayer.isCreative()) {
                    itemStack.shrink(1);
                }
                SiegeNetworkHandler.wrapper.sendToServer(new MessageAdrenalSurge());
                entityPlayer.getCooldownTracker().setCooldown(this, 45);
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);
    }
}
