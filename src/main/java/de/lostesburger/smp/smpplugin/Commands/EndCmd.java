package de.lostesburger.smp.smpplugin.Commands;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EndCmd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!Main.config.getBoolean("end.enabled")) return false;

        if(!(sender instanceof Player)) return false;
        String präfix = Main.config.getString("präfix");

        Player player = (Player) sender;

        if(args.length != 1){
            player.sendMessage(präfix+"use /endp <tp>");
            return false;
        }

        if(player.getHealth() != 20) {
            player.sendMessage(präfix+"§cDu musst volle Leben haben");
            return false;
        }

        switch (args[0]){
            case "tp":
                World world = Bukkit.getWorld(Main.config.getString("end.world"));
                Location location = new Location(world, Main.config.getInt("end.x"), Main.config.getInt("end.y"), Main.config.getInt("end.z") );

                player.teleport(location);

                player.sendMessage(präfix+"§aDu wurdest teleportiert!");

                break;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        if(args.length == 1){
            list.add("tp");
        }

        return list;
    }
}
