package de.lostesburger.smp.smpplugin.Listener.InvisItemFrames;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class CreateInvisFramesRecipe {
    public static void create(){
        ItemStack stack = new ItemStack(Material.ITEM_FRAME, 1);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("§cInvisible Item frame");
        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), Main.invisFrameKey), PersistentDataType.BYTE, (byte) 1);
        stack.setItemMeta(meta);
        Main.invisFrame = stack;

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(Main.getInstance(), "invisItemFrame"), stack);
        recipe.shape(" X ", " P ", " X ");
        recipe.setIngredient('X', Material.GLASS);
        recipe.setIngredient('P', Material.ITEM_FRAME);

        if(Main.getInstance().getServer().getRecipe(new NamespacedKey(Main.getInstance(), "invisItemFrame")) != null  ){
            Main.getInstance().getServer().removeRecipe(new NamespacedKey(Main.getInstance(), "invisItemFrame"));
        }

        Main.getInstance().getServer().addRecipe(recipe);
    }
}