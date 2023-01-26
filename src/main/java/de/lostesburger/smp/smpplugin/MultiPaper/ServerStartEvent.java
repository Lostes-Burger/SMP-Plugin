package de.lostesburger.smp.smpplugin.MultiPaper;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.event.Event;
import de.dytanic.cloudnet.driver.event.EventListener;
import de.dytanic.cloudnet.driver.event.events.service.CloudServiceStartEvent;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import de.dytanic.cloudnet.ext.bridge.player.executor.PlayerExecutor;
import de.lostesburger.smp.smpplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;

public class ServerStartEvent extends Event{


    @EventListener
    public void onStart(CloudServiceStartEvent event){

        ServiceInfoSnapshot info = event.getServiceInfo();

        if(!info.getName().startsWith("anarchy")) return;

        Bukkit.broadcastMessage(Main.präfix+"§c Service §r'§f"+info.getName()+"§r'§a started§5 and connected to the Master Channel!");

        //Main.getInstance().getLogger().log(Level.WARNING, info.getProperty(BridgeServiceProperty.ONLINE_COUNT).toString());


    }

}
