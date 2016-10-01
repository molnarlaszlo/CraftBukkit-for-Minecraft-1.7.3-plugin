/**/ // Thread handling
	protected static BukkitScheduler threadSd = server.getScheduler();
	protected static HashMap<Integer, Boolean> threadSwitch = new HashMap<Integer, Boolean>();
	protected static HashMap<Integer, Integer> threadBody = new HashMap<Integer, Integer>();
	protected static HashMap<Integer, Integer> threadDelay = new HashMap<Integer, Integer>();
	protected static HashMap<Integer, Callable<Void>> threadFunc = new HashMap<Integer, Callable<Void>>();
	
	protected static void stopThreads() {
		for(int i = 0; i < threadSwitch.size(); i++) {
			if(threadSwitch.get(i) == true) {
				threadSd.cancelTask(i);
				threadSwitch.put(i, false);
			}
		}
	}
	protected static void startThread(Integer id) {
		if(threadBody.containsKey(id) == true) {
			if(threadSwitch.get(id) == false) {
				threadSwitch.put(id, true);
				threadSd.runTask(plugin, threadBody.get(id));
			}
			else {
				logger.info("The thread is already running.");
			}
		}
		else {
			logger.info("The thread does not exist.");
		}
	}
	protected static void openThread(Integer id, Callable<Void> func, Integer delay) {
		if(threadSwitch.containsKey(id) == false) {
			
			/***/ // Define the head
			threadSwitch.put(id, true);
			threadDelay.put(id, delay);
			threadFunc.put(id, func);
			
			/***/ // Define the body
			threadBody.put(id, threadSd.scheduleSyncRepeatingTask(plugin, new Runnable() { public void run() { try {
				func.call();
			} catch (Exception e) {
				e.printStackTrace();
			} }}, 0L, delay));
		}
		else {
			logger.info("The thread is already defined.");
		}
	}
	protected static Integer firstOpenThread() {
		Boolean success = false;
		Integer counter = 0;
		
		while(success == false) {
			if(threadSwitch.containsKey(counter) == false)
				return counter;
			else
				counter++;
		}
		
		return null;
	}
	
	/**/ // Trivia
	protected static Callable<Void> Trivia_main() {
		
		return null;
	}
	
	
					Sign sign = null;
				BlockState block = player.getWorld().getBlockAt(player.getWorld().getSpawnLocation()).getState();
				if(block instanceof Sign)
				{
					sign = (Sign)block;
					String hanyan = sign.getLine(1).replaceAll(YELLOW+"", "");
					sign.setLine(1, ""+YELLOW+(Integer.parseInt(hanyan)+1));
					sign.update(true);
				}
				
				package Main;
import java.net.*;
import java.io.*;

public class Secur {
    public static String getText(String url) throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                    connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
            response.append(inputLine);

        in.close();

        return response.toString();
    }

    public static String main() throws Exception {
        String content = Secur.getText("http://mc.ultrablock.hu/code.html");
        //System.out.println(content);
		return content;
    }
}

