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

/* EVENTLISTENER
 * Events not related to players and blocks and mob.
 */
public class EventListener implements Listener {
    
    /* PREVENT LAG WITH DISABLING RAIN
     * To prevent lag we disable weather change when its
     * sunny and rain can't fall.
     */
    @EventHandler(priority = EventPriority.HIGH)
    public static void WeatherChangeEvent(WeatherChangeEvent event)
    {
    	event.setCancelled(true);
    }

    /* DISABLE PORTAL CREATION
     * We do not want to allow portal creation anywhere on the server
     * because it can break buildings.
     */
    @EventHandler(priority = EventPriority.HIGH)
    public static void onPortalCreate(PortalCreateEvent event)
    { 
    	event.setCancelled(true);
    }
    
    /* PREVENT CREATIVE USER FROM XP FLOOD
     * We have creative users and we do not want them to
     * flood XP for themselfes.
     */
    @EventHandler(priority = EventPriority.HIGH)
    public static void AntiXpBottle(ExpBottleEvent event)
    {
    	event.setCancelled(true);
    }
    
    /*** END OF FILE ***/
}
