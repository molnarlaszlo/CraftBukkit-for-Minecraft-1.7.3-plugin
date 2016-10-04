package Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import Main.Main;
import Main.ServerPlayer;

/*
 * 
 */
public class MobListener implements Listener {

	/*
	 * 
	 */
    @EventHandler(priority = EventPriority.HIGH)
    public static void CreatureSpawnEvent(CreatureSpawnEvent event)
    {
        event.setCancelled(true);
    }
    
    /*
     * 
     */
    @EventHandler(priority = EventPriority.HIGH)
    public static void EntityExplodeEvent(EntityExplodeEvent event)
    {
    	event.setCancelled(true);
    }
    
    /*
	 * 
	 */
    @EventHandler(priority = EventPriority.HIGH)
    public static void EntityChangeBlockEvent(EntityChangeBlockEvent event)
    {
        EntityType entity = event.getEntityType();
        
        if(entity.equals(EntityType.ENDER_CRYSTAL))
            event.setCancelled(true);
        else if(entity.equals(EntityType.ENDER_DRAGON))
            event.setCancelled(true);
        else if(entity.equals(EntityType.ENDERMAN))
            event.setCancelled(true);
        else if(entity.equals(EntityType.CREEPER))
            event.setCancelled(true);
        else if(entity.equals(EntityType.GHAST))
            event.setCancelled(true);
    }
    
    /*** END OF FILE ***/
}
