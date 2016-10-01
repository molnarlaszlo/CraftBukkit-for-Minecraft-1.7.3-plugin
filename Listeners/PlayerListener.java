package Listeners;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Button;

import Main.*;

public class PlayerListener implements Listener
{
	public static Material[] DoorTypes = { Material.ACACIA_DOOR, Material.BIRCH_DOOR, Material.DARK_OAK_DOOR, Material.JUNGLE_DOOR, Material.SPRUCE_DOOR, Material.WOOD_DOOR, Material.WOODEN_DOOR };
	public static String deathMessages[] = {" át tört a kódunkon, s ezzel megszegve minden elméletet.", " át tört a kódunkon, s ezzel megszegve minden elméletet.", " almája romlott volt.", " is belátja most már, a láva nem játék.", " vad creeperekkel játszott.", " teli pénztárcával sétált végig Budapesten.",
			" a jobb létre szenderült!", " megleste a mikulást!", " megkóstolta a bolond gombát!", " megkóstolta a házipálinkánkat!", " megtudta milyen egy adminnal vitatkozni.", " mostmár tudja hogy néz ki az ender sárkány belülrõl!",
			" megnyerte a fõnyereményt!", " tudta melyik a helyes út.", " megtudta mire jó a respawn gomb!", " beadta a kulcsot.", " szellemes kedvében van.",  " beadta a törölközöt.", " fûbe harapott.", " ellátogatott a kék osztrigába.",
			" már csak statisztika!", " belefáradt az egészbe...", " megmurdelt.", " feldobta a pacskert.", " nem tanult a kémia dolgozatra!", " már csak statisztika.", " belehalt az unalomba!", " leszédült a peremrõl.",
			" a gumikacsa helyett a kenyérpirítóval fürdött.", " fürdés közben szárított hajat.", " szaltó közben meggondolta magát.", " megtudta, hogy a borotva nem finom...", " egyszerre végig nézte a Dallas összes epizódját!",
			" troll-kodott a moderátorokkal!", " megtudta, milyen a szabad esés...", " creeperek eledele lett.", " villamosszékben végezte."};
	
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
    public static void onPlayerLogin(PlayerLoginEvent event)
    {
		if( event.getResult().equals(PlayerLoginEvent.Result.KICK_BANNED) )
			event.disallow(PlayerLoginEvent.Result.KICK_BANNED, RED + "Ki lettél tiltva a szerverröl!");
		else if( event.getResult().equals(PlayerLoginEvent.Result.KICK_WHITELIST) )
			event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, GREEN + "Kérlek jelentkezz a weboldalunkon!");
		else if( event.getResult().equals(PlayerLoginEvent.Result.KICK_FULL) )
			event.disallow(PlayerLoginEvent.Result.KICK_FULL, RED + "Sajnáljuk, de a szerver tele van.");
		else if( event.getResult().equals(PlayerLoginEvent.Result.KICK_OTHER) )
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER, RED + "Öhm, valami hiba történt, bocsánat.");
		else
			if(Main.players.containsKey(event.getPlayer()) == false)
				Main.players.put(event.getPlayer(), new ServerPlayer(event.getPlayer()));
    }
	@EventHandler(priority = EventPriority.HIGH)
	public static void PlayerJoinEvent(PlayerJoinEvent event)
    {
    	Player player = event.getPlayer();

		event.setJoinMessage(YELLOW + getPlayer(player).name + " csatlakozott hozzánk.");
    	if(getPlayer(player).welcome.length() > 0)
    		event.setJoinMessage(GREEN + ">> " + getPlayer(player).welcome + "\n" + YELLOW + getPlayer(player).name + " csatlakozott hozzánk.");
    }
    @EventHandler(priority = EventPriority.HIGH)
    public static void PlayerQuitEvent(PlayerQuitEvent event)
    {
    	Player player = event.getPlayer();
    	Main.players.remove(player);

        event.setQuitMessage(YELLOW + player.getName() + YELLOW + " itthagyott minket.");
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public static void onPlayerChat(AsyncPlayerChatEvent event)
    {
		/**/ // Essentials
		Player player = event.getPlayer();
		String message = event.getMessage();
        Integer perm = getPlayer(player).permission;
        String prefix = "<" + ServerPlayer.getPermissionColor(perm) + player.getName() + WHITE + "> ";
        
        /***/ // Administrators can modify chatcolors.
        if(perm > 6)
        	message = message.replaceAll("&", "\247");
        
        /**/ // Anti-spam
        if(message.equals(message.toUpperCase()))
        	message = message.toLowerCase();
        
        message = message.replaceAll("^ +| +$|( )+", "$1").trim();
        message = message.replaceAll("^ +| +$|(\\!)+", "$1").trim();
        message = message.replaceAll("^ +| +$|(\\?)+", "$1").trim();
        message = message.replaceAll("^ +| +$|(\\.)+", "$1").trim();
        message = message.replaceAll("^ +| +$|(\\*)+", "$1").trim();
        
        Boolean sendIt = true;
        if(onPlayerChat_containsBannedWords(message))
        	sendIt = false;
        
		/**/ // Censorship module
        String _badwords = "buzi, búzi, geci, pina, picsa, fasz, szar, kurva, kocsog, köcsög, kibaszott, buzerans, buzeráns, fogyatekos, fogyatékos";
        String[] badwords = _badwords.split(", ");
        for(int i = 0; i < badwords.length; i++)
        	message = message.replaceAll(badwords[i], onPlayerChat_genStars(badwords[i].length()));
        
        /**/ // Shout out
        if(getPlayer(player).inMute > 0) {
        	player.sendMessage(YELLOW + "Lenémítottak téged, a játékosok nem látják mit írsz.");
        	Main.sendToModChannel(player, "MUTED_PLAYER", player.getName()+": "+message, false);
        }
        else {
        	Object players[] = Main.server.getOnlinePlayers().toArray();
            for(int i = 0; i < players.length; i++)
            	if(sendIt)
            		((Player) players[i]).sendMessage(prefix + message.replaceAll( ((Player) players[i]).getName(), YELLOW + ((Player) players[i]).getName() + WHITE));
        }
        
        /**/ // Get lost CraftBukkit
        event.setCancelled(true);
    }
	public static ChatColor onPlayerChat_getColor(Integer perm) {
		switch(perm) {
			case 0: return GRAY;
			case 1: return LIGHT_PURPLE;
			case 2: return WHITE;
			case 3: return GREEN;
			case 4: return AQUA;
			case 5: return RED;
			case 6: return RED;
			default: return YELLOW;
		}
	}
	public static String onPlayerChat_genStars(Integer length) {
		String msg = "";
		for(int i = 1; i <= length; i++)
			msg = msg + "*";
		return msg;
	}
	public static Boolean onPlayerChat_containsBannedWords(String msg) {
		String[] words = msg.split(" ");
		String[] blocked = "szeró|szeróm|szerver|szerverem|ip".split("|");
		
		for(int x = 0; x < words.length; x++)
			for(int y = 0; y < blocked.length; y++)
				if(words[x].equalsIgnoreCase(blocked[y]))
					return true;
		return false;
	}


    @EventHandler(priority = EventPriority.HIGH)
    public static void blockChange(EntityChangeBlockEvent event)
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
    
    @EventHandler(priority = EventPriority.HIGH)
    public static void InventoryOpenEvent(InventoryOpenEvent event)
    {
    	if(getPlayer((Player) event.getPlayer()).loggedIn == false)
    		event.setCancelled(true);
    	else if(event.getInventory().getHolder() instanceof Chest || event.getInventory().getHolder() instanceof DoubleChest)
            event.setCancelled(false);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public static void PlayerPickupItemEvent(PlayerPickupItemEvent event)
    {
    	Player player = event.getPlayer();
    	Integer perm = getPlayer(player).permission;
    	
    	if(getPlayer(player).loggedIn == false)
    		event.setCancelled(true);
    	if(perm < 2)
    		event.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public static void PlayerDropItemEvent(PlayerDropItemEvent event)
    {
    	Player player = event.getPlayer();
    	Integer perm = getPlayer(player).permission;
    	
    	if(getPlayer(player).loggedIn == false)
    		event.setCancelled(true);
    	if(perm < 2)
    		event.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public static void onPlayerRespawn(PlayerRespawnEvent event)
    {
    	event.setRespawnLocation(Main.getLocationPoint("players_points", "SYSTEM", "spawn"));
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public static void onPlayerMove(PlayerMoveEvent event)
    {
    	
    }
    
	@EventHandler(priority = EventPriority.HIGH)
	public static void onPlayerDeathEvent(PlayerDeathEvent event)
    { 
		Player player = event.getEntity();

    	if(getPlayer(player).inInsurance > 0) {
    		event.setKeepLevel(true);
    		event.setKeepInventory(true);
    		player.sendMessage(YELLOW + ">> A cuccaidat megvédte a biztosítás.");
    	}
		
    	Main.saveLocationPoint("players_points", player.getName(), "death", player.getLocation());
    	event.setDroppedExp(0);
    	event.setDeathMessage(DARK_PURPLE + player.getName() + deathMessages[Main.random.nextInt(deathMessages.length)]);
    }
	
	@EventHandler(priority = EventPriority.HIGH)
    public static void onTeleport(PlayerTeleportEvent event)
    {
    	if(event.getCause().equals(TeleportCause.END_PORTAL)){
    		Location nether;
    		nether = new Location(Bukkit.getWorld("world_nether"), Bukkit.getWorld("world_nether").getSpawnLocation().getX(), Bukkit.getWorld("world_nether").getSpawnLocation().getY(), Bukkit.getWorld("world_nether").getSpawnLocation().getZ());

			event.getPlayer().sendMessage(DARK_RED+"Az alvilágba teleportálsz.");
    		event.setTo(nether);
    		return;
    	}
    }

	@EventHandler(priority = EventPriority.HIGH)
	public static void PlayerBucketEmptyEvent(PlayerBucketEmptyEvent event)
    {
    	Player player = event.getPlayer();
    	Integer perm = getPlayer(player).permission;
    	
    	if(getPlayer(player).loggedIn == false)
    		event.setCancelled(true);
    	if(perm < 4 && player.getItemInHand().getType().equals(Material.LAVA_BUCKET))
    		event.setCancelled(true);
    	if(perm < 2 && player.getItemInHand().getType().equals(Material.WATER_BUCKET))
    		event.setCancelled(true);
    }
	
	public static BlockFace getPlayerDirection(Player player, Boolean inverted) {
		double d = (player.getLocation().getYaw() * 4.0F / 360.0F) + 0.5D;
		int i = (int) d;
		
		if(inverted)
			switch(d < i ? i - 1 : i) {
				case 0: return BlockFace.NORTH;
				case 1: return BlockFace.EAST;
				case 2: return BlockFace.SOUTH;
				case 3: return BlockFace.WEST;
				case 4: return BlockFace.SOUTH;
				default: return null;
			}
		else
			switch(d < i ? i - 1 : i) {
				case 0: return BlockFace.SOUTH;
				case 1: return BlockFace.WEST;
				case 2: return BlockFace.NORTH;
				case 3: return BlockFace.EAST;
				case 4: return BlockFace.NORTH;
				default: return null;
			}
	}

	public static String listActionItems(ItemStack[] items) {
		String out = "";

		for(int i = 0; i < items.length; i++)
			if(items[i] != null)
				if(i == items.length - 1)
					out = out + items[i].getAmount() + " darab " + convertMaterialName(items[i].getType()) + ".";
				else
					out = out + items[i].getAmount() + " darab " + convertMaterialName(items[i].getType()) + ", ";
		
		return out;
	}
	public static String convertMaterialName(Material mat) {
		switch(mat.toString().toUpperCase()) {
			case "STONE": return "kö";
			case "GRANITE": return "gránit";
			case "POLISHED_GRANITE": return "";
			case "DIORITE": return "diorit";
			case "POLISHED_DIORITE": return "";
			case "ANDESITE": return "andezit";
			case "POLISHED_ANDESITE": return "";
			case "GRASS": return "fü kocka";
			case "DIRT": return "föld";
			case "PODZOL": return "podzol";
			case "COBBLESTONE": return "tört kö";
			
			default: return mat.toString().toLowerCase();
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler(priority = EventPriority.HIGH)
	public static void PlayerInteractEvent(PlayerInteractEvent event)
    {
    	Player player = event.getPlayer();
    	ServerPlayer splayer = getPlayer(player);
    	Integer perm = getPlayer(player).permission;
    	ItemStack item = player.getItemInHand();
    	World world = player.getWorld();

    	if(event.getClickedBlock() == null)
    		return;
    	if(event.getPlayer() == null)
    		return;
    	
    	Block block = event.getClickedBlock();
    	int blockx = block.getLocation().getBlockX();
    	int blocky = block.getLocation().getBlockY();
    	int blockz = block.getLocation().getBlockZ();
    	Block block2 = null;
    	
    	/**/ // Magic stick
    	if(event.getAction().equals(Action.LEFT_CLICK_BLOCK) && player.getGameMode().equals(GameMode.SURVIVAL))
    	{
    		magicStick(player, block.getLocation(), player.getItemInHand().getType(), 16, 10);
    	}
    	
    	/**/ // Open blocked chests
    	if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType().equals(Material.CHEST)) {
    		if(!block.getRelative(BlockFace.UP).getType().equals(Material.AIR)) {
    			Chest chest = (Chest) block.getState();
        		player.openInventory(chest.getInventory());
    		}
    	}
    	
    	if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType().equals(Material.STONE_BUTTON)) {
    		Button button = (Button) block.getState().getData();

    		if(block.getRelative(button.getAttachedFace()).getType().equals(Material.CHEST))
    		{
    			Chest chest = (Chest) block.getRelative(button.getAttachedFace()).getState();
    			Block underBlock = block.getRelative(button.getAttachedFace()).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN);
    			
    			if(underBlock.getType().equals(Material.IRON_BLOCK))
    			{
    				chest.getBlockInventory().clear();
        			chest.update();
        			
        			player.sendMessage(YELLOW + ">> A kuka bedarálja a szemeted.");
    			}
    			else if(underBlock.getType().equals(Material.GOLD_BLOCK)) {
    				Integer fullTransaction = 0;
    				Inventory inv = chest.getBlockInventory();
    				
    				for(int i = 0; i < inv.getSize(); i++) {
    					if(inv.getItem(i) != null)
	    					if(inv.getItem(i).getType().equals(Material.DIAMOND))
	    					{
	    						splayer.saveMoney(splayer.getMoney() + (inv.getItem(i).getAmount()));
	    						fullTransaction = fullTransaction + (inv.getItem(i).getAmount());
	    						inv.clear(i);
	    					}
	    					else if(inv.getItem(i).getType().equals(Material.DIAMOND_BLOCK)) {
	    						splayer.saveMoney(splayer.getMoney() + (inv.getItem(i).getAmount() * 9));
	    						fullTransaction = fullTransaction + (inv.getItem(i).getAmount() * 9);
	    						inv.clear(i);
	    					}
    				}
    				
    				if(fullTransaction == 0)
    					player.sendMessage(YELLOW + ">> Tegyél gyémántot a ládába, azután nyomd meg a gombot!");
    				else
    					player.sendMessage(GREEN + ">> Feltöltöttél " + YELLOW + fullTransaction + GREEN + " fillért a számládra.");
    			}
    			else if(underBlock.getType().equals(Material.EMERALD_BLOCK)) {
    				Boolean fullTransaction = false;
    				Inventory inv = chest.getBlockInventory();
    				
    				for(int i = 0; i < inv.getSize(); i++) {
    					if(inv.getItem(i) != null)
	    					if(inv.getItem(i).getType().equals(Material.GOLD_INGOT))
	    					{
	    						splayer.saveKarma(splayer.getKarma() + (inv.getItem(i).getAmount() * 2));
	    						fullTransaction = true;
	    						inv.clear(i);
	    					}
	    					else if(inv.getItem(i).getType().equals(Material.GOLD_BLOCK)){
	    						splayer.saveKarma(splayer.getKarma() + (inv.getItem(i).getAmount() * 18));
	    						fullTransaction = true;
	    						inv.clear(i);
	    					}
    				}
    				
    				if(!fullTransaction)
    					player.sendMessage(YELLOW + ">> Tegyél aranyat a ládába, azután nyomd meg a gombot!");
    				else
    					player.sendMessage(GREEN + ">> Az Istenek kegyesen fogadták a felajánlott aranyad.");
    			}
    			else if(underBlock.getType().equals(Material.REDSTONE_BLOCK)) {
    				player.sendMessage(RED + ">> A posta ezen szolgáltatása jelenleg szünetel.");
    			}
    			else if(underBlock.getType().equals(Material.DIAMOND_BLOCK)) {
    				Inventory inv = chest.getBlockInventory();
    				ItemStack[] items = new ItemStack[inv.getSize()];
    				Integer itemsCounter = 0;
    				
    				for(int i = 0; i < inv.getSize(); i++) {
    					if(inv.getItem(i) != null)
    					{
    						items[itemsCounter] = new ItemStack(inv.getItem(i));
    						itemsCounter++;
    						inv.clear(i);
    					}
    				}
    				
    				if(Arrays.asList(items).isEmpty())
    					player.sendMessage(YELLOW + ">> Tegyél gyémántot a ládába, azután nyomd meg a gombot!");
    				else {
    					player.sendMessage(GREEN + listActionItems(items));
    					player.sendMessage(GREEN + ">> Elindítottál egy aukciót.");
    				}
    			}
    		}
    	}
    	
    	/**/ // Akció gombok - Gombok
    	if((event.getAction() == Action.RIGHT_CLICK_BLOCK) && (block.getType() == Material.WOOD_BUTTON || block.getType() == Material.STONE_BUTTON)) {
    		String cmd = Database.grab(String.class, "actionbuttons", "cmd", "world = '"+world.getName()+"' AND x='"+blockx+"' AND y='"+blocky+"' AND z='"+blockz+"' AND type='1'", "noCmd").toString();
    		if(cmd.equals("noCmd") == false)
    			player.chat("/"+cmd);
    	}
    	
    	/**/ // Double woodendoors
    	if(event.getAction() == Action.RIGHT_CLICK_BLOCK && Arrays.asList(DoorTypes).contains(block.getType())) {
    		if(Arrays.asList(DoorTypes).contains(block.getRelative(BlockFace.DOWN).getType()))
    			block = block.getRelative(BlockFace.DOWN);
    		
    		if(Arrays.asList(DoorTypes).contains(block.getRelative(BlockFace.EAST).getType()))
    			openDoor(block.getRelative(BlockFace.EAST));
    		else if(Arrays.asList(DoorTypes).contains(block.getRelative(BlockFace.WEST).getType()))
    			openDoor(block.getRelative(BlockFace.WEST));
    		else if(Arrays.asList(DoorTypes).contains(block.getRelative(BlockFace.NORTH).getType()))
    			openDoor(block.getRelative(BlockFace.NORTH));
    		else if(Arrays.asList(DoorTypes).contains(block.getRelative(BlockFace.SOUTH).getType()))
    			openDoor(block.getRelative(BlockFace.SOUTH));
    	}
    	
    	/**/ // Openable irondoors
    	if(event.getAction() == Action.RIGHT_CLICK_BLOCK && block.getType().equals(Material.IRON_DOOR_BLOCK)) {
    		
    		Material[] underBlocks = {Material.IRON_BLOCK, Material.GOLD_BLOCK, Material.EMERALD_BLOCK, Material.DIAMOND_BLOCK, Material.BEDROCK};
    		
    		Block underBlock1 = null;
    		Block underBlock2 = null;
    		
    		if(Arrays.asList(underBlocks).contains(block.getLocation().getWorld().getBlockAt(blockx, blocky - 1, blockz).getType()))
    			underBlock1 = block.getLocation().getWorld().getBlockAt(blockx, blocky - 1, blockz);
    		else if(Arrays.asList(underBlocks).contains(block.getLocation().getWorld().getBlockAt(blockx, blocky - 2, blockz).getType()))
    			underBlock1 = block.getLocation().getWorld().getBlockAt(blockx, blocky - 2, blockz);
    		else if(Arrays.asList(underBlocks).contains(block.getLocation().getWorld().getBlockAt(blockx, blocky - 3, blockz).getType()))
    			underBlock1 = block.getLocation().getWorld().getBlockAt(blockx, blocky - 3, blockz);

    		if(underBlock1 != null) {
    			if(underBlock1.getRelative(BlockFace.EAST).getType().equals(underBlock1.getType()))
        			underBlock2 = underBlock1.getRelative(BlockFace.EAST);
        		else if(underBlock1.getRelative(BlockFace.WEST).getType().equals(underBlock1.getType()))
        			underBlock2 = underBlock1.getRelative(BlockFace.WEST);
        		else if(underBlock1.getRelative(BlockFace.NORTH).getType().equals(underBlock1.getType()))
        			underBlock2 = underBlock1.getRelative(BlockFace.NORTH);
        		else if(underBlock1.getRelative(BlockFace.SOUTH).getType().equals(underBlock1.getType()))
        			underBlock2 = underBlock1.getRelative(BlockFace.SOUTH);
        		
    			if(underBlock2 != null)
    				if(underBlock2.getRelative(BlockFace.UP).getRelative(BlockFace.UP).getType().equals(Material.IRON_DOOR_BLOCK))
    					block2 = underBlock2.getRelative(BlockFace.UP).getRelative(BlockFace.UP);
        		
    			if(perm < 2)
    				player.sendMessage(YELLOW + ">> Épitési jog nélkül nem tudsz ajtókat kinyitni.");
    			else if(perm < 3 && underBlock1.getType().equals(Material.GOLD_BLOCK))
    				player.sendMessage(YELLOW + ">> Ezt a régi, rozsdás ajtót nem tudod kinyitni.");
    			else if(perm < 4 && underBlock1.getType().equals(Material.EMERALD_BLOCK))
    				player.sendMessage(YELLOW + ">> Ez az ajtó csak játékeseményeken nyílik ki.");
    			else if(perm < 4 && underBlock1.getType().equals(Material.DIAMOND_BLOCK))
    				player.sendMessage(YELLOW + ">> Ezt a régi, rozsdás ajtót nem tudod kinyitni.");
    			else if(perm < 5 && underBlock1.getType().equals(Material.BEDROCK))
    				player.sendMessage(YELLOW + ">> Ezt a régi, rozsdás ajtót nem tudod kinyitni.");
    			else {
    				String[] lines1_1 = null;
    				String[] lines1_2 = null;
    				String[] lines1_3 = null;
    				String[] lines1_4 = null;
    				if(underBlock1.getRelative(BlockFace.EAST).getType().equals(Material.WALL_SIGN))
    					lines1_1 = ((Sign)underBlock1.getRelative(BlockFace.EAST).getState()).getLines();
    				if(underBlock1.getRelative(BlockFace.WEST).getType().equals(Material.WALL_SIGN))
    					lines1_2 = ((Sign)underBlock1.getRelative(BlockFace.WEST).getState()).getLines();
    				if(underBlock1.getRelative(BlockFace.NORTH).getType().equals(Material.WALL_SIGN))
    					lines1_3 = ((Sign)underBlock1.getRelative(BlockFace.NORTH).getState()).getLines();
    				if(underBlock1.getRelative(BlockFace.SOUTH).getType().equals(Material.WALL_SIGN))
    					lines1_4 = ((Sign)underBlock1.getRelative(BlockFace.SOUTH).getState()).getLines();
    				
    				if(block2 == null || !underBlock1.getType().equals(underBlock2.getType()))
    				{
    					if(lines1_1 == null && lines1_2 == null && lines1_3 == null && lines1_4 == null)
    						openDoor(block);
    					else {
    						Boolean canEnter = false;
    						
    						if(lines1_1 != null)
    						for(int i = 0; i < lines1_1.length; i++)
    							if(lines1_1[i].equalsIgnoreCase(player.getName()))
    								canEnter = true;
    						
    						if(lines1_2 != null)
    						for(int i = 0; i < lines1_2.length; i++)
    							if(lines1_2[i].equalsIgnoreCase(player.getName()))
    								canEnter = true;
    						
    						if(lines1_3 != null)
    						for(int i = 0; i < lines1_3.length; i++)
    							if(lines1_3[i].equalsIgnoreCase(player.getName()))
    								canEnter = true;
    						
    						if(lines1_4 != null)
    						for(int i = 0; i < lines1_4.length; i++)
    							if(lines1_4[i].equalsIgnoreCase(player.getName()))
    								canEnter = true;
    						
    						if(canEnter == true || perm > 4)
    							openDoor(block);
    						else
    							player.sendMessage(YELLOW + ">> Ezt a régi, rozsdás ajtót nem tudod kinyitni.");
    					}
    				}
    				else {
    					String[] lines2_1 = null;
        				String[] lines2_2 = null;
        				String[] lines2_3 = null;
        				String[] lines2_4 = null;
        				if(underBlock2.getRelative(BlockFace.EAST).getType().equals(Material.WALL_SIGN))
        					lines2_1 = ((Sign)underBlock2.getRelative(BlockFace.EAST).getState()).getLines();
        				if(underBlock2.getRelative(BlockFace.WEST).getType().equals(Material.WALL_SIGN))
        					lines2_2 = ((Sign)underBlock2.getRelative(BlockFace.WEST).getState()).getLines();
        				if(underBlock2.getRelative(BlockFace.NORTH).getType().equals(Material.WALL_SIGN))
        					lines2_3 = ((Sign)underBlock2.getRelative(BlockFace.NORTH).getState()).getLines();
        				if(underBlock2.getRelative(BlockFace.SOUTH).getType().equals(Material.WALL_SIGN))
        					lines2_4 = ((Sign)underBlock2.getRelative(BlockFace.SOUTH).getState()).getLines();
        				
        				if(lines1_1 == null && lines1_2 == null && lines1_3 == null && lines1_4 == null &&
        				   lines2_1 == null && lines2_2 == null && lines2_3 == null && lines2_4 == null) {
        					openDoor(block);
        					openDoor(block2);
        				}
    					else {
    						Boolean canEnter = false;
    						
    						if(lines1_1 != null)
    						for(int i = 0; i < lines1_1.length; i++)
    							if(lines1_1[i].equalsIgnoreCase(player.getName()))
    								canEnter = true;
    						
    						if(lines1_2 != null)
    						for(int i = 0; i < lines1_2.length; i++)
    							if(lines1_2[i].equalsIgnoreCase(player.getName()))
    								canEnter = true;
    						
    						if(lines1_3 != null)
    						for(int i = 0; i < lines1_3.length; i++)
    							if(lines1_3[i].equalsIgnoreCase(player.getName()))
    								canEnter = true;
    						
    						if(lines1_4 != null)
    						for(int i = 0; i < lines1_4.length; i++)
    							if(lines1_4[i].equalsIgnoreCase(player.getName()))
    								canEnter = true;
    						
    						if(lines2_1 != null)
        					for(int i = 0; i < lines2_1.length; i++)
        						if(lines2_1[i].equalsIgnoreCase(player.getName()))
        							canEnter = true;
        						
        					if(lines2_2 != null)
        					for(int i = 0; i < lines2_2.length; i++)
        						if(lines2_2[i].equalsIgnoreCase(player.getName()))
        							canEnter = true;
        						
        					if(lines2_3 != null)
        					for(int i = 0; i < lines2_3.length; i++)
        						if(lines2_3[i].equalsIgnoreCase(player.getName()))
        							canEnter = true;
        						
        					if(lines2_4 != null)
        					for(int i = 0; i < lines2_4.length; i++)
        						if(lines2_4[i].equalsIgnoreCase(player.getName()))
        							canEnter = true;
    						
    						if(canEnter == true || perm > 4) {
    							openDoor(block);
    							openDoor(block2);
    						}
    						else
    							player.sendMessage(YELLOW + ">> Ezt a régi, rozsdás ajtót nem tudod kinyitni.");
    					}
    				}
    			}
        		
    			event.setUseItemInHand(Result.DENY);
    		}
    	}
    	
    }
	
    @SuppressWarnings("deprecation")
	public static void openDoor(Block block) {
    	if(block.getData() >= 8)
			block = block.getRelative(BlockFace.DOWN);
		
		if (block.getData() < 4)
			block.setData((byte)(block.getData() + 4));
		else
			block.setData((byte)(block.getData() - 4));
		
    	block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
    }

    public static void magicStick(Player player, Location place, Material type, Integer size, Integer ySize) {
    	Double dist = Double.POSITIVE_INFINITY;
    	
    	int bx = place.getBlockX();
    	int by = place.getBlockY();
    	int bz = place.getBlockZ();
    	
    	if(ySize < 0) ySize = 0;
		if(ySize > 250) ySize = 250;

		if(type.equals(Material.COAL))
			type = Material.COAL_ORE;
		else if(type.equals(Material.IRON_INGOT))
			type = Material.IRON_ORE;
		else if(type.equals(Material.REDSTONE))
			type = Material.REDSTONE_ORE;
		else if(type.equals(Material.GOLD_INGOT))
			type = Material.GOLD_ORE;
		else if(type.equals(Material.EMERALD))
			type = Material.EMERALD_ORE;
		else if(type.equals(Material.DIAMOND))
			type = Material.DIAMOND_ORE;
		else
			return;
		
    	for(int x = place.getBlockX() - size; x <= place.getBlockX() + size; x++)
    		for(int y = place.getBlockY() - ySize; y <= place.getBlockY() + ySize; y++)
    			for(int z = place.getBlockZ() - size; z <= place.getBlockZ() + size; z++)
    				if(place.getWorld().getBlockAt(x, y, z).getType().equals(type))
    					if(Math.sqrt((x-bx)*(x-bx) + (y-by)*(y-by) + (z-bz)*(z-bz)) < dist)
	    					dist = Math.sqrt((x-bx)*(x-bx) + (y-by)*(y-by) + (z-bz)*(z-bz));
    					

    	if(dist < 3)
			player.sendMessage(ChatColor.YELLOW + "Tûzforró!!!");
		else if(dist < 5)
			player.sendMessage(ChatColor.GOLD + "Forró!");
		else if(dist < 7.5)
			player.sendMessage(ChatColor.RED + "Melegedik!");
		else if(dist < 10)
			player.sendMessage(ChatColor.GRAY + "Langyos...");
		else if(dist < 16)
			player.sendMessage(ChatColor.AQUA + "Hideg.");
		else
			player.sendMessage(ChatColor.BLUE + "Jéghideg!!!");
    }
}
