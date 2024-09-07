package de.lostesburger.smp.smpplugin.Utils;

import de.lostesburger.smp.smpplugin.Commands.DisEnchant;
import de.lostesburger.smp.smpplugin.Commands.EndCmd;
import de.lostesburger.smp.smpplugin.Commands.TpaCommand;
import de.lostesburger.smp.smpplugin.Commands.TpacceptCommand;
import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class RegisterCommands {
    public RegisterCommands(JavaPlugin plugin){
        if(Main.config.getBoolean("tpa.enabled")){
            plugin.getCommand("tpa").setExecutor(new TpaCommand());
            plugin.getCommand("tpaccept").setExecutor(new TpacceptCommand());
            plugin.getLogger().log(Level.INFO, "Registered /tpa Command");
            plugin.getLogger().log(Level.INFO, "Registered /tpaccept Command");
        }
        if(Main.config.getBoolean("end.enabled")){
            plugin.getCommand("endp").setExecutor(new EndCmd());
            plugin.getLogger().log(Level.INFO, "Registered /endp Command");
        }
        if(Main.config.getBoolean("disEnchantCommand.enabled")){
            plugin.getCommand("disEnchant").setExecutor(new DisEnchant());
            plugin.getLogger().log(Level.INFO, "Registered /disEnchant Command");
        }
    }
}
