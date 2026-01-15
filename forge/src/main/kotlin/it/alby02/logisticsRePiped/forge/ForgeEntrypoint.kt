/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.forge

import dev.architectury.platform.forge.EventBuses
import it.alby02.logisticsRePiped.CommonClass
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(CommonClass.MOD_ID)
object ForgeEntrypoint {
    init {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(CommonClass.MOD_ID, MOD_BUS)
        CommonClass.init()
    }
}
