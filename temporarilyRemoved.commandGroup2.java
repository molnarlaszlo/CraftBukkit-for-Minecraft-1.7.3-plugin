		else if(command.equalsIgnoreCase("m") || command.equalsIgnoreCase("msg"))
		{
			if(args.length < 2)
				player.sendMessage(RED+">> Így használd: /m [játékosnév] [üzenet]");
			else {
				Player targetplayer = server.getPlayer(args[0]);
				if(targetplayer == null)
					player.sendMessage(RED + ">> A megadott játékos nem található.");
				else {
					String msg = FinalMessage(args, 1);
					player.sendMessage(GRAY + "[" + name + GRAY + " >> " + targetplayer.getName() + GRAY + "] " + msg);
					targetplayer.sendMessage(GRAY + "[" + targetplayer.getName() + GRAY + " << " + name + GRAY + "] " + msg);
	
					databaseUpdate("UPDATE players SET lastMsg = '"+targetplayer.getName()+"' WHERE name = '"+name+"'");
					databaseUpdate("UPDATE players SET lastMsg = '"+name+"' WHERE name = '"+targetplayer.getName()+"'");
					databaseInsert("INSERT INTO privatemessages (_when, _from, _to, _msg) VALUES ('"+getDate("")+"', '"+name+"', '"+targetplayer.getName()+"', '"+msg+"')");
					
					sendToModChannel(player, "Privát üzenet", DARK_PURPLE + "[" + GRAY + name + DARK_PURPLE + " >> " + targetplayer.getName() + DARK_PURPLE + "] " + GRAY + msg, false);
				}
			}
		}
		else if(command.equalsIgnoreCase("r"))
		{
			Player targetplayer = server.getPlayer(databaseGetString("lastMsg", "players", "name='"+name+"'", ""));
	
			if(args.length == 0)
				player.sendMessage(RED+">> Így használd: /r [üzenet]");
			else if(targetplayer == null)
				player.sendMessage(RED+(">> "+databaseGetString("lastMsg", "players", "name='"+name+"'", "")+" játékos nem tartózkodik a szerveren."));
			else {
				String msg = FinalMessage(args, 0);
				player.chat("/msg "+databaseGetString("lastMsg", "players", "name='"+name+"'", "")+" "+msg);
			}
		}

		
		/**/ // Permissions for administrators.
		if(perm > 4)
		{
			
		}
		
		
		/**/ // Permissions for moderators.
		if(perm > 3)
		{
			if(command.equalsIgnoreCase("modhelp"))
			{
				player.sendMessage(GRAY+"-------------<Moderátori parancs lista>--------------");
				player.sendMessage(AQUA + "/cc" + WHITE + " - Párbeszédablak törlése");
				player.sendMessage(AQUA + "/ide <játékos>" + WHITE + " - Játékos magadhoz rántása");
				player.sendMessage(AQUA + "/ktp x y z" + WHITE + " - Teleportálás koordináta szerint");
				player.sendMessage(AQUA + "/reggel" + WHITE + " - Idõ reggelre állítása");
				player.sendMessage(AQUA + "/este" + WHITE + " - Idõ estére állítása");
				player.sendMessage(AQUA + "/kick <játékos" + WHITE + " - Játékos kirúgása a szerverrõl");
				player.sendMessage(AQUA + "/h" + WHITE + " - Üzenet a moderátor csatornán");
				player.sendMessage(AQUA + "/inv <játékos>" + WHITE + " - Játékos eszköztárának megnyitása");
				player.sendMessage(AQUA + "/debug" + WHITE + " - Debug mód aktiválása");
				player.sendMessage(AQUA + "/fx" + WHITE + " - FastPoint beállítása");
				player.sendMessage(AQUA + "/pri" + WHITE + " - Bûnlista parancslistájának lekérése");
				player.sendMessage(AQUA + "/ip <játékos>" + WHITE + " - Játékos IP címének lekérése");
				player.sendMessage(AQUA + "/man <játékos>" + WHITE + " - Játékos kezelése");
				player.sendMessage(AQUA + "/memory" + WHITE + " - Memória teszt");
				player.sendMessage(AQUA + "/welcome játékos/set/del/rep" + WHITE + " - Játékos üdvözlõ üzenetének kezelése");
				player.sendMessage(GRAY+"-----------------------------------------------------");
			}
			else if(command.equalsIgnoreCase("id"))
			{
				player.sendMessage(YELLOW + "A kezedben tartott tárgy azonosítója: "+item.getTypeId());
			}
			else if(command.equalsIgnoreCase("fx"))
			{
				saveLocationPoint("players_points", name, "fx1", player.getLocation());
				player.sendMessage(YELLOW + "Frissíteted FastPont-od erre:  x: "+player.getLocation().getBlockX()+"; y: "+player.getLocation().getBlockY()+"; z: "+player.getLocation().getBlockZ());
			}
			else if(command.equalsIgnoreCase("cc"))
			{
				for(int i = 0; i < 180; i++)
					server.broadcastMessage("");	
				server.broadcastMessage(GREEN+"A chat ablakot törölte: "+player.getName());
			}
			else if(command.equalsIgnoreCase("h"))
			{
				if(args.length == 0)
					player.sendMessage(RED+">> Így használd: /h [üzenet]");
				else {
					String msg = FinalMessage(args, 0);
					databaseInsert("INSERT INTO privatemessages (_when, _from, _to, _msg) VALUES ('"+getDate("")+"', '"+name+"', 'DEV_CHANNEL', '"+msg+"')");
					sendToModChannel(player, name, DARK_PURPLE + "[DEV csatorna] " + YELLOW + name + WHITE + ": " + msg, true);
				}
			}
			else if(command.equalsIgnoreCase("ip"))
			{
				if(args.length == 1)
				{
					Player targetplayer = server.getPlayer(args[0]);
					if(targetplayer == null)
						player.sendMessage(RED + ">> A megadott játékos nem található."); 
					else
						player.sendMessage(GRAY+targetplayer.getName()+" IP címe: "+targetplayer.getAddress());
				}
				else
				{
					player.sendMessage(GRAY+"IP címed: "+player.getAddress());
				}
			}
			else if(command.equalsIgnoreCase("memory"))
			{
				int freeram = (int) Math.floor(Runtime.getRuntime().freeMemory() / 1024L / 1024L);
				int maxram = (int) Math.floor(Runtime.getRuntime().maxMemory() / 1024L / 1024L);
				int usedram = (maxram - freeram);
					
				player.sendMessage(GRAY+"---<{Szerver memória táblája alias Laggmeter}>---");
				player.sendMessage(GRAY+"Maximum: " + maxram + "MB");
				player.sendMessage(GRAY+"Felhasznált: " + usedram + "MB");
				player.sendMessage(GRAY+"Szabad: " + freeram + "MB");
				
				if(freeram < 256)
					player.sendMessage(RED+">> A szerver laggol!");
			}
			else if(command.equalsIgnoreCase("kick"))
			{
				if(args.length == 0)
					player.sendMessage(RED + ">> Így használd: /kick <játékos> [indoklás]");
				if(args.length == 1) {
					Player targetplayer = server.getPlayer(args[0]);
					
					if(server.getPlayer(args[0]) != null)
						player.sendMessage(RED + ">> A megadott játékos nincs a szerveren.");
					else {
						targetplayer.kickPlayer(RED + "Ki lettél rúgva a szerverröl!");
						server.broadcastMessage(RED + targetplayer.getName() + " ki lett rúgva a szerverrõl.");
					}
				}
				else {
					Player targetplayer = server.getPlayer(args[0]);
					
					if(server.getPlayer(args[0]) != null)
						player.sendMessage(RED + ">> A megadott játékos nincs a szerveren.");
					else {
						targetplayer.kickPlayer(RED + "Ki lettél rúgva a szerverröl! Indoklás:\n" + FinalMessage(args, 1));
						server.broadcastMessage(RED + targetplayer.getName() + " ki lett rúgva a szerverrõl.");
					}
				}
			}
			else if(command.equalsIgnoreCase("pri"))
			{
				if(args.length == 0)
				{
				 	player.sendMessage(GOLD + "[A priusz modul használata]");
				 	player.sendMessage(GREEN + "/pri "+GOLD+"-- priusz parancslista lekérése");
				 	player.sendMessage(GREEN + "/pri [játékos] (részletek) "+GOLD+"-- Játékos bûnlistájának lekérése, részletek megadásával bûn bejegyzése.");
				 	player.sendMessage(GREEN + "/pri [játékos] del "+GOLD+"-- Játékos teljes bûnlistájának törlése");
				 	player.sendMessage(GREEN + "A bûnlistába bejegyzést csak az erre vonatkozó szabvány szerint szabad tenni!");
				 	player.sendMessage(GOLD + "---------------------------");
				} 
				else if(args.length == 1)
				{
					/*if(Priszulisz.Priszulisz.getString(String.valueOf((args[0]+".ID"))) == null || Priszulisz.Priszulisz.getInt(String.valueOf((args[0]+".ID"))) == 0)
					{
					 	player.sendMessage(GRAY+" ** "+GREEN+args[0]+" játékosnak még egy bejegyzett bûne sincs.");
					}
					else {
						player.sendMessage(GRAY+" ** "+RED+args[0]+GRAY+" játékos bûnei:");
						int ID = Integer.parseInt(Priszulisz.Priszulisz.getString(String.valueOf((args[0]+".ID"))));
						
						for(int a = 1; a <= ID; a++)
						{
							String pri = (String) Priszulisz.Priszulisz.get((new StringBuilder(String.valueOf(args[0]))+"."+a+".PRI").toString());
							String dev = (String) Priszulisz.Priszulisz.get((new StringBuilder(String.valueOf(args[0]))+"."+a+".DEV").toString());
							String date = (String) Priszulisz.Priszulisz.get((new StringBuilder(String.valueOf(args[0]))+"."+a+".DATE").toString());
							
							player.sendMessage(GRAY+"("+RED+a+GRAY+") ["+RED+date+GRAY+"] "+dev+" : "+GRAY+pri);
						}
					}*/
				}
				else
				{
					/*int ID = Priszulisz.Priszulisz.getInt(String.valueOf(args[0]+".ID")) + 1;
					String PRI = FinalMessage(args, 1);

					Priszulisz.Priszulisz.set((new StringBuilder(String.valueOf(args[0]))+".ID").toString(), Integer.valueOf(ID));
					Priszulisz.Priszulisz.set((new StringBuilder(String.valueOf(args[0]))+"."+ID+".PRI").toString(), String.valueOf(PRI));
					Priszulisz.Priszulisz.set((new StringBuilder(String.valueOf(args[0]))+"."+ID+".DEV").toString(), String.valueOf(player.getName()));
					Priszulisz.Priszulisz.set((new StringBuilder(String.valueOf(args[0]))+"."+ID+".DATE").toString(), String.valueOf(getDate("")));
					Priszulisz.saveConfig();
					
					Object online_players[] = server.getOnlinePlayers().toArray();
					for(int i = 0; i < online_players.length; i++)
					{ 
						Player p = (Player) online_players[i];
						if(canReadModChanel(p))
						   p.sendMessage(DARK_PURPLE+"[Frissült "+DARK_PURPLE+args[0]+DARK_PURPLE+" játékos bûnlistája] "+GRAY+FinalMessage(args, 1));
					}*/
				}
			}
			else if(command.equalsIgnoreCase("ide"))
			{
				if(args.length == 1)
				{
					Player targetplayer = server.getPlayer(args[0]);
					
					if(targetplayer == null)
						player.sendMessage(RED + ">> A megadott játékos nem található.");
					else if(getPlayerStatBool("inJail", targetplayer.getName()))
						player.sendMessage(RED + targetplayer.getName() + " épp börtön büntetését tölti, nem teleportálhatod el.");
					else
					{
						Location loc = player.getLocation();
						databaseUpdate("UPDATE locations SET world = '"+loc.getWorld().getName()+"', x = '"+loc.getX()+"', y = '"+loc.getY()+"', z = '"+loc.getZ()+"', yaw = '"+loc.getYaw()+"', pitch = '"+loc.getPitch()+"', enabled = '1' WHERE name = '"+targetplayer.getName()+"_back'");

						targetplayer.teleport(player.getLocation());
						targetplayer.sendMessage(LIGHT_PURPLE + name + " elránt magához!");
						player.sendMessage(LIGHT_PURPLE + "Magadhoz teleportáltad ezt a játékost: "+targetplayer.getName());
					}
				}
				else
					player.sendMessage(RED + ">> Így használd: /ide játékos");
			}
			else if(command.equalsIgnoreCase("clean") || command.equalsIgnoreCase("clear"))
			{
				if(args.length == 0)
				{
					player.getInventory().clear();
					player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 90);
					player.sendMessage(LIGHT_PURPLE+"Kiürítetted saját eszköztárad!");
				}
				else if(args.length == 1)
				{
					Player targetplayer = server.getPlayer(args[0]);
					
					if(server.getPlayer(args[0]) != null)
					{
						targetplayer.getInventory().clear();
						targetplayer.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 90);
						targetplayer.sendMessage(GRAY+"Szokatlan érzés járja át a tested, és eltûnik minden felszerelésed!");
						player.sendMessage(LIGHT_PURPLE+"Kiürítetted "+targetplayer.getName()+" eszköztárát!");
					} 
					else
					{
						player.sendMessage(RED + ">> A megadott játékos nem található.");
					}
				}
				else
				{
					player.sendMessage(RED+">> Így használd: /clean [játékos]");
				}
			}
			else if(command.equalsIgnoreCase("debug"))
			{
				if(args.length != 1)
					player.sendMessage(RED+">> Így használd: /debug on/off");
				else if(args[0].equalsIgnoreCase("on"))
				{
					databaseUpdate("UPDATE players SET inDebug = '1' WHERE name='"+name+"'");
					player.sendMessage(YELLOW+"Bekapcsoltad a debug módot.");
				} 
				else if(args[0].equalsIgnoreCase("off"))
				{
					databaseUpdate("UPDATE players SET inDebug = '0' WHERE name='"+name+"'");
					player.sendMessage(YELLOW+"Kikapcsoltad a debug módot.");
				}
				else
					player.sendMessage(RED+">> Így használd: /debug on/off");
			}
			else if(command.equalsIgnoreCase("welcome"))
			{
				if(args.length == 0)
				{
					player.sendMessage(GOLD+"----<{Így használd a /welcome parancsot}>----");
					player.sendMessage(GRAY+">> /welcome játékos -- Játékos üdvözlõ üzenetének lekérése");
					player.sendMessage(GRAY+">> /welcome del játékos -- Játékos üdvözlõ üzenetének törlése");
					player.sendMessage(GRAY+">> /welcome set játékos üzenet -- Játékos üdvözlõ üzenetének beállítása");
					player.sendMessage(GOLD+"---------------------------------------------");
				}
				else if(args.length == 1)
				{
					if(Main.databaseGetString("welcome", "players", "name = '"+args[0]+"'", "").length() > 0)
			    	{
						player.sendMessage(GOLD + args[0] + GRAY + " üdvözlõ üzenete: ");
						player.sendMessage(GREEN + ">> " + Main.databaseGetString("welcome", "players", "name = '"+args[0]+"'", ""));
			    	}
					else {
						player.sendMessage(GOLD + args[0] + GRAY + " játékosnak nincs üdvözlõ üzenete.");
					}
				}
				else if(args.length > 1)
				{
					Player targetplayer = server.getPlayer(args[1]);
					if(targetplayer == null) {
						player.sendMessage(RED + ">> A megadott játékos nem található.");
					}
					else if(args[0].equalsIgnoreCase("set"))
					{
						String msg = FinalMessage(args, 2);
						player.sendMessage(GREEN+targetplayer.getName()+" üdvözlõ üzenetét beállítottad erre: " + msg);
						databaseUpdate("UPDATE players SET welcome = '"+msg+"' WHERE name = '"+targetplayer.getName()+"'");
					}
					else if(args[0].equalsIgnoreCase("del"))
					{
						player.sendMessage(GRAY+"Törölted "+GOLD+targetplayer.getName()+GRAY+" üdvözlö üzenetét.");
						databaseUpdate("UPDATE players SET welcome = '' WHERE name = '"+targetplayer.getName()+"'");
					}
					else
					{
						player.sendMessage(GOLD+"----<{Így használd a /welcome parancsot}>----");
						player.sendMessage(GRAY+">> /welcome játékos -- Játékos üdvözlõ üzenetének lekérése");
						player.sendMessage(GRAY+">> /welcome del játékos -- Játékos üdvözlõ üzenetének törlése");
						player.sendMessage(GRAY+">> /welcome set játékos üzenet -- Játékos üdvözlõ üzenetének beállítása");
						player.sendMessage(GOLD+"---------------------------------------------");
					}
				}
			}
			else if(command.equalsIgnoreCase("ktp"))
			{
				if(getPlayerStatBool("inJail", name))
					player.sendMessage(RED+"Börtönben vagy, ne is próbálj megszökni!");
				else if(args.length != 3)
					player.sendMessage(RED+">> Így használd: /ktp x y z");
				else {
					try {
						int ktp_x = Integer.parseInt(args[0]);
						int ktp_y = Integer.parseInt(args[1]);
						int ktp_z = Integer.parseInt(args[2]);
						 
						int pfoot = server.getWorld(player.getWorld().getName()).getBlockAt(ktp_x, ktp_y, ktp_z).getTypeId();
						int pfoot2 = server.getWorld(player.getWorld().getName()).getBlockAt(ktp_x, ktp_y+2, ktp_z).getTypeId();
						int pbody = server.getWorld(player.getWorld().getName()).getBlockAt(ktp_x, ktp_y+1, ktp_z).getTypeId();
						 
						// Nem engedjük a játékost falba teleportálni
						if(pfoot !=0 && pfoot !=70 && pfoot !=72 && pfoot !=323 && pfoot !=175 && pfoot !=397 && pfoot !=390 && pfoot !=37 && pfoot !=38 && pfoot !=6 && pfoot !=50 && pfoot !=76 && pfoot !=65 && pfoot !=39 && pfoot !=40 && pfoot !=78 && pfoot !=106 && pfoot !=111 && pfoot !=171 && pfoot !=175 && pfoot !=331 && pfoot !=27 && pfoot !=28 && pfoot !=66 && pfoot !=69 && pfoot !=77 && pfoot !=131 && pfoot !=143 && pfoot !=404 && pfoot !=356 && pfoot !=157 && server.getWorld(player.getWorld().getName()).getBlockAt(ktp_x, ktp_y, ktp_z).getType() != Material.WATER)
						{
							if(pfoot2 !=0 && pfoot2 !=70 && pfoot2 !=72 && pfoot2 !=323 && pfoot2 !=175 && pfoot2 !=397 && pfoot2 !=390 && pfoot2 !=37 && pfoot2 !=38 && pfoot2 !=6 && pfoot2 !=50 && pfoot2 !=76 && pfoot2 !=65 && pfoot2 !=39 && pfoot2 !=40 && pfoot2 !=78 && pfoot2 !=106 && pfoot2 !=111 && pfoot2 !=171 && pfoot2 !=175 && pfoot2 !=331 && pfoot2 !=27 && pfoot2 !=28 && pfoot2 !=66 && pfoot2 !=69 && pfoot2 !=77 && pfoot2 !=131 && pfoot2 !=143 && pfoot2 !=404 && pfoot2 !=356 && pfoot2 !=157 && server.getWorld(player.getWorld().getName()).getBlockAt(ktp_x, ktp_y, ktp_z).getType() != Material.WATER)
								player.sendMessage(RED + "Nem teleportálhatlak oda, mivel ott nincs szabad hely.");
							else
								ktp_y=ktp_y+1;
						}
						else if(pbody !=0 && pbody !=70 && pbody !=72 && pbody !=323 && pbody !=175 && pbody !=397 && pbody !=390 && pbody !=37 && pbody !=38 && pbody !=6 && pbody !=50 && pbody !=76 && pbody !=65 && pbody !=39 && pbody !=40 && pbody !=78 && pbody !=106 && pbody !=111 && pbody !=171 && pbody !=175 && pbody !=331 && pbody !=27 && pbody !=28 && pbody !=66 && pbody !=69 && pbody !=77 && pbody !=131 && pbody !=143 && pbody !=404 && pbody !=356 && pbody !=157 && server.getWorld(player.getWorld().getName()).getBlockAt(ktp_x, ktp_y, ktp_z).getType() != Material.WATER)
							player.sendMessage(RED+"Nem teleportálhatlak oda, mivel ott nincs szabad hely.");
						else {
							saveLocationPoint("players_locations", name, "back", player.getLocation());
							player.teleport(new Location(player.getWorld(), ktp_x, ktp_y, ktp_z));
							player.sendMessage(BLUE+"Ide teleportáltál: "+ktp_x+" "+ktp_y+" "+ktp_z+".");
							player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 180);
						}
					}
					catch(NumberFormatException e)
					{
						player.sendMessage(RED+"Legközelebb számokat adj meg kérlek!");
					}
				}
			}
		}
		
		
		/**/ // Permissions for donator players.
		if(perm > 2)
		{
			if(command.equalsIgnoreCase("viphelp"))
			{
				player.sendMessage(GRAY+"------------< Támogatók parancslistája >-----------");
				player.sendMessage(GREEN + "/chest" + WHITE + " - Enderchested menyitása");
				player.sendMessage(GREEN + "/craft" + WHITE + " - Virtuális barkácsasztal megnyitása");
				player.sendMessage(GREEN + "/end" + WHITE + " - Teleportálás a végbe");
				player.sendMessage(GREEN + "/fly" + WHITE + " - Repülés engedélyezése/letiltása");
				player.sendMessage(GREEN + "/merch" + WHITE + " - Kereskedés falusival");
				player.sendMessage(GREEN + "/nether" + WHITE + " - Teleportálás az alvilágba");
				player.sendMessage(GREEN + "/table" + WHITE + " - Enchant asztal menyitása");
				player.sendMessage(GREEN + "/lift" + WHITE + " - Teleportálás egy szinttel feljebb");
				player.sendMessage(GRAY+"------------------------------------------------");
			}
			else if(command.equalsIgnoreCase("craft"))
			{
				player.openWorkbench(null, true);
			}
			else if(command.equalsIgnoreCase("table"))
			{
				player.openEnchanting(null, true);
			}
			else if(command.equalsIgnoreCase("merch"))
			{
				player.openMerchant(null, true);
			}
			else if(command.equalsIgnoreCase("chest"))
			{
				player.openInventory(player.getEnderChest());
			}
			else if(command.equalsIgnoreCase("nether"))
			{
				if(server.getAllowNether()) {
					saveLocationPoint("players_locations", name, "back", player.getLocation());
					player.teleport(server.getWorld("world_nether").getSpawnLocation());
					player.sendMessage(DARK_RED + "Az alvilágba teleportálsz.");
				}
				else {
					player.sendMessage(RED + "Az alvilág nem engedélyezett ezen a szerveren!");
				}
			}
			else if(command.equalsIgnoreCase("lift"))
			{
				Location location = player.getWorld().getHighestBlockAt(player.getLocation()).getRelative(BlockFace.UP).getLocation();
				if(player.getLocation().equals(player.getWorld().getHighestBlockAt(player.getLocation())))
					player.sendMessage(RED + "Nem tudsz ennél feljebb teleportálni.");
				else
					player.teleport(location);
			}
			else if(command.equalsIgnoreCase("end"))
			{
				if(server.getAllowEnd()){
					saveLocationPoint("players_locations", name, "back", player.getLocation());
					player.teleport(server.getWorld("world_the_end").getSpawnLocation());
					player.sendMessage(DARK_RED+"A végbe teleportálsz.");
				}
				else {
					player.sendMessage(RED+"A vég nem engedélyezett ezen a szerveren!");
				}
			}
			else if(command.equalsIgnoreCase("fly"))
			{
				if(player.getAllowFlight() == false)
				{
					player.setAllowFlight(true);
					player.setFlying(true);
					player.sendMessage(GREEN + ">> Repülés mód bekapcsolva.");
					databaseUpdate("UPDATE players SET inFly = '1' WHERE name = '"+name+"'");
				}
				else {
					player.setAllowFlight(false);
					player.setFlying(false);
					player.sendMessage(RED + ">> Repülés mód kikapcsolva.");
					databaseUpdate("UPDATE players SET inFly = '0' WHERE name = '"+name+"'");
				}
			}
		}
		
		
		/**/ // Permissions for authorized players.
		if(perm > 1)
		{
			if(command.equalsIgnoreCase("rang")) 
			{
				if(args.length == 0)
					player.sendMessage( YELLOW+"Jelenlegi rangod: " + rangok[perm] );
				else {
					int jog = getPlayerStatInt("perm", args[0]);
					if(jog < 0)
						jog = 0;
					else if(jog >= rangok.length)
						jog = 0;
					
					player.sendMessage(YELLOW + args[0] + " jelenlegi rangja: " + rangok[jog] );
				}
			}
			else if(command.equalsIgnoreCase("fej")) 
			{
				if(args.length == 0)
					player.sendMessage(RED+"Így használd: /fej <játékos>");
				else if(item.getType() != Material.AIR)
					player.sendMessage(RED+"Fejet csak üres kézzel vásárolhatsz!");
				else if(getPlayerStatInt("money", name) < 40)
					player.sendMessage(RED+"40 fillérbe kerül egy fej. Nincs annyi pénzed sajnos.");
				else {
					setPlayerStat("money", name, getPlayerStatInt("money", name) - 40);
					player.sendMessage(GREEN + "Megvásároltad " + args[0] + " fejét.");
					
					ItemStack playerskull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
					SkullMeta meta = (SkullMeta) playerskull.getItemMeta();
					meta.setOwner(args[0]);
					meta.setDisplayName(GREEN+args[0]+" feje");
					playerskull.setItemMeta(meta);
					player.setItemInHand(playerskull);
				}
			}
			else if(command.equalsIgnoreCase("ima"))
			{
				if(getPlayerStatInt("karma", name) < 0)
					player.sendMessage(RED + ">> A karma szinted alacsony, így az Istenek nem fognak rajtad segíteni.");
				else if(getLocationType(player.getLocation(), 16)) {
					Inventory inv = player.getInventory();
					for(int i = 0; i < inv.getSize(); i++)
						if(inv.getItem(i) != null)
							if(Arrays.asList(tools).contains(inv.getItem(i).getType()) || Arrays.asList(armors).contains(inv.getItem(i).getType()))
								inv.getItem(i).setDurability((short) 0);
					player.sendMessage(YELLOW + ">> A Kovácsisten megjavítja elhasznált eszközeid.");
				}
				else if(getLocationType(player.getLocation(), 17)) {
					player.setHealth(player.getMaxHealth());
					player.setFoodLevel(20);
					player.setFireTicks(0);
					
					for (PotionEffect effect : player.getActivePotionEffects())
				        player.removePotionEffect(effect.getType());
					
					setPlayerStat("karma", name, getPlayerStatInt("karma", name) - 50);
					player.sendMessage(ChatColor.GREEN+"A gyógyító istennö ad egy csókot a homlokodra.");
				}
				else if(getLocationType(player.getLocation(), 18)) {
					setPlayerStat("karma", name, getPlayerStatInt("karma", name) - 50);
					teleportToLocationPoint(player, "players_points", name, "death", false, GRAY + ">> A Halálisten elteleportál oda, ahol legutóbb meghaltál.", YELLOW + ">> A Halálisten már nem emlészik, hogy hol haltál meg.");
				}
				else if(getLocationType(player.getLocation(), 19)) {
					Inventory inv = player.getInventory();
					for(int i = 0; i < inv.getSize(); i++)
					{
						if(inv.getItem(i) != null)
							if(Arrays.asList(tools).contains(inv.getItem(i).getType()) || Arrays.asList(armors).contains(inv.getItem(i).getType()))
							{
								inv.getItem(i).removeEnchantment(Enchantment.ARROW_DAMAGE);
								inv.getItem(i).removeEnchantment(Enchantment.ARROW_FIRE);
								inv.getItem(i).removeEnchantment(Enchantment.ARROW_INFINITE);
								inv.getItem(i).removeEnchantment(Enchantment.ARROW_KNOCKBACK);
								inv.getItem(i).removeEnchantment(Enchantment.DAMAGE_ALL);
								inv.getItem(i).removeEnchantment(Enchantment.DAMAGE_ARTHROPODS);
								inv.getItem(i).removeEnchantment(Enchantment.DAMAGE_UNDEAD);
								inv.getItem(i).removeEnchantment(Enchantment.DEPTH_STRIDER);
								inv.getItem(i).removeEnchantment(Enchantment.DIG_SPEED);
								inv.getItem(i).removeEnchantment(Enchantment.DURABILITY);
								inv.getItem(i).removeEnchantment(Enchantment.FIRE_ASPECT);
								inv.getItem(i).removeEnchantment(Enchantment.KNOCKBACK);
								inv.getItem(i).removeEnchantment(Enchantment.LOOT_BONUS_BLOCKS);
								inv.getItem(i).removeEnchantment(Enchantment.LOOT_BONUS_MOBS);
								inv.getItem(i).removeEnchantment(Enchantment.LUCK);
								inv.getItem(i).removeEnchantment(Enchantment.LURE);
								inv.getItem(i).removeEnchantment(Enchantment.OXYGEN);
								inv.getItem(i).removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
								inv.getItem(i).removeEnchantment(Enchantment.PROTECTION_EXPLOSIONS);
								inv.getItem(i).removeEnchantment(Enchantment.PROTECTION_FALL);
								inv.getItem(i).removeEnchantment(Enchantment.PROTECTION_FIRE);
								inv.getItem(i).removeEnchantment(Enchantment.PROTECTION_PROJECTILE);
								inv.getItem(i).removeEnchantment(Enchantment.SILK_TOUCH);
								inv.getItem(i).removeEnchantment(Enchantment.THORNS);
								inv.getItem(i).removeEnchantment(Enchantment.WATER_WORKER);
							}
					}
					
					setPlayerStat("karma", name, getPlayerStatInt("karma", name) - 50);
					player.sendMessage(YELLOW + ">> Az mágia Istene eltávolítja a mágiát minden eszközödröl.");
				}
				else if(getLocationType(player.getLocation(), 20)) {
					player.sendMessage(RED + ">> A háboró Istene szabadságra ment. Nem tudsz most imádkozni hozzá.");
				}
				else
					player.sendMessage(RED + ">> Templomban kell lenned, hogy imádkozhass.");
				
			}
			else if(command.equalsIgnoreCase("biztosit")) 
			{
				if(getPlayerStatInt("money", name) < 2 * 9 * 10)
					player.sendMessage(RED + ">> Egy biztosítás ára " + YELLOW + "2" + RED + " pont!");
				else if(getPlayerStatBool("inInsurance", name) == true)
					player.sendMessage(RED+"Már van biztosításod.");
				else {
					setPlayerStat("money", name, getPlayerStatInt("money", name) - 2 * 9 * 10);
					setPlayerStat("inInsurance", name, 1);
					player.sendMessage(GREEN+"Biztosítást kötöttél 2 ponttért.");
				}
			}
			else if(command.equalsIgnoreCase("szint"))
			{
				if(args.length == 0) 
					player.sendMessage(YELLOW + "Jelenlegi szinted: "+databaseGetInteger("level", "players", "name='"+name+"'", 0)+".");
				else
					player.sendMessage(YELLOW + args[0] + " jelenlegi szintje: " + databaseGetInteger("level", "players", "name='"+args[0]+"'", 0) + ".");
			}
			else if(command.equalsIgnoreCase("pont")) {
				player.sendMessage(GREEN + getPlayerStatInt("points", name).toString() + YELLOW + " pontod van.");
			}
			else if(command.equalsIgnoreCase("penz")) {
				player.sendMessage(GREEN + getPlayerStatInt("money", name).toString() + YELLOW + " fillér van a számládon.");
			}
			else if(command.equalsIgnoreCase("atm"))
			{
				Integer money = 0;
				if(item.getType().equals(Material.DIAMOND))
					money = item.getAmount() * 10;
				else if(item.getType().equals(Material.DIAMOND_BLOCK))
					money = item.getAmount() * 9 * 10;
				else
				{
					player.sendMessage(RED+"A gép csak gyémántot és aranyat fogad el!");
				}
				
				if(money > 0) {
					setPlayerStat("money", name, getPlayerStatInt("money", name) + money);
					player.sendMessage(GREEN + ">> Feltöltöttél "+money+" fillért a számládra.");
					player.setItemInHand(null);
				}	
			}
			else if(command.equalsIgnoreCase("kivesz"))
			{
				Integer transaction = -1;
				
				if(args.length != 1)
					player.sendMessage(RED + ">> Így használd: /kivesz <összeg>");
				else if(!item.getType().equals(Material.AIR))
					player.sendMessage(RED+"Ezt csak üres kézzel használhatod!");
				else {
					try {
						transaction = Integer.parseInt(args[0]);
					}
					catch(Exception e) {

					}
					finally {
						if(transaction % 10 > 0)
							player.sendMessage(RED + ">> Kérlek teljes, kerek számot adj meg! (10, 20, 30... )");
						else if(getPlayerStatInt("money", name) < transaction)
							player.sendMessage(RED + ">> Nincs elég pénz a számládon ehhez.");
						else if(transaction > 640)
							player.sendMessage(RED + ">> Ekkora tranzakciót nem tud feldolgozni a bank.");
						else if(transaction < 10)
							player.sendMessage(RED + ">> Ekkora tranzakciót nem tud feldolgozni a bank.");
						else {
							player.sendMessage(GREEN + ">> Kivettél " + transaction + " fillért a számládról!");
							player.getInventory().addItem(new ItemStack[] {
								new ItemStack(Material.DIAMOND, transaction / 10)
							});
							setPlayerStat("money", name, getPlayerStatInt("money", name) - transaction);
						}
					}
				}
			}
			else if(command.equalsIgnoreCase("haza"))
			{
				if(getPlayerStatBool("inJail", name))
			 		player.sendMessage(RED+"Börtönben vagy, ne is próbálj megszökni!");
				else if(args.length == 0)
					teleportToLocationPoint(player, "players_points", name, "home", true, GREEN+">> Hazateleportáltál.", RED+">> Nincs beállított otthonpontod.");
				else if(args.length == 1 && perm > 3)
					teleportToLocationPoint(player, "players_points", args[0], "home", true, GREEN+">> "+args[0]+" otthonába teleportáltál.", RED+">> "+args[0]+" játékosnak nincs beállított otthonpontod.");
				else
					player.sendMessage(RED+">> Így használd: /haza");
			}
			else if(command.equalsIgnoreCase("utoljara"))
			{
				if(args.length != 1)
					player.sendMessage(RED + ">> Így használd: /utoljara játékos");
				else {
					Player targetplayer = server.getPlayer(args[0]);
					if(targetplayer != null)
						player.sendMessage(GREEN + targetplayer.getName() + " most is ittvan!");
					else {
						OfflinePlayer target = server.getOfflinePlayer(args[0]);
						if(target.getLastPlayed() == 0)
							player.sendMessage(LIGHT_PURPLE + args[0] + " játékos még nem járt a szerveren.");
						else
							player.sendMessage(RED + args[0] + RED + " utoljára ekkor lépett be: " + RED + formatLongToDate(target.getLastPlayed()));
					}
				}
			}
			else if(command.equalsIgnoreCase("tp"))
			{
				if(args.length != 1)
					player.sendMessage((RED+">> Így használd: /tp játékos"));
				else if(server.getPlayer(args[0]) == null)
					player.sendMessage(RED + ">> A megadott játékos nem található.");
				else if(getPlayerStatBool("inJail", name))
			   		player.sendMessage(RED + ">> Börtönben vagy, ne is próbálj megszökni!");
				else if(getPlayerStatBool("inJail", args[0]))
			   		player.sendMessage(RED + ">> Börtönben van, ne is próbálj hozzá teleportálni!");
				else if(getPlayerStatBool("inTpOff", args[0]) == true)
					player.sendMessage(RED + ">> "+args[0]+" nem szeretné, hogy hozzá teleportálj.");
				else {
					Player targetplayer = server.getPlayer(args[0]);
					
					saveLocationPoint("players_locations", name, "back", player.getLocation());
					player.teleport(targetplayer.getLocation());
					player.sendMessage(LIGHT_PURPLE+"A varázslat "+targetplayer.getDisplayName()+LIGHT_PURPLE+" felé repít.");
					targetplayer.sendMessage(LIGHT_PURPLE+player.getName()+" hozzád teleportált.");
				}
			}
			else if(command.equalsIgnoreCase("me"))
			{
				if(args.length == 0)
					player.sendMessage(RED+">> Így használd: /me gondolat");
				else if(getPlayerStatBool("inSoap", name))
					player.sendMessage(YELLOW + "Lenémítottak téged, a játékosok nem látják mit írsz.");
				else
					server.broadcastMessage(GRAY + " ** " + name + " " + GRAY + FinalMessage(args, 0));
			}
			else if(command.equalsIgnoreCase("szeret"))
			{
				if(args.length != 1)
					player.sendMessage(RED+">> Így használd: /szeret [valami]");
				else if(getPlayerStatBool("inSoap", name))
					player.sendMessage(YELLOW + "Lenémítottak téged, a játékosok nem látják mit írsz.");
				else
					server.broadcastMessage(LIGHT_PURPLE + args[0] + DARK_PURPLE + ", " + LIGHT_PURPLE + name + DARK_PURPLE + " nagyon-nagyon szeret téged!  "+LIGHT_PURPLE+"<<333");
			}
			else if(command.equalsIgnoreCase("karma"))
			{
				int karma = getPlayerStatInt("karma", name);

			   	if(karma == 0)
			   		player.sendMessage(RED+"Szürke hétköznapi érzésed van.");
			   	if(karma <= -100)
			   		player.sendMessage(RED+"Nagyon szerencsétlennek érzed magad!");
			   	if(karma < 0)
			   		player.sendMessage(RED+"Szerencsétlennek érzed magad!");
			   	if(karma > 0 && karma <= 50)
			   		player.sendMessage(GRAY+"Szürke hétköznapi érzésed van.");
			   	if(karma > 50 && karma <= 100)
			   		player.sendMessage(GOLD+"Szerencsésnek érzed magad!");
			   	if(karma > 100)
			   		player.sendMessage(AQUA+"Nagyon szerencsésnek érzed magad!");
			}
			else if(command.equalsIgnoreCase("l") || command.equalsIgnoreCase("list"))
			{
				 StringBuilder online = new StringBuilder();
				 Collection<? extends Player> _players = Bukkit.getOnlinePlayers();
				 Player[] players = (Player[]) _players.toArray();
				 
				 for(int i = 0; i <= players.length - 1; i++) {
					 if(online.length() > 0)
						 online.append(", ");
					 online.append(players[i].getName() + WHITE);
				 }

				 player.sendMessage(GREEN+"Akik jelenleg ittvannak:");
				 player.sendMessage(GREEN+"["+players.length+"/"+Bukkit.getMaxPlayers()+"]"+WHITE+" "+online);
			}
			else if(command.equalsIgnoreCase("luck"))
			{
				if(item.getAmount() == item.getMaxStackSize())
				{
					player.setItemInHand(new ItemStack(Material.getMaterial(getRandomItem()), 1));
					player.sendMessage(GREEN+"Próbára tetted a szerencséd, íme a jutalmad!");
				}
				else
					player.sendMessage(RED+"Egy teljes stack ("+item.getMaxStackSize()+" darab) tárgyat kell a kezedben tartanod!");
			}
			else if(command.equalsIgnoreCase("dob"))
			{
				int roll = random.nextInt(6)+1;  
				
				if(roll == 1 || roll == 2 || roll == 4)
					server.broadcastMessage(GRAY+" ** "+name+GREEN+" dob egy "+roll+"-est");
				else if(roll == 3)
					server.broadcastMessage(GRAY+" ** "+name+GREEN+" dob egy "+roll+"-ast");
				else if(roll == 5)
					server.broadcastMessage(GRAY+" ** "+name+GREEN+" dob egy "+roll+"-öst");
				else if(roll == 6)
					server.broadcastMessage(GRAY+" ** "+name+GREEN+" dob egy "+roll+"-ost");
			}
			else if(command.equalsIgnoreCase("kalap"))
			{
				PlayerInventory playerInv = player.getInventory();
				Integer id = item.getTypeId();
				Boolean badBlock = false;
			
				if(player.getTargetBlock((Set<Material>) null, 50).getType() != Material.WOOD_BUTTON && player.getTargetBlock((Set<Material>) null, 50).getType() != Material.STONE_BUTTON)
					player.sendMessage(RED+"Ezt a parancsot csak akció gombra telepítve használhatod!");
				else if(item.getAmount() == 0)
					player.sendMessage(RED+"Hogy tennéd a fejedre a semmit?");
				else {
					Integer BadBlocks[] = {6,7,8,9,10,11,26,27,28,30,31,34,36,37,38,39,40,50,51,55,59,60,62,63,64,65,66,68,69,70,73,75,76,77,83,90,92,95,93,94,122,119,127,131,132,106,107,120,120};
					for(int p = 0; p < BadBlocks.length; p++)
						if(BadBlocks[p].equals(id))
							badBlock = true;
					
					if(badBlock == true)
						player.sendMessage(RED+"A kezedben tartott tárgy olyan dolog amit nem tehetsz a fejedre.");
					else if(id >= 140)
						player.sendMessage(RED+"A kezedben tartott tárgy olyan dolog amit nem tehetsz a fejedre.");
					else if(playerInv.getHelmet() != null) 
						player.sendMessage(RED+"Már van a fejeden valami, elösször azt vedd le!");
					else {
						if(getPlayerStatInt("money", name) < 10)
							player.sendMessage(RED + ">> Ehhez nincs elég pénzed.");
						else {
							setPlayerStat("money", name, getPlayerStatInt("money", name) - 10);
							
							if(item.getAmount() == 1)
							{
								playerInv.setHelmet(item);
								playerInv.setItemInHand(null);
								player.sendMessage(GREEN+"A fejedre helyezted amit a kezedben tartottál.");
							}
						    if(item.getAmount() > 1)
						    {
								ItemStack newHelmet = new ItemStack(item.getType(), 1, item.getDurability());
								newHelmet.setAmount(1);
								playerInv.setHelmet(newHelmet);
								item.setAmount(item.getAmount() - 1);
								player.sendMessage(GREEN+"A fejedre helyezted amit a kezedben tartottál.");
						    }
						}
					}
				}
			}
			else if(command.equalsIgnoreCase("jg")) 
			{
				if(getPlayerStatInt("money", name) < 10)
					player.sendMessage(RED + ">> Egy játék ára 10 fillér.");
				else {
					setPlayerStat("money", name, getPlayerStatInt("money", name) - 10);
					if(random.nextInt(3^10) == (2^15)) {
						server.broadcastMessage(">> " + GREEN + name + " megnyerte a fõnyereményt <<");
						setPlayerStat("money", name, getPlayerStatInt("money", name) + 1024);
					}
					else
						player.sendMessage(YELLOW + "A gép zörög... zümmög... és elnyelte a pénzed.");
				}
			}
			else if(command.equalsIgnoreCase("pia"))
			{
				if(getPlayerStatInt("money", name) < 5)
					player.sendMessage(RED + ">> Egy ital ára 5 fillér. Neked nincs ennyid.");
				else {
					setPlayerStat("money", name, getPlayerStatInt("money", name) - 6);
					player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1200, 2000));
					player.sendMessage(YELLOW + "Legurítassz egy jó hideg sört! Vagy töbet?");
				}
			}
		}
