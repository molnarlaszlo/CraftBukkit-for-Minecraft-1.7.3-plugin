package Listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.material.Button;

import Main.Main;
import Main.ServerPlayer;

public class BlockListener implements Listener {

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
    
    BlockFace[] blockFaces = {BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.UP, BlockFace.DOWN};
	
    /* HANDLE BLOCK BURN EVENT
     * 
     */
    @EventHandler(priority = EventPriority.HIGH)
    public static void BlockBurn(BlockBurnEvent event)
    {
        event.setCancelled(true);
    }
    
    /* HANDLE BLOCK SPREAD EVENT
     * 
     */
    @EventHandler(priority = EventPriority.HIGH)
    public static void BlockSpread(BlockSpreadEvent event)
    {
    	 if(event.getNewState().getType().equals(Material.FIRE))
    		 event.setCancelled(true);
    }
    
    /* HANDLE ENTITY BREAK DOOR EVENT
     * Prevent mobs from breaking wood doors to help the player keep their buildings safe.
     */
    @EventHandler(priority = EventPriority.HIGH)
	public static void EntityBreakDoorEvent(EntityBreakDoorEvent event)
    {
    	event.setCancelled(true);
    }
    
    /*
     * 
     */
    @EventHandler(priority = EventPriority.HIGH)
    public static void BlockPhysicsEvent(org.bukkit.event.block.BlockPhysicsEvent event) {
    	if(event.getBlock().getType().equals(Material.STONE_BUTTON))
    		event.setCancelled(true);
    }
    
    /*
     * 
     */
    @EventHandler(priority = EventPriority.HIGH)
    public static void onBlockIgnite(BlockIgniteEvent event)
    {
    	IgniteCause cause = event.getCause();
    	
    	if(cause.equals(IgniteCause.LAVA))
    		event.setCancelled(true);
    	else if(cause.equals(IgniteCause.FIREBALL))
    		event.setCancelled(true);
    	else if(cause.equals(IgniteCause.EXPLOSION))
    		event.setCancelled(true);
    	else if(cause.equals(IgniteCause.ENDER_CRYSTAL))
    		event.setCancelled(true);
    	else if(cause.equals(IgniteCause.LIGHTNING))
    		event.setCancelled(true);
    	else if(cause.equals(IgniteCause.SPREAD))
    		event.setCancelled(true);

    	/**/ // No flint and steel for minperms
    	if(cause.equals(IgniteCause.FLINT_AND_STEEL))
    		if(event.getPlayer() != null)
        		if(getPlayer(event.getPlayer()).permission < 4)
        			event.setCancelled(true);
    }
    
    /* HANDLE ON BLOCK BREAK EVENT (when a player or a force removes a block from the world).
     * 
     */
    @EventHandler(priority = EventPriority.HIGH)
    public static void onBlockBreak(BlockBreakEvent event)
    {
    	// Do we still need this?
        if(event.isCancelled())
            return;
        
        Player player = event.getPlayer();				// The player who indicated the event (force-related later).
        Integer perm = getPlayer(player).permission;	// The current permission level of the player.
        
        Block block = event.getBlock();					// The removed block.
    	// int blockx = block.getLocation().getBlockX(); -- At this moment, we do not have building rules based on areas.
    	// int blocky = block.getLocation().getBlockY();
    	// int blockz = block.getLocation().getBlockZ();
    	
        event.setCancelled(true);
    	if(getPlayer(player).loggedIn == false)
    		player.sendMessage(RED + "Építéshez kérlek jelentkezz be a /login parancsot használva.");
    	else if(perm < 2)
        	player.sendMessage(RED + "Nincs jogod építeni, kérj egy moderátortól.");
    	else if(block.getWorld().getName().equalsIgnoreCase("world_the_end") && perm < 2)
        	event.getPlayer().sendMessage(RED + "Ebben a világban csak VIP játékosok építhetnek!");
        else
        	event.setCancelled(false);
    	
    	// If the player want to remove an ActionButton.
    	if(block.getType() == Material.WOOD_BUTTON || block.getType() == Material.STONE_BUTTON) {
    		String sql = "world = '"+block.getWorld().getName()+"' AND x='"+blockx+"' AND y='"+blocky+"' AND z='"+blockz+"' AND type='1'";
    				
    		if(cmd.equals("noCmd") == false) {
    			if(perm > 3) {
    				// We notify the player that he or she removed an ActionButton. Also we delete the db record for the button.
    				player.sendMessage(YELLOW + ">> Eltöröltél egy akciógombot amin ez a parancs volt: " + Main.databaseGetString("cmd", "actionbuttons", sql, "noCmd"));
           			Main.databaseDelete("DELETE FROM actionbuttons WHERE world = '" + sql);
    			}
    			else {
    				// The player has not permission to remove the block. Event cancelled. The button stays.
            		player.sendMessage(YELLOW + ">> Egy láthatatlan erö visszateszi a gombot a helyére.");
            		event.setCancelled(true);
    			}
    		}
        }
    	
    	// If the event is still going on and the block has a button on it (on any side), we remove the button.
    	if(event.isCancelled() == false)
    		for(int i = 0; i < blockFaces.length; i++)
    			if(block.getRelative(blockFaces[i]) != null)
    				if(block.getRelative(blockFaces[i]).getType().equals(Material.STONE_BUTTON))
    				{
    					Button button = (Button) block.getRelative(blockFaces[i]).getState().getData();
    					if(block.getRelative(blockFaces[i]).getRelative(button.getAttachedFace()).equals(block))
    						block.getRelative(blockFaces[i]).breakNaturally();
    				}
    }
    
