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

public class BeaconRange implements Listener {
    @EventHandler
    public void beaconPlace(BlockPlaceEvent event) {
        if(!(Main.config.getBoolean("beacon.range.enabled"))) return;

        Block block = event.getBlock();
        Material mat = block.getType();

        if(mat != Material.BEACON) return;

        Player player = event.getPlayer();

        Beacon beacon = (Beacon) block.getState();

        Location locBeacon = block.getLocation();
        Location locDia = new Location(locBeacon.getWorld(), locBeacon.getBlockX(), locBeacon.getBlockY()-1, locBeacon.getBlockZ());

        Block diaBlock = locBeacon.getWorld().getBlockAt(locDia);

        if(diaBlock.getType() != Material.DIAMOND_BLOCK) return;

        beacon.setEffectRange(Main.config.getInt("beacon.range.range"));

        player.sendMessage(Main.präfix+"§aYou activated a super Beacon! Beacon range set to: §5"+Main.config.getInt("beacon.range.range"));
    }

}
