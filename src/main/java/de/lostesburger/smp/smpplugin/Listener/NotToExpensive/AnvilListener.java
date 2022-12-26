package de.lostesburger.smp.smpplugin.Listener.NotToExpensive;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;

import java.util.logging.Level;

public class AnvilListener implements Listener {
    //long lastCalled = System.currentTimeMillis();

    @EventHandler(priority = EventPriority.HIGHEST)

    public void onAnvil(PrepareAnvilEvent event){
        if(!Main.config.getBoolean("notToExpensive.enabled")) return;

        AnvilInventory aInv = event.getInventory();

        aInv.setMaximumRepairCost(Integer.MAX_VALUE);


        if(event.getResult() == null) return;

        Player player = (Player) event.getViewers().get(0);

        if(aInv.getRepairCost() >= 40){
            aInv.setRepairCost(Main.config.getInt("notToExpensive.setPrice"));
            // Main.getInstance().getLogger().log(Level.INFO, player.getName()+" bypassed the anvil to Expensive!");
        }
    }
}
