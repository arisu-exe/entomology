package io.github.fallOut015.entomology.block;

import io.github.fallOut015.entomology.tileentity.BugJarTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

public class BugJarBlock extends Block {
    public static final BooleanProperty HAS_FIREFLY = BooleanProperty.create("has_firefly");

    protected static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 12.0D, 13.0D);

    public BugJarBlock(Properties builder) {
        super(builder);
        this.registerDefaultState(this.stateDefinition.any().setValue(HAS_FIREFLY, false));
    }

    @Override
    public void destroy(IWorld world, BlockPos pos, BlockState state) {
        @Nullable BugJarTileEntity jar = (BugJarTileEntity) world.getBlockEntity(pos);
        if(jar != null && jar.hasInsect() && !world.isClientSide()) {
            world.addFreshEntity(jar.removeInsect());
        }

        super.destroy(world, pos, state);
    }
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateContainer) {
        stateContainer.add(HAS_FIREFLY);
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = BlocksEntomology.BUG_JAR.get().defaultBlockState();
        context.getLevel().setBlockEntity(context.getClickedPos(), BlocksEntomology.BUG_JAR.get().createTileEntity(state, context.getLevel()));
        return state;
    }
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BugJarTileEntity();
    }
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
    @Override
    public boolean canSurvive(BlockState state, IWorldReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP);
    }
}