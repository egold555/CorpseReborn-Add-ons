package org.golde.bukkit.corpserebornaddons.lootcorpseinradius;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ActionBar {

	public static Class<?> getNmsClass(final String nmsClassName) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + nmsClassName);
    }
    
    public static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(23);
    }
	
	public static void send(final Player p, final String msg) {
		try {
            if (getServerVersion().equalsIgnoreCase("v1_12_R1") || getServerVersion().equalsIgnoreCase("v1_12_R2")) {
                final Object icbc = getNmsClass("ChatComponentText").getConstructor(String.class).newInstance(ChatColor.translateAlternateColorCodes('&', msg));
                final Object cmt = getNmsClass("ChatMessageType").getField("GAME_INFO").get(null);
                final Object ppoc = getNmsClass("PacketPlayOutChat").getConstructor(getNmsClass("IChatBaseComponent"), getNmsClass("ChatMessageType")).newInstance(icbc, cmt);
                final Object nmsp = p.getClass().getMethod("getHandle", (Class<?>[])new Class[0]).invoke(p, new Object[0]);
                final Object pcon = nmsp.getClass().getField("playerConnection").get(nmsp);
                pcon.getClass().getMethod("sendPacket", getNmsClass("Packet")).invoke(pcon, ppoc);
            }
            else if (getServerVersion().equalsIgnoreCase("v1_9_R1") || getServerVersion().equalsIgnoreCase("v1_9_R2") || getServerVersion().equalsIgnoreCase("v1_10_R1") || getServerVersion().equalsIgnoreCase("v1_11_R1")) {
                final Object icbc = getNmsClass("ChatComponentText").getConstructor(String.class).newInstance(ChatColor.translateAlternateColorCodes('&', msg));
                final Object ppoc2 = getNmsClass("PacketPlayOutChat").getConstructor(getNmsClass("IChatBaseComponent"), Byte.TYPE).newInstance(icbc, (byte)2);
                final Object nmsp2 = p.getClass().getMethod("getHandle", (Class<?>[])new Class[0]).invoke(p, new Object[0]);
                final Object pcon2 = nmsp2.getClass().getField("playerConnection").get(nmsp2);
                pcon2.getClass().getMethod("sendPacket", getNmsClass("Packet")).invoke(pcon2, ppoc2);
            }
            else if (getServerVersion().equalsIgnoreCase("v1_8_R2") || getServerVersion().equalsIgnoreCase("v1_8_R3")) {
                final Object icbc = getNmsClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, "{'text': '" + msg + "'}");
                final Object ppoc2 = getNmsClass("PacketPlayOutChat").getConstructor(getNmsClass("IChatBaseComponent"), Byte.TYPE).newInstance(icbc, (byte)2);
                final Object nmsp2 = p.getClass().getMethod("getHandle", (Class<?>[])new Class[0]).invoke(p, new Object[0]);
                final Object pcon2 = nmsp2.getClass().getField("playerConnection").get(nmsp2);
                pcon2.getClass().getMethod("sendPacket", getNmsClass("Packet")).invoke(pcon2, ppoc2);
            }
            else {
                final Object icbc = getNmsClass("ChatSerializer").getMethod("a", String.class).invoke(null, "{'text': '" + msg + "'}");
                final Object ppoc2 = getNmsClass("PacketPlayOutChat").getConstructor(getNmsClass("IChatBaseComponent"), Byte.TYPE).newInstance(icbc, (byte)2);
                final Object nmsp2 = p.getClass().getMethod("getHandle", (Class<?>[])new Class[0]).invoke(p, new Object[0]);
                final Object pcon2 = nmsp2.getClass().getField("playerConnection").get(nmsp2);
                pcon2.getClass().getMethod("sendPacket", getNmsClass("Packet")).invoke(pcon2, ppoc2);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
