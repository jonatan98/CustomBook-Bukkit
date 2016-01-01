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
	
	public static void setDefaultContent(String filename){
		//TODO insert the default books
		if(filename.equalsIgnoreCase("joinbooks")){
			File joinbooks = Main._file_joinbooks;
			List<String> lines = Arrays.asList(
					"<books>",
					"	<!-- DO NOT CHANGE ANYTHING UNTIL THE NEXT COMMENT -->",
					"	<version>" + xml_version + "</version>",
					"	<!-- Copy and paste the <book> block to create a new book -->",
					"	<book>",
					"		<config>",
					"			<!-- books.join.<PERMISSION> (replace <PERMISSION> with the values underneath) -->",
					"			<!-- Separate permissions with commas (,) -->",
					"			<permissions>book1, moderator</permissions>",
					"		</config>",
					"		<!-- Maximum 16 characters (including spaces) -->",
					"		<title>Joinbook 1</title>",
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
					"			<!-- books.join.<PERMISSION> (replace <PERMISSION> with the values underneath) -->",
					"			<!-- Separate permissions with commas (,) -->",
					"			<permissions>book2, moderator</permissions>",
					"		</config>",
					"		<!-- Maximum 16 characters (including spaces) -->",
					"		<title>Joinbook 2</title>",
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
			Path file = Paths.get(joinbooks.getPath());
			try {
				Files.write(file, lines, Charset.forName("UTF-8"));
				console.sendMessage(Main.replaceColors(c_prefix + 
						"Default Join Books stored in Join Books.xml", true));
			} catch (IOException e) {
				e.printStackTrace();
				console.sendMessage(Main.replaceColors(c_prefix + 
						"Default Join Books failed to be stored in Join Books.xml", true));
			}
		}else if(filename.equalsIgnoreCase("normalbooks")){
			File normalbooks = Main._file_normalbooks;
			List<String> lines = Arrays.asList(
					"<books>",
					"	<!-- DO NOT CHANGE ANYTHING UNTIL THE NEXT COMMENT -->",
					"	<version>" + xml_version + "</version>",
					"	<book>",
					"		<config>",
					"			<command>book1</command>",
					"			<!-- books.normal.<PERMISSION> (replace <PERMISSION> with the values underneath) -->",
					"			<!-- Separate permissions with commas (,) -->",
					"			<permissions></permissions>",
					"		</config>",
					"		<!-- Maximum 16 characters (including spaces) -->",
					"		<title>Normal book 1</title>",
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
					"			<command>book2</command>",
					"			<!-- books.normal.<PERMISSION> (replace <PERMISSION> with the values underneath) -->",
					"			<!-- Separate permissions with commas (,) -->",
					"			<permissions></permissions>",
					"		</config>",
					"		<!-- Maximum 16 characters (including spaces) -->",
					"		<title>Normal book 2</title>",
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
			Path file = Paths.get(normalbooks.getPath());
			try {
				Files.write(file, lines, Charset.forName("UTF-8"));
				console.sendMessage(Main.replaceColors(c_prefix + 
						"Default Normal Books stored in Join Books.xml", true));
			} catch (IOException e) {
				e.printStackTrace();
				console.sendMessage(Main.replaceColors(c_prefix + 
						"Default Normal Books failed to be stored in Join Books.xml", true));
			}
		}
	}
	
	public static void verifyXMLVersions(){
		//TODO check if the version is equal to the current version, if not, add needed values, and remove unused
		
		/*List<String> outdatedValues = new ArrayList<String>();
		outdatedValues.add("<random_tag>");*/
	}
}