else if(command.equalsIgnoreCase("warp"))
		{
			if(playerIsInJail(name))
				player.sendMessage(RED+"Börtönben vagy, ne is próbálj megszökni!");
			else if(args.length == 0)
			{
				String warpList = (String)WarpConfig.Warp.get((new StringBuilder(String.valueOf("WARP"))).toString());
				player.sendMessage(GREEN+"Ezekre a helyekre teleportálhatsz:\n"+warpList);
			}
			else if(args.length == 1)
			{
				String where = args[0];
				
				if(where == "del" || where == "warp" || where == "set" || (where == "modhq" && perm < 4) || (where == "adminhq" && perm != 5))
					player.sendMessage(RED+"Tiltott warp.");
				else if(WarpConfig.Warp.getBoolean(String.valueOf(where+".enabled")) == false || WarpConfig.Warp.getString(String.valueOf(where)) == null)
					player.sendMessage(RED+"Nem ismerek ilyen warp-ot!");
				else {
					int wX = WarpConfig.Warp.getInt(where+".x");
					int wY = WarpConfig.Warp.getInt(where+".y");
					int wZ = WarpConfig.Warp.getInt(where+".z");
					
					User.SaveLocation(player, "back", true);
					
					World world4 = Bukkit.getWorld(WarpConfig.Warp.getString(where+".w"));
					Location WarpTeleport = new Location(world4, wX, wY, wZ);
					
					player.teleport(WarpTeleport);
					player.sendMessage(LIGHT_PURPLE+"Elteleportálsz ide: "+where);
					player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 180);
				}
			}
			else if(args.length == 2 && perm > 4 && args[0].equalsIgnoreCase("del"))
			{
				String warp = args[1];
				
				if(WarpConfig.Warp.getBoolean(String.valueOf(warp+".enabled")) == false || WarpConfig.Warp.getString(String.valueOf(warp)) == null) {
					player.sendMessage(RED+"Nem ismerek ilyen warp-ot!");
				}
				else {
					Warp.delWarp(warp);
					player.sendMessage(RED+"Töröldted ezt a warp-ot: "+warp);
				}
			}
			else if(args.length == 2 && perm > 4 && args[0].equalsIgnoreCase("set"))
			{
				String warp = args[1];
				
				if(warp.equalsIgnoreCase("set") || warp.equalsIgnoreCase("warp") || warp.equalsIgnoreCase("del")) 
					player.sendMessage(RED+"Tiltott warp név.");
				else {
					double x = player.getLocation().getX();
					double y = player.getLocation().getY();
					double z = player.getLocation().getZ();
					
					String world3 = player.getWorld().getName();
					Warp.SaveWarp(player, warp, x, y, z, world3);
					player.sendMessage(YELLOW+"Beállítottad ezt a warp-ot: "+warp);
				}
			}
			else
			{
				player.sendMessage(RED+"Nem ismerek ilyen warp-ot!");
			}
		}
		

		@EventHandler(priority=org.bukkit.event.EventPriority.HIGHEST)
	public void onEntityDeathEvent(EntityDeathEvent event)
	{
		if(event.getEntity().getKiller() != null) {
			String victim = event.getEntity().toString();
			int xp = 0;
			String killer = event.getEntity().getKiller().getName().toString();
			Player killer2 = event.getEntity().getKiller();
			int level = UserConfig.UserConfig.getInt(killer+".l");
    		int xp2 = UserConfig.UserConfig.getInt(killer+".x");
			
			switch (victim) {
		        case "CraftPig":  			xp = 1; break;
		        case "CraftCow":  			xp = 2; break;
		        case "CraftHorse":  		xp = -5; break;
		        case "CraftSheep":  		xp = 3; break;
		        case "CraftChicken":  		xp = 2; break;
		        case "CraftSquid":  		xp = 1; break;
		        case "CraftVillager":  		xp = -50; break;
		        case "CraftPigMooshroom": 	xp = 4; break;
		        case "CraftCreeper":  		xp = 10; break;
		        case "CraftSkeleton":  		xp = 5; break;
		        case "CraftSpider":  		xp = 7; break;
		        case "CraftZombie":  		xp = 5; break;
		        case "CraftSlime":  		xp = 15; break;
		        case "CraftGhast":  		xp = 50; break;
		        case "CraftMagmaCube":  	xp = 50; break;
		        case "CraftPigZombie":  	xp = 12; break;
		        case "CraftEnderMan":  		xp = 30; break;
		        case "CraftCaveSpider":  	xp = 10; break;
		        case "CraftSilverFish":  	xp = 20; break;
		        case "CraftBlaze":  		xp = 25; break;
		        case "CraftBat":  			xp = 1; break;
		        case "CraftWitch":  		xp = 30; break;
		        case "CraftOcelot":  		xp = -10; break;
		        //case "CraftWolf":  		xp = -15; break;
		
		        default: xp = 0; break;
			}
		}
	}

   
    	if(player.getItemInHand().getType() == Material.GOLD_SWORD){
    		double fx_x = player.getLocation().getX();
    		double fx_y = player.getLocation().getY();
    		double fx_z = player.getLocation().getZ();
    		
    		String fx_w = player.getWorld().getName();

    		if(event.getAction() == Action.LEFT_CLICK_BLOCK){
    			UserConfig.UserConfig.set(player.getName()+".fx1.x", fx_x);
    			UserConfig.UserConfig.set(player.getName()+".fx1.y", fx_y);
    			UserConfig.UserConfig.set(player.getName()+".fx1.z", fx_z);
    			UserConfig.UserConfig.set(player.getName()+".fx1.w", fx_w);
    			UserConfig.saveConfig();
    			
    			player.sendMessage(GREEN+"Beállítottad a zóna egyik pontját!");
    			event.setCancelled(true);
    			return;
    		}
    		
    		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
    			UserConfig.UserConfig.set(player.getName()+".fx2.x", fx_x);
    			UserConfig.UserConfig.set(player.getName()+".fx2.y", fx_y);
    			UserConfig.UserConfig.set(player.getName()+".fx2.z", fx_z);
    			UserConfig.UserConfig.set(player.getName()+".fx2.w", fx_w);
    			UserConfig.saveConfig();
    			
    			player.sendMessage(GREEN+"Beállítottad a zóna egyik pontját!");
    			event.setCancelled(true);
    			return;
    		}
    		
    		else{return;}
    	}
    	
    	// Get Book data
    	if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.BOOKSHELF)
    	{
    		Block book = event.getClickedBlock();
    		int x = book.getX();
    		int y = book.getY();
    		int z = book.getZ();
    		String bookaddres = x+"-"+y+"-"+z;

    		int id = BookDataConfig.BookData.getInt(String.valueOf(bookaddres+".Id"));
    		if(id > 0){player.sendMessage(AQUA+"A könyv azonosítója amire nézel: "+id+"\n"+GRAY+">> További információkért keress fel egy moderátort.");}
    		return;
    	}
    	
    	// Teleport with book
    	if(event.getAction() == Action.LEFT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.BOOKSHELF)
    	{
    		Block book = event.getClickedBlock();
    		int x = book.getX();
    		int y = book.getY();
    		int z = book.getZ();
    		String bookaddres = x+"-"+y+"-"+z;
    		
    		/* CREATE NEW BOOK*/
    		int IDE = UserConfig.UserConfig.getInt((new StringBuilder(String.valueOf(player.getName()+".IDE"))).toString());
    		if(IDE == 185)
    		{
    			int idid = BookDataConfig.BookData.getInt((new StringBuilder(String.valueOf(bookaddres))).append(".Id").toString());
        		
        		player.sendMessage(RED+"Törölted a "+idid+" azonosítójú varázskönyvet!");
        		
        		BookDataConfig.BookData.set((new StringBuilder(String.valueOf(bookaddres)+".Id")).toString(), String.valueOf("numb"));
            	//Csináljunk neki helyre állítást mint a welcome rep
            	BookDataConfig.saveConfig();
            	UserConfig.UserConfig.set((new StringBuilder(String.valueOf(player.getName()))).append(".IDE").toString(), Integer.valueOf(0));
        		UserConfig.saveConfig();
        		return;
    		}
    		
    		if(IDE == 186)
    		{
    			int xx = Integer.parseInt(UserConfig.UserConfig.getString(player.getName()+".Point"+".X"));
        		int yyy = Integer.parseInt(UserConfig.UserConfig.getString(player.getName()+".Point"+".Y"));
        		
        		int yy = yyy + 1;
        		int zz = Integer.parseInt(UserConfig.UserConfig.getString(player.getName()+".Point"+".Z"));
        		String ww = UserConfig.UserConfig.getString(player.getName()+".Point"+".W");

        		if(xx == 0 || yy == 0 || z == 0)
        		{
        			player.sendMessage(YELLOW+"Nincs kijelölt fastpoint-od.");
        			return;
        		}

        		int id = BookDataConfig.BookData.getInt((new StringBuilder(String.valueOf("max"))).toString());

        		if(BookDataConfig.BookData.getString(String.valueOf((bookaddres)+".Id")) == null || BookDataConfig.BookData.getString(String.valueOf((bookaddres)+".Id")) == "numb")
        		{
	        		BookDataConfig.BookData.set((new StringBuilder(String.valueOf(x+"-"+y+"-"+z)+".Id")).toString(), Integer.valueOf(id));
	            	BookDataConfig.BookData.set((new StringBuilder(String.valueOf("max"))).toString(), Integer.valueOf(id+1));
	            	BookDataConfig.BookData.set((new StringBuilder(String.valueOf(x+"-"+y+"-"+z)+".X")).toString(), Integer.valueOf(xx));
	            	BookDataConfig.BookData.set((new StringBuilder(String.valueOf(x+"-"+y+"-"+z)+".Y")).toString(), Integer.valueOf(yy));
	            	BookDataConfig.BookData.set((new StringBuilder(String.valueOf(x+"-"+y+"-"+z)+".Z")).toString(), Integer.valueOf(zz));
	            	BookDataConfig.BookData.set((new StringBuilder(String.valueOf(x+"-"+y+"-"+z)+".W")).toString(), String.valueOf(ww));
	            	BookDataConfig.saveConfig();
	            	
	            	UserConfig.UserConfig.set((new StringBuilder(String.valueOf(player.getName()+".Point"))).append(".W").toString(), Integer.valueOf(0));
	        		UserConfig.UserConfig.set((new StringBuilder(String.valueOf(player.getName()+".Point"))).append(".X").toString(), Integer.valueOf(0));
	                UserConfig.UserConfig.set((new StringBuilder(String.valueOf(player.getName()+".Point"))).append(".Y").toString(), Integer.valueOf(0));
	                UserConfig.UserConfig.set((new StringBuilder(String.valueOf(player.getName()+".Point"))).append(".Z").toString(), Integer.valueOf(0));
	        		UserConfig.UserConfig.set((new StringBuilder(String.valueOf(player.getName()))).append(".IDE").toString(), Integer.valueOf(0));
	        		UserConfig.saveConfig();
	        		player.sendMessage(GRAY+"["+GREEN+"book:"+id+GRAY+"] Létrehozva itt: "+GREEN+bookaddres);
	        		event.setCancelled(true);
	        		return;
        		}
        		
        		else
        		{
					int idid = BookDataConfig.BookData.getInt((new StringBuilder(String.valueOf(bookaddres))).append(".Id").toString());
					player.sendMessage(RED+"Már van aktív varázskönyv a közeledben aminek azonosítója ez: "+idid);
        			return;
        		}
    		}
    		

    		if(BookDataConfig.BookData.getString(String.valueOf((x+"-"+y+"-"+z)+".Id")) == null || BookDataConfig.BookData.getInt(String.valueOf((x+"-"+y+"-"+z)+".Id")) == 0)
    		{
    			return;
    		}
    		
    		event.setCancelled(true);
    		User.SaveLocation(player, "back", true);

    		int xxx = BookDataConfig.BookData.getInt((new StringBuilder(String.valueOf(bookaddres))).append(".X").toString());
    		int yyy = BookDataConfig.BookData.getInt((new StringBuilder(String.valueOf(bookaddres))).append(".Y").toString());
    		int zzz = BookDataConfig.BookData.getInt((new StringBuilder(String.valueOf(bookaddres))).append(".Z").toString());
    		
    		Location mLoc;
    		World w = Bukkit.getWorld((String) BookDataConfig.BookData.get((new StringBuilder(String.valueOf(bookaddres))).append(".W").toString()));
        
    		mLoc = new Location(w, xxx, yyy, zzz);
        
    		player.teleport(mLoc);
    		player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 180);
    		player.sendMessage(ChatColor.LIGHT_PURPLE + "A varázskönyv elteleportál!");
    		return;
    	}

		
	else if(command.equalsIgnoreCase("v")) /*** TODO ***/
			{
				String trivalasz = Trivia.getAnswer().toLowerCase();
				String valasz = FinalMessage(args, 0);
				String display_valasz = FinalMessage(args, 0);
				
				if(TriviaConfig.Trivia.getBoolean((new StringBuilder(String.valueOf(("Config")))+".Van")) == true && TriviaConfig.Trivia.getBoolean((new StringBuilder(String.valueOf(("Config")))+".Failed")) == false)
				{
					if(args.length < 1)
						player.sendMessage(RED+">> Így használd: /v válasz");
					else if(valasz.toLowerCase().equalsIgnoreCase(trivalasz))
					{
						String hint_raw = Trivia.getAnswer();
						int hint_count = hint_raw.length();
						int pont = 1;
						
						if(hint_count>8){pont=2;}
						
						server.broadcastMessage(GREEN+player.getName()+" szerint: "+display_valasz);
						server.broadcastMessage(GREEN+player.getName()+" eltalálta a helyes választ! Jutalma "+pont+" pont!");
			 
						UserConfig.UserConfig.set(player.getName()+".Pont", UserConfig.UserConfig.getInt(player.getName()+".Pont")+pont);
						UserConfig.saveConfig();
						
						UserConfig.UserConfig.set(player.getName()+".Karma", UserConfig.UserConfig.getInt(player.getName()+".Karma")+30);
						//server.getScheduler().cancelTask(tid);

						Trivia.Failed();
					}
					else
					{
						server.broadcastMessage(GOLD+player.getName()+WHITE+" szerint: "+display_valasz);
					}
				}
				else
				{
					player.sendMessage(RED+"Jelenleg nincs feltett kérdés!");
				}
			}
			
			if(command.equalsIgnoreCase("kuka"))
			{
				PlayerInventory playerInv = player.getInventory();

				Integer karma = UserConfig.UserConfig.getInt(player.getName()+".Karma");
				Integer db = player.getItemInHand().getAmount();
				Integer pont = UserConfig.UserConfig.getInt(player.getName()+".Pont");
				
				if(player.getTargetBlock((Set<Material>) null, 50).getType() != Material.WOOD_BUTTON && player.getTargetBlock((Set<Material>) null, 50).getType() != Material.STONE_BUTTON)
					player.sendMessage(RED+"Ezt a parancsot csak akció gombra telepítve használhatod!");
				else if(player.getItemInHand().getType() == Material.AIR)
					player.sendMessage(RED+"Hidd el, fájna ha ledaráltatnád a kezed vele.");
				else if(player.getItemInHand().getType() == Material.GOLD_INGOT || player.getItemInHand().getType() == Material.LAPIS_ORE)
				{	
					Integer newmoney = karma + (db * 3);
					UserConfig.UserConfig.set(player.getName()+".Karma", newmoney);
					UserConfig.saveConfig();
					
					player.setItemInHand(null);
					player.sendMessage(GOLD+"A kuka megvillan és nagy csikorgást hallasz benne.");
				}
				else if(player.getItemInHand().getType() == Material.GOLD_BLOCK)
				{
					if(karma >= 200)
						player.sendMessage(RED+"A kuka nem bedarálja be, mert nem történne semmi.");
					else {
						Integer newmoney = karma + (9 * 3);
						UserConfig.UserConfig.set(player.getName()+".Karma", newmoney);
						UserConfig.saveConfig();
						
						player.setItemInHand(null);
						player.sendMessage(GOLD+"A kuka megvillan és nagy csikorgást hallasz benne.");
					}
				}	
				else if(player.getItemInHand().getType() == Material.DIAMOND_BLOCK)
				{
					Integer newmoney = pont + 1;
					
					UserConfig.UserConfig.set(player.getName()+".Pont", newmoney);
					UserConfig.saveConfig();
				
					player.setItemInHand(null);
					player.sendMessage(GOLD+"A kuka megvillan és nagy csikorgást hallasz benne.");
				}
				else {
					playerInv.setItemInHand(null);
					player.sendMessage(GRAY+"A kuka ledarálta a kezedben tartott tárgyat.");
				}
			}
			
			else if(command.equalsIgnoreCase("duhongo")) /*** TODO ***/
			{
				if(playerIsInJail(name))
					player.sendMessage(RED+"Börtönben vagy, ne is próbálj megszökni!");
				else if(args.length >= 0) 
				{
					User.SaveLocation(player, "back", true);
				
					Double xw = (double) -534;
					Double yw = (double) 75;
					Double zw = (double) 97;		   
					Location mLoc;
				
					mLoc = new Location(world, xw, yw, zw);
				
					player.teleport(mLoc);
					player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 180);
					player.sendMessage(GOLD +player.getName()+" a dühöngõbe teleportáltál..");
				}
			}
			
			else if(command.equalsIgnoreCase("adminbolt")) 
			{
				if(args.length == 0){
					player.sendMessage(RED+"Adminboltban ezeket vásárolhatod meg:");
					player.sendMessage(YELLOW+"bedrock"+WHITE+" - 640 fillér - "+YELLOW+"1 darab alapkö");
					player.sendMessage(YELLOW+"coal"+WHITE+" - 20 fillér - "+YELLOW+"1 darab szénérc");
					player.sendMessage(YELLOW+"lazurit"+WHITE+" - 20 fillér - "+YELLOW+"1 darab lazuritérc");
					player.sendMessage(YELLOW+"diamond"+WHITE+" - 320 fillér - "+YELLOW+"1 darab gyémántérc");
					player.sendMessage(YELLOW+"obsidian"+WHITE+" - 640 fillér - "+YELLOW+"64 darab obszidián");
					player.sendMessage(YELLOW+"emerald"+WHITE+" - 64 fillér - "+YELLOW+"1 darab smaragdérc");
					player.sendMessage(YELLOW+"redstone"+WHITE+" - 64 fillér - "+YELLOW+"1 darab vörösköérc");
					player.sendMessage(YELLOW+"mycelium"+WHITE+" - 640 fillér - "+YELLOW+"12 darab mycelium");
					player.sendMessage(RED+"lacom"+WHITE+" - 320 fillér - "+YELLOW+"1 darab "+RED+"Lacom"+YELLOW+" fej");
					player.sendMessage(YELLOW+"saddle"+WHITE+" - 20 fillér - "+YELLOW+"1 darab nyereg");
					player.sendMessage(YELLOW+"beacon"+WHITE+" - 800 fillér - "+YELLOW+"1 darab jelzöfény");
					player.sendMessage(YELLOW+"golden"+WHITE+" - 30 fillér - "+YELLOW+"3 darab aranyalma");
					player.sendMessage(YELLOW+"lead"+WHITE+" - 40 fillér - "+YELLOW+"1 darab lasszó");
					player.sendMessage(YELLOW+"name"+WHITE+" - 320 fillér - "+YELLOW+"1 darab névcédula");
					player.sendMessage(YELLOW+"star"+WHITE+" - 640 fillér - "+YELLOW+"1 darab nether csillag");
					player.sendMessage(YELLOW+"end"+WHITE+" - 640 fillér - "+YELLOW+"1 darab végzetportál");
					player.sendMessage(YELLOW+"table"+WHITE+" - 640 fillér - "+YELLOW+"1 darab varázslóasztal");
				}
				else if(player.getItemInHand().getType() != Material.AIR){
					player.sendMessage(RED+"Ezt csak üres kézzel vásárolhatod meg: "+YELLOW+args[0]);
				}
				else {
					Integer stack = 1;
					Integer id = 0;
					Integer price = 0;
					String namee = "";
					
					Boolean success = true;
					
					switch( args[0] ) {
					
						case "lacom":
							ItemStack playerskull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
							SkullMeta meta = (SkullMeta)playerskull.getItemMeta();
							meta.setOwner("Lacom");
							meta.setDisplayName(GREEN+"Lacom feje");
							playerskull.setItemMeta(meta);
							player.setItemInHand(playerskull);
							
							if(UserConfig.UserConfig.getInt(player.getName()+".Penz")-320 < 0){
								player.sendMessage(RED+"Nincs elég pénzed megvásárolni Lacom fejét!");
							}
							else {
								UserConfig.UserConfig.set(player.getName()+".Penz", (UserConfig.UserConfig.getInt(player.getName()+".Penz")-320));
								UserConfig.saveConfig();
								
								player.sendMessage(GREEN+"Megvásároltad "+RED+"Lacom"+GREEN+" fejét.");
							}
						break;
						case "bedrock":
							id=7;
							price=640;
							namee="alapkövet";
						break;
						case "coal":
							id=16;
							price=20;
							namee="szénércet";
						break;
						case "lazurit":
							id=21;
							price=20;
							namee="lazuritércet";
						break;
						case "diamond":
							id=56;
							price=320;
							namee="gyémántércet";
						break;
						case "obsidian":
							stack=64;
							id=49;
							price=640;
							namee="obszidiánt";
						break;
						case "emerald":
							id=129;
							price=64;
							namee="smaragdércet";
						break;
						case "redstone":
							id=73;
							price=64;
							namee="vörösköércet";
						break;
						case "mycelium":
							id=110;
							price=640;
							namee="myceliumot";
						break;
						case "saddle":
							id=329;
							price=20;
							namee="nyerget";
						break;
						case "beacon":
							id=138;
							price=800;
							namee="jelzöfényt";
						break;
						case "golden":
							stack=3;
							id=322;
							price=30;
							namee="arany almát";
						break;
						case "lead":
							id=420;
							price=20;
							namee="lasszót";
						break;
						case "name":
							id=421;
							price=320;
							namee="névcédulát";
						break;
						case "star":
							id=399;
							price=640;
							namee="pokol csillagot";
						break;
						case "end":
							id=120;
							price=640;
							namee="végzetportált";
						break;
						case "table":
							id=116;
							price=640;
							namee="varázsló asztalt";
						break;
						default:
							success = false;
						break;
					}

					if(success){
						if(UserConfig.UserConfig.getInt(player.getName()+".Penz")-price < 0)
							player.sendMessage(RED+"Nincs elég pénzed megvásárolni ezt: "+YELLOW+args[0]+RED+"!");
						else {
							UserConfig.UserConfig.set(player.getName()+".Penz", (UserConfig.UserConfig.getInt(player.getName()+".Penz")-price));
							UserConfig.saveConfig();
							
							player.setItemInHand(new ItemStack(id, stack));
							
							player.sendMessage(GREEN+"Megvásárolsz "+stack+" "+WHITE+namee+GREEN+".");
						}
					}
					else
						player.sendMessage(RED + "Nem ismerek ilyen terméket: " + YELLOW + args[0] + RED + "!");
					}
				}
