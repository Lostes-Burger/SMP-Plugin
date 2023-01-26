package de.lostesburger.smp.smpplugin.Listener.CustomRecipe;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class OpGap {

    public static void registerRecipie(){
        ItemStack stack = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1);

        ItemMeta meta = stack.getItemMeta();

        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "custom-crafted"), PersistentDataType.BYTE, (byte) 1);
        stack.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(Main.getInstance(), "craftedEnchApple"), stack);
        recipe.shape("XXX", "XPX", "XXX");
        recipe.setIngredient('X', Material.GOLD_BLOCK);
        recipe.setIngredient('P', Material.APPLE);

        if(Main.getInstance().getServer().getRecipe(new NamespacedKey(Main.getInstance(), "craftedEnchApple")) != null  ){
            Main.getInstance().getServer().removeRecipe(new NamespacedKey(Main.getInstance(), "craftedEnchApple"));
        }

        Main.getInstance().getServer().addRecipe(recipe);
    }

}
