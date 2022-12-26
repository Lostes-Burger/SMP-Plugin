package de.lostesburger.smp.smpplugin.Listener.MoreEnchant;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class AnvilListener implements Listener {

    private ItemStack cloneItem1;

    @EventHandler
    public void onAnvil(PrepareAnvilEvent event){
        if(!Main.config.getBoolean("moreEnchant.enabled")) return;

        AnvilInventory aInv = event.getInventory();

        ItemStack item1 = aInv.getFirstItem();
        if(item1 == null) return;
        ItemStack item2 = aInv.getSecondItem();
        if(item2 == null) return;

        if(item2.getType() != Material.ENCHANTED_BOOK) return;

        EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) item2.getItemMeta();
        final boolean[] ench = {false};

        storageMeta.getStoredEnchants().forEach((enchantment, integer) -> item1.getEnchantments().forEach((enchantment2, integer2) -> {

                    if(!enchantment.canEnchantItem(item1)) return;

                    if (enchantment.getName() != enchantment2.getName()) return;


                    if (integer != integer2) return;

                    int newLvl = integer + Main.config.getInt("moreEnchant.addLvl");


                    if (newLvl != enchantment.getMaxLevel() + Main.config.getInt("moreEnchant.addLvl")) return;

                    cloneItem1 = item1.clone();

                    cloneItem1.addUnsafeEnchantment(enchantment, newLvl);
                    cloneItem1.getItemMeta().addEnchant(enchantment2, newLvl, false);

                    ench[0] = true;

            }
        )
        );

        if(!ench[0]) return;

        event.setResult(cloneItem1);
    }
}
