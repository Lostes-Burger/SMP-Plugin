package de.lostesburger.smp.smpplugin.Gulag;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.MaterialData;


public class GulagFunctions {

    public static String sendGulagMessage(String s){
        String finalS = Main.config.getString("gulag.msg").replace("{msg}", s);
        return finalS;
    }

    public static void createDoubleChest(Location loc, PlayerInventory inv){
        Location leftLoc = new Location(loc.getWorld(), loc.getBlockX()+1, loc.getBlockY(), loc.getBlockZ());
        leftLoc.getBlock().setType(Material.CHEST);
        Chest leftChest = (Chest) leftLoc.getBlock().getBlockData();

        Location rightLoc = loc.clone();
        rightLoc.getBlock().setType(Material.CHEST);
        Chest rightChest = (Chest) rightLoc.getBlock().getBlockData();


        Inventory leftChestI = leftChest.getBlockInventory();
        Inventory rightChestI = rightChest.getBlockInventory();

        int c = 0;

        for (int i = 1; i < 45; i++ ){

            if(i > 27){
                leftChest.getBlockInventory().setItem(i, inv.getItem(i));
            }else {
                rightChest.getBlockInventory().setItem(i, inv.getItem(i));
            }

        }

        if(leftChest.getBlockInventory().getContents().length == 0){
            leftLoc.getBlock().setType(Material.AIR);
        }

    }

    public static void startGulagGame(Player killerP, Player killedP){
        Main.gulagCFight.put(killedP.getUniqueId(), killerP.getUniqueId());
        World world = Bukkit.getWorld(Main.config.getString("gulag.world"));

        Location killerSpawn = getLocationFromConfig("gulag.spawn1", world);
        Location killedSpawn = getLocationFromConfig("gulag.spawn2", world);

        if(!killerP.isOnline()) return;
        if(!killedP.isOnline()) return;

        killedP.teleport(killedSpawn);
    }

    public static Location getLocationFromConfig(String confPath, World world){
        Location loc = new Location(world, Main.config.getInt(confPath+".x"), Main.config.getInt(confPath+".y"), Main.config.getInt(confPath+".z"), Float.valueOf(Main.config.getInt(confPath+".yaw")), Float.valueOf(Main.config.getInt(confPath+".pich"))   );
        return loc;
    }


}
