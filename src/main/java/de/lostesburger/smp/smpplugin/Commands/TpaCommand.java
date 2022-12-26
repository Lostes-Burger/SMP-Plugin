package de.lostesburger.smp.smpplugin.Commands;

import de.lostesburger.smp.smpplugin.Main;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class TpaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;

        if(!Main.config.getBoolean("tp.enabled")) return false;


        String präfix = Main.config.getString("präfix");

        Player player = (Player) sender;

        if (Main.config.getBoolean("tp.fullHealth")){
            if(player.getHealth() != 20){
                player.sendMessage(präfix+" §cDu brauchst Volle Leben, um dich teleportieren zu können!");
                return false;
            }
        }

        if(args.length != 1){
            player.sendMessage(präfix+ " §cBenutze /tpa <player>");
            return false;
        }

        Player targetP = Bukkit.getPlayer(args[0]);

        if(targetP == null){
            player.sendMessage(präfix+" §cError: Player konnte nicht gefunden werden!");
            return false;
        }

        TextComponent message = new net.md_5.bungee.api.chat.TextComponent(präfix+ "§5 "+player.getName()+" §aHat angefragt sich teleportieren zu können! \n \n §c- Klicke hier zum Akzeptieren!");
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "tpaaccept "+player.getName()));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cTpa request annehmen!").create()));

        targetP.spigot().sendMessage(message);

        player.sendMessage(präfix+ " §5Tpa request wurde gesendet an §a"+targetP.getName());

        if(Main.tpaRequests.containsKey(player.getName())){
            Main.tpaRequests.remove(player.getName());
        }

        Main.tpaRequests.put(player.getName(), targetP.getName());
        return false;
    }
}
