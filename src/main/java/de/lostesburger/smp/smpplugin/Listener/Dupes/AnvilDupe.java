package de.lostesburger.smp.smpplugin.Listener.Dupes;

import de.lostesburger.smp.smpplugin.Main;
import de.lostesburger.smp.smpplugin.Utils.Scheduler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Deprecated
public class AnvilDupe implements Listener {
    @Deprecated
    @EventHandler(priority = EventPriority.HIGH)
    public void onAnvilUse(InventoryClickEvent e) {
        if(!Main.config.getBoolean("dupes.anvilDupe.enabled")) return;

        Inventory t = e.getClickedInventory();

        if (t == null){
            return;
        }
        if (t.getType() != InventoryType.ANVIL){
            return;
        }

        if(e.getCurrentItem() == null){
            return;
        }

        if(e.getRawSlot() != 2){
            return;
        }

        Location l = e.getClickedInventory().getLocation();
        ItemStack toDupe = e.getCurrentItem();
        Player p = (Player) e.getWhoClicked();
        if(toDupe == null) return;

        ItemStack duped = toDupe.clone();

        Scheduler.Task task = Scheduler.runLaterAsync(() ->
        {
            if (l.getBlock().getType() == Material.AIR) {
                l.getWorld().dropItemNaturally(p.getLocation(), duped).setVelocity(p.getEyeLocation().getDirection());
            }

        }, 5);

        Main.schedulers.add(task);
    }


}
