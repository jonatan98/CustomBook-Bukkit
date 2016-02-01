package me.jonna.CustomBook;

import org.bukkit.command.ConsoleCommandSender;

public class Functions {

	static String c_prefix = Main.c_prefix;
	static String p_prefix = Main.p_prefix;
	
	static ConsoleCommandSender console = Main.console;
	
	public static void initConfig(){
		//TODO set the default config if not set already
	}
	
	public static void verifyXMLVersion(){
		//TODO check if the version is equal to the current version, if not, add needed values, and remove unused
		
		/*List<String> outdatedValues = new ArrayList<String>();
		outdatedValues.add("<random_tag>");*/
	}
}