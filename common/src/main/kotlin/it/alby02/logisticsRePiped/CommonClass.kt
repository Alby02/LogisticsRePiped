/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped

import it.alby02.logisticsRePiped.block.ModBlocks
import it.alby02.logisticsRePiped.block.entity.ModBlockEntities
import it.alby02.logisticsRePiped.item.ModItemGroups
import it.alby02.logisticsRePiped.item.ModItems

class CommonClass {
    companion object {
        const val MOD_ID = "logisticsrepiped"

        fun init() {
            ModBlocks.register()
            ModBlockEntities.register()
            ModItems.register()
            ModItemGroups.register()

            dev.architectury.utils.EnvExecutor.runInEnv(dev.architectury.utils.Env.CLIENT) {
                Runnable {
                    dev.architectury.registry.client.rendering.RenderTypeRegistry.register(
                        net.minecraft.client.renderer.RenderType.cutout(),
                        ModBlocks.UNROUTED_PIPE_BLOCK.get(),
                        ModBlocks.BASIC_PIPE_BLOCK.get(),
                        ModBlocks.EXTRACTION_PIPE_BLOCK.get(),
                        ModBlocks.INSERTION_PIPE_BLOCK.get()
                    )
                }
            }
        }
    }
}
