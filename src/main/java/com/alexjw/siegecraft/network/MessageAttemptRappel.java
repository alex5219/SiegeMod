package com.alexjw.siegecraft.network;

import com.alexjw.siegecraft.server.blocks.ModBlocks;
import com.alexjw.siegecraft.server.data.SiegeData;
import com.alexjw.siegecraft.server.helper.SiegeHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;

public class MessageAttemptRappel implements IMessageHandler<MessageAttemptRappel, IMessage>, IMessage {
    public MessageAttemptRappel() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    @Override
    public IMessage onMessage(MessageAttemptRappel message, MessageContext ctx) {
        if (ctx.side.isServer()) {
            EntityPlayer entityPlayer = ctx.getServerHandler().player;
            if (entityPlayer != null) {
                if (SiegeData.rappelList.get(entityPlayer) != null) {
                    if (SiegeHelper.canRappel(entityPlayer) || SiegeData.rappelList.get(entityPlayer).size() > 1) {
                        if (!(SiegeData.rappelList.get(entityPlayer).size() > 1)) {
                            addRope(entityPlayer);
                        } else {
                            deleteRope(entityPlayer);
                        }
                    } else {
                        addRope(entityPlayer);
                    }
                } else if (SiegeHelper.canRappel(entityPlayer)) {
                    addRope(entityPlayer);
                }
            }
        }
        return null;
    }

    private void addRope(EntityPlayer entityPlayer) {
        ArrayList<BlockPos> rappelPos;
        if (SiegeData.rappelList.get(entityPlayer) != null) {
            rappelPos = SiegeData.rappelList.get(entityPlayer);
        } else {
            rappelPos = new ArrayList<>();
        }
        for (int y = 0; y < SiegeHelper.getRappelHeight(entityPlayer); y++) {
            BlockPos newBlockPos = new BlockPos(entityPlayer.posX, entityPlayer.posY + y, entityPlayer.posZ);
            entityPlayer.world.setBlockState(newBlockPos, ModBlocks.blockRope.getState(entityPlayer.getHorizontalFacing()));
            rappelPos.add(newBlockPos);
        }
        SiegeData.rappelList.put(entityPlayer, rappelPos);
    }

    private void deleteRope(EntityPlayer entityPlayer) {
        ArrayList<BlockPos> blockPos;
        if (SiegeData.rappelList.get(entityPlayer) != null) {
            blockPos = SiegeData.rappelList.get(entityPlayer);
        } else {
            blockPos = new ArrayList<>();
        }
        for (BlockPos blockPos1 : blockPos) {
            entityPlayer.world.setBlockToAir(blockPos1);
        }
        SiegeData.rappelList.put(entityPlayer, new ArrayList<BlockPos>());
    }
}

