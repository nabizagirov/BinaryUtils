package dev.zgrv.binaryUtils.builders

import net.kyori.adventure.text.Component
import org.bukkit.Material

class ItemBuilder(
    val material: Material,
    val amount: Int = 1,
) {

    private val lore: MutableMap<LoreSection, Component> = mutableMapOf()


    fun lore(component: Component) {
        lore(LoreSection.DESCRIPTION, component)
    }

    fun ability()


    private enum class LoreSection {
        STATS,
        DESCRIPTION,
        SKILL,
        SKILL_DESCRIPTION
    }


    private fun lore(section: LoreSection, component: Component) {
        lore[section] = component
    }

}