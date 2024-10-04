package dev.zgrv.binaryUtils.ability

import org.bukkit.event.Event
import java.io.Serializable

/**
 * A class container for abilities
 *
 * Inherits Serializable so that it can be stored in a PersistentDataContainer
 */
open class AbilityContainer : Serializable {
    protected open val abilities: MutableSet<Ability> = mutableSetOf()

    /**
     * Passes event to each ability in the container
     *
     * @param event The event to be handled by each ability in the container
     */
    open fun pass(event: Event) {
        abilities.forEach { it.handle(event) }
    }

    /**
     * Adds an ability to the container. Container is simple MutableSet.
     *
     * @param ability The ability that will be added to the container.
     *
     * @return Boolean - the ability has been added or not
     */
    open fun add(ability: Ability): Boolean {
        return abilities.add(ability)
    }

    /**
     * Shows all abilities in the container
     *
     * @return List of abilities
     */
    open fun list() = abilities.toList()

}