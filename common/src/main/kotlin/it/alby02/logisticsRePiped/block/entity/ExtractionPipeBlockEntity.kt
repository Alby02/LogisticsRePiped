/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.block.entity

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class ExtractionPipeBlockEntity(pos: BlockPos, state: BlockState) : BasicPipeBlockEntity(pos, state) {
    // Override the generic basic pipe type to the specific extraction pipe type once registered
    override fun getType() = ModBlockEntities.EXTRACTION_PIPE.get()

    private var tickCounter = 0

    fun tick(level: Level, pos: BlockPos, state: BlockState) {
        tickCounter++
        if (tickCounter >= 20) { // Try extracting once per second
            tickCounter = 0
            extractItem()
        }
    }

    private fun extractItem() {
        // Here we will:
        // 1. Look at adjacent sides that are NOT pipes (they are inventories).
        // 2. See if there is an item we can pull.
        // 3. If so, pull 1 item, wrap it in our routing object, and push it to a connected pipe.
        // This requires abstracting item inventory access since Architectury differs slightly between Forge (Capabilities) and Fabric (Storage).
        // We will add the Architectury Inventory API dependency or wrapper later for this.
    }
}
