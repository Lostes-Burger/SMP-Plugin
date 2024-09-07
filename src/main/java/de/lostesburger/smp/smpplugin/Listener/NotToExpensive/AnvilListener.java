package de.lostesburger.smp.smpplugin.Listener.NotToExpensive;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;


public class AnvilListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)

    public void onAnvil(PrepareAnvilEvent event){
        if(!Main.config.getBoolean("notToExpensive.enabled")) return;

        AnvilInventory aInv = event.getInventory();
        aInv.setMaximumRepairCost(Integer.MAX_VALUE);

        if(event.getResult() == null) return;
        if(aInv.getRepairCost() >= 40){
            aInv.setRepairCost(Main.config.getInt("notToExpensive.setPrice"));
        }
    }
}
