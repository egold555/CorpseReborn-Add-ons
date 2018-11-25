package org.golde.bukkit.corpserebornaddons.nolavacorpse;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.golde.bukkit.corpsereborn.CorpseAPI.events.CorpseSpawnEvent;

public class Main extends JavaPlugin implements Listener{

	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("NoLavaCorpse is starting.");
	}

	public void onDisable() {
		getLogger().info("NoLavaCorpse is stopping.");
	}

	@EventHandler
	public void onCorpseSpawn(CorpseSpawnEvent e) {
		DamageCause cause = Bukkit.getPlayer(e.getCorpse().getCorpseName()).getLastDamageCause().getCause();
		if(cause == DamageCause.LAVA) {
			e.setCancelled(true);
		}
	}

}