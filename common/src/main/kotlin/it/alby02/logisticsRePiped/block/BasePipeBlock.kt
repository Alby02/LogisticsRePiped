/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SimpleWaterloggedBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

open class BasePipeBlock(properties: Properties) : Block(properties), SimpleWaterloggedBlock {

    companion object {
        val NORTH: BooleanProperty = BooleanProperty.create("north")
        val EAST: BooleanProperty = BooleanProperty.create("east")
        val SOUTH: BooleanProperty = BooleanProperty.create("south")
        val WEST: BooleanProperty = BooleanProperty.create("west")
        val UP: BooleanProperty = BooleanProperty.create("up")
        val DOWN: BooleanProperty = BooleanProperty.create("down")
        val WATERLOGGED: BooleanProperty = BlockStateProperties.WATERLOGGED

        val PROPERTY_BY_DIRECTION = mapOf(
            Direction.NORTH to NORTH,
            Direction.EAST to EAST,
            Direction.SOUTH to SOUTH,
            Direction.WEST to WEST,
            Direction.UP to UP,
            Direction.DOWN to DOWN
        )

        // The core is a 6x6x6 box in the center, from 5 to 11
        private val CORE_SHAPE: VoxelShape = box(5.0, 5.0, 5.0, 11.0, 11.0, 11.0)
        private val UP_SHAPE: VoxelShape = box(5.0, 11.0, 5.0, 11.0, 16.0, 11.0)
        private val DOWN_SHAPE: VoxelShape = box(5.0, 0.0, 5.0, 11.0, 5.0, 11.0)
        private val NORTH_SHAPE: VoxelShape = box(5.0, 5.0, 0.0, 11.0, 11.0, 5.0)
        private val SOUTH_SHAPE: VoxelShape = box(5.0, 5.0, 11.0, 11.0, 11.0, 16.0)
        private val EAST_SHAPE: VoxelShape = box(11.0, 5.0, 5.0, 16.0, 11.0, 11.0)
        private val WEST_SHAPE: VoxelShape = box(0.0, 5.0, 5.0, 5.0, 11.0, 11.0)

        // The holes are 4x4x4 inside the 6x6x6 shapes
        private val CORE_HOLE: VoxelShape = box(6.0, 6.0, 6.0, 10.0, 10.0, 10.0)
        private val UP_HOLE: VoxelShape = box(6.0, 10.0, 6.0, 10.0, 16.0, 10.0)
        private val DOWN_HOLE: VoxelShape = box(6.0, 0.0, 6.0, 10.0, 6.0, 10.0)
        private val NORTH_HOLE: VoxelShape = box(6.0, 6.0, 0.0, 10.0, 10.0, 6.0)
        private val SOUTH_HOLE: VoxelShape = box(6.0, 6.0, 10.0, 10.0, 10.0, 16.0)
        private val EAST_HOLE: VoxelShape = box(10.0, 6.0, 6.0, 16.0, 10.0, 10.0)
        private val WEST_HOLE: VoxelShape = box(0.0, 6.0, 6.0, 6.0, 10.0, 10.0)

        private val SOLID_SHAPES = arrayOfNulls<VoxelShape>(64)
        private val HOLLOW_SHAPES = arrayOfNulls<VoxelShape>(64)

        init {
            for (i in 0..63) {
                var solid = CORE_SHAPE
                var hole = CORE_HOLE
                
                if ((i and 1) != 0) { solid = Shapes.or(solid, DOWN_SHAPE); hole = Shapes.or(hole, DOWN_HOLE) }
                if ((i and 2) != 0) { solid = Shapes.or(solid, UP_SHAPE); hole = Shapes.or(hole, UP_HOLE) }
                if ((i and 4) != 0) { solid = Shapes.or(solid, NORTH_SHAPE); hole = Shapes.or(hole, NORTH_HOLE) }
                if ((i and 8) != 0) { solid = Shapes.or(solid, SOUTH_SHAPE); hole = Shapes.or(hole, SOUTH_HOLE) }
                if ((i and 16) != 0) { solid = Shapes.or(solid, WEST_SHAPE); hole = Shapes.or(hole, WEST_HOLE) }
                if ((i and 32) != 0) { solid = Shapes.or(solid, EAST_SHAPE); hole = Shapes.or(hole, EAST_HOLE) }
                
                SOLID_SHAPES[i] = solid
                HOLLOW_SHAPES[i] = Shapes.join(solid, hole, net.minecraft.world.phys.shapes.BooleanOp.ONLY_FIRST)
            }
        }

        fun getShapeIndex(state: BlockState): Int {
            var index = 0
            if (state.getValue(DOWN)) index = index or 1
            if (state.getValue(UP)) index = index or 2
            if (state.getValue(NORTH)) index = index or 4
            if (state.getValue(SOUTH)) index = index or 8
            if (state.getValue(WEST)) index = index or 16
            if (state.getValue(EAST)) index = index or 32
            return index
        }
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false)
                .setValue(WATERLOGGED, false)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, WATERLOGGED)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape {
        val index = getShapeIndex(state)
        val entity = (context as? net.minecraft.world.phys.shapes.EntityCollisionContext)?.entity as? net.minecraft.world.entity.LivingEntity
        val isHoldingWrenchOrPipe = entity != null && (
            entity.mainHandItem.item is it.alby02.logisticsRePiped.item.WrenchItem ||
            (entity.mainHandItem.item as? net.minecraft.world.item.BlockItem)?.block is BasePipeBlock
        )

        return if (isHoldingWrenchOrPipe) SOLID_SHAPES[index]!! else HOLLOW_SHAPES[index]!!
    }

    // Determines if a pipe should visually connect to the block at the given position
    open fun canConnectTo(state: BlockState, neighborState: BlockState, direction: Direction): Boolean {
        val block = neighborState.block
        if (block is BasePipeBlock) {
             // We only connect if the neighbor *also* agrees to connect to us
             return block.acceptsConnectionFrom(neighborState, state, direction.opposite)
        }
        return false
    }

    // By default, a pipe accepts connections from any pipe
    open fun acceptsConnectionFrom(state: BlockState, neighborState: BlockState, direction: Direction): Boolean {
        return neighborState.block is BasePipeBlock
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        val level = context.level
        val pos = context.clickedPos
        val fluidState = level.getFluidState(pos)
        
        val defaultState = defaultBlockState()
        return defaultState
            .setValue(DOWN, canConnectTo(defaultState, level.getBlockState(pos.below()), Direction.DOWN))
            .setValue(UP, canConnectTo(defaultState, level.getBlockState(pos.above()), Direction.UP))
            .setValue(NORTH, canConnectTo(defaultState, level.getBlockState(pos.north()), Direction.NORTH))
            .setValue(SOUTH, canConnectTo(defaultState, level.getBlockState(pos.south()), Direction.SOUTH))
            .setValue(WEST, canConnectTo(defaultState, level.getBlockState(pos.west()), Direction.WEST))
            .setValue(EAST, canConnectTo(defaultState, level.getBlockState(pos.east()), Direction.EAST))
            .setValue(WATERLOGGED, fluidState.type === Fluids.WATER)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun updateShape(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        level: LevelAccessor,
        currentPos: BlockPos,
        neighborPos: BlockPos
    ): BlockState {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level))
        }
        val property = PROPERTY_BY_DIRECTION[direction]!!
        val wantsToConnect = canConnectTo(state, neighborState, direction)
        val currentlyConnected = state.getValue(property)
        var finalConnection = wantsToConnect

        if (neighborState.block is BasePipeBlock) {
            val neighborProperty = PROPERTY_BY_DIRECTION[direction.opposite]!!
            if (neighborState.hasProperty(neighborProperty)) {
                val neighborPointsToUs = neighborState.getValue(neighborProperty)

                if (!currentlyConnected && wantsToConnect) {
                    if (!neighborPointsToUs) {
                        finalConnection = false
                    }
                } else if (currentlyConnected) {
                    if (!neighborPointsToUs) {
                        finalConnection = false
                    }
                }
            }
        }

        return state.setValue(property, finalConnection)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getFluidState(state: BlockState): FluidState {
        return if (state.getValue(WATERLOGGED)) Fluids.WATER.getSource(false) else super.getFluidState(state)
    }
}
