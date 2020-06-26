package com.alexjw.siegecraft.server.blocks;

import com.alexjw.siegecraft.Siege;
import com.alexjw.siegecraft.SiegeTabs;
import com.alexjw.siegecraft.server.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Objects;

public class BlockFakeAir extends Block {
    public BlockFakeAir() {
        super(Material.BARRIER);
        this.setBlockUnbreakable();
        this.setResistance(6000001.0F);
        this.setUnlocalizedName("fake_air");
        this.setRegistryName(Siege.MODID, "fake_air");
        this.setCreativeTab(SiegeTabs.tabGadgets);
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
    }

    public boolean canCollideCheck(IBlockState p_canCollideCheck_1_, boolean p_canCollideCheck_2_) {
        return false;
    }

    public boolean isOpaqueCube(IBlockState p_isOpaqueCube_1_) {
        return false;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState p_getCollisionBoundingBox_1_, IBlockAccess p_getCollisionBoundingBox_2_, BlockPos p_getCollisionBoundingBox_3_) {
        return NULL_AABB;
    }

    public boolean isCollidable() {
        return false;
    }

    public void onEntityCollidedWithBlock(World p_onEntityCollidedWithBlock_1_, BlockPos p_onEntityCollidedWithBlock_2_, IBlockState p_onEntityCollidedWithBlock_3_, Entity p_onEntityCollidedWithBlock_4_) {
    }

    public boolean isFullCube(IBlockState p_isFullCube_1_) {
        return false;
    }

    public EnumBlockRenderType getRenderType(IBlockState p_getRenderType_1_) {
        return EnumBlockRenderType.INVISIBLE;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess p_getBlockFaceShape_1_, IBlockState p_getBlockFaceShape_2_, BlockPos p_getBlockFaceShape_3_, EnumFacing p_getBlockFaceShape_4_) {
        return BlockFaceShape.UNDEFINED;
    }
}