package de.lostesburger.smp.smpplugin;

import de.lostesburger.corelib.Chat.CommandRestriction.Commands.CommandPreventManager;
import de.lostesburger.corelib.NMS.Minecraft;
import de.lostesburger.corelib.NMS.Version;
import de.lostesburger.smp.smpplugin.Utils.RegisterCommands;
import de.lostesburger.smp.smpplugin.Utils.RegisterListeners;
import de.lostesburger.smp.smpplugin.Utils.RegisterRecipes;
import de.lostesburger.smp.smpplugin.Utils.Scheduler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public final class Main extends JavaPlugin { 
    public static ArrayList<Scheduler.Task> schedulers = new ArrayList<Scheduler.Task>();
    public static FileConfiguration config;
    public static HashMap<String, String> tpaRequests = new HashMap<String, String>();
    public static String präfix;
    private static Plugin instance;
    public static ItemStack invisFrame;
    public static String invisFrameKey = "invisibleKey";
    public static Version McVer;

    @Override
    public void onEnable() {
        boolean start = true;
        instance = this;

        this.getLogger().log(Level.WARNING, "Starting SPM Plugin v4.0");
        McVer = Minecraft.getVersion();

        if(McVer != Version.v1_21){
            this.getLogger().log(Level.SEVERE, "SMP Plugin using API 1.21 (downgrading Deprecated no full support)");
        }

        if(Scheduler.isFolia()){
            this.getLogger().severe("Server is running Folia, a version not fully supported by this plugin");
            this.getLogger().warning("Not supported features:");
            this.getLogger().warning("- Command blocking");
            this.getLogger().warning("- Anvil dupe");
            this.getLogger().warning("Unknown errors in the game itself can occur (including major security flaws)");
            this.getLogger().warning("or errors not found inside this plugin");
        }


        /**
         * Config file(s)
         */
        this.getLogger().log(Level.INFO, "Loading/Creating Configuration...");
        File confFile = new File(this.getDataFolder().getPath(), "config.yml");
        if(confFile == null) saveConfig();
        saveDefaultConfig();
        config = getConfig();
        präfix = config.getString("präfix");

        this.getLogger().log(Level.INFO, "Loaded/Created Configuration");

        /**
         * Commands & Events
         */
        this.getLogger().log(Level.INFO, "Registering Commands/Listeners...");
        new RegisterListeners(this);
        new RegisterCommands(this);

        /**
         * Custom recipe(s)
         */
        this.getLogger().log(Level.INFO, "Registering Custom recipe(s)...");
        new RegisterRecipes(this);

        if(!start){
            getLogger().log(Level.WARNING, "SPM Plugin stopped due to error!");
            Runtime.getRuntime().halt(0);
        }
    }


    public static Plugin getInstance(){
        return instance;
    }

    @Override
    public void onDisable() {
        schedulers.forEach(task -> {
            getLogger().log(Level.INFO, "Scheduler stopped!");
            task.cancel();
        });
        getLogger().log(Level.WARNING, "SPM Plugin Shut down!");
    }
}
