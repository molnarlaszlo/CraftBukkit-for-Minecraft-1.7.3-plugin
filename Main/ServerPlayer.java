package Main;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class ServerPlayer {

	private static Player player;
	public static Integer permission;
	
	public static String name;
	public static String title;
	public static String welcome;
	public static String password;
	
	public static Integer money;
	public static Integer karma;
	public static Integer point;
	
	public static Integer inMute;
	public static Integer inJail;
	public static Integer inInsurance;
	
	public static boolean loggedIn;
	public static boolean teleportPreference;
	
	enum Permission {
		NULL, BANNED, PUNISHED, DEFAULT, MEMBER, DONATOR, MODERATOR_HELPER, PRE_MODERATOR, MODERATOR, PRE_ADMINISTRATOR, ADMINISTRATOR, DEVELOPER, TESTER, SYSTEM, OWNER;
	}
	
	public ServerPlayer(Player player) {
		
		/**/ // Most important variables
		ServerPlayer.player = player;
		name = player.getName();
		
		/**/ // Create player profile if it does not exist
		if(isExist() == false)
			createProfile();
		
		loggedIn = false;
		permission = getPermission();
		
		welcome = getWelcome();
		password = getPassword();
		
		money = getMoney();
		karma = getKarma();
		point = getPoint();
		
		inMute = getMute();
		inJail = getJail();
		inInsurance = getInsurance();
		
	}
	public static boolean isExist() {
		if( Database.grab(String.class, "players", "name", "name = '" + name + "'", "PLAYER_DOESNOT").toString().equals("PLAYER_DOESNOT"))
			return false;
		else
			return true;
	}
	public static void createProfile() {
		Database.insert("players", "uuid, name", "'" + player.getUniqueId() + "', '" + player.getName() + "'");
	}
	public static void msg(String msg) {
		player.sendMessage(msg);
	}
	
	public static void setGameMode(GameMode gm) {
		player.setGameMode(gm);
	}

	public static String getWelcome() {
		return (String) Database.grab(String.class, "players", "welcome", "name = '" + name + "'", "");
	}
	public static String getWelcome(String name) {
		return (String) Database.grab(String.class, "players", "welcome", "name = '" + name + "'", "");
	}
	public static String getPassword() {
		return (String) Database.grab(String.class, "players", "password", "name = '" + name + "'", "");
	}
	public static String getPassword(String name) {
		return (String) Database.grab(String.class, "players", "password", "name = '" + name + "'", "");
	}
	
	
	public static Integer getMoney() {
		return (Integer) Database.grab(Integer.class, "players", "money", "name = '" + name + "'", 0);
	}
	public static Integer getMoney(String name) {
		return (Integer) Database.grab(Integer.class, "players", "money", "name = '" + name + "'", 0);
	}
	public static Integer getKarma() {
		return (Integer) Database.grab(Integer.class, "players", "karma", "name = '" + name + "'", 0);
	}
	public static Integer getKarma(String name) {
		return (Integer) Database.grab(Integer.class, "players", "karma", "name = '" + name + "'", 0);
	}
	public static Integer getPoint() {
		return (Integer) Database.grab(Integer.class, "players", "points", "name = '" + name + "'", 0);
	}
	public static Integer getPoint(String name) {
		return (Integer) Database.grab(Integer.class, "players", "points", "name = '" + name + "'", 0);
	}
	public static Integer getPermission() {
		return (Integer) Database.grab(Integer.class, "players", "perm", "name = '" + name + "'", 0);
	}
	public static Integer getPermission(String name) {
		return (Integer) Database.grab(Integer.class, "players", "perm", "name = '" + name + "'", 0);
	}
	
	public static Integer getBan() {
		return (Integer) Database.grab(Integer.class, "players", "inBan", "name = '" + name + "'", 0);
	}
	public static Integer getBan(String name) {
		return (Integer) Database.grab(Integer.class, "players", "inBan", "name = '" + name + "'", 0);
	}
	public static Integer getMute() {
		return (Integer) Database.grab(Integer.class, "players", "inMute", "name = '" + name + "'", 0);
	}
	public static Integer getMute(String name) {
		return (Integer) Database.grab(Integer.class, "players", "inMute", "name = '" + name + "'", 0);
	}
	public static Integer getJail() {
		return (Integer) Database.grab(Integer.class, "players", "inJail", "name = '" + name + "'", 0);
	}
	public static Integer getJail(String name) {
		return (Integer) Database.grab(Integer.class, "players", "inJail", "name = '" + name + "'", 0);
	}
	public static Integer getInsurance() {
		return (Integer) Database.grab(Integer.class, "players", "inInsurance", "name = '" + name + "'", 0);
	}
	public static Integer getInsurance(String name) {
		return (Integer) Database.grab(Integer.class, "players", "inInsurance", "name = '" + name + "'", 0);
	}
	

	public static boolean getTeleportPreference() {
		return (boolean) Database.grab(Boolean.class, "players", "inTpOff", "name = '" + name + "'", false);
	}
	public static boolean getTeleportPreference(String name) {
		return (boolean) Database.grab(Boolean.class, "players", "inTpOff", "name = '" + name + "'", false);
	}
	
	public static void setLogin(Boolean value) {
		loggedIn = value;
	}
	public static void setTeleportPreference(Boolean value) {
		teleportPreference = value;
		Database.update("players", "inTpOff", "'"+value+"'");
	}
	public static void saveMoney(Integer value) {
		Database.update("players", "money = '"+value+"'", "name='"+name+"'");
	}
	public static void saveKarma(Integer value) {
		Database.update("players", "karma = '"+value+"'", "name='"+name+"'");
	}
	public static void savePoint(Integer value) {
		Database.update("players", "points = '"+value+"'", "name='"+name+"'");
	}
	public static void savePassword(String value) {
		Database.update("players", "password = '"+value+"'", "name='"+name+"'");
	}
	public static void savePermission(Integer value) {
		Database.update("players", "perm", "'"+value+"'");
	}
	
	public static Permission getPermission(Integer permissionId) {
		switch(permissionId) {
			case -2: return Permission.BANNED;
			case -1: return Permission.PUNISHED;
			case 0: return Permission.DEFAULT;
			case 1: return Permission.MEMBER;
			case 2: return Permission.DONATOR;
			case 3: return Permission.MODERATOR_HELPER;
			case 4: return Permission.PRE_MODERATOR;
			case 5: return Permission.MODERATOR;
			case 6: return Permission.PRE_ADMINISTRATOR;
			case 7: return Permission.ADMINISTRATOR;
			case 8: return Permission.DEVELOPER;
			case 9: return Permission.TESTER;
			default: return Permission.DEFAULT;
		}
	}
	public static Integer getPermission(Permission permissionId) {
		switch(permissionId) {
			case BANNED: return -2;
			case PUNISHED: return -1;
			case DEFAULT: return 0;
			case MEMBER: return 1;
			case DONATOR: return 2;
			case MODERATOR_HELPER: return 3;
			case PRE_MODERATOR: return 4;
			case MODERATOR: return 5;
			case PRE_ADMINISTRATOR: return 6;
			case ADMINISTRATOR: return 7;
			case DEVELOPER: return 8;
			case TESTER: return 9;
			case SYSTEM: return 10;
			case OWNER: return 11;
			default: return 0;
		}
	}
	public static ChatColor getPermissionColor(Integer permissionId) {
		switch(permissionId) {
			case -2: return ChatColor.DARK_PURPLE;
			case -1: return ChatColor.LIGHT_PURPLE;
			case 0: return ChatColor.GRAY;
			case 1: return ChatColor.WHITE;
			case 2: return ChatColor.GREEN;
			case 3: return ChatColor.DARK_GREEN;
			case 4: return ChatColor.DARK_AQUA;
			case 5: return ChatColor.AQUA;
			case 6: return ChatColor.DARK_RED;
			case 7: return ChatColor.RED;
			case 8: return ChatColor.YELLOW;
			case 9: return ChatColor.YELLOW;
			case 10: return ChatColor.DARK_PURPLE;
			case 11: return ChatColor.RED;
			default: return ChatColor.GRAY;
		}
	}
	public ChatColor getPermissionColor(Permission permission) {
		switch(permission) {
			case BANNED: return ChatColor.DARK_PURPLE;
			case PUNISHED: return ChatColor.LIGHT_PURPLE;
			case DEFAULT: return ChatColor.GRAY;
			case MEMBER: return ChatColor.WHITE;
			case DONATOR: return ChatColor.GREEN;
			case MODERATOR_HELPER: return ChatColor.DARK_GREEN;
			case PRE_MODERATOR: return ChatColor.DARK_AQUA;
			case MODERATOR: return ChatColor.AQUA;
			case PRE_ADMINISTRATOR: return ChatColor.DARK_RED;
			case ADMINISTRATOR: return ChatColor.RED;
			case DEVELOPER: return ChatColor.YELLOW;
			case TESTER: return ChatColor.YELLOW;
			case SYSTEM: return ChatColor.DARK_PURPLE;
			case OWNER: return ChatColor.RED;
			default: return ChatColor.GRAY;
		}
	}
}
