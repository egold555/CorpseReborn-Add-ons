package org.golde.bukkit.corpserebornaddons.onlyownerscanopencorpses;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.golde.bukkit.corpsereborn.CorpseAPI.events.CorpseClickEvent;

public class Main extends JavaPlugin implements Listener{

	public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("OnlyOwnersCanClickCorpses is starting.");
	}

	public void onDisable() {
		getLogger().info("OnlyOwnersCanClickCorpses is stopping.");
	}

	@EventHandler
	public void onCorpseClick(CorpseClickEvent e) {
		if(!e.getClicker().getName().equalsIgnoreCase(e.getCorpse().getPlayer().getName())) {
			e.setCancelled(true);
			e.getClicker().sendMessage(ChatColor.RED + "Only owners of the corpse can loot this corpse.");
		}
	}

}