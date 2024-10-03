package dev.zgrv.binaryUtils.ability

import net.kyori.adventure.text.Component
import org.bukkit.event.Event
import java.io.Serializable
import kotlin.time.Duration


/**
 * Class describing the ability
 *
 * @property name is ability's name.
 * @property description is ability's description.
 * @property cooldown is ability's cooldown
 * @property lastUseTime is time last of ability use
 *
 */

abstract class Ability(
    val name: Component,
    val description: Component,
    val cooldown: Duration,
    var lastUseTime: Duration = Duration.ZERO,
) : Serializable {

    /**
     * Handles the event
     *
     * @param event The event that will be handled
     */
    abstract fun handle(event: Event)

    fun isOnCooldown() = cooldown > Duration.ZERO

}