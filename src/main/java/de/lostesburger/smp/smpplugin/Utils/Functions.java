package de.lostesburger.smp.smpplugin.Utils;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Functions {

    public static boolean removeDias(Player player){
        int itemC = getItemCount(player.getInventory(), Material.DIAMOND);
        int price = Main.config.getInt("tp.price");

        if(itemC >= price){
            ItemStack item = new ItemStack(Material.DIAMOND, price);
            player.getInventory().removeItem(new ItemStack [] {item});
            return true;
        }else {
            return false;
        }
    }


    public static int getItemCount(Inventory inv, Material mat){
        int count = 0;

        ItemStack[] items = inv.getContents();

        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getType() == mat) {
                count += items[i].getAmount();
            }
        }

        return count;
    }
}
