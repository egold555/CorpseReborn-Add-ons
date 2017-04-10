package org.golde.bukkit.corpsereborngravestones;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.golde.bukkit.corpsereborn.CorpseAPI.events.CorpseSpawnEvent;
import org.golde.bukkit.corpsereborn.nms.Corpses.CorpseData;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.world.DataException;
public class Main extends JavaPlugin implements Listener{

	public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("CorpseRebornGravestones is starting.");
		getDataFolder().mkdir();
	}

	public void onDisable() {
		getLogger().info("CorpseRebornGravestones is stopping.");
	}
	
	@EventHandler
	public void onCorpseSpawn(CorpseSpawnEvent e) {
		try {
			placeGravestone(1, e.getCorpse());
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (DataException e1) {
			e1.printStackTrace();
		} catch (MaxChangedBlocksException e1) {
			e1.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void placeGravestone(int type, CorpseData cd) throws IOException, DataException, MaxChangedBlocksException {
		String playerName = cd.getPlayer().getName();
		CuboidClipboard cc = null;
		Location l = cd.getPlayer().getLocation();
		EditSession es = new EditSession(new BukkitWorld(cd.getPlayer().getWorld()), 999999999);
		
		if(type == 1) {
			cc = CuboidClipboard.loadSchematic(new File(getDataFolder(), "Cobble-Grave1.schematic"));
			cc.rotate2D(-90);
			cc.paste(es, new Vector(l.getBlockX()-2.5, l.getBlockY()+2, l.getBlockZ()-5.5), false);
		}else if(type == 2) {
			cc = CuboidClipboard.loadSchematic(new File(getDataFolder(), "Stone-Grave1.schematic"));	
		}else if(type == 3) {
			cc = CuboidClipboard.loadSchematic(new File(getDataFolder(), "Cobble-Grave2.schematic"));	
		}else {
			cc = CuboidClipboard.loadSchematic(new File(getDataFolder(), "Stone-Grave2.schematic"));	
		}
		
		
	}

}