package net.trickety.spigot.cosmetics

import net.trickety.spigot.cosmetics.hats.Glass
import org.bukkit.entity.Player

enum class Hats(private val clazz: Class<out Cosmetic>) {

    GLASS(Glass::class.java);

    fun create(player: Player): Cosmetic {
        return clazz.getConstructor(Player::class.java).newInstance(player)
    }

}