    /* HANDLE ON BLOCK PLACE EVENT (when a player place a block in a world).
     * We do not let player to place block everywhere. They must log in before they
     * start to build and they need at least level 2 permission level.
     * Also we prevent non-vip players to build in the "The End" world.
     */
    @EventHandler(priority = EventPriority.HIGH)
    public static void onBlockPlace(BlockPlaceEvent event)
    {
    	Player player = event.getPlayer();				// The player who indicated the event.
    	Integer perm = getPlayer(player).permission;	// The current permission level of the player.
        
        Block block = event.getBlock();
        // int blockx = block.getLocation().getBlockX(); -- At this moment, we do not have building rules based on areas.
    	// int blocky = block.getLocation().getBlockY();
    	// int blockz = block.getLocation().getBlockZ();
    	
    	event.setCancelled(true);
    	if(getPlayer(player).loggedIn == false)
    		player.sendMessage(RED + "Építéshez kérlek jelentkezz be a /login parancsot használva.");
    	else if(perm < 2)
    		player.sendMessage(RED + "Nincs jogod építeni, kérj egy moderátortól.");
    	else if(block.getWorld().getName().equalsIgnoreCase("world_the_end") && perm < 2)	
    		event.getPlayer().sendMessage(RED + "Ebben a világban csak VIP játékosok építhetnek!");
    	else
    		event.setCancelled(false);
    }
    
    /* HANDLE ON SIGN CHANGE EVENT (when a player place a sign block and writes on it).
     * Send the sign's text to the DEV chat channel for security reasons.
     * Preventing player to hold secrets.
     */
    @EventHandler(priority = EventPriority.HIGH)
    public static void onSignChange(SignChangeEvent event)
    {
    	Player player = event.getPlayer();	// The player who placed the sign and edited the text.
    	Block block = event.getBlock();		// The sign block. Can be converted to Sign type.
    	
    	// Replace & in all lines to color the text of the sign.
    	for(int i = 0; i < event.getLines().length; i++)
    		event.setLine(i, event.getLine(i).replace("&", "\247"));

    	// Set universal color (blue) for the command signs.
        if(event.getLine(0).equalsIgnoreCase("[Command]"))
            event.setLine(0, BLUE + "[Command]");
        
        // If it not an empty sign and it is not a LOCK sign we send the text of the sign to the DEV channel.
        if(!event.getLine(0).equalsIgnoreCase("[LOCK]") || !(event.getLine(0).equalsIgnoreCase("") && event.getLine(1).equalsIgnoreCase("") && event.getLine(2).equalsIgnoreCase("") && event.getLine(3).equalsIgnoreCase("")))
        	Main.sendToModChannel(player, "SIGN_CHANGE", 
        		YELLOW + player.getName() + " táblát("+
        		block.getWorld().getName()+", "+block.getLocation().getBlockX()+", "+block.getLocation().getBlockY()+", "+block.getLocation().getBlockZ() + ") " + 
        		" szerkesztett: " + event.getLine(0) + " " + event.getLine(1) + " " + event.getLine(2) + " " + event.getLine(3), 
        	false);
    }
    
    /*** END OF FILE ***/
}
