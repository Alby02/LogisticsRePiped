/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.fabric

import it.alby02.logisticsRePiped.CommonClass
import net.fabricmc.api.ModInitializer

class FabricEntrypoint : ModInitializer {
    override fun onInitialize() {
        CommonClass.init()
    }
}
