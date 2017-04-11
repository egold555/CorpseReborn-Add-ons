package org.golde.bukkit.corpserebornaddons.noitemsnocorpse;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.golde.bukkit.corpsereborn.CorpseAPI.events.CorpseSpawnEvent;

public class Main extends JavaPlugin implements Listener{

	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("NoItemsNoCorpse is starting.");
	}

	public void onDisable() {
		getLogger().info("NoItemsNoCorpse is stopping.");
	}

	@EventHandler
	public void onCorpseSpawn(CorpseSpawnEvent e) {
		if(isPlayerInventoryEmpty(e.getCorpse().getPlayer())) {
			e.setCancelled(true);
		}
	}

	boolean isPlayerInventoryEmpty(Player player) {
		
		for(ItemStack item : player.getInventory().getContents())
		{
			if(item != null) {
				return false;
			}
		}
		for(ItemStack item : player.getInventory().getArmorContents())
		{
			if(item != null) {
				return false;
			}
		}
		return true;
	}

}