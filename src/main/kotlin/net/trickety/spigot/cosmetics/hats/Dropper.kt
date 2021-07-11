package net.trickety.spigot.cosmetics.hats

import net.trickety.spigot.cosmetics.Cosmetic
import net.trickety.spigot.cosmetics.Cosmetics
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Dropper(player: Player): Cosmetic(player) {

    init {
        this.player.inventory.helmet = ItemStack(Material.DROPPER)
        Cosmetics.registerCosmetic(this, player)
    }

    override fun destroy() {
        Cosmetics.unregisterCosmetic(this, player)
        this.player.inventory.helmet = null
        super.destroy()
    }
}