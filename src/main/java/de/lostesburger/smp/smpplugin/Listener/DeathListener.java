package de.lostesburger.smp.smpplugin.Listener;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event){

        Player player = event.getPlayer();
        String präfix = Main.config.getString("präfix");

        if(!Main.config.getBoolean("death.message")) return;

        Location loc = player.getLocation();

        String s = präfix+"§4Death-world: "+loc.getWorld().getName()+" §5x: "+loc.getBlockX()+" §by: "+loc.getBlockY()+" §az: "+loc.getBlockZ();

        player.sendMessage(s);
        System.out.println(s);
    }
}
