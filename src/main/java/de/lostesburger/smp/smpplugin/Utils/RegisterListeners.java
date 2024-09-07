package de.lostesburger.smp.smpplugin.Utils;

import de.lostesburger.corelib.Chat.CommandRestriction.Commands.CommandPreventManager;
import de.lostesburger.corelib.Chat.CommandRestriction.TabComplete.TabPreventManager;
import de.lostesburger.corelib.NMS.Version;
import de.lostesburger.smp.smpplugin.Listener.BeaconRange.BeaconRange;
import de.lostesburger.smp.smpplugin.Listener.BedrockBreak.BlockPlace;
import de.lostesburger.smp.smpplugin.Listener.Death.DeathListener;
import de.lostesburger.smp.smpplugin.Listener.Dupes.AnvilDupe;
import de.lostesburger.smp.smpplugin.Listener.JoinEvent.PlayerJoin;
import de.lostesburger.smp.smpplugin.Listener.NotToExpensive.AnvilListener;
import de.lostesburger.smp.smpplugin.Listener.PhantomSpawn.EntitySpawnEvent;
import de.lostesburger.smp.smpplugin.Listener.QuitEvent.PlayerQuit;
import de.lostesburger.smp.smpplugin.Listener.RaidFarms.RaidListeners;
import de.lostesburger.smp.smpplugin.Listener.TeleportResistance.PlayerTeleport;
import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.logging.Level;

public class RegisterListeners {
    public RegisterListeners(Plugin plugin){
        PluginManager pm = Bukkit.getPluginManager();
        if(Main.config.getBoolean("death.message")){
            pm.registerEvents(new DeathListener(), plugin);
            plugin.getLogger().log(Level.INFO, "Registered Death message listener(s)");
        }
        if(Main.config.getBoolean("notToExpensive.enabled")){
            pm.registerEvents(new AnvilListener(), plugin);
            plugin.getLogger().log(Level.INFO, "Registered not to expensive listener(s)");
        }
        if(Main.config.getBoolean("disablePhantomSpawn.enabled")){
            pm.registerEvents(new EntitySpawnEvent(), plugin);
            plugin.getLogger().log(Level.INFO, "Registered disable phantom spawn listener(s)");
        }
        if(Main.config.getBoolean("moreEnchant.enabled")){
            pm.registerEvents(new de.lostesburger.smp.smpplugin.Listener.MoreEnchant.AnvilListener(), plugin);
            plugin.getLogger().log(Level.INFO, "Registered more enchant listener(s)");
        }
        if(Main.config.getBoolean("bedrock.break")){
            pm.registerEvents(new BlockPlace(), plugin);
            plugin.getLogger().log(Level.INFO, "Registered break bedrock listener(s)");
        }
        if(Main.config.getBoolean("beacon.range.enabled")){
            pm.registerEvents(new BeaconRange(), plugin);
            plugin.getLogger().log(Level.INFO, "Registered beacon range listener(s)");
        }
        if(Main.config.getBoolean("join.enabled")){
            pm.registerEvents(new PlayerJoin(), plugin);
            plugin.getLogger().log(Level.INFO, "Registered player join listener(s)");
        }
        if(Main.config.getBoolean("leave.enabled")){
            pm.registerEvents(new PlayerQuit(), plugin);
            plugin.getLogger().log(Level.INFO, "Registered player leave listener(s)");
        }
        if(Main.config.getBoolean("teleportResistance.enabled")){
            pm.registerEvents(new PlayerTeleport(), plugin);
            plugin.getLogger().log(Level.INFO, "Registered teleport resistance listener(s)");
        }
        if(Main.config.getBoolean("command.enabled") ){
            new CommandPreventManager(Main.getInstance(),
                    (ArrayList<String>) Main.config.getStringList("command.blacklist"),
                    Main.config.getBoolean("command.disableSubCommands"),
                    Main.config.getMapList("command.replace"),
                    Main.präfix+Main.config.getString("command.blacklistError"),
                    Main.präfix+Main.config.getString("command.subCommandError"),
                    Main.config.getString("command.blacklistBypassPerm")
            );
            plugin.getLogger().log(Level.INFO, "Registered command blacklist listener(s)");

            if(Main.config.getBoolean("command.modifyTabComplete")){
                new TabPreventManager(Main.getInstance(),
                        (ArrayList<String>) Main.config.getStringList("command.blacklist"),
                        Main.config.getBoolean("command.hideSubCommands"),
                        Main.config.getMapList("command.replace"),
                        Main.config.getString("command.blacklistBypassPerm")
                );
                plugin.getLogger().log(Level.INFO, "Registered tab complete restricting listener(s)");
            }

        }
       if(Main.config.getBoolean("dupes.anvilDupe.enabled")){
            pm.registerEvents(new AnvilDupe(), plugin);
            plugin.getLogger().log(Level.INFO, "Registered anvil dupe listener(s)");
       }
        if(Main.config.getBoolean("AllowMendingInfinityBow.enabled")){
            pm.registerEvents(new de.lostesburger.smp.smpplugin.Listener.MendingInfinityBow.AnvilListener(), plugin);
            plugin.getLogger().log(Level.INFO, "Registered Mending-Infinity Bow listener(s)");
        }
        if(Main.config.getBoolean("raidFarms.enabled")){
            if(Main.McVer != Version.v1_21){
                plugin.getLogger().warning("You are running a Server Version below 1.21 no need to re-enable Raid Farms!");
                Main.config.set("raidFarms.enabled", false);
            }else {
                pm.registerEvents(new RaidListeners(), plugin);
                plugin.getLogger().log(Level.INFO, "Registered Raid Farms listener(s)");
            }

        }
    }
}
