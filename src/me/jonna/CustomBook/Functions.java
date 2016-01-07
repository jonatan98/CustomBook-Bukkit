package me.jonna.CustomBook;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.ConsoleCommandSender;

public class Functions {
	
	static String xml_version = "1.0";

	static String c_prefix = Main.c_prefix;
	static String p_prefix = Main.p_prefix;
	
	static ConsoleCommandSender console = Main.console;
	
	public static void initConfig(){
		//TODO set the default config if not set already
	}
	
	public static void setDefaultContent(){
		File books = Main._file_books;
		List<String> lines = Arrays.asList(
				"<books>",
				"	<!-- DO NOT CHANGE ANYTHING UNTIL THE NEXT COMMENT -->",
				"	<version>" + xml_version + "</version>",
				"	<!-- Copy and paste the <book> block to create a new book -->",
				"	<book>",
				"		<config>",
				"			<!-- Decide if book is given on first join, every join or on command  -->",
				"			<!-- Accepted values: firstjoin, join, normal -->",
				"			<type>firstjoin, join, normal</type>",
				"			<!-- What should be written after /cb or /custombook -->",
				"			<command>book1</command>",
				"			<!-- books.normal.<PERMISSION> (replace <PERMISSION> with the values underneath) -->",
				"			<!-- Permission to receive book on command -->",
				"			<!-- Separate permissions with commas (,) -->",
				"			<permissions>book1, moderator</permissions>",
				"			<!-- Permission to receive book on join (books.join.<PERMISSION>) -->",
				"			<permissions>book1, moderator</permissions>",
				"		</config>",
				"		<!-- Maximum 16 characters (including spaces) -->",
				"		<title>Book 1</title>",
				"		<author>/4/jn1234</author>",
				"		<!-- You can place as many lores as you want -->",
				"		<!-- Each lore starts at a new line -->",
				"		<lore>Loore</lore>",
				"		<lore>More lore</lore>",
				"		<!-- You can place as many pages as you want -->",
				"		<page>Page 1/z/Line 2, page 1</page>",
				"		<page>Page 2</page>",
				"		<amount>1</amount>",
				"		<durability>0</durability>",
				"	</book>",
				"	<book>",
				"		<config>",
				"			<!-- Decide if book is given on first join, every join or on command  -->",
				"			<!-- Accepted values: firstjoin, join, normal -->",
				"			<type>firstjoin, join, normal</type>",
				"			<!-- What should be written after /cb or /custombook -->",
				"			<command>book2</command>",
				"			<!-- books.normal.<PERMISSION> (replace <PERMISSION> with the values underneath) -->",
				"			<!-- Permission to receive book on command -->",
				"			<!-- Separate permissions with commas (,) -->",
				"			<permissions>book2, moderator</permissions>",
				"			<!-- Permission to receive book on join (books.join.<PERMISSION>) -->",
				"			<permissions>book2, moderator</permissions>",
				"		</config>",
				"		<!-- Maximum 16 characters (including spaces) -->",
				"		<title>Book 2</title>",
				"		<author>/5/jn1234</author>",
				"		<!-- You can place as many lores as you want -->",
				"		<!-- Each lore starts at a new line -->",
				"		<lore>Such lore. Much cool. Wow.</lore>",
				"		<lore>Much 2. lore</lore>",
				"		<!-- You can place as many pages as you want -->",
				"		<page>Page 1/z/Line 2, page 1</page>",
				"		<page>Page 2</page>",
				"		<amount>2</amount>",
				"		<durability>0</durability>",
				"	</book>",
				"</books>");
		Path file = Paths.get(books.getPath());
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
			console.sendMessage(Main.replaceColors(c_prefix + 
					"Default Books stored in Books.xml", true));
		} catch (IOException e) {
			e.printStackTrace();
			console.sendMessage(Main.replaceColors(c_prefix + 
					"Default Books failed to be stored in Books.xml", true));
		}
	}
	
	public static void verifyXMLVersions(){
		//TODO check if the version is equal to the current version, if not, add needed values, and remove unused
		
		/*List<String> outdatedValues = new ArrayList<String>();
		outdatedValues.add("<random_tag>");*/
	}
}