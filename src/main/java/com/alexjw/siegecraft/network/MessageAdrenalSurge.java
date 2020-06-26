package com.alexjw.siegecraft.network;

import com.alexjw.siegecraft.server.helper.SiegeHelper;
import com.alexjw.siegecraft.server.operators.Operator;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.List;

public class MessageAdrenalSurge implements IMessageHandler<MessageAdrenalSurge, IMessage>, IMessage {

    public MessageAdrenalSurge() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    @Override
    public IMessage onMessage(MessageAdrenalSurge message, MessageContext ctx) {
        if (ctx.side.isServer()) {
            List<EntityPlayer> onlinePlayers = ctx.getServerHandler().player.world.playerEntities;
            for (EntityPlayer attacker : onlinePlayers) {
                Operator operator = SiegeHelper.getOperator(attacker);
                if (operator != null) {
                    if (operator.getEnumTeam().equals(Operator.Team.ATTACKER)) {
                        attacker.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 200, 0));
                    }
                }
            }
        }

        return null;
    }
}

