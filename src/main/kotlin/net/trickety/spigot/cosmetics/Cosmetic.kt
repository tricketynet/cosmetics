package net.trickety.spigot.cosmetics

import org.bukkit.entity.Player

abstract class Cosmetic(open val player: Player) {

    open fun onTick() {
        //
    }

    open fun onPlayerMove() {
        //
    }

    open fun destroy() {
        //
    }
}