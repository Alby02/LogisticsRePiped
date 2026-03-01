/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.item

import it.alby02.logisticsRePiped.block.BasePipeBlock
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.core.Direction

class WrenchItem(properties: Properties) : Item(properties) {

    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        val pos = context.clickedPos
        val state = level.getBlockState(pos)

        if (state.block is BasePipeBlock) {
            if (!level.isClientSide) {
                val hit = context.clickLocation
                val localX = hit.x - pos.x
                val localY = hit.y - pos.y
                val localZ = hit.z - pos.z

                // The core is from 5/16 (0.3125) to 11/16 (0.6875)
                val coreMin = 5.0 / 16.0
                val coreMax = 11.0 / 16.0

                var directionToToggle: Direction? = null

                if (localX < coreMin) {
                    directionToToggle = Direction.WEST
                } else if (localX > coreMax) {
                    directionToToggle = Direction.EAST
                } else if (localY < coreMin) {
                    directionToToggle = Direction.DOWN
                } else if (localY > coreMax) {
                    directionToToggle = Direction.UP
                } else if (localZ < coreMin) {
                    directionToToggle = Direction.NORTH
                } else if (localZ > coreMax) {
                    directionToToggle = Direction.SOUTH
                } else {
                    // Clicked on the core face directly
                    directionToToggle = context.clickedFace
                }

                if (directionToToggle != null) {
                    val prop = BasePipeBlock.PROPERTY_BY_DIRECTION[directionToToggle] ?: return InteractionResult.PASS
                    val currentValue = state.getValue(prop)
                    
                    // If we are trying to establish a new connection (currentValue is FALSE -> TRUE)
                    // We must ensure the block on that side is actually a valid connection target.
                    if (!currentValue) {
                        val neighborPos = pos.relative(directionToToggle)
                        val neighborState = level.getBlockState(neighborPos)
                        
                        val pipeBlock = state.block as BasePipeBlock
                        // Check if the pipe logic even allows connecting to that block
                        if (!pipeBlock.canConnectTo(state, neighborState, directionToToggle)) {
                            // Can't force a connection if the neighbor refuses or is invalid (air, etc)
                            return InteractionResult.PASS
                        }
                    }
                    
                    val newState = state.setValue(prop, !currentValue)
                    level.setBlock(pos, newState, 3)
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide)
        }

        return super.useOn(context)
    }
    
    // In minecraft, instant-breaking is done by block mining speed or Tool material, but for custom pipes,
    // the cleanest generic way is checking if it's correct tool for drops.
    // Given the simplicity requested, right click is implemented. Instant mining can be further refined
    // by overriding getDestroySpeed.
    
    override fun getDestroySpeed(itemStack: net.minecraft.world.item.ItemStack, blockState: BlockState): Float {
        if (blockState.block is BasePipeBlock) {
            return 100.0f // Almost instant
        }
        return super.getDestroySpeed(itemStack, blockState)
    }
}
