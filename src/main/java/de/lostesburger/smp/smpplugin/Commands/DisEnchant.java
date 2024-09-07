package de.lostesburger.smp.smpplugin.Commands;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisEnchant implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!Main.config.getBoolean("disEnchantCommand.enabled")) return false;

        if(!(sender instanceof Player)) return false;
        String präfix = Main.config.getString("präfix");
        Player player = (Player) sender;
        if(Main.config.getString("disEnchantCommand.permission") != null){
            if(!player.hasPermission(Main.config.getString("disEnchantCommand.permission"))){
                player.sendMessage(Main.präfix+Main.config.getString("disEnchantCommand.message"));
                return false;
            }
        }

        if(args.length != 1){
            player.sendMessage(präfix+"§cuse /disEnchant <enchantment>");
            return false;
        }

        Enchantment enchantment = Enchantment.getByName(args[0]);
        if(enchantment == null){
            player.sendMessage(präfix+"§cselected enchantment is not present on item hold");
            return false;
        }

        ItemStack item = player.getInventory().getItemInMainHand().clone();

        Map<Enchantment, Integer> enchantments = item.getEnchantments();
        if(item.getType() == Material.ENCHANTED_BOOK) {
            player.sendMessage(präfix+"§cEnchanted-Books not supported");
            return false;
        }
        if(enchantments.size() == 0) return false;

        if(enchantments.containsKey(enchantment)){
            item.removeEnchantment(enchantment);
            player.getInventory().setItemInMainHand(item);
            player.sendMessage(präfix+"§aEnchantment removed");
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return null;
        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();

        Map<Enchantment, Integer> enchantments = item.getEnchantments();
        if(item.getType() == Material.ENCHANTED_BOOK) {
            return null;
        }

        List<String> stingList = new ArrayList<String>();

        enchantments.forEach((Enchantment enchantment, Integer integer) -> {
            stingList.add(enchantment.getName());
        });
        return stingList;
    }
}
