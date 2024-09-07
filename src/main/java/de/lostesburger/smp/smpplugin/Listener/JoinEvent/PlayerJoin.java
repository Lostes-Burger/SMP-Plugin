package de.lostesburger.smp.smpplugin.Listener.JoinEvent;

import de.lostesburger.corelib.Chat.ColorUtils.ColorUtils;
import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


import java.util.List;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(!Main.config.getBoolean("join.enabled")) return;

        String joinMsg = Main.config.getString("join.message");
        if(joinMsg == null || joinMsg.equals("")) {
            event.setJoinMessage(null);
        }else {
            if(!event.getPlayer().hasPlayedBefore()){
                List<String> title = Main.config.getStringList("join.first.title");
                if(title != null){
                    if(title.size() == 2){
                        event.getPlayer().sendTitle(title.get(0), title.get(1), 1,Main.config.getInt("join.first.titleDuration"), 1);
                    }
                }
                String firstJoinMsg = Main.config.getString("join.first.message").replace("{player}", event.getPlayer().getName());
                if(firstJoinMsg == null || firstJoinMsg.equals("")) {
                    event.setJoinMessage(null);
                }else {
                    event.setJoinMessage(ColorUtils.toColor(firstJoinMsg));
                }

            }else {
                event.setJoinMessage(joinMsg.replace("{player}", event.getPlayer().getName()));
            }
        }

        Player player = event.getPlayer();

        if(Main.config.getBoolean("join.sound.enabled")){

            player.playSound(player.getLocation(), Sound.valueOf(Main.config.getString("join.sound.sound")), 100.0F, 1.0F);
        }
    }
}
