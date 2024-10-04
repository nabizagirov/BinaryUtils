package dev.zgrv.binaryUtils

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class BinaryUtils : JavaPlugin(), Listener {

    companion object {
        lateinit var plugin: JavaPlugin

    }

    override fun onEnable() {
        plugin = this
        server.pluginManager.registerEvents(this, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }


}
