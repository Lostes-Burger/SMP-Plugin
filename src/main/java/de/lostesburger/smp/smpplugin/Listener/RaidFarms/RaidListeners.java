package de.lostesburger.smp.smpplugin.Listener.RaidFarms;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Raider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RaidListeners implements Listener {
    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        Player damager = e.getEntity().getKiller();
        LivingEntity damaged = e.getEntity();
        if (damager != null) {
            if (damaged instanceof Raider) {
                Raider raider = (Raider)damaged;

                if (raider.isPatrolLeader() && raider.getRaid() == null ) {
                    int amplifier = 0;
                    PotionEffect effect = damager.getPotionEffect(PotionEffectType.BAD_OMEN);
                    if (effect != null) {
                        amplifier = effect.getAmplifier();
                        damager.removePotionEffect(PotionEffectType.BAD_OMEN);
                    }

                    PotionEffect newEffect = new PotionEffect(PotionEffectType.BAD_OMEN, 120000, amplifier, false, false, true);
                    if (Boolean.FALSE.equals(damaged.getWorld().getGameRuleValue(GameRule.DISABLE_RAIDS))) {
                        damager.addPotionEffect(newEffect);
                    }

                    if (Main.config.getBoolean("raidFarms.preventBottleDrops")) {
                        e.getDrops().removeIf((item) -> {
                            return item.getType() == Material.OMINOUS_BOTTLE;
                        });
                    }
                }

            }
        }
    }

    @EventHandler
    public void onPotion(EntityPotionEffectEvent e) {
        if (e.getEntity() instanceof Player && e.getNewEffect() != null && e.getNewEffect().getType() == PotionEffectType.RAID_OMEN && e.getNewEffect().getDuration() > 1) {
            e.setCancelled(true);
            ((Player)e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.RAID_OMEN, 1, e.getNewEffect().getAmplifier(), false, true, true));
        }

    }
}
