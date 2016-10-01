package Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import Main.Main;
import Main.ServerPlayer;

public class EventListener implements Listener {

	public static ChatColor RED = Main.RED;
    public static ChatColor BLUE = Main.BLUE;
    public static ChatColor GOLD = Main.GOLD;
    public static ChatColor GRAY = Main.GRAY;
    public static ChatColor AQUA = Main.AQUA;
    public static ChatColor GREEN = Main.GREEN;
    public static ChatColor WHITE = Main.WHITE;
    public static ChatColor YELLOW = Main.YELLOW;
    public static ChatColor DARK_RED = Main.DARK_RED;
    public static ChatColor DARK_AQUA = Main.DARK_AQUA;
    public static ChatColor DARK_PURPLE = Main.DARK_PURPLE;
    public static ChatColor LIGHT_PURPLE = Main.LIGHT_PURPLE;
    
    public static ServerPlayer getPlayer(Player player) {
    	return Main.players.get(player);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public static void WeatherChangeEvent(WeatherChangeEvent event)
    {
    	event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public static void onPortalCreate(PortalCreateEvent event)
    { 
    	event.setCancelled(true); 
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public static void AntiXpBottle(ExpBottleEvent event)
    {

    }
    
}
