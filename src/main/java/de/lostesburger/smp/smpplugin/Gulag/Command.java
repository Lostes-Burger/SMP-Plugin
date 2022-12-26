package de.lostesburger.smp.smpplugin.Gulag;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player killerP = (Player) sender;

        if(args.length != 2){
            killerP.sendMessage(GulagFunctions.sendGulagMessage("§e|§cBenutze /gulag accept <player>"));
            return false;
        }

        switch (args[0]){
            case "accept":
                Player killedP = Bukkit.getPlayer(args[1]);
                if(killedP == null) {
                    killerP.sendMessage(GulagFunctions.sendGulagMessage("§e|§cBenutze /gulag accept <player> (Player = null)"));
                    return false;
                }

                UUID killerUUID = Main.gulagRequests.get(killedP.getUniqueId());

                if(killerUUID == null){
                    killerP.sendMessage("§e|§c Dieser Spieler hat kein offenes Gulag spiel!");
                    return false;
                }

                if(killerUUID != killerP.getUniqueId()){
                    killerP.sendMessage("§e|§c Dieser Spieler hat kein offenes Gulag spiel gegen dich!");
                    return false;
                }



                break;

        }

        return false;
    }
}
