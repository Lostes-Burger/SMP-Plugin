package de.lostesburger.smp.smpplugin.Commands;

import de.lostesburger.smp.smpplugin.Main;

import de.lostesburger.smp.smpplugin.Utils.Functions;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class TpaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        if(!Main.config.getBoolean("tpa.enabled")) return false;

        String präfix = Main.config.getString("präfix");

        Player player = (Player) sender;
        if(args.length != 1){
            player.sendMessage(präfix+ "§c/tpa <player>");
            return false;
        }

        if(Functions.getItemCount(player.getInventory(), Material.DIAMOND) <= Main.config.getInt("tpa.price") ){
            player.sendMessage(präfix+Main.config.getString("tpa.priceError"));
            return false;
        }

        if (Main.config.getBoolean("tp.fullHealth")){
            if(player.getHealth() != 20){
                player.sendMessage(präfix+Main.config.getString("tpa.healthError"));
                return false;
            }
        }



        Player targetP = Bukkit.getPlayer(args[0]);
        if(targetP.getUniqueId() == player.getUniqueId()){
            player.sendMessage(präfix+Main.config.getString("tpa.ownTpaError"));
            return false;
        }
        if(targetP == null){
            player.sendMessage(präfix+Main.config.getString("tpa.playerNotFoundError"));
            return false;
        }

        TextComponent message = new TextComponent(präfix+ Main.config.getString("tpa.tpaMessage").replaceAll("-player-", player.getName()));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "tpaaccept "+player.getName()));

        //Component tpacceptMessage = Component.text(präfix+ Main.config.getString("tpa.tpaMessage").replaceAll("(player)", player.getName()));
        //tpacceptMessage.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "tpaaccept "+player.getName()))
          //                      .hoverEvent(HoverEvent.showText(Component.text(" "+Main.config.getString("tpa.hoverText"))));

        targetP.sendMessage(message);

        player.sendMessage(präfix+ Main.config.getString("tpa.tpaSendMessage"));

        if(Main.tpaRequests.containsKey(player.getName())){
            Main.tpaRequests.remove(player.getName());
        }

        Main.tpaRequests.put(player.getName(), targetP.getName());
        return false;
    }
}
