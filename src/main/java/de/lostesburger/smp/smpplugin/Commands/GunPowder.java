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
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GunPowder implements CommandExecutor, TabCompleter {

    HashMap<UUID, Location> lpp = new HashMap<UUID, Location>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;

        if(!Main.config.getBoolean("gunp.enabled")) return false;

        Player player = (Player) sender;
        String präfix = Main.config.getString("präfix");

        if(args.length != 1){
            player.sendMessage(präfix+"use /gunp <tp, back>");
            return false;
        }

        if(player.getHealth() != 20) {
            player.sendMessage(präfix+"§cDu musst volle Leben haben");
            return false;
        }

        switch (args[0]){
            case "tp":
                lpp.put(player.getUniqueId(), player.getLocation());

                World world = Bukkit.getWorld(Main.config.getString("gunp.world"));
                Location loc = new Location(world, Main.config.getInt("gunp.x"), Main.config.getInt("gunp.y"), Main.config.getInt("gunp.z"));
                player.teleport(loc);

                player.sendMessage(präfix+"§aDu wurdest teleportiert!");
                break;

            case "back":

                Location pos = null;

                if(lpp.get(player.getUniqueId()) != null){
                    pos = lpp.get(player.getUniqueId());
                }else {
                    World map = Bukkit.getWorld("world");
                    pos = new Location(map, 0, 108, 0);
                }

                player.teleport(pos);

                player.sendMessage(präfix+"§aDu wurdest teleportiert!");

                lpp.remove(player.getUniqueId());
                break;
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> list = new ArrayList<>();


        if(args.length == 1){
            list.add("tp");
            list.add("back");
        }

        return list;
    }
}
