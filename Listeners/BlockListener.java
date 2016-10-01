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
    
    @EventHandler(priority = EventPriority.HIGH)
    public static void BlockBurn(BlockBurnEvent event)
    {
        event.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public static void BlockBlockSpread(BlockSpreadEvent event)
    {
    	 if(event.getNewState().getType().equals(Material.FIRE))
    		 event.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
	public static void EntityBreakDoorEvent(EntityBreakDoorEvent event)
    {
    	event.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public static void BlockPhysicsEvent(org.bukkit.event.block.BlockPhysicsEvent event) {
    	if(event.getBlock().getType().equals(Material.STONE_BUTTON))
    		event.setCancelled(true);
    }
    
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
    
    
    @EventHandler(priority = EventPriority.HIGH)
    public static void onBlockBreak(BlockBreakEvent event)
    {
        if(event.isCancelled())
            return;
        
        Player player = event.getPlayer();
        Integer perm = getPlayer(player).permission;
        
        Block block = event.getBlock();
    	int blockx = block.getLocation().getBlockX();
    	int blocky = block.getLocation().getBlockY();
    	int blockz = block.getLocation().getBlockZ();
    	
    	/**/ // Player without build permission can not build.
    	if(getPlayer(player).loggedIn == false)
    	{
    		player.sendMessage(RED + "Építéshez kérlek jelentkezz be a /login parancsot használva.");
    		event.setCancelled(true);
    	}
    	else if(perm < 2)
        {
        	player.sendMessage("Nincs jogod építeni, kérj egy moderátortól.");
        	event.setCancelled(true);
        }
    	else if(block.getWorld().getName().equalsIgnoreCase("world_the_end") && perm < 2){
        	event.getPlayer().sendMessage(RED+"Ebben a világban csak VIP játékosok építhetnek!");
        	event.setCancelled(true);
        }/*
    	else {
    		// Event buttons
        	if(block.getType() == Material.WOOD_BUTTON || block.getType() == Material.STONE_BUTTON) {
        		if(perm > 3) {
        			String cmd = Main.databaseGetString("cmd", "actionbuttons", "world = '"+block.getWorld().getName()+"' AND x='"+blockx+"' AND y='"+blocky+"' AND z='"+blockz+"' AND type='1'", "noCmd");
            		if(cmd.equals("noCmd") == false) {
            			player.sendMessage(YELLOW + ">> Eltöröltél egy akciógombot amin ez a parancs volt: "+cmd);
            			Main.databaseDelete("DELETE FROM actionbuttons WHERE world = '"+block.getWorld().getName()+"' AND x='"+blockx+"' AND y='"+blocky+"' AND z='"+blockz+"' AND type='1'");
            		}
        		}
        		else {
        			player.sendMessage(YELLOW + ">> Egy láthatatlan erö visszateszi a gombot a helyére.");
        			event.setCancelled(true);
        		}
        	}
    	}*/
    	
    	if(event.isCancelled() == false) {
    		
    		BlockFace[] blockFaces = {BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.UP, BlockFace.DOWN};
    		for(int i = 0; i < blockFaces.length; i++)
    			if(block.getRelative(blockFaces[i]) != null)
    				if(block.getRelative(blockFaces[i]).getType().equals(Material.STONE_BUTTON))
    				{
    					Button button = (Button) block.getRelative(blockFaces[i]).getState().getData();
    					if(block.getRelative(blockFaces[i]).getRelative(button.getAttachedFace()).equals(block))
    						block.getRelative(blockFaces[i]).breakNaturally();
    				}
    	}
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public static void onBlockPlace(BlockPlaceEvent event)
    {
    	Player player = event.getPlayer();
    	Integer perm = getPlayer(player).permission;
        
        Block block = event.getBlock();
        int blockx = block.getLocation().getBlockX();
    	int blocky = block.getLocation().getBlockY();
    	int blockz = block.getLocation().getBlockZ();
    	
    	if(getPlayer(player).loggedIn == false)
    	{
    		player.sendMessage(RED + "Építéshez kérlek jelentkezz be a /login parancsot használva.");
    		event.setCancelled(true);
    	}
        else if(perm < 2)
        {
        	player.sendMessage("Nincs jogod építeni, kérj egy moderátortól.");
        	event.setCancelled(true);
        	return;
        }
    	else if(block.getWorld().getName().equalsIgnoreCase("world_the_end") && perm < 2){
        	event.getPlayer().sendMessage(RED+"Ebben a világban csak VIP játékosok építhetnek!");
        	event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public static void onSignChange(SignChangeEvent event)
    {
    	Player player = event.getPlayer();
    	Block block = event.getBlock();
    	String content = "";
    	
    	for(int i = 0; i < event.getLines().length; i++)
    		content = content + " " + event.getLine(i);
    	
    	for(int i = 0; i < event.getLines().length; i++)
    		event.setLine(i, event.getLine(i).replace("&", "\247"));

        if(event.getLine(0).equalsIgnoreCase("[Parancs]"))
            event.setLine(0, BLUE + "[Parancs]");
        
        //if(!event.getLine(0).equalsIgnoreCase("[VED]") || !(event.getLine(0).equalsIgnoreCase("") && event.getLine(1).equalsIgnoreCase("") && event.getLine(2).equalsIgnoreCase("") && event.getLine(3).equalsIgnoreCase("")))
        	//Main.sendToModChannel(player, "SIGN_CHANGE", YELLOW + player.getName() + " táblát("+block.getWorld().getName()+", "+block.getLocation().getBlockX()+", "+block.getLocation().getBlockY()+", "+block.getLocation().getBlockZ() + ") " + " szerkesztett: " + content, false);
    }
}
