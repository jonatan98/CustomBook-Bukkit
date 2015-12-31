package me.jonna.CustomBook;

//Newest version

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main extends JavaPlugin implements Listener{
	public final Logger logger = Logger.getLogger("minecraft");
	
	public File _file_joinbooks;
	public File _file_normalbooks;
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has been disabled!" + "on version" +  pdfFile.getVersion() );
		
	}
	@Override
	public void onEnable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has been enabled!" + " on version" + pdfFile.getVersion() );
		
		//Generate plugin dir if not existant
		File f = new File(getDataFolder() + "/");
		if(!f.exists())
		    f.mkdir();
		_file_joinbooks = new File(getDataFolder() + "/Join Books.xml");
		if(!_file_joinbooks.exists())
		    f.mkdir();
		_file_normalbooks = new File(getDataFolder() + "/Normal Books.xml");
		if(!_file_normalbooks.exists())
		    f.mkdir();
		
		
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	String messagePrefix = ChatColor.GREEN + "[" + ChatColor.DARK_PURPLE + "Books" + ChatColor.GREEN +  "]" + ChatColor.WHITE;
	String noPermission = ChatColor.DARK_RED + "You have no permission to perform this command.";
	
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		logger.info("Player joined");
		Player p = event.getPlayer();
		
		//Read the Join Books.xml
		try{
    		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    		Document doc = dBuilder.parse(_file_joinbooks);
    				
    		//optional, but recommended
    		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
    		doc.getDocumentElement().normalize();
    		
    		for(int i = 0; i < doc.getElementsByTagName("book").getLength(); i++){
    			Node nNode = doc.getElementsByTagName("book").item(i);
    					
    			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

    				Element bookData = (Element) nNode;
    			
		    		//TODO find config / permissions to see which custom permissions are required
		    		Node config = bookData.getElementsByTagName("config").item(0);
		    		
	    			
		    		if(p.hasPermission("books.join.*")){
		    			
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
		    			logger.info("No permission");
		    		}
	    		}else{
	    			//Element is of wrong type
	    			logger.info("Element is of wrong type");
	    		}
	    		
    		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		
		if(label.equalsIgnoreCase("cb") || label.equalsIgnoreCase("custombook")){
			
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(args.length == 0){
					if(player.hasPermission("books.help")){
				//Help
						player.sendMessage(messagePrefix + ChatColor.GREEN + "Help");
						
						int u = 1;
						//Max amount of pages
						int s = 500;
						
						do{
							
								if(!(getConfig().getString("List." + u + ".Title") == null)){
									player.sendMessage(ChatColor.GRAY + "* " + ChatColor.WHITE + "/cb " + getConfig().getString("List." + u + ".Title"));
									
									u++;
								}else{
									u = 501;
								}
								
						}while(u < s);
						
						player.sendMessage(ChatColor.GRAY + "* " + ChatColor.GREEN + "/cb info" + ChatColor.DARK_PURPLE + " - " + ChatColor.GREEN + "Plugin information");
						
						if(player.hasPermission("books.create.normal") || player.hasPermission("books.create.*")){
							player.sendMessage(ChatColor.GRAY + "* " + ChatColor.GREEN + "/cb create normal <name>" + ChatColor.DARK_PURPLE + " - " + ChatColor.GREEN + "Creates a book given on command");
						}
						if(player.hasPermission("books.create.join") || player.hasPermission("books.create.*")){
							player.sendMessage(ChatColor.GRAY + "* " + ChatColor.GREEN + "/cb create join <name>" + ChatColor.DARK_PURPLE + " - " + ChatColor.GREEN + "Creates a book given on first join");
						}
						if(player.hasPermission("books.reload")){
							player.sendMessage(ChatColor.GRAY + "* " + ChatColor.GREEN + "/cb reload" + ChatColor.DARK_PURPLE + " - " + ChatColor.GREEN + "Reloads the confiuration file");
						}
					}else{
						player.sendMessage(noPermission);
					}
				}else{
					
						if((player.hasPermission("books.custom." + args[0]) || player.hasPermission("books.custom.*")) && !(getConfig().getString("Books." + args[0].toLowerCase() + ".Content") == null) && !(args[0].equalsIgnoreCase("config") || args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("info"))){
							//Actually give the book
						
								ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
								BookMeta meta = (BookMeta) book.getItemMeta();
								meta.setTitle(replaceColors(getConfig().getString("Books." + args[0].toLowerCase() + ".Title")));
								meta.setAuthor(replaceColors(getConfig().getString("Books." + args[0].toLowerCase() + ".Author")));
								List<String> pages = new ArrayList<String>();
								
								int x = 0;
								int y = 500;
								do{
									x++;
									if(!(getConfig().getString("Books." + args[0].toLowerCase() + ".Content.Page-" + x) == null)){
										pages.add(replaceColors(getConfig().getString("Books." + args[0].toLowerCase() + ".Content.Page-" + x)));
									}else{
										x = 501;
									}
								}while(x < y);
								
								meta.setPages(pages);
								book.setItemMeta(meta);
						        player.getInventory().addItem(book);
						        if(getConfig().getBoolean("Books." + args[0].toLowerCase() + ".EnableMessage") == true){
						        	player.sendMessage(replaceColors(messagePrefix + getConfig().getString("Books." + args[0].toLowerCase() + ".Message")));
						        }
						
						
						
						}
						
					if((player.hasPermission("books.custom." + args[0]) || player.hasPermission("books.custom.*")) && getConfig().getString("Books." + args[0] + ".Content") == null && !(args[0].equalsIgnoreCase("config") || args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("info"))){
						//Book doesn't exist
						player.sendMessage(messagePrefix + "Book doesn't exist");
						
					}else if((!player.hasPermission("books.custom." + args[0]) && !player.hasPermission("books.custom.*")) && !(getConfig().getString("Books." + args[0] + ".Content") == null) && !(args[0].equalsIgnoreCase("config") || args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("info"))){
						//No Permission && Book exists
						player.sendMessage(noPermission);
					}else if((!player.hasPermission("books.custom." + args[0]) && !player.hasPermission("books.custom.*")) && getConfig().getString("Books." + args[0] + ".Content") == null && !(args[0].equalsIgnoreCase("config") || args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("info"))){
						//No Permission && Book doesn't exist
						player.sendMessage(noPermission);
						
					}
					
					if(args[0].equalsIgnoreCase("create")){	
					//Create
						if(player.hasPermission("books.create.*") || player.hasPermission("books.create.normal") || player.hasPermission("books.create.join")){
							if(args.length == 3){
								if(args[1].equalsIgnoreCase("normal") || args[1].equalsIgnoreCase("join")){
									
										if(args[1].equalsIgnoreCase("normal")){
											if(player.hasPermission("books.create.normal") || player.hasPermission("books.create.*")){
												if(!(getConfig().getString("Books." + args[2] + ".Title") == null)){
													player.sendMessage(messagePrefix + "A book by that name already exists");	
												}else{
												//Create Normal book
													
													getConfig().set("Books." + args[2].toLowerCase() + ".Title", "Title");
													getConfig().set("Books." + args[2].toLowerCase() + ".Author", "Author");
													getConfig().set("Books." + args[2].toLowerCase() + ".Content.AmountOfPages", "1");
													getConfig().set("Books." + args[2].toLowerCase() + ".Content.Page-1", "Content");
													getConfig().set("Books." + args[2].toLowerCase() + ".EnableMessage", false);
													getConfig().set("Books." + args[2].toLowerCase() + ".Message", "You have been given a custom book");
													
													int xx = 0;
													int uu = 500;
													do{
														xx++;
														
														if(!(getConfig().getString("List." + xx + ".Title") == null)){
															getConfig().set("List." + xx + ".Title", args[2]);
															getConfig().set("List." + xx + ".Description", "New unconfigured book");
															saveConfig();
															
															xx = 500;
														}
													
													}while(xx < uu);
													player.sendMessage(messagePrefix + args[2] + " has been saved [Normal Book]");
												}
											}else{
												player.sendMessage(noPermission);
											}
										}else if(args[1].equalsIgnoreCase("join")){
											if(player.hasPermission("books.create.join") || player.hasPermission("books.create.*")){
												int xx = 0;
												int yy = 500;
												
												do{
													xx++;
													
													if(!(getConfig().getString("JoinBooks." + xx + ".Title") == null)){
														
													}else{
														getConfig().set("JoinBooks." + xx + ".Title", "/a/Title");
														getConfig().set("JoinBooks." + xx + ".Author", "/b/Author");
														getConfig().set("JoinBooks." + xx + ".Content.Page-1", "/c/Page 1");
														getConfig().set("JoinBooks." + xx + ".Content.Page-2", "/d/Page 2");
														getConfig().set("JoinBooks." + xx + ".EnableMessage", false);
														getConfig().set("JoinBooks." + xx + ".Message", "/5/You have been given a custom book");
														
														saveConfig();
														player.sendMessage(messagePrefix + args[2] + " has been saved [Join Book - ID = " + xx + "]");
														xx = 500;
													}
												}while(xx < yy);
											}
										}
									
								}else{
									player.sendMessage("Invalid type, available types are normal and join");
									player.sendMessage("/cb create <normal/join> <name>");
									
								}
							}else{
								player.sendMessage("/cb create <normal/join> <name>");
							}
							
							
						}else{
							player.sendMessage(noPermission);
						}
						
					}else if(args[0].equalsIgnoreCase("config")){
					//Config changing
						player.sendMessage(messagePrefix + " In Game config changing is currently not available, will be released in a future update");
						
						player.sendMessage(messagePrefix + " Configuration file reloaded successfully");
					}else if(args[0].equalsIgnoreCase("reload")){
					//Reload the config file
						if(player.hasPermission("books.reload")){
							reloadConfig();
							saveConfig();
							player.sendMessage(messagePrefix + " Configuration file successfully reloaded!");
						}else{
							player.sendMessage(noPermission);
						}
					}else if(args[0].equalsIgnoreCase("info")){
						if(player.hasPermission("books.help")){
							player.sendMessage(messagePrefix + " Information");
							player.sendMessage(ChatColor.GREEN + "Developers: " + ChatColor.BLUE + "jn1234 (Main)" + ChatColor.GRAY + " - " + ChatColor.BLUE + "MineCrafterCity");
							player.sendMessage(ChatColor.GREEN + "Plugin info: www.dev.bukkit.org/bukkit-plugins/custom-book");
							
							PluginDescriptionFile pdfFile = this.getDescription();
							player.sendMessage(ChatColor.GREEN + "Plugin version: " + pdfFile.getVersion());
						}else{
							player.sendMessage(noPermission);
						}
					}
				}
			
			}else{
				//This is what happens if the command is forced trough console
				this.logger.info("[Books] Help");
				this.logger.info("Books commands is not available from console");
			}
		}//End of the cb commands
		
		
		return false;
	}
	
	public String replaceColors(String s){
		return replaceColors(s, false);
	}
	public String replaceColors(String s, boolean console){
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
