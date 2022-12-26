package de.lostesburger.smp.smpplugin.Listener.BeaconRange;

import de.lostesburger.smp.smpplugin.Listener.SpawnerBreak.BlockBreak;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BeaconRange implements Listener {
    @EventHandler
    public void beaconPlace(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material mat = block.getType();

        if(mat != Material.BEACON) return;

        Player player = event.getPlayer();

        Beacon beacon = (Beacon) block.getState();

        Location loc = block.getLocation();

        System.out.println("§aBeacon-Break: §5Player: "+player.getName()+"§c Location:§9 World: "+loc.getWorld()+"§e X: "+loc.getBlockX()+"§c Y: "+loc.getBlockY()+" §fZ: "+loc.getBlockZ() );

    }

}
