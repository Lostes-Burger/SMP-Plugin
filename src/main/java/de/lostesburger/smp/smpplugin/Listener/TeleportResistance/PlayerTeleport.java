package de.lostesburger.smp.smpplugin.Listener.TeleportResistance;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerTeleport implements Listener {
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
        if(!Main.config.getBoolean("teleportResistance.enabled")) return;

        if(event.getCause() == PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT) return;
        if(event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) return;
        if(event.getFrom().distance(event.getTo()) < 100) return;

        Player player = event.getPlayer();

        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Main.config.getInt("teleportResistance.time"), Main.config.getInt("teleportResistance.lvl") ));
    }
}
