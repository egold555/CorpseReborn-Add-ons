package org.golde.bukkit.craddons.nohead;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.golde.bukkit.corpsereborn.CorpseAPI.CorpseAPI;
import org.golde.bukkit.corpsereborn.CorpseAPI.events.CorpseSpawnEvent;
import org.golde.bukkit.corpsereborn.nms.Corpses.CorpseData;

public class Main extends JavaPlugin implements Listener{

	public void onEnable() {
		getCommand("s").setExecutor(this);
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("CRCutOffHead is starting.");
	}

	public void onDisable() {
		getLogger().info("CRCutOffHead is stopping.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player)sender;
		Location l = p.getLocation();
		l.setYaw(Float.valueOf(args[0]));
		l.setPitch(Float.valueOf(args[1]));
		CorpseAPI.spawnCorpse(p, l);
		
		return true;
	}

	@EventHandler
	public void corpseSpawn(CorpseSpawnEvent e) {
		/*CorpseData c = e.getCorpse();
		Player killer = e.getKiller();
		if(killer != null) {
			killer.getInventory().addItem(playerSkull(c.getPlayer().getName()));
		}
		c.getOrigLocation().setYaw(180F);
		c.resendCorpseToEveryone();*/
	}

	public static ItemStack playerSkull(String name) {
		ItemStack item = new ItemStack(397, 1, (short) 3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(name);
		meta.setDisplayName(ChatColor.RESET + name + "'s head");
		item.setItemMeta(meta);
		return item;
	}

}