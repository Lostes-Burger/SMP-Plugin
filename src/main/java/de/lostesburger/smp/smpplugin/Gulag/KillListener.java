package de.lostesburger.smp.smpplugin.Gulag;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.*;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class KillListener implements Listener {
    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        Player killedP = (Player) event.getEntity();
        Player killerP = (Player) event.getEntity().getKiller();

        if(killerP == null) return;
        event.setKeepInventory(true);

        PlayerInventory killedPInv = killedP.getInventory();
        killedP.spigot().respawn();
        killedP.setGameMode(GameMode.SPECTATOR);

        Main.gulagRequests.put(killedP.getUniqueId(), killerP.getUniqueId());
        Main.gulagRequestsTime.put(killedP.getUniqueId(), System.currentTimeMillis());

        killedP.sendMessage(GulagFunctions.sendGulagMessage("§e|§c Du wurdest von §5"+killerP.getName()+" §Cgetötet! \n§e|§9 Der killer hat 1 min Zeit dem Mach beizutreten!"));
        killerP.sendMessage(GulagFunctions.sendGulagMessage("§e|§a Du hast§5 "+killedP.getName()+"§a getötet \n§e|§9 Du hast 1 min Zeit um dem Gulag beizutreten! \n§e|§5Benutze /gulag accept "+killedP.getName()));

        BukkitTask runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if(Main.gulagRequests.get(killedP.getUniqueId()) != null){
                    if(Main.gulagRequests.get(killedP.getUniqueId()) != killerP.getUniqueId()) return;

                    Player p = Bukkit.getPlayer(killedP.getUniqueId());

                    Location loc = killedP.getBedSpawnLocation();
                    if(loc == null){
                        World w = Bukkit.getWorld("world");
                        loc = w.getSpawnLocation();
                    }

                    if(p.isOnline()){

                        p.teleport(loc);
                        p.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage(GulagFunctions.sendGulagMessage("§e|§c Dein Killer hat die Gulag anfrage nicht angenommen §e|§a Du bkommst nun deinen Stuff wieder!"));

                    }else {
                        p.setGameMode(GameMode.SURVIVAL);
                        GulagFunctions.createDoubleChest(p.getLocation(), killedPInv);
                    }


                    Main.gulagRequests.remove(killedP.getUniqueId());
                }

                cancel();
            }
        }.runTaskTimer(Main.getInstance(), 0,1200);

    }
}
