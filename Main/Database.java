package Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;

import lib.PatPeter.SQLibrary.MySQL;

public class Database {

	public static MySQL database;
	
	private static Boolean paused = false;
	private static Boolean defined = false;
	
	private static String serverName;
	private static String userName;
	private static String userPass;
	private static String databaseName;
	private static Integer serverPort;
	
	public Database() {
		
	}
	public Database(String serverName, Integer serverPort, String userName, String userPass, String databaseName) {
		defineConnection(serverName, serverPort, userName, userPass, databaseName);
	}
	
	public static boolean defineConnection(String serverName, Integer serverPort, String userName, String userPass, String databaseName) {
		
		Database.defined = false;
		
		if(serverName.equals(null))
			Main.log(Level.WARNING, "MySQL_internal", "You must enter the server name.");
		else if(serverPort.equals(null))
			Main.log(Level.WARNING, "MySQL_internal", "You must enter the server port.");
		else if(serverPort < 1 || serverPort > 65536)
			Main.log(Level.WARNING, "MySQL_internal", "You must enter a valid server port (between 1 and 65535).");
		else if(userName.equals(null))
			Main.log(Level.WARNING, "MySQL_internal", "You must enter the server user name.");
		else if(userPass.equals(null))
			Main.log(Level.WARNING, "MySQL_internal", "You must enter the server user password.");
		else if(databaseName.equals(null))
			Main.log(Level.WARNING, "MySQL_internal", "You must enter the server database name.");
		else {
			
			if(serverName.equalsIgnoreCase("localhost") || serverName.equalsIgnoreCase("127.0.0.1"))
				Main.log(Level.WARNING, "MySQL_internal", Main.GREEN + "We will define a local mysql connection.");
			else
				Main.log(Level.WARNING, "MySQL_internal", Main.YELLOW + "We will define an external mysql connection.");
			
			if(!serverPort.equals(3306))
				Main.log(Level.WARNING, "MySQL_internal", Main.YELLOW + "You are using a strange port for the connection. Is it the good one?");
			
			if(userName.length() < 6 || userName.equalsIgnoreCase("root") || userName.equalsIgnoreCase("admin") || userName.equalsIgnoreCase("default"))
				Main.log(Level.WARNING, "MySQL_internal", Main.YELLOW + "You are using a weak username for the connection. Please consider a better one.");
			if(userName.length() < 6 || userName.equalsIgnoreCase("root") || userName.equalsIgnoreCase("admin") || userName.equalsIgnoreCase("default"))
				Main.log(Level.WARNING, "MySQL_internal", Main.YELLOW + "You are using a weak password for the connection. Please consider a better one.");
			if(databaseName.length() < 6 ||  databaseName.equalsIgnoreCase("minecraft") || databaseName.equalsIgnoreCase("default"))
				Main.log(Level.WARNING, "MySQL_internal", Main.YELLOW + "You are using a weak database name for the connection. Please consider a better one.");
			
			if(databaseName.length() > 64)
				Main.log(Level.WARNING, "MySQL_internal", Main.RED + "Database name or table name is too long. Try using an shorter one.");
			else {
				try {
					Database.serverName = serverName;
					Database.serverPort = serverPort;
					Database.userName = userName;
					Database.userPass = userPass;
					Database.databaseName = databaseName;
					
					Database.database = new MySQL(Main.logger, Main.plugin.getName() + ": ", Database.serverName, Database.serverPort, Database.databaseName, Database.userName, Database.userPass);
					Database.defined = true;
				}
				catch(Exception e) {
					Main.log(Level.SEVERE, "MySQL_internal", Main.RED + "Error: " + e.getMessage());
				}
				finally {
					if(Database.defined && serverName.equalsIgnoreCase("localhost"))
						Main.log(Level.INFO, "MySQL_internal", Main.GREEN + "We have defined a local mysql connection.");
					else if(Database.defined && !serverName.equalsIgnoreCase("localhost"))
						Main.log(Level.INFO, "MySQL_internal", Main.GREEN + "We have defined an external mysql connection.");
					else
						Main.log(Level.INFO, "MySQL_internal", Main.RED + "We have failed to define a mysql connection.");
				}
			}
		}
		
		return Database.defined;
		
	}
	public static boolean connect() {
		
		int tries = 1;
		while(tries < 32 && Database.database.isOpen() == false) {
			try {
				Database.database.open();
			}
			catch(Exception e) {
				Main.log(Level.SEVERE, "MySQL_internal", Main.RED + "Error: " + e.getMessage());
			}
			finally {
				tries++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Main.log(Level.SEVERE, "SYSTEM_external", Main.RED + "Error: " + e.getMessage());
				}
			}
		}

