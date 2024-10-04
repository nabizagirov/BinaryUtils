package dev.zgrv.binaryUtils.builders

import dev.zgrv.binaryUtils.ability.Ability
import dev.zgrv.binaryUtils.ability.AbilityContainer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import java.util.*

/**
 * Builder class for ItemStack
 *
 * Used for easy customization of ItemStack
 *
 * @property material is ItemStack material.
 * @property amount is amount of ItemStack. Base value - 1
 */
class ItemBuilder(
    private val material: Material,
) {
    private val item: ItemStack = ItemStack(material)
    private val meta: ItemMeta = item.itemMeta
    private val abilityContainer: AbilityContainer = AbilityContainer()
    private val lore: MutableMap<LoreSection, MutableList<Component>> = mutableMapOf()
    private val attributes: MutableMap<Attribute, AttributeModifier> = EnumMap(Attribute::class.java)


    /**
     * Sets the name of ItemStack
     */
    fun name(component: Component) {
        meta.itemName(component)
    }

    fun amount(amount: Int) {
        if (amount > item.maxStackSize)
            throw RuntimeException("Amount of ItemStack(${item.type}) must be less than ${item.maxStackSize}")
        else item.amount = amount
    }

    fun lore(component: Component) {
        lore(LoreSection.DESCRIPTION, component)
    }

    /**
     *  Sets the unbreakable property of the ItemStack
     *
     * @param bool if true, the ItemStack is unbreakable.
     *              if false, the ItemStack is breakable.
     */
    fun unbreakable(bool: Boolean) {
        meta.isUnbreakable = bool
    }

    /**
     * Adds an ability into ability container
     *
     * @param ability is ability that will be added
     */
    fun ability(ability: Ability) {
        if (abilityContainer.add(ability)) {
            lore(LoreSection.ABILITY, ability.name)
            lore(LoreSection.ABILITY, ability.description)
        }
    }

    /**
     * Adds enchantment to ItemStack.
     * All enchants ignores level restriction.
     *
     * @param enchantment is an enchantment that will be added to ItemStack
     * @param level is a level of the enchantment
     */
    fun addEnchant(enchantment: Enchantment, level: Int) {
        meta.addEnchant(enchantment, level, true)
    }


    fun setAttribute(attribute: Attribute, modifier: AttributeModifier) {
        attributes[attribute] = modifier
    }

    fun addItemFlags(vararg itemFlags: ItemFlag) {
        meta.addItemFlags(*itemFlags)
    }


    /**
     * Enum class for describing lore sections
     * */
    private enum class LoreSection(val priority: Int) {
        DESCRIPTION(1),
        ABILITY(2)
    }


    /**
     * Adds a component to the lore of the specified section.
     *
     * If the section does not already have a lore entry, a new list will be created.
     *
     * @param section the lore section to which the component should be added
     * @param component the component to be added to the section
     * @param append if true, the component is added to the existing list of components in the section;
     *               if false, the list for the section is replaced with a new list containing only the component
     */
    private fun lore(section: LoreSection, component: Component, append: Boolean = true) {
        lore.getOrPut(section) { mutableListOf() }.let { list ->
            if (append) list.add(component)
            else lore[section] = mutableListOf(component)
        }
    }

    /**
     * Adds a list of components to the lore of the specified section.
     *
     * If the section does not already have a lore entry, a new list will be created.
     *
     * @param section the lore section to which the components should be added
     * @param components the list of components to be added to the lore
     * @param append if true, the components are added to the existing list of components in the section;
     *               if false, the list for the section is replaced with the new list of components
     */
    private fun lore(section: LoreSection, components: List<Component>, append: Boolean = true) {
        if (append) lore.getOrPut(section) { mutableListOf() }.addAll(components)
        else lore[section] = components.toMutableList()
    }


    private fun applyAttributes() {
        attributes.forEach { meta.addAttributeModifier(it.key, it.value) }
    }


    private val loreDivider = Component.text("----------------------").decoration(TextDecoration.ITALIC, false)
    private fun applyLore() {
        val result = lore.keys
            .sortedBy { it.priority }
            .flatMap { section ->
                val rows = lore[section].orEmpty() // Используем orEmpty(), чтобы избежать null
                rows + loreDivider
            }

        meta.lore(result)
    }

    fun build(): ItemStack {
        applyAttributes()
        applyLore()

        item.itemMeta = meta
        return item
    }
}


inline fun item(material: Material, builder: ItemBuilder.() -> Unit): ItemStack {
    return ItemBuilder(material).apply(builder).build()
}