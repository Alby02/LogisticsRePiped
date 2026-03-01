/*
 * Copyright (c) 2025-2026 Alberto Montrucchio
 * Licensed under the EUPL-1.2-or-later
 */

package it.alby02.logisticsRePiped.item

import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import it.alby02.logisticsRePiped.CommonClass
import net.minecraft.world.item.Item
import net.minecraft.core.registries.Registries

object ModItems {
    val ITEMS: DeferredRegister<Item> = DeferredRegister.create(CommonClass.MOD_ID, Registries.ITEM)

    val GREEN_GEM: RegistrySupplier<Item> = ITEMS.register("greengem") {
        Item(Item.Properties().`arch$tab`(ModItemGroups.LOGISTICS_TAB))
    }

    val WRENCH: RegistrySupplier<Item> = ITEMS.register("wrench") {
        WrenchItem(Item.Properties().`arch$tab`(ModItemGroups.LOGISTICS_TAB).stacksTo(1))
    }

    fun register() {
        ITEMS.register()
    }
}
