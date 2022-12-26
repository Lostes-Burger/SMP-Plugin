package de.lostesburger.smp.smpplugin.Listener.QuitEvent;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onJoin(PlayerQuitEvent event) {

        if (Main.config.getString("leave.message") == null) {
            event.setQuitMessage(null);
        } else {
            event.setQuitMessage(Main.config.getString("leave.message").replace("{player}", event.getPlayer().getName()));
        }
    }
}
