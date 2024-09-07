package de.lostesburger.smp.smpplugin.Listener.MendingInfinityBow;

import de.lostesburger.smp.smpplugin.Main;
import de.lostesburger.smp.smpplugin.Utils.Scheduler;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;


public class AnvilListener implements Listener {

    @EventHandler
    public void onAnvil(PrepareAnvilEvent event) {
        if(!Main.config.getBoolean("AllowMendingInfinityBow.enabled")) return;
        AnvilInventory aInv = event.getInventory();
        ItemStack item1 = aInv.getFirstItem();
        if(item1 == null) return;
        ItemStack item2 = aInv.getSecondItem();
        if(item2 == null) return;
        if(item1.getType() != Material.BOW) return;
        if(!item1.getEnchantments().containsKey(Enchantment.MENDING) && !item1.getEnchantments().containsKey(Enchantment.INFINITY)) return;
        if(item1.getEnchantments().containsKey(Enchantment.MENDING) && item1.getEnchantments().containsKey(Enchantment.INFINITY)) return;

        Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
        if(item2.getType() == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) item2.getItemMeta();
            enchantments = storageMeta.getStoredEnchants();
        }else {
            enchantments = item2.getEnchantments();
        }
        if(enchantments.size() == 0) return;


        HashMap<Enchantment, Integer> map = new HashMap<Enchantment, Integer>();
        map.put(Enchantment.INFINITY, 1);
        map.put(Enchantment.MENDING, 1);

        item1.getEnchantments().forEach((Enchantment enchantment, Integer integer) -> {
            if(map.containsKey(enchantment)){
                map.remove(enchantment);
            }
        });

        if(map.size() != 1) return;

        ItemStack stack = item1.clone();

        map.forEach((Enchantment enchantment, Integer integer) -> {
            ItemMeta meta = stack.getItemMeta();
            meta.addEnchant(enchantment, integer, true);
            stack.setItemMeta(meta);
            //stack.addUnsafeEnchantment(enchantment, integer);
        });



        event.setResult(stack);
        Scheduler.run(() ->{
            event.getInventory().setRepairCost(25);
        });
    }
}
