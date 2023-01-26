package de.lostesburger.smp.smpplugin.CommandOveride;

import de.lostesburger.smp.smpplugin.Main;
import de.lostesburger.smp.smpplugin.Utils.Functions;
import io.papermc.paper.event.player.PlayerSignCommandPreprocessEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.server.TabCompleteEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class PreCommandEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)

    public void onPreCommand(PlayerCommandPreprocessEvent event){
        String[] message = event.getMessage().replaceAll("/","").split(" ");
        Player player = event.getPlayer();

        if(player.hasPermission("commandBlock.bypass")) return;
        message[0].replaceAll(" ", "");

        if(event.getMessage().replaceAll("/", "").contains(":")){
            event.setCancelled(true);
            player.sendMessage(Main.präfix+Main.config.getString("commandBlock.subCMDdisabled"));
            return;
        }

        Main.config.getStringList("commandBlock.cmds").forEach(s -> {

            if(message[0].equalsIgnoreCase(s)){
                event.setCancelled(true);
                player.sendMessage(Main.präfix+Main.config.getString("commandBlock.message"));
            }

        });

        if(event.isCancelled()) return;

        String replace = Main.config.getString("commandBlock.replace."+message[0]);

        if(replace == null) return;
        event.setCancelled(true);
        player.performCommand(replace);

    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onTab(PlayerCommandSendEvent event) {
        Player player = event.getPlayer();

        if(player.hasPermission("commandBlock.bypass")) return;

        ArrayList<String> cmds = new ArrayList<>();

        event.getCommands().forEach(cmd -> {
            if(cmd.contains(":") && Main.config.getBoolean("commandBlock.hideSubCommands")){
                return;
            }
            cmds.add(cmd.replaceAll(" ",""));
        });

        event.getCommands().clear();

        if(!Main.config.getBoolean("commandBlock.hideAllNotPluginCMDS")){

            Main.config.getStringList("commandBlock.cmds").forEach(s -> {
                s.replaceAll(" ","");

                if(cmds.contains(s)){
                    cmds.remove(s);
                }

            });
        }


        event.getCommands().addAll(cmds);
    }
}
