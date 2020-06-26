package com.alexjw.siegecraft.server.helper;

import com.alexjw.siegecraft.server.blocks.ModBlocks;
import com.alexjw.siegecraft.server.data.SiegeData;
import com.alexjw.siegecraft.server.data.SiegePlayer;
import com.alexjw.siegecraft.server.entity.EntityCamera;
import com.alexjw.siegecraft.server.entity.EntityDrone;
import com.alexjw.siegecraft.server.entity.operator.*;
import com.alexjw.siegecraft.server.items.ModItems;
import com.alexjw.siegecraft.server.items.armor.ItemDroneArmor;
import com.alexjw.siegecraft.server.items.armor.ItemOperatorArmor;
import com.alexjw.siegecraft.server.operators.Operator;
import com.alexjw.siegecraft.server.operators.OperatorManager;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class SiegeHelper {
    public static boolean hasAmmo(EntityPlayer entityPlayer) {
        return findAmmo(entityPlayer) != ItemStack.EMPTY || entityPlayer.capabilities.isCreativeMode;
    }

    public static ItemStack findAmmo(EntityPlayer entityPlayer) {
        if (entityPlayer.getHeldItemOffhand().getItem().equals(ModItems.itemStim)) {
            return entityPlayer.getHeldItem(EnumHand.OFF_HAND);
        } else if (entityPlayer.getHeldItemMainhand().getItem().equals(ModItems.itemStim)) {
            return entityPlayer.getHeldItem(EnumHand.MAIN_HAND);
        } else {
            for (int i = 0; i < entityPlayer.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = entityPlayer.inventory.getStackInSlot(i);
                if (itemstack.getItem().equals(ModItems.itemStim)) {
                    return itemstack;
                }
            }
            return ItemStack.EMPTY;
        }
    }

    public static Operator getOperator(EntityPlayer entityPlayer) {
        if (entityPlayer.getArmorInventoryList().iterator().next() != null) {
            return getOperatorFromArmor(entityPlayer.getArmorInventoryList());
        } else {
            return null;
        }
    }

    public static Operator getOperatorFromArmor(Iterable<ItemStack> equipmentAndArmor) {
        return getOperatorFromArmor(equipmentAndArmor.iterator().next());
    }

    public static Operator getOperatorFromArmor(ItemStack itemstack) {
        if (itemstack != null) {
            Item item = itemstack.getItem();
            if (item instanceof ItemOperatorArmor) {
                return ((ItemOperatorArmor) item).getOperator();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static boolean isDroning(EntityPlayer entityPlayer) {
        boolean isDrone = false;
        for (ItemStack itemStack : entityPlayer.getEquipmentAndArmor()) {
            Item item = itemStack.getItem();
            isDrone = item instanceof ItemDroneArmor;
        }
        return isDrone;
    }

    public static SiegePlayer getSiegePlayerByEntity(EntityPlayer player) {
        SiegePlayer siegePlayerByEntity = null;
        if (SiegeData.getTeam(player) != null) {
            for (SiegePlayer siegePlayer : Objects.requireNonNull(SiegeData.getTeam(player)).getPlayers()) {
                if (siegePlayer.getEntityPlayer().equals(player)) {
                    siegePlayerByEntity = siegePlayer;
                }
            }
        }
        return siegePlayerByEntity;
    }

    public static void enterCamera(EntityPlayer player) {
        /* Confirm this is all serverside*/
        World world = player.getEntityWorld();

        if (!world.isRemote) {
            int cameraNumber = 0;
            if (!SiegeData.viewingCamera.containsKey(player)) SiegeData.viewingCamera.put(player, 0);
            else cameraNumber = SiegeData.viewingCamera.get(player);
            SiegePlayer siegePlayer = getSiegePlayerByEntity(player);
            /* Make sure that the siegeplayer isn't null for the current player*/
            if (siegePlayer != null) {
                ItemStack[] items = saveItems(player);
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ModItems.itemCameraMonitor));
                spawnDummy(player, items);
                EntityCamera entityCamera = SiegeData.getCameras().get(cameraNumber);
                if (entityCamera != null) {
                    player.startRiding(entityCamera);
                    player.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ModItems.itemDroneArmorHead));
                    player.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(ModItems.itemDroneArmorChest));
                    player.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(ModItems.itemDroneArmorLegs));
                    player.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(ModItems.itemDroneArmorFeet));
                }
            }
        }
    }

    public static void updateCamera(EntityPlayer player) {
        /* Confirm this is all serverside*/
        World world = player.getEntityWorld();

        if (!world.isRemote) {
            SiegePlayer siegePlayer = getSiegePlayerByEntity(player);
            /* Make sure that the siegeplayer isn't null for the current player*/
            if (siegePlayer != null && player.isRiding()) {
                player.dismountRidingEntity();
                int cameraNumber = 0;
                if (!SiegeData.viewingCamera.containsKey(player)) SiegeData.viewingCamera.put(player, 0);
                else cameraNumber = SiegeData.viewingCamera.get(player);
                /* Make sure that the siegeplayer isn't null for the current player*/
                EntityCamera entityCamera = SiegeData.getCameras().get(cameraNumber);
                if (entityCamera != null) {
                    player.sendMessage(new TextComponentString("Camera: " + cameraNumber));
                    player.startRiding(entityCamera);
                }
            }
        }
    }

    public static void exitCamera(EntityPlayer player) {
        /* Confirm this is all serverside*/
        World world = player.getEntityWorld();

        if (!world.isRemote) {
            SiegePlayer siegePlayer = getSiegePlayerByEntity(player);
            /* Make sure that the siegeplayer isn't null for the current player*/
            if (siegePlayer != null && player.isRiding()) {
                /* Assign the entity dummy to the stored dummy*/
                EntityDummy entityDummy = siegePlayer.getPlayerDummy();
                if (entityDummy != null) {
                    player.dismountRidingEntity();
                    /* Add the items from the dummy to the player */
                    swapItems(entityDummy.getDummyInventory(), player);
                    /* Carry over the health of the dummy to the player */
                    player.setHealth(entityDummy.getHealth());
                    /* Set the player's armor to the operator armor they had originally*/
                    player.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(siegePlayer.getOperator().getHelmet()));
                    player.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(siegePlayer.getOperator().getChestplate()));
                    player.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(siegePlayer.getOperator().getLeggings()));
                    player.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(siegePlayer.getOperator().getBoots()));

                    /* Teleport the player to the dummy location*/
                    player.setPositionAndUpdate(entityDummy.posX, entityDummy.posY, entityDummy.posZ);
                    /* Once the player is in the dummies position, kill the dummy*/
                    entityDummy.setDead();
                }
            }
        }
    }

    public static void startDroning(EntityPlayer player, World world) {
        /* Confirm this is all serverside*/
        if (!world.isRemote) {
            /* Make sure that the siegeplayer isn't null for the current player*/
            if (getSiegePlayerByEntity(player) != null) {
                /* Check if the player is droning*/
                if (!SiegeHelper.isDroning(getSiegePlayerByEntity(player).getEntityPlayer())) {
                    /* Check if they are currently droning, if so, this doesn't work */
                    ItemStack[] items = saveItems(player);
                    /* Check if there is a drone already alive. */
                    if (getSiegePlayerByEntity(player).getPlayerDrone() != null) {
                        /* Add the items from the drone to the player */
                        swapItems(getSiegePlayerByEntity(player).getPlayerDrone().getDroneInventory(), player);
                        /* Carry over the health of the drone to the player */
                        player.setHealth(getSiegePlayerByEntity(player).getPlayerDrone().getHealth());
                    } else {
                        /* Give the player the swap device so they can go back*/
                        ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ModItems.itemSwapDevice));
                    }
                    /* Create a player dummy where the player is currently standing*/
                    spawnDummy(player, items);
                    /* Set the player's armor to drone */
                    player.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ModItems.itemDroneArmorHead));
                    player.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(ModItems.itemDroneArmorChest));
                    player.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(ModItems.itemDroneArmorLegs));
                    player.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(ModItems.itemDroneArmorFeet));
                    /* Check if the player has a drone so we can change the positions*/
                    if (getSiegePlayerByEntity(player).getPlayerDrone() != null) {
                        /* Load the saved entity drone*/
                        EntityDrone entityDrone = getSiegePlayerByEntity(player).getPlayerDrone();
                        /* Teleport the player to where the drone was standing*/
                        player.setPositionAndUpdate(entityDrone.posX, entityDrone.posY, entityDrone.posZ);
                        /* Kill the drone because the player is taking it's place*/
                        entityDrone.setDead();
                    }
                }
            }
        }
    }


    public static void stopDroning(EntityPlayer player, World world) {
        /* Confirm this is all serverside*/
        if (!world.isRemote) {
            /* Make sure that the siegeplayer isn't null for the current player*/
            if (getSiegePlayerByEntity(player) != null) {
                /* Check if they are currently droning, if so, this doesn't work */
                if (SiegeHelper.isDroning(getSiegePlayerByEntity(player).getEntityPlayer())) {
                    /* Save the items of the player*/
                    ItemStack[] items = saveItems(player);
                    /* Check if there is a dummy already available*/
                    if (getSiegePlayerByEntity(player).getPlayerDummy() != null) {
                        /* Create a player drone where the player is currently standing*/
                        spawnDrone(player, items);
                        /* Add the items from the dummy to the player */
                        swapItems(getSiegePlayerByEntity(player).getPlayerDummy().getDummyInventory(), player);
                        /* Carry over the health of the dummy to the player */
                        player.setHealth(getSiegePlayerByEntity(player).getPlayerDummy().getHealth());
                        /* Set the player's armor to the operator armor they had originally*/
                        player.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(getSiegePlayerByEntity(player).getOperator().getHelmet()));
                        player.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(getSiegePlayerByEntity(player).getOperator().getChestplate()));
                        player.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(getSiegePlayerByEntity(player).getOperator().getLeggings()));
                        player.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(getSiegePlayerByEntity(player).getOperator().getBoots()));
                        /* Assign the entity dummy to the stored dummy*/
                        EntityDummy entityDummy = getSiegePlayerByEntity(player).getPlayerDummy();
                        /* Teleport the player to the dummy location*/
                        player.setPositionAndUpdate(entityDummy.posX, entityDummy.posY, entityDummy.posZ);
                        /* Once the player is in the dummies position, kill the dummy*/
                        entityDummy.setDead();
                    } else {
                        /* Create a player drone where the player is currently standing*/
                        spawnDrone(player, items);
                        /* No dummy exists... this drone should be dead...*/
                        player.setDead();
                    }
                }
            }
        }
    }

    private static ItemStack[] saveItems(EntityPlayer player) {
        ItemStack[] items = new ItemStack[36];
        for (int i = 0; i < 36; i++) {
            /* Loop the entire inventory and save it*/
            items[i] = player.inventory.getStackInSlot(i);
            /* Clear the items as it goes */
            player.inventory.setInventorySlotContents(i, new ItemStack(Items.AIR));
            /* Make sure that it saves this way*/
            player.inventory.markDirty();
        }
        return items;
    }

    private static void swapItems(ItemStack[] items, EntityPlayer player) {
        /* Loop through the items saved and give them to the player*/
        for (int i = 0; i < 36; i++) ItemHandlerHelper.giveItemToPlayer(player, items[i], i);
    }

    private static void spawnDrone(EntityPlayer player, ItemStack[] items) {
        if (!player.world.isRemote) {
            /* Create the entity */
            EntityDrone entity = new EntityDrone(player.world, items);
            /* Set the display name of the entity to the player's*/
            entity.setCustomNameTag(player.getDisplayNameString().toLowerCase() + " Drone");
            /* Set the player's inventory to the entity*/
            entity.setDroneInventory(items);
            /* Set the drones health to the players*/
            entity.setHealth(player.getHealth());
            /* Spawn the entity in the world*/
            player.world.spawnEntity(entity);
            /* Teleport the entity to where the player currently stands.*/
            entity.setPositionAndRotation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
            /* Save the drone into the gamedata*/
            getSiegePlayerByEntity(player).createDrone(entity, player);
        }
    }

    private static void spawnDummy(EntityPlayer player, ItemStack[] items) {
        if (!player.world.isRemote) {
            /* Create the entity */
            EntityDummy entity;
            Operator playerOperator = SiegeHelper.getOperator(player);
            if (playerOperator != null) {
                if (playerOperator.equals(OperatorManager.operatorAsh))
                    entity = new EntityAsh(player.world, items);
                else if (playerOperator.equals(OperatorManager.operatorBandit))
                    entity = new EntityBandit(player.world, items);
                else if (playerOperator.equals(OperatorManager.operatorDoc))
                    entity = new EntityDoc(player.world, items);
                else if (playerOperator.equals(OperatorManager.operatorFinka))
                    entity = new EntityFinka(player.world, items);
                else if (playerOperator.equals(OperatorManager.operatorJackal))
                    entity = new EntityJackal(player.world, items);
                else if (playerOperator.equals(OperatorManager.operatorMaverick))
                    entity = new EntityMaverick(player.world, items);
                else if (playerOperator.equals(OperatorManager.operatorRook))
                    entity = new EntityRook(player.world, items);
                else if (playerOperator.equals(OperatorManager.operatorSledge))
                    entity = new EntitySledge(player.world, items);
                else if (playerOperator.equals(OperatorManager.operatorSmoke))
                    entity = new EntitySmoke(player.world, items);
                else if (playerOperator.equals(OperatorManager.operatorVigil))
                    entity = new EntitySmoke(player.world, items);
                else
                    entity = new EntityAsh(player.world, items);
                entity.setDummyInventory(items);
                entity.setHealth(player.getHealth());
                entity.setPositionAndRotation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
                player.world.spawnEntity(entity);
                getSiegePlayerByEntity(player).createDummy(entity, player);
            }
        }
    }

    public static Operator getRandomAttacker(){
        Random random = new Random();
        ArrayList<Operator> attackers = new ArrayList<>();
        for(Operator operator:OperatorManager.operatorArrayList) {
            if (operator.getTeam().equals(Operator.Team.ATTACKER) && !operator.isHidden()) {
                attackers.add(operator);
            }
        }
        int operatorNumber = random.nextInt(attackers.size());
        return attackers.get(operatorNumber);
    }

    public static Operator getRandomDefender(){
        Random random = new Random();
        ArrayList<Operator> defenders = new ArrayList<>();
        for (Operator operator : OperatorManager.operatorArrayList) {
            if (operator.getTeam().equals(Operator.Team.DEFENDER) && !operator.isHidden()) {
                defenders.add(operator);
            }
        }
        int operatorNumber = random.nextInt(defenders.size());
        return defenders.get(operatorNumber);
    }

    public static boolean isBlockConnecting(World world, BlockPos position) {
        boolean isBlockConnecting = false;
        int radius = 0;
        int x = MathHelper.floor(position.getX());
        int y = MathHelper.floor(position.getY());
        int z = MathHelper.floor(position.getZ());
        AxisAlignedBB aabb = new AxisAlignedBB((double) x - 1, y, (double) z - 1, x + 1, y, z + 1).expand(radius, 0, radius);

        int i = MathHelper.floor(aabb.minX);
        int j = MathHelper.floor(aabb.maxX + 1.0D);
        int k = MathHelper.floor(aabb.minY);
        int l = MathHelper.floor(aabb.maxY + 1.0D);
        int i1 = MathHelper.floor(aabb.minZ);
        int j1 = MathHelper.floor(aabb.maxZ + 1.0D);

        for (int k1 = i; k1 < j; ++k1) {
            for (int l1 = k; l1 < l; ++l1) {
                for (int i2 = i1; i2 < j1; ++i2) {
                    IBlockState block = world.getBlockState(new BlockPos(k1, l1, i2));
                    if (!(block.getBlock().equals(Blocks.AIR)) && !(block.getBlock().equals(ModBlocks.blockRope))) {
                        isBlockConnecting = true;
                    }
                }
            }
        }
        return isBlockConnecting;
    }

    public static boolean checkForBlock(World world, BlockPos position, Block block) {
        boolean isBlockAbove = false;
        int radius = 0;
        int x = MathHelper.floor(position.getX());
        int y = MathHelper.floor(position.getY());
        int z = MathHelper.floor(position.getZ());
        AxisAlignedBB aabb = new AxisAlignedBB(x, y, z, x, y, z).expand(radius, 0, radius);

        int i = MathHelper.floor(aabb.minX);
        int j = MathHelper.floor(aabb.maxX + 1.0D);
        int k = MathHelper.floor(aabb.minY);
        int l = MathHelper.floor(aabb.maxY + 1.0D);
        int i1 = MathHelper.floor(aabb.minZ);
        int j1 = MathHelper.floor(aabb.maxZ + 1.0D);

        for (int yy = 0; yy < 12; yy++) {
            for (int k1 = i; k1 < j; ++k1) {
                for (int l1 = k; l1 < l; ++l1) {
                    for (int i2 = i1; i2 < j1; ++i2) {
                        IBlockState blockCheck = world.getBlockState(new BlockPos(k1, l1 + yy, i2));
                        if ((blockCheck.getBlock().equals(block))) {
                            isBlockAbove = true;
                        }
                    }
                }
            }
        }
        return isBlockAbove;
    }

    public static boolean canVault(EntityPlayer entityPlayer) {
        return isBlockConnecting(entityPlayer.getEntityWorld(), entityPlayer.getPosition());
    }

    public static boolean isRopeOut(EntityPlayer entityPlayer) {
        boolean canRappel = false;
        if (SiegeData.rappelList != null) {
            if (SiegeData.rappelList.get(entityPlayer) != null) {
                if (SiegeData.rappelList.get(entityPlayer).size() > 1) {
                    canRappel = true;
                }
            }
        }
        return canRappel;
    }

    public static boolean canRappel(EntityPlayer entityPlayer) {
        boolean canRappel = true;
        BlockPos blockPos = entityPlayer.getPosition();
        canRappel = !isRopeOut(entityPlayer);
        if (entityPlayer.world.canSeeSky(blockPos)) {
            for (int y = 0; y < 4; y++) {
                if (!(isBlockConnecting(entityPlayer.getEntityWorld(), new BlockPos(blockPos.getX(), blockPos.getY() + y, blockPos.getZ())))) {
                    canRappel = false;
                }
            }
        }
        if (checkForBlock(entityPlayer.world, blockPos, ModBlocks.blockFakeAir)) {
            canRappel = true;
        }
        return canRappel;
    }

    public static int getRappelHeight(EntityPlayer entityPlayer) {
        int height = 0;
        BlockPos blockPos = entityPlayer.getPosition();
        while (!entityPlayer.world.canSeeSky(blockPos)) {
            height++;
            entityPlayer.sendMessage(new TextComponentString("" + height));
            blockPos = new BlockPos(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ());
        }
        return (height - 1);
    }
}
