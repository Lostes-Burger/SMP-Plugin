package de.lostesburger.smp.smpplugin.Utils;

import de.lostesburger.smp.smpplugin.Main;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class Scheduler {
    private static final boolean isFolia = testFolia();

    private static boolean testFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void run(Runnable runnable){
        if(isFolia){
            Bukkit.getGlobalRegionScheduler().execute(Main.getInstance(), runnable);
        }else {
            Bukkit.getScheduler().runTask(Main.getInstance(), runnable);
        }
    }

    public static void runAsync(Runnable runnable){
        if(isFolia){
            Bukkit.getGlobalRegionScheduler().execute(Main.getInstance(), runnable);
        }else {
            Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), runnable);
        }
    }

    public static Task runLater(Runnable runnable, long delay){
        if(isFolia){
            return new Task(Bukkit.getGlobalRegionScheduler().runDelayed(Main.getInstance(), t -> runnable.run(), delay ));
        }else {
            return new Task(Bukkit.getScheduler().runTaskLater(Main.getInstance(), runnable, delay));
        }
    }

    public static Task runLaterAsync(Runnable runnable, long delay){
        if(isFolia){
            return new Task(Bukkit.getGlobalRegionScheduler().runDelayed(Main.getInstance(), t -> runnable.run(), delay ));
        }else {
            return new Task(Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), runnable, delay));
        }
    }

    public static Task runTimer(Runnable runnable, long delay, long period){
        if(isFolia){
            return new Task(Bukkit.getGlobalRegionScheduler().runAtFixedRate(Main.getInstance(), t -> runnable.run(), delay < 1 ? 1 : delay, period));
        }else {
            return new Task(Bukkit.getScheduler().runTaskTimer(Main.getInstance(), runnable, delay, period));
        }
    }

    public static Task runTimerAsync(Runnable runnable, long delay, long period){
        if(isFolia){
            return new Task(Bukkit.getGlobalRegionScheduler().runAtFixedRate(Main.getInstance(), t -> runnable.run(), delay < 1 ? 1 : delay, period));
        }else {
            return new Task(Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), runnable, delay, period));
        }
    }

    public static boolean isFolia(){
        return isFolia;
    }

    public static class Task {
        private Object foliaTask;
        private BukkitTask bukkitTask;

        Task(Object foliaTask){
            this.foliaTask = foliaTask;
        }

        Task(BukkitTask bukkitTask){
            this.bukkitTask = bukkitTask;
        }

        public void cancel(){
            if(foliaTask != null){
                if(((ScheduledTask) foliaTask).isCancelled()) return;
                ((ScheduledTask) foliaTask).cancel();

            }else {
                if(bukkitTask.isCancelled()) return;
                bukkitTask.cancel();
            }
        }
    }
}
