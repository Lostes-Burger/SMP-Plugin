package de.lostesburger.smp.smpplugin.Restart;

import de.lostesburger.smp.smpplugin.Main;
import de.lostesburger.smp.smpplugin.Utils.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class RestartManager {
    public RestartManager(int seconds, String customMsg){
        final int[] count = {0};
        Scheduler.Task task = Scheduler.runTimerAsync(() ->{
            count[0] = count[0] +1;
            int timeLeft = seconds-count[0];

            switch (timeLeft){
                case 30:

            }
        }, 20, 600);

    }

    private void notifyMembers(int seconds){
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendTitle(Main.config.getStringList("restart.title").get(0), Main.config.getStringList("restart.title").get(1).replace("{sec}", String.valueOf(seconds)), 1, 30, 1);
        });
    }
}
