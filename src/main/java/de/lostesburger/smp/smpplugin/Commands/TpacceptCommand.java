package de.lostesburger.smp.smpplugin.Commands;

import de.lostesburger.smp.smpplugin.Main;

import de.lostesburger.smp.smpplugin.Utils.Functions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpacceptCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;

        if(!Main.config.getBoolean("tp.enabled")) return false;


        String präfix = Main.config.getString("präfix");

        Player player = (Player) sender;

        if(args.length != 1){
            player.sendMessage(präfix+ "§cBenutze /tpaccept <player>");
            return false;
        }

        Player targetP = Bukkit.getPlayer(args[0]);

        if(targetP == null){
            player.sendMessage(präfix+"§cError: Player konnte nicht gefunden werden!");
            return false;
        }

        String tpatarget = Main.tpaRequests.get(targetP.getName());

        if(tpatarget != player.getName()){
            player.sendMessage(präfix+ "§cError: Die Tpa Anfrage wurde nicht an dich gestellt!");
            return false;
        }

        Main.tpaRequests.remove(targetP.getName());

        if(!Functions.removeDias(targetP)){
            targetP.sendMessage(präfix+"§cKeine Dias du lutscha");
            return false;
        }

        Location loc = player.getLocation();

        targetP.teleport(loc);

        targetP.sendMessage(präfix+"§aDu wurdest teleportiert!");
        player.sendMessage(präfix+ "§5 "+targetP.getName()+"§a wurde teleportiert!");
        return false;
    }
}
