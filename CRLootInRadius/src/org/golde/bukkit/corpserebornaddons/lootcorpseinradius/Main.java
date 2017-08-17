package org.golde.bukkit.corpserebornaddons.lootcorpseinradius;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;
import org.golde.bukkit.corpsereborn.CorpseAPI.CorpseAPI;
import org.golde.bukkit.corpsereborn.CorpseAPI.events.CorpseClickEvent;
import org.golde.bukkit.corpsereborn.CorpseAPI.events.CorpseRemoveEvent;
import org.golde.bukkit.corpsereborn.nms.Corpses.CorpseData;
import org.golde.bukkit.corpsereborn.nms.TypeOfClick;

public class Main extends JavaPlugin implements Listener{

	private String actionMessage = "";
	private int radius = 0;
	private Map<Player, CorpseData> mapFromPlayerToClickableCorpse = new HashMap<Player, CorpseData>();
	boolean allowLootingInRadius;

	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		saveDefaultConfig();
		getLogger().info("LootCorpseInRadius is starting.");
		FileConfiguration config = getConfig();
		actionMessage = config.getString("message", "Config message not found.");
		actionMessage = actionMessage.replace("&", "§");
		radius = config.getInt("radius", 10);
		allowLootingInRadius = config.getBoolean("allow-looting", true);
	}

	public void onDisable() {
		getLogger().info("LootCorpseInRadius is stopping.");
	}

	@EventHandler
	public void move(PlayerMoveEvent e) {
		if(!e.getPlayer().hasPermission("craddons.lootinradius")) {return;}
		
		//BAD way of doing this using PlayerMoveEvent
		List<CorpseData> list = CorpseAPI.getCorpseInRadius(e.getPlayer().getLocation(), radius);
		if(list.size() == 0) {return;}
		CorpseData cd = list.get(0);
		mapFromPlayerToClickableCorpse.put(e.getPlayer(), cd);
		
		String newMsg = actionMessage.replace("%corpse%", cd.getCorpseName());
		
		ActionBar.send(e.getPlayer(), newMsg);
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void rickClick(PlayerInteractEvent e) {
		if(!allowLootingInRadius) {return;}
		if(!e.getPlayer().hasPermission("craddons.lootinradius")) {return;}
		if(e.getAction() == Action.PHYSICAL) {return;}
		
		try {
			//Handle clicking of corpse
			Player player = e.getPlayer();
			CorpseData cd = mapFromPlayerToClickableCorpse.getOrDefault(player, null);
			if(cd == null) {return;}
			
			InventoryView view = player.openInventory(cd.getLootInventory());
			cd.setInventoryView(view);
			cd = null; //TODO

			//Call corpse click event (For other plugins that use it)
			Bukkit.getServer().getPluginManager().callEvent(new CorpseClickEvent(cd, player, TypeOfClick.BOTH));
		}catch(Exception ex) {
			ex.printStackTrace();
		}

	}
	
	@EventHandler
	public void removeCorpse(CorpseRemoveEvent e) {
		for(CorpseData cd1:e.getCorpses()) {
			for(CorpseData cd2: mapFromPlayerToClickableCorpse.values()) {
				
				//Simple way to see if corpses are the same
				if(cd1.getEntityId() == cd2.getEntityId()){
					mapFromPlayerToClickableCorpse.values().remove(cd1);
				}
			}
		}
		
	}



}