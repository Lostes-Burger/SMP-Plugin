package de.lostesburger.smp.smpplugin.Utils;

import de.lostesburger.smp.smpplugin.Listener.CustomRecipe.OpGap;
import de.lostesburger.smp.smpplugin.Listener.InvisItemFrames.CreateInvisFramesRecipe;
import de.lostesburger.smp.smpplugin.Listener.InvisItemFrames.HangPlaceBlockListener;
import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class RegisterRecipes {
    public RegisterRecipes(JavaPlugin plugin){
        Scheduler.Task recipes = Scheduler.runLater(() -> {
            if(Main.config.getBoolean("customCrafting.invisItemFrames")){
                CreateInvisFramesRecipe.create();
                Bukkit.getPluginManager().registerEvents(new HangPlaceBlockListener(), plugin);
                plugin.getLogger().log(Level.INFO, "Registered Custom recipe: Invisible Item Frames");
            }

            if(Main.config.getBoolean("customCrafting.enchantedApple")){
                OpGap.registerRecipie();
                plugin.getLogger().log(Level.INFO, "Registered Custom recipe: Enchanted Golden Apples");
            }
        }, 90);
        Main.schedulers.add(recipes);
    }
}
