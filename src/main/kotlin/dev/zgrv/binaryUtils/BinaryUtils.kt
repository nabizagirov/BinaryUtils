package dev.zgrv.binaryUtils

import org.bukkit.plugin.java.JavaPlugin

class BinaryUtils : JavaPlugin() {


    companion object {
        lateinit var plugin: BinaryUtils
        val pdc = "_binary_${plugin.name.lowercase()}"
    }

    override fun onEnable() {
        plugin = this
        // Plugin startup logic
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

}
