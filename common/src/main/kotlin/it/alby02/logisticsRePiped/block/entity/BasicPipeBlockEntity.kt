/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.block.entity

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

open class BasicPipeBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(ModBlockEntities.BASIC_PIPE.get(), pos, state) {
    // This will hold the forwarding logic, queues of items currently passing through the router, etc.
}
