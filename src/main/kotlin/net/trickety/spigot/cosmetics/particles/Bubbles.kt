package net.trickety.spigot.cosmetics.particles

import net.trickety.spigot.cosmetics.Cosmetic
import net.trickety.spigot.cosmetics.Cosmetics
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.entity.Player

class Bubbles(player: Player): Cosmetic(player) {

    init {
        Cosmetics.registerCosmetic(this, player)
    }

    override fun onPlayerMove() {
        Bukkit.getOnlinePlayers().forEach { players ->
            if (!players.canSee(player) && player != players) return@forEach
            players.playEffect(player.location.add(0.0, 0.2, 0.0), Effect.SPELL, 0)
        }
        super.onPlayerMove()
    }

    override fun destroy() {
        Cosmetics.unregisterCosmetic(this, player)
        super.destroy()
    }
}