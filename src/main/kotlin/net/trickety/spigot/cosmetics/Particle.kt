package net.trickety.spigot.cosmetics

import net.trickety.spigot.cosmetics.particle.*
import org.bukkit.entity.Player

enum class Particle(private val clazz: Class<out Cosmetic>) {

    BUBBLES(Bubbles::class.java),
    COLORS(Colors::class.java),
    ENDERMAN(Enderman::class.java),
    FLAMES(Flames::class.java),
    HEARTS(Hearts::class.java),
    MAGIC(Magic::class.java),
    NOTES(Notes::class.java),
    VILLAGER(Villager::class.java);

    fun create(player: Player): Cosmetic {
        return clazz.getConstructor(Player::class.java).newInstance(player)
    }
}