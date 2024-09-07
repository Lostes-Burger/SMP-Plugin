package de.lostesburger.smp.smpplugin.Listener.BedrockBreak;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        if(!Main.config.getBoolean("bedrock.break")) return;

        Material mat = Material.getMaterial(Main.config.getString("bedrock.block"));
        Block block = event.getBlock();
        Location loc = block.getLocation();


        if(mat != block.getType()) return;
        World world = Bukkit.getWorld(Main.config.getString("bedrock.world"));


        if(world.getName() != loc.getWorld().getName()) return;

        Block block2 = world.getBlockAt(loc.getBlockX(), loc.getBlockY()+1, loc.getBlockZ());
        Material mat2 = block2.getType();

        if(mat2 != Material.BEDROCK) return;

        world.getBlockAt(loc.getBlockX(), loc.getBlockY()+1, loc.getBlockZ()).setType(Material.AIR);
        world.getBlockAt(loc).setType(Material.AIR);
    }
}