		if(Database.database.isOpen())
		{
			if(Database.serverName.equalsIgnoreCase("localhost"))
				Main.log(Level.WARNING, "MySQL_internal", Main.GREEN + "We have connected to a local mysql connection.");
			else if(Database.defined && !serverName.equalsIgnoreCase("localhost"))
				Main.log(Level.WARNING, "MySQL_internal", Main.GREEN + "We have connected to an external mysql connection.");
			
			return true;
		}
		else {
			Main.log(Level.SEVERE, "MySQL_internal", Main.RED + "Error: We are not able to connect to your database.");
			return false;
		}
		
	}
	public static void disconnect() {
		Database.database.close();
	}
	public static boolean isDefined() {
		return Database.defined;
	}
	public static void isConnected() {
		Database.database.isOpen();
	}
	public static boolean isTable(String table) {
		return Database.database.isTable(table);
	}
	public static void setPause(boolean value) {
		if(value)
		{
			Database.paused = true;
			Main.log(Level.WARNING, "MySQL_internal", Main.YELLOW + "We have paused your database connection.");
		}
		else {
			Database.paused = false;
			Main.log(Level.WARNING, "MySQL_internal", Main.YELLOW + "We have disabled your pause on your database connection.");
		}
	}
	public static void update(String table, String updates, String where) {
		try {
			database.query("UPDATE " + table + " SET " + updates + " WHERE " + where);
		} catch (SQLException e) {
			Main.log(Level.SEVERE, "MySQL_internal", "Update -> Exception: " + e.getMessage());
		}
	}
	public static void delete(String table, String where) {
		try {
			database.query("DELETE FROM " + table + " WHERE " + where);
		} catch (SQLException e) {
			Main.log(Level.SEVERE, "MySQL_internal", "Delete -> Exception: " + e.getMessage());
		}
	}
	public static void insert(String table, String columns, String values) {
		try {
			database.query("INSERT INTO " + table + " ( " + columns + " ) VALUES " + " ( " + values + " )");
		} catch (SQLException e) {
			Main.log(Level.SEVERE, "MySQL_internal", "Insert -> Exception: " + e.getMessage());
		}
	}
	public static <T> Object grab(T type, String table, String columns, String condition, Object defaultReturn) {

		if(Database.paused)
			return null;

		try {
			ResultSet rs = database.query("SELECT " + columns + " FROM " + table + " WHERE " + condition);
            while (rs.next()) {
            	
            	/**/ // Get one column
            	if(StringUtils.countMatches(columns, ",") == 0) {
            		if(type.equals(String.class))
                		return rs.getString(columns);
            		else if(type.equals(Boolean.class))
                		return rs.getBoolean(columns);
            		else if(type.equals(Integer.class))
                		return rs.getInt(columns);
            		else if(type.equals(Float.class))
                		return rs.getFloat(columns);
            		else if(type.equals(Double.class))
                		return rs.getDouble(columns);
            	}
            	else {
            		
            	}
            	
            }
            	
        } catch (Exception e) {
            Main.log(Level.SEVERE, "MySQL_internal", "Grab -> Exception: " + e.getMessage());
        }
		
		return defaultReturn;
	}
	
	/*** END OF FILE ***/
}
