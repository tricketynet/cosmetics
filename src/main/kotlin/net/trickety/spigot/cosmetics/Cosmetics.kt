package net.trickety.spigot.cosmetics

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask
import java.lang.IllegalStateException

object Cosmetics: Listener {

    private val cosmetics: MutableMap<Player, MutableSet<Cosmetic>> = mutableMapOf()
    private var task: BukkitTask? = null

    fun init(plugin: Plugin) {
        if (task != null) throw IllegalStateException("Cosmetics already initialized")
        Bukkit.getPluginManager().registerEvents(this, plugin)
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, {
            cosmetics.forEach { entry -> entry.value.forEach { it.onTick() } }
        }, 1, 1)
    }

    fun destroy() {
        task?.cancel()
    }

    fun registerCosmetic(cosmetic: Cosmetic, player: Player) {
        cosmetics.putIfAbsent(player, mutableSetOf())
        cosmetics[player]!!.add(cosmetic)
    }

    fun unregisterCosmetic(cosmetic: Cosmetic, player: Player) {
        cosmetics[player]?.remove(cosmetic)
    }

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        cosmetics[event.player]?.forEach { it.onPlayerMove() }
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        cosmetics.remove(event.player)?.forEach { it.destroy() }
    }

}