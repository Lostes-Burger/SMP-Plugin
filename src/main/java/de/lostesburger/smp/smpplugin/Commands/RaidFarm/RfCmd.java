package de.lostesburger.smp.smpplugin.Commands.RaidFarm;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RfCmd implements CommandExecutor, TabCompleter{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        String präfix = Main.config.getString("präfix");

        if(args.length != 1){
            player.sendMessage(präfix+"use /rf <tp, back, effect>");
            return false;
        }

        if(player.getHealth() != 20) {
            player.sendMessage(präfix+"§cDu musst volle Leben haben");
            return false;
        }

        switch (args[0]){
            case "tp":
                Main.lastPlayerPos.put(player.getUniqueId(), player.getLocation());
                World world = Bukkit.getWorld(Main.config.getString("rf.world"));
                Location loc = new Location(world, 74, 73, 803);
                player.teleport(loc);

                player.sendMessage(präfix+"§aDu wurdest teleportiert!");
                break;

            case "back":

                Location pos = null;

                if(Main.lastPlayerPos.get(player.getUniqueId()) != null){
                    pos = Main.lastPlayerPos.get(player.getUniqueId());
                }else {
                    World map = Bukkit.getWorld("world");
                    pos = new Location(map, 0, 108, 0);
                }

                player.teleport(pos);

                player.sendMessage(präfix+"§aDu wurdest teleportiert!");

                Main.lastPlayerPos.remove(player.getUniqueId());
                break;

            case "effect":
                player.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 9999 ,1 ), true);
                player.sendMessage(präfix+"§aDu hast nun den Effect Bad Omen!");
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
            list.add("effect");
        }

        return list;
    }
}
