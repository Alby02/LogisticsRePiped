/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.item

import dev.architectury.registry.CreativeTabRegistry
import dev.architectury.registry.registries.DeferredRegister
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import dev.architectury.registry.registries.RegistrySupplier
import it.alby02.logisticsRePiped.CommonClass

object ModItemGroups {
    val TABS: DeferredRegister<CreativeModeTab> = DeferredRegister.create(CommonClass.MOD_ID, Registries.CREATIVE_MODE_TAB)

    val GREEN_GEM_GROUP: RegistrySupplier<CreativeModeTab> = TABS.register("gem") {
        CreativeTabRegistry.create(
            Component.translatable("itemgroup.gem")
        ) {
            ItemStack(ModItems.GREEN_GEM.get())
        }
    }

    fun register() {
        TABS.register()
        
        // Populate the tab
        // Note: Architectury's CreativeTabRegistry.create doesn't support the 'entries' lambda directly in the same way 
        // as Fabric. We might need to use an event or implicit population if using arch$tab.
        // For now, if we use arch$tab settings on items, they will appear here.
    }
}
