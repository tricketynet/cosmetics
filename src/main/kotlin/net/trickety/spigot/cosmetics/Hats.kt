package net.trickety.spigot.cosmetics

import net.trickety.spigot.cosmetics.hats.*
import org.bukkit.entity.Player

enum class Hats(private val clazz: Class<out Cosmetic>) {

    BECAON(Beacon::class.java),
    DAYLIGHT_DETECTOR(DaylightDetector::class.java),
    DISPENSER(Dispenser::class.java),
    DROPPER(Dropper::class.java),
    GLASS(Glass::class.java);

    fun create(player: Player): Cosmetic {
        return clazz.getConstructor(Player::class.java).newInstance(player)
    }
}