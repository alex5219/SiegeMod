package com.alexjw.siegecraft.server.blocks;

import com.alexjw.siegecraft.Siege;
import com.alexjw.siegecraft.SiegeTabs;
import com.alexjw.siegecraft.server.data.SiegeData;
import com.alexjw.siegecraft.server.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

public class BlockRope extends Block {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    protected static final AxisAlignedBB LADDER_EAST_AABB = new AxisAlignedBB
            (0.1D, 0.0D, 0.45D, 0.015625D, 1.0D, 0.534375D);
    protected static final AxisAlignedBB LADDER_WEST_AABB = new AxisAlignedBB
            (0.9D, 0.0D, 0.45D, 0.984375D, 1.0D, 0.534375D);
    protected static final AxisAlignedBB LADDER_SOUTH_AABB = new AxisAlignedBB
            (0.45D, 0.0D, 0.1D, 0.534375D, 1.0D, 0.015625D);
    protected static final AxisAlignedBB LADDER_NORTH_AABB = new AxisAlignedBB
            (0.45D, 0.0D, 0.9D, 0.534375D, 1.0D, 0.984375D);

    public BlockRope() {
        super(Material.BARRIER);
        this.setUnlocalizedName("rope");
        this.setRegistryName(Siege.MODID, "rope");
        this.setCreativeTab(SiegeTabs.tabGadgets);
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
    }

    public AxisAlignedBB getBoundingBox(IBlockState p_getBoundingBox_1_, IBlockAccess p_getBoundingBox_2_, BlockPos p_getBoundingBox_3_) {
        switch (p_getBoundingBox_1_.getValue(FACING)) {
            case NORTH:
                return LADDER_NORTH_AABB;
            case SOUTH:
                return LADDER_SOUTH_AABB;
            case WEST:
                return LADDER_WEST_AABB;
            case EAST:
            default:
                return LADDER_EAST_AABB;
        }
    }

    public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState iBlockState, Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) entity;
            for (BlockPos blockPos1 : SiegeData.rappelList.get(entityPlayer)) {
                if (blockPos.equals(blockPos1)) {
                    if (entityPlayer.collidedHorizontally) {
                        entityPlayer.fallDistance = 0.0F;
                        if (entityPlayer.isSneaking()) {
                            entityPlayer.motionY = 0.08D;
                        } else {
                            entityPlayer.motionY = 0.2342D;
                        }
                    }
                }
                if (!entityPlayer.world.isRemote) {
                    double motionX = entityPlayer.posX - entityPlayer.lastTickPosX;
                    double motionZ = entityPlayer.posZ - entityPlayer.lastTickPosZ;
                    double motionY = entityPlayer.posY - entityPlayer.lastTickPosY;
                    if (motionY > 0.0D && (motionX == 0D || motionZ == 0D)) {
                        entityPlayer.fallDistance = 0.0F;
                    }
                }
            }
        }
    }

    public int getMetaFromState(IBlockState p_getMetaFromState_1_) {
        return p_getMetaFromState_1_.getValue(FACING).getIndex();
    }

    public IBlockState getStateFromMeta(int p_getStateFromMeta_1_) {
        EnumFacing enumfacing = EnumFacing.getFront(p_getStateFromMeta_1_);
        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    public boolean isOpaqueCube(IBlockState p_isOpaqueCube_1_) {
        return false;
    }

    public boolean isFullCube(IBlockState p_isFullCube_1_) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public IBlockState withRotation(IBlockState p_withRotation_1_, Rotation p_withRotation_2_) {
        return p_withRotation_1_.withProperty(FACING, p_withRotation_2_.rotate(p_withRotation_1_.getValue(FACING)));
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess p_getBlockFaceShape_1_, IBlockState p_getBlockFaceShape_2_, BlockPos p_getBlockFaceShape_3_, EnumFacing p_getBlockFaceShape_4_) {
        return BlockFaceShape.UNDEFINED;
    }

    public IBlockState getState(EnumFacing enumFacing) {
        IBlockState state = null;
        switch (enumFacing) {
            case EAST:
                state = withRotation(this.getDefaultState(), Rotation.COUNTERCLOCKWISE_90);
                break;
            case SOUTH:
                state = withRotation(this.getDefaultState(), Rotation.NONE);
                break;
            case WEST:
                state = withRotation(this.getDefaultState(), Rotation.CLOCKWISE_90);
                break;
            default:
                state = withRotation(this.getDefaultState(), Rotation.CLOCKWISE_180);
                break;
        }
        return state;
    }
}

