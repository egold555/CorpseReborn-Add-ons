package org.golde.bukkit.corpserebornaddons.lootpermission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.golde.bukkit.corpsereborn.CorpseAPI.events.CorpseClickEvent;

public class Main extends JavaPlugin implements Listener{

	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("CRLootPermission is starting.");
	}

	public void onDisable() {
		getLogger().info("CRLootPermission is stopping.");
	}

	@EventHandler
	public void onCorpseClick(CorpseClickEvent e) {
		if(!e.getClicker().hasPermission("corpses.loot")) {
			e.setCancelled(true);
			e.getClicker().sendMessage(ChatColor.RED + "You do not have permission to loot that corpse!");
		}
	}


}