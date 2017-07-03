package org.golde.bukkit.corpserebornapitest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.golde.bukkit.corpsereborn.CorpseAPI.*;
import org.golde.bukkit.corpsereborn.CorpseAPI.events.*;
import org.golde.bukkit.corpsereborn.nms.Corpses.CorpseData;
public class MyPlugin extends JavaPlugin implements Listener{

	public void onEnable() {
		getCommand("test").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("CorpseRebornAPITest plugin is starting.");
	}

	public void onDisable() {
		getLogger().info("CorpseRebornAPITest plugin is stopping.");
	}
	
	void log(String msg){
		getLogger().info(msg);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onCorpseSpawn(CorpseSpawnEvent e){
		log("CorpseSpawnEvent Triggered");
		log("   Canceled: " + e.isCancelled());
		log("	FromCMD: " + e.fromCommand());
		log("	Who: ");
		log("		Name: " + e.getCorpse().getPlayer().getName());
		if(e.getCorpse().getPlayer().getKiller() != null){
			log("		Killer: " + e.getCorpse().getPlayer().getKiller().getName());
		}
		log(" ");
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onCorpseRemove(CorpseRemoveEvent e){
		log("CorpseRemoveEvent Triggered");
		log("	Removed: " + e.getCorpses().size());
		log("	FromCMD: " + e.fromCommand());
		log("	Who: ");
		for(CorpseData cd:e.getCorpses()){
			log("		" + cd.getPlayer().getName());	
		}
		log(" ");
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onCorpseClick(CorpseClickEvent e){
		log("CorpseClickEvent Triggered");
		log("	Clicker: " + e.getClicker().getName());
		log("	Clicked On: " + e.getCorpse().getPlayer().getName());
		log("	Type of click: " + e.getClickType());
		log(" ");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player)sender;
		
		CorpseData cd = CorpseAPI.spawnCorpse(p, "TestCorpse", p.getLocation(), null, null, null, null, null, null, null, CorpseAPI.ROTATION_EAST);
		cd.setCanSee(Bukkit.getPlayer("Notch"), false);
		
		
		return true;
	}
	
	@EventHandler
	public void cancel(CorpseSpawnEvent e){
		e.setCancelled(false);
	}

}