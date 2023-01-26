package de.lostesburger.smp.smpplugin.Listener.JoinEvent;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(Main.config.getString("join.message") == null) {
            event.setJoinMessage(null);
        }else {
            event.setJoinMessage(Main.config.getString("join.message").replace("{player}", event.getPlayer().getName()));
        }

        Player player = event.getPlayer();

        if(Main.config.getBoolean("join.sound.enabled")){
            player.playSound(player.getLocation(), Sound.valueOf(Main.config.getString("join.sound.sound")), 100.0F, 1.0F);
        }

        player.sendTitle("§bKleckzz §8● §cANACHY","§9§lCashing your data from the server...", 1,80,1);
    }
}
