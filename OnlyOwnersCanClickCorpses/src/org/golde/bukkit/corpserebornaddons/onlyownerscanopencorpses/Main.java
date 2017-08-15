package org.golde.bukkit.corpserebornaddons.onlyownerscanopencorpses;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.golde.bukkit.corpsereborn.CorpseAPI.events.CorpseClickEvent;

public class Main extends JavaPlugin implements Listener{

	private String errorMessage = "";
	
	public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
		getLogger().info("OnlyOwnersCanClickCorpses is starting.");
		errorMessage = getConfig().getString("error-message");
		errorMessage = errorMessage.replace("&", "§");
	}

	public void onDisable() {
		getLogger().info("OnlyOwnersCanClickCorpses is stopping.");
	}

	@EventHandler
	public void onCorpseClick(CorpseClickEvent e) {
		if(e.getClicker().hasPermission("ooccc.admin")) {
			return;
		}
		if(!e.getClicker().getName().equalsIgnoreCase(e.getCorpse().getCorpseName())) {
			e.setCancelled(true);
			e.getClicker().sendMessage(errorMessage);
		}
	}

}