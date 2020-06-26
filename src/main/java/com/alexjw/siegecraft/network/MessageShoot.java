package com.alexjw.siegecraft.network;

import com.alexjw.siegecraft.server.items.guns.ItemWeapon;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageShoot implements IMessageHandler<MessageShoot, IMessage>, IMessage {

    public MessageShoot() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    @Override
    public IMessage onMessage(MessageShoot message, MessageContext ctx) {
        if (ctx.side.isServer()) {
            EntityPlayer entityPlayer = ctx.getServerHandler().player;
            if (entityPlayer.getHeldItemMainhand().getItem() instanceof ItemWeapon) {
                ItemWeapon itemWeapon = (ItemWeapon) entityPlayer.getHeldItemMainhand().getItem();
                itemWeapon.onServerFire(entityPlayer.getHeldItemMainhand(), entityPlayer);
            }
        }

        return null;
    }
}

