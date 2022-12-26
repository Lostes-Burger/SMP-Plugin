package de.lostesburger.smp.smpplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemStackCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        ItemStack stack = player.getInventory().getItemInMainHand();

        if(stack == null) return false;

        player.sendMessage(stack.toString());

        return false;
    }
}
