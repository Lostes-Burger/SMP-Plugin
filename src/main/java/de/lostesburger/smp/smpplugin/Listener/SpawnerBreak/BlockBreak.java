package de.lostesburger.smp.smpplugin.Listener.SpawnerBreak;

import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){

        if(!Main.config.getBoolean("mobSpawner.drop")) return;

        Block block = event.getBlock();
        Player player = event.getPlayer();

        Material type = block.getType();

        if(type != Material.SPAWNER) return;

        Location loc = block.getLocation();
        ItemStack spawner = new ItemStack(Material.SPAWNER, 1);


        loc.getWorld().dropItemNaturally(loc, spawner);

    }
}
