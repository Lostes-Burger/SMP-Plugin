package de.lostesburger.smp.smpplugin.Listener.InvisItemFrames;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class HangPlaceBlockListener implements Listener {

    public boolean isFrameEntity(Entity entity){
        if(entity.getType() == EntityType.ITEM_FRAME){
            return true;
        }else {
            return false;
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    private void onHangingPlace(HangingPlaceEvent event)
    {
        if(!isFrameEntity(event.getEntity()) || event.getPlayer() == null)  return;

        ItemStack frame;
        Player p = event.getPlayer();
        if(p.getInventory().getItemInMainHand().getType() == Material.ITEM_FRAME ) {
            frame = p.getInventory().getItemInMainHand();
        }
        else if(p.getInventory().getItemInOffHand().getType() == Material.ITEM_FRAME) {
            frame = p.getInventory().getItemInOffHand();
        }
        else return;


        if(frame.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.getInstance(), Main.invisFrameKey), PersistentDataType.BYTE))
        {
            ItemFrame itemFrame = (ItemFrame) event.getEntity();
            itemFrame.setVisible(false);

            event.getEntity().getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), Main.invisFrameKey), PersistentDataType.BYTE, (byte) 1);
        }

    }
}
