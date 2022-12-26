package de.lostesburger.smp.smpplugin;

import de.lostesburger.smp.smpplugin.Commands.EndCmd;
import de.lostesburger.smp.smpplugin.Commands.GunPowder;
import de.lostesburger.smp.smpplugin.Commands.RaidFarm.RfCmd;
import de.lostesburger.smp.smpplugin.Commands.TpaCommand;
import de.lostesburger.smp.smpplugin.Commands.TpacceptCommand;
import de.lostesburger.smp.smpplugin.Listener.BeaconRange.BeaconRange;
import de.lostesburger.smp.smpplugin.Listener.BedrockBreak.BlockPlace;
import de.lostesburger.smp.smpplugin.Listener.DeathListener;
import de.lostesburger.smp.smpplugin.Listener.JoinEvent.PlayerJoin;
import de.lostesburger.smp.smpplugin.Listener.NotToExpensive.AnvilListener;
import de.lostesburger.smp.smpplugin.Listener.PhantomSpawn.EntitySpawnEvent;
import de.lostesburger.smp.smpplugin.Listener.QuitEvent.PlayerQuit;
import de.lostesburger.smp.smpplugin.Listener.SpawnerBreak.BlockBreak;
import de.lostesburger.smp.smpplugin.Listener.TeleportResistance.PlayerTeleport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.EntityBlockStorage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public final class Main extends JavaPlugin {

    public static FileConfiguration config;
    public static HashMap<String, String> tpaRequests = new HashMap<String, String>();
    public static HashMap<UUID, Location> lastPlayerPos = new HashMap<UUID, Location>();

    public static HashMap<UUID, UUID> gulagRequests = new HashMap<UUID, UUID>();
    public static HashMap<UUID, Long> gulagRequestsTime = new HashMap<UUID, Long>();
    public static HashMap<UUID, UUID> gulagCFight = new HashMap<UUID, UUID>();

    public static String präfix;

    private static Main instance;

    @Override
    public void onEnable() {
        boolean start = true;

        this.getLogger().log(Level.WARNING, "Starting Kleckzz SMP System v0.4");

        /**
         * Config file(s)
         */
        this.getLogger().log(Level.INFO, "Loading/Creating Config file(s)...");

        File confFile = new File(this.getDataFolder().getPath(), "config.yml");
        if(confFile == null){
            saveConfig();
        }
        saveDefaultConfig();
        config = getConfig();
        präfix = config.getString("präfix");

        this.getLogger().log(Level.INFO, "Loaded/Created Config file(s)");


        /**
         * Commands & Events
         */
        this.getLogger().log(Level.INFO, "Registering Command(s)/Listener(s)...");

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new DeathListener(), this);
        pm.registerEvents(new AnvilListener(), this);
        pm.registerEvents(new EntitySpawnEvent(), this);
        pm.registerEvents(new BlockBreak(), this);
        pm.registerEvents(new de.lostesburger.smp.smpplugin.Listener.MoreEnchant.AnvilListener(), this);
        pm.registerEvents(new BlockPlace(), this);
        pm.registerEvents(new BeaconRange(), this);
        pm.registerEvents(new PlayerJoin(), this);
        pm.registerEvents(new PlayerQuit(), this);
        pm.registerEvents(new PlayerTeleport(), this);
        //Register Commands
        getCommand("tpa").setExecutor(new TpaCommand());
        getCommand("tpaccept").setExecutor(new TpacceptCommand());
        getCommand("rf").setExecutor(new RfCmd());
        getCommand("endp").setExecutor(new EndCmd());
        getCommand("gunp").setExecutor(new GunPowder());
        getCommand("itemStackInfo").setExecutor(new ItemStackCMD());

        this.getLogger().log(Level.INFO, "Registerd Command(s)/Listener(s)");

        /**
         * Custom recipe(s)
         */
        this.getLogger().log(Level.INFO, "Registering Custom recipe(s)...");

        if(Main.config.getBoolean("customCrafting.invisItemFrames")){
            createInvisItemFramesRecipe();
        }

        this.getLogger().log(Level.INFO, "Registerd Custom recipe(s)");

        if(!start){
            getLogger().log(Level.WARNING, "Kleckzz SMP Plugin stopped due to error!");
            Runtime.getRuntime().halt(0);
        }
        instance = this;


    }

    public void createInvisItemFramesRecipe(){
        ItemStack stack = new ItemStack(Material.ITEM_FRAME, 1);
        //EntityBlockStorage storage = (EntityBlockStorage) stack.getData();

        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName("§cInvisible Item frame");
        meta.getPersistentDataContainer().set(new NamespacedKey(this, "invisible"), PersistentDataType.BYTE, (byte) 1);
        stack.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "invisItemFrame"), stack);
        recipe.shape(" X ", " P ", " X ");
        recipe.setIngredient('X', Material.GLASS);
        recipe.setIngredient('P', Material.ITEM_FRAME);

        if(getServer().getRecipe(new NamespacedKey(this, "invisItemFrame")) != null  ){
            getServer().removeRecipe(new NamespacedKey(this, "invisItemFrame"));
        }

        getServer().addRecipe(recipe);
    }

    public static Plugin getInstance(){
        return instance;
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.WARNING, "Kleckzz SPM Plugin Shut down!");
    }
}
