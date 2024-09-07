package de.lostesburger.smp.smpplugin.Listener.BeaconRange;


import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.block.BlockPlaceEvent;
@Deprecated
public class BeaconRange implements Listener {
    @EventHandler
    public void beaconPlace(BlockPlaceEvent event) {
        if(!(Main.config.getBoolean("beacon.range.enabled"))) return;

        Block block = event.getBlock();
        Material mat = block.getType();
        Material configMat = Material.getMaterial(Main.config.getString("beacon.range.block"));
        int depth = Main.config.getInt("beacon.range.depth");

        if(mat != Material.BEACON && mat != configMat) return;
        Location loc = block.getLocation();
        Location locBelow = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY()-depth, loc.getBlockZ());
        Location locAbove = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY()+depth, loc.getBlockZ());
        Player player = event.getPlayer();
        Beacon beacon = null;

        if(mat == Material.BEACON){
            if(locBelow.getWorld().getBlockAt(locBelow).getType() != configMat) return;
            beacon = (Beacon) block.getState();
        }

        if(mat == configMat){
            if(locAbove.getWorld().getBlockAt(locAbove).getType() != Material.BEACON) return;
            beacon = (Beacon) locAbove.getWorld().getBlockAt(locAbove).getState();
        }

        if(beacon == null) return;

        beacon.setEffectRange(Main.config.getInt("beacon.range.range"));
        beacon.update();

        player.sendMessage(Main.präfix+"§aBeacon range set to: §c"+Main.config.getInt("beacon.range.range"));
    }

}
