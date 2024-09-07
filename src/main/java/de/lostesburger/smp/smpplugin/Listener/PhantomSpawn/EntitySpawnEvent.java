package de.lostesburger.smp.smpplugin.Listener.PhantomSpawn;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntitySpawnEvent implements Listener {

    @EventHandler
    public void onSpawn(org.bukkit.event.entity.EntitySpawnEvent event) {

        if(!Main.config.getBoolean("disablePhantomSpawn.enabled")) return;
        Entity entity = event.getEntity();

        if(entity.getType() == EntityType.PHANTOM){
            event.setCancelled(true);

            String replace = Main.config.getString("disablePhantomSpawn.replaceWith");

            if(replace != null) {
                EntityType type = EntityType.fromName(replace);
                Location loc = entity.getLocation();
                LivingEntity entity1 = (LivingEntity) loc.getWorld().spawnEntity(loc, type);

                if(Main.config.getBoolean("disablePhantomSpawn.slowFalling")){
                    entity1.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 9999 ,3));
                }

            }
        }

    }
}
