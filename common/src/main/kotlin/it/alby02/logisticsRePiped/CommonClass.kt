/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped

import it.alby02.logisticsRePiped.block.ModBlocks
import it.alby02.logisticsRePiped.item.ModItemGroups
import it.alby02.logisticsRePiped.item.ModItems

class CommonClass {
    companion object {
        const val MOD_ID = "logisticsrepiped"

        fun init() {
            ModItems.register()
            ModBlocks.register()
            ModItemGroups.register()
        }
    }
}
