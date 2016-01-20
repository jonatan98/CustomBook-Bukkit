package me.jonna.CustomBook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Main extends JavaPlugin implements Listener{	
	public static File _file_books;
	
	//Console prefix
	public static String c_prefix = "/a/[/5/Books/a/] /f/";
	//Debug prefix
	public static String d_prefix = "/a/[/5/Books Debug/a/] /f/";
	//Player prefix
	public static String p_prefix = "/a/[/5/Books/a/] /f/";
	public static String no_permission = "/4/You have no permission to perform this command.";
	
	public static boolean debug = false;
	
	public static ConsoleCommandSender console = Bukkit.getConsoleSender();
	
	@Override
	public void onDisable() {
		
	}
	@Override
	public void onEnable(){
		getConfig().addDefault("debug", false);
		getConfig().options().copyDefaults(true);
	    saveConfig();
	    debug = getConfig().getBoolean("debug");
		//Generate plugin dir if not existant
		File f = new File(getDataFolder() + "/");
		if(!f.exists())
		    f.mkdir();
		_file_books = new File(getDataFolder() + "/Books.xml");
		if(!_file_books.exists()){
		    try {
				if(_file_books.createNewFile()){
					console.sendMessage(replaceColors(c_prefix + 
							"Books.xml did not exist, but was successfully created", true));
				}else{
					console.sendMessage(replaceColors(c_prefix + 
							"Books.xml did not exist, and failed to be created", true));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		    Functions.setDefaultContent();
		}
		try{
			BufferedReader br = new BufferedReader(new FileReader(_file_books));     
			if (br.readLine() == null) {
			    console.sendMessage(replaceColors(c_prefix + "Empty Books.xml file", true));
			    Functions.setDefaultContent();
			}else{
				if(debug){
					console.sendMessage(replaceColors(d_prefix + "Books.xml is not empty", true));
				}
			}
			br.close();
		}catch(FileNotFoundException e){
			console.sendMessage(replaceColors(c_prefix + "Tried to get Books.xml file but file not found.", true));
		}catch(IOException e){
			e.printStackTrace();
		}

		Functions.verifyXMLVersions();
		Functions.initConfig();
		
		//TODO calculate if any books have too long pages, too long titles, etc.
		
		
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player p = event.getPlayer();
		
		//Read Books.xml
		try{
    		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    		Document doc = dBuilder.parse(_file_books);
    				
    		//optional, but recommended
    		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
    		doc.getDocumentElement().normalize();
    		
    		for(int i = 0; i < doc.getElementsByTagName("book").getLength(); i++){
    			Node nNode = doc.getElementsByTagName("book").item(i);
    					
    			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

    				Element bookData = (Element) nNode;
    				String[] types = bookData.getElementsByTagName("type").item(0).getTextContent().split(",");
    				boolean isJoinBook = false;
    				for(int w = 0; w < types.length; w++){
    					if(types[w].trim().equalsIgnoreCase("join"))
    						isJoinBook = true;
    					else if(types[w].trim().equalsIgnoreCase("firstjoin"))
    						if(!p.hasPlayedBefore())
    							isJoinBook = true;
    				}
    				
    				if(isJoinBook){
    			
			    		//TODO find config / permissions to see which custom permissions are required
			    		//Node config = bookData.getElementsByTagName("config").item(0);
			    		
	    				boolean hasPermission = false;
	    				String[] permissions = bookData.getElementsByTagName("permissions").item(1).getTextContent().split(",");
	    				for(int z = 0; z < permissions.length; z++){
	    					if(p.hasPermission("books.join." + permissions[z].trim())){
	    						hasPermission = true;
	    						z = permissions.length;
	    					}
	    				}
		    			
			    		if(p.hasPermission("books.join.*") || p.hasPermission("books.*") || hasPermission){
			    			
			    			String title = replaceColors(bookData.getElementsByTagName("title").item(0).getTextContent());
			    			String author = replaceColors(bookData.getElementsByTagName("author").item(0).getTextContent());
			    			List<String> lore = new ArrayList<>(bookData.getElementsByTagName("lore").getLength());
			    			for(int z = 0; z < bookData.getElementsByTagName("lore").getLength(); z++){
			    				lore.add(z, replaceColors(bookData.getElementsByTagName("lore").item(z).getTextContent()));
			    			}
			    			List<String> pages = new ArrayList<>(bookData.getElementsByTagName("page").getLength());
			    			for(int z = 0; z < bookData.getElementsByTagName("page").getLength(); z++){
			    				pages.add(z, replaceColors(bookData.getElementsByTagName("page").item(z).getTextContent()));
			    			}
			    			short durability = Short.parseShort(bookData.getElementsByTagName("durability").item(0).getTextContent());
			    			int amount = Integer.parseInt(bookData.getElementsByTagName("amount").item(0).getTextContent());
			    			
			    			//Give book
			    			ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
							BookMeta meta = (BookMeta) book.getItemMeta();
							meta.setTitle(title);
							meta.setAuthor(author);
							meta.setLore(lore);
							meta.setPages(pages);
							book.setItemMeta(meta);
							book.setAmount(amount);
							book.setDurability(durability);
					        p.getInventory().addItem(book);
			    		}else{
			    			if(debug){
				    			console.sendMessage(replaceColors(c_prefix + 
				    					p.getName() + " has no permission to get the join book '" + 
				    					bookData.getElementsByTagName("title").item(0).getTextContent()
				    					+ "/r/' by " + 
				    					bookData.getElementsByTagName("author").item(0).getTextContent(), true));
			    			}
			    		}
    				}//ELSE book is not a joinbook
	    		}else{
	    			//Element is of wrong type
	    			if(debug){
		    			console.sendMessage(replaceColors(c_prefix + 
		    					"Element is of wrong type (misconfigured book)", true));
	    			}
	    		}
    		}
    		if(doc.getElementsByTagName("book").getLength() == 0 && debug){
    			console.sendMessage(replaceColors(
    					"No books are created", true));
    		}
		}catch(Exception e){
			e.printStackTrace();
			if(debug){
				console.sendMessage(replaceColors(c_prefix + 
						"Failed to read Books.xml", true));
			}
		}
	}
	
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		
		if(label.equalsIgnoreCase("cb") || label.equalsIgnoreCase("custombook")){
			
			if(sender instanceof Player){
				Player player = (Player) sender;
				bookCommand(player, args);
			}else{
				if(args.length == 0){
					console.sendMessage(replaceColors(c_prefix + "Help", true));
					console.sendMessage(replaceColors("/cb list [join / normal] - get a list of books"));
					console.sendMessage(replaceColors("/cb give <player name / all> <book name> - give a book to a player or all players", true));
					console.sendMessage(replaceColors("/cb reload - reload the config.yml", true));
					console.sendMessage(replaceColors("/cb version - plugin info", true));
				}else if(args[0].equalsIgnoreCase("reload")){
					reloadConfig();
					debug = getConfig().getBoolean("debug");
					console.sendMessage(replaceColors(c_prefix + 
							"Reloaded plugin"));
				}else if(args[0].equalsIgnoreCase("list")){
					//List all books
					if(args.length == 1 || !args[1].equalsIgnoreCase("join")){
						//List of normal books
						sendMessage(null, replaceColors(c_prefix + "/6/Normal books"));
						listBooks(null, "normal");
					}else{
						//List of join books
						sendMessage(null, replaceColors(c_prefix + "/6/Join Books"));
						listBooks(null, "join");
					}
				}else if(args[0].equalsIgnoreCase("give")){
					//Give book to player from console
					if(args.length >= 3){
						String args_str = "";
						for(int o = 2; o < args.length; o++){ args_str += args[o] + " "; }
						args_str = args_str.trim();
						if(args[1].equalsIgnoreCase("*") || args[1].equalsIgnoreCase("all")){
							//Give to all players
							giveBook("console", null, args_str);
						}else{
							//Give to specific player
							Player targetPlayer = Bukkit.getPlayerExact(args[1]);
							if(targetPlayer != null){
								giveBook("console", Bukkit.getPlayer(args[1]), args_str);
							}else{
								sendMessage(null, replaceColors("/4/No player by the name " + args[1] + " is online", true));
							}
						}
					}else{
						sendMessage(null, replaceColors("/cb give <player/*> <bookname>", true));
					}
				}else if(args[0].equalsIgnoreCase("version")){
					PluginDescriptionFile pdfFile = this.getDescription();
					List<String> authors = pdfFile.getAuthors();
					console.sendMessage(replaceColors(c_prefix + pdfFile.getName() + " version " + pdfFile.getVersion() + " by " + authors.get(0), true));
					console.sendMessage("Website: " + pdfFile.getWebsite());
				}else if(args[0].equalsIgnoreCase("debug")){
					if(getConfig().getBoolean("debug")){
						getConfig().set("debug", false);
						saveConfig();
						console.sendMessage(replaceColors(d_prefix + "Disabled", true));
					}else{
						getConfig().set("debug", true);
						saveConfig();
						console.sendMessage(replaceColors(d_prefix + "Enabled", true));
					}
				}else{
					//This is what happens if the command is forced trough console
					console.sendMessage(replaceColors(c_prefix + 
							"&4Books can not be given to console", true));
				}
			}
		}//End of the cb commands
		
		
		return false;
	}
	
	//Moved to separate function in case it gets interesting to try to use /book to override essentials
	private void bookCommand(Player p, String[] args){
		if(args.length == 0 || args[0].equalsIgnoreCase("help")){
			//List of commands
			List<String> commandos = new ArrayList<String>();
			commandos.add("cb help - view this information");
			commandos.add("cb list [join / normal] - get a list of books");
			commandos.add("cb <book name> - receive a book");
			commandos.add("cb create <book type> [command] - save the book in your hand");
			
			p.sendMessage(replaceColors(p_prefix + "Help"));
			for(int e = 0; e < commandos.size(); e++){
				p.sendMessage(replaceColors("/" + commandos.get(e)));
			}
		}else if(args[0].equalsIgnoreCase("list")){
			if(args.length == 1 || !args[1].equalsIgnoreCase("join")){
				//List of normal books
				p.sendMessage(replaceColors("/6/Normal books"));
				listBooks(p, "normal");
			}else{
				//List of join books
				p.sendMessage(replaceColors("/6/Join Books"));
				listBooks(p, "join");
			}
		}else if(args[0].equalsIgnoreCase("reload")){
			if(p.hasPermission("books.reload") || p.hasPermission("books.*")){
				reloadConfig();
				debug = getConfig().getBoolean("debug");
				p.sendMessage(replaceColors(p_prefix + 
						"Reloaded plugin"));
			}else{
				p.sendMessage(replaceColors(no_permission));
			}
		}else if(args[0].equalsIgnoreCase("give")){
			if(args.length >= 3){
				String args_str = "";
				for(int o = 2; o < args.length; o++){ args_str += args[o] + " "; }
				args_str = args_str.trim();
				if(args[1].equalsIgnoreCase("*") || args[1].equalsIgnoreCase("all")){
					//Give to all players
					giveBook(p, null, args_str, "books.give.");
				}else{
					//Give to specific player
					Player targetPlayer = Bukkit.getPlayerExact(args[1]);
					if(targetPlayer != null){
						giveBook(p, Bukkit.getPlayer(args[1]), args_str, "books.give.");
					}else{
						p.sendMessage(replaceColors("/4/No player by the name " + args[1] + " is online"));
					}
				}
			}else{
				p.sendMessage(replaceColors("/cb give <player/*> <bookname>"));
			}
		}else if(args[0].equalsIgnoreCase("version")){
			PluginDescriptionFile pdfFile = this.getDescription();
			List<String> authors = pdfFile.getAuthors();
			p.sendMessage(replaceColors(p_prefix + pdfFile.getName() + " version " + pdfFile.getVersion() + " by " + authors.get(0)));
			p.sendMessage("Website: " + pdfFile.getWebsite());
		}else if(args[0].equalsIgnoreCase("create")){
			//Command to fetch a book from the players inventory and save it in the books file
			createBook(p, args);
		}else{
			//Convert command into a string, to enable multi-word commands
			String args_str = "";
			for(int i = 0; i < args.length; i++){ args_str += args[i] + " "; }
			args_str = args_str.trim();
			
			giveBook(p, args_str, "books.normal.");
		}
	}
	
	//Function to give book to a specific player or all players
	private void giveBook(String sender, Player tp, String args_str){
		giveBook(null, tp, args_str, "books.give."); //Last argument just because I'm too lazy to make a function that doesn't need it
	}
	private void giveBook(Player p, String args_str, String permission){
		giveBook(p, p, args_str, permission);
	}
	private void giveBook(Player p, Player tp, String args_str, String permission){
		try{
    		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    		Document doc = dBuilder.parse(_file_books);
    				
    		//optional, but recommended
    		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
    		doc.getDocumentElement().normalize();
    		
    		boolean no_books = true;
    		for(int i = 0; i < doc.getElementsByTagName("book").getLength(); i++){
    			Node nNode = doc.getElementsByTagName("book").item(i);
    					
    			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
    				Element bookData = (Element) nNode;
    				boolean isNormalBook = false;
    				String[] types = bookData.getElementsByTagName("type").item(0).getTextContent().split(",");
    				for(int w = 0; w < types.length; w++){
    					if(types[w].trim().equalsIgnoreCase("normal"))
    						isNormalBook = true;
    				}
    				if(isNormalBook){
	    				if(bookData.getElementsByTagName("command").item(0).getTextContent()
	    						.equalsIgnoreCase(args_str)){
	    					no_books = false;
				    		//TODO find config / permissions to see which custom permissions are required
				    		//Node config = bookData.getElementsByTagName("config").item(0);
				    		boolean hasPermission = false;
				    		if(p != null){
				    			String[] permissions = bookData.getElementsByTagName("permissions").item(0).getTextContent().split(",");
					    		for(int z = 0; z < permissions.length; z++){
					    			if(p.hasPermission(permission + permissions[z].trim())){
					    				hasPermission = true;
					    				z = permissions.length;
					    			}
					    		}
					    		if(p.hasPermission(permission + "*") || p.hasPermission("books.*")){
					    			hasPermission = true;
					    		}
				    		}else{
				    			hasPermission = true;
				    		}
				    		if(hasPermission){
				    			String title = replaceColors(bookData.getElementsByTagName("title").item(0).getTextContent());
				    			String author = replaceColors(bookData.getElementsByTagName("author").item(0).getTextContent());
				    			List<String> lore = new ArrayList<>(bookData.getElementsByTagName("lore").getLength());
				    			for(int z = 0; z < bookData.getElementsByTagName("lore").getLength(); z++){
				    				lore.add(z, replaceColors(bookData.getElementsByTagName("lore").item(z).getTextContent()));
				    			}
				    			List<String> pages = new ArrayList<>(bookData.getElementsByTagName("page").getLength());
				    			for(int z = 0; z < bookData.getElementsByTagName("page").getLength(); z++){
				    				pages.add(z, replaceColors(bookData.getElementsByTagName("page").item(z).getTextContent()));
				    			}
				    			short durability = Short.parseShort(bookData.getElementsByTagName("durability").item(0).getTextContent());
				    			int amount = Integer.parseInt(bookData.getElementsByTagName("amount").item(0).getTextContent());
				    			
				    			//Give book
				    			ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
								BookMeta meta = (BookMeta) book.getItemMeta();
								meta.setTitle(title);
								meta.setAuthor(author);
								meta.setLore(lore);
								meta.setPages(pages);
								book.setItemMeta(meta);
								book.setAmount(amount);
								book.setDurability(durability);
						        if(tp != null){
						        	tp.getInventory().addItem(book);
						        	sendMessage(p, replaceColors("/a/Book \"" + title + "\" has been given to " + tp.getName()));
						        }else{
						        	for(Player player : Bukkit.getOnlinePlayers()){
						        		player.getInventory().addItem(book);
						        	}
						        	sendMessage(p, replaceColors("/a/Book \"" + title + "\" has been given to all online players"));
						        }
				    		}
	    				}
    				}//ELSE is not a normal book
	    		}else{
	    			//Element is of wrong type
	    			p.sendMessage(replaceColors("/4/Misconfigured book"));
	    		}
	    		
    		}
    		//Check if no book exists
    		if(no_books){
    			p.sendMessage(replaceColors(p_prefix + "The book '" + args_str + "/r//f/' does not exist"));
    		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void listBooks(Player p, String type){
		File f = _file_books;
		
		//Read the Book content
		try{
    		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    		Document doc = dBuilder.parse(f);
    				
    		//optional, but recommended
    		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
    		doc.getDocumentElement().normalize();
    		
    		boolean displayed_book = false;
    		boolean no_perm = false;
    		
    		for(int i = 0; i < doc.getElementsByTagName("book").getLength(); i++){
    			Node nNode = doc.getElementsByTagName("book").item(i);
    					
    			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
    				Element bookData = (Element) nNode;
    				boolean isRightType = false;
    				String[] types = bookData.getElementsByTagName("type").item(0).getTextContent().split(",");
    				for(int w = 0; w < types.length; w++){
    					if(debug){
    						console.sendMessage("Book type " + w + "of book " + 
    								bookData.getElementsByTagName("title").item(0).getTextContent() + 
    								": " + types[w].trim());
    					}
    					if(type.equalsIgnoreCase("normal")){
    						if(types[w].trim().equalsIgnoreCase("normal")){
    							isRightType = true; 
    							//Quit the loop
    							w = types.length;
							}
    					}else if(type.equalsIgnoreCase("join")){
    						if(types[w].trim().equalsIgnoreCase("join") || 
    								types[w].trim().equalsIgnoreCase("firstjoin")){
    							isRightType = true;
    							//Quit the loop
    							w = types.length;
    						}
    					}
    				}
    				if(isRightType){
    					int permission_index = 0;
    					String permission_str = "books.normal.";
    					if(type.equalsIgnoreCase("join")){
    						permission_index = 1;
    						permission_str = "books.join.";
    					}
			    		boolean hasPermission = false;
			    		if(p != null){
			    			String[] permissions = bookData.getElementsByTagName("permissions").item(permission_index).getTextContent().split(",");
				    		for(int z = 0; z < permissions.length; z++){
				    			if(p.hasPermission(permission_str + permissions[z].trim())){
				    				hasPermission = true;
				    				z = permissions.length;
				    			}
				    		}
				    		if(p.hasPermission(permission_str + "*") || p.hasPermission("books.*")){
				    			hasPermission = true;
				    		}
			    		}else{
			    			hasPermission = true;
			    		}
			    		
			    		if(hasPermission){
			    			String title = bookData.getElementsByTagName("title").item(0).getTextContent();
			    			String author = bookData.getElementsByTagName("author").item(0).getTextContent();
			    			if(type.equalsIgnoreCase("join")){
			    				sendMessage(p, replaceColors(title + "/r//f/ by " + author));
			    			}else{
			    				String command = bookData.getElementsByTagName("command").item(0).getTextContent();
			    				sendMessage(p, replaceColors("/cb " + command + ": " + title + "/r//f/ by " + author));
			    			}
			    			displayed_book = true;
			    		}else{
			    			//No permission to view book
			    			no_perm = true;
			    		}
    				}//ELSE the book is not of requested type
	    		}else{
	    			//Element is of wrong type
	    			if(debug){
	    				console.sendMessage(replaceColors("/4/Misconfigured book"));
	    			}
	    		}
	    		
    		}
    		
    		if(!displayed_book){
    			if(no_perm)
    				p.sendMessage(replaceColors("/4/You do not have permission to obtain any custom books"));
    			else
    				p.sendMessage(replaceColors("There are no custom books of the requested type"));
    		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	private void createBook(Player p, String[] args){
		if(p.hasPermission("books.create") || p.hasPermission("books.*")){
			if(p.getItemInHand().getTypeId() == 387){
				if(args.length >= 3){
					String args_str = "";
					for(int i = 1; i < args.length; i++){ args_str += args[i] + " "; }
					args_str = args_str.trim();
					/*
					 * Calculate how many of the arguments are defining the book type
					 */
					String book_type = "";
					String book_name = "";
					String[] types = args_str.split(",");
					for(int i = 0; i < types.length; i++){
						//Maybe add some kind of check to see whether the types are accepted or not
						if(i == types.length - 1){
							//Last index
							book_type += types[i].trim().split(" ")[0];
							book_name = types[i].trim().split(" ", 2)[1];
						}else{
							book_type += types[i].trim() + ", ";
						}
					}
					
					/*
					 * Get book item and get the contents
					 */
					ItemStack book = (ItemStack) p.getItemInHand();
					BookMeta meta = (BookMeta) book.getItemMeta();
					String book_author = meta.getAuthor();
					String book_title = meta.getTitle();
					String book_displayname = meta.getDisplayName();
					List<String> book_lore = meta.getLore();
					List<String> book_pages = meta.getPages();
					
					/*
					 * Save the book in the xml document (and take backup of old file in case anything goes wrong)
					 */
					
				}else{
					p.sendMessage("/cb create <types> [command]");
					p.sendMessage("Example: /cb create firstjoin,normal,join information");
				}
			}else{
				p.sendMessage(replaceColors("/4/You have to hold a written book."));
			}
		}else{
			p.sendMessage(replaceColors(no_permission));
		}
	}
	
	private void sendMessage(Player p, String msg){
		if(p == null){
			console.sendMessage(msg);
		}else{
			p.sendMessage(msg);
		}
	}
	
	public static String replaceColors(String s){
		return replaceColors(s, false);
	}
	public static String replaceColors(String s, boolean console){
		String prefix = "/";
		String suffix = "/";
		
		return s.replaceAll(prefix + "0" + suffix, ChatColor.BLACK + "").replaceAll(prefix + "1" + suffix, ChatColor.DARK_BLUE + "")
				.replaceAll(prefix + "2" + suffix, ChatColor.DARK_GREEN + "").replaceAll(prefix + "3" + suffix, ChatColor.DARK_AQUA + "")
				.replaceAll(prefix + "4" + suffix, ChatColor.DARK_RED + "").replaceAll(prefix + "5" + suffix, ChatColor.DARK_PURPLE + "")
				.replaceAll(prefix + "6" + suffix, ChatColor.GOLD + "").replaceAll(prefix + "7" + suffix, ChatColor.GRAY + "")
				.replaceAll(prefix + "8" + suffix, ChatColor.DARK_GRAY + "").replaceAll(prefix + "9" + suffix, ChatColor.BLUE + "")
				.replaceAll(prefix + "a" + suffix, ChatColor.GREEN + "").replaceAll(prefix + "b" + suffix, ChatColor.AQUA + "")
				.replaceAll(prefix + "c" + suffix, ChatColor.RED + "").replaceAll(prefix + "d" + suffix, ChatColor.LIGHT_PURPLE + "")
				.replaceAll(prefix + "e" + suffix, ChatColor.YELLOW + "").replaceAll(prefix + "f" + suffix, ChatColor.WHITE + "")
				.replaceAll(prefix + "m" + suffix, ChatColor.STRIKETHROUGH + "").replaceAll(prefix + "n" + suffix, ChatColor.UNDERLINE + "")
				.replaceAll(prefix + "l" + suffix, ChatColor.BOLD + "").replaceAll(prefix + "k" + suffix, ChatColor.MAGIC + "")
				.replaceAll(prefix + "o" + suffix, ChatColor.ITALIC + "").replaceAll(prefix + "r" + suffix, ChatColor.RESET + "")
				.replaceAll(prefix + "z" + suffix, "\n");
	}
}
