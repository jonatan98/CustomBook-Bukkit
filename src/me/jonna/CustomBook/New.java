package me.jonna.CustomBook;

//Newest version

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class New extends JavaPlugin implements Listener{
	public final Logger logger = Logger.getLogger("minecraft");
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has been disabled!" + "on version" +  pdfFile.getVersion() );
		
	}
	@Override
	public void onEnable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has been enabled!" + " on version" + pdfFile.getVersion() );
		
		getConfig().addDefault("Books.information.Title", "/c/Server information");
		getConfig().addDefault("Books.information.Author", "/b/the server");
		getConfig().addDefault("Books.information.Content.Page-1", "Welcome to the server!");
		getConfig().addDefault("Books.information.Content.Page-2", "This book is made using the plugin, custom-book aka books which you can read more about using /cb info");
		getConfig().addDefault("Books.information.EnableMessage", false);
		getConfig().addDefault("Books.information.Message", "You have been given a custom book");
		
		getConfig().addDefault("JoinBooks.1.Title", "/c/Welcome to our server!");
		getConfig().addDefault("JoinBooks.1.Author", "jn1234");
		getConfig().addDefault("JoinBooks.1.Content.Page-1", "This book is given using a plugin named custom books aka books");
		getConfig().addDefault("JoinBooks.1.Content.Page-2", "/1/S/2/e/3/c/4/o/5/n/6/d /7/p/8/a/9/g/a/e");
		getConfig().addDefault("JoinBooks.1.EnableMessage", false);
		getConfig().addDefault("JoinBooks.1.Message", "You have been given a custom book");
		
		getConfig().addDefault("List.1.Title", "information");
		getConfig().addDefault("List.1.Description", "Awesome Custom Books plugin, www.bukkit.org/bukkit-plugins/custom-book/");
		
		List<String> playerList = getConfig().getStringList("PlayerList");
		playerList.add("RandomPlayerName2");
		getConfig().addDefault("PlayerList", playerList);
		getConfig().options().copyDefaults(true);
		saveConfig();
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	String messagePrefix = ChatColor.GREEN + "[" + ChatColor.DARK_PURPLE + "Books" + ChatColor.GREEN +  "]" + ChatColor.WHITE;
	String noPermission = ChatColor.DARK_RED + "You have no permission to perform this command.";
	
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player p = event.getPlayer();	
		//List<String> playerList = (List<String>) getConfig().getStringList("playerList");
		
		int a = 0;
		int i = 500;
		
		do{
			a++;
			
			if(!(getConfig().getString("JoinBooks." + a + ".Title") == null)){
				if(p.hasPermission("books.join." + a) || p.hasPermission("books.join.*")){
					if(!(getConfig().getStringList("PlayerList").contains(p.getName()))){
						ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
						BookMeta meta = (BookMeta) book.getItemMeta();
						meta.setTitle(getConfig().getString("JoinBooks." + a + ".Title").replaceAll("/0/", ChatColor.BLACK + "")
								.replaceAll("/1/", ChatColor.DARK_BLUE + "").replaceAll("/2/", ChatColor.DARK_GREEN + "")
								.replaceAll("/3/", ChatColor.DARK_AQUA + "").replaceAll("/4/", ChatColor.DARK_RED + "")
								.replaceAll("/5/", ChatColor.DARK_PURPLE + "").replaceAll("/6/", ChatColor.GOLD + "")
								.replaceAll("/7/", ChatColor.GRAY + "").replaceAll("/8/", ChatColor.DARK_GRAY + "")
								.replaceAll("/9/", ChatColor.BLUE + "").replaceAll("/a/", ChatColor.GREEN + "")
								.replaceAll("/b/", ChatColor.AQUA + "").replaceAll("/c/", ChatColor.RED + "")
								.replaceAll("/d/", ChatColor.LIGHT_PURPLE + "").replaceAll("/e/", ChatColor.YELLOW + "")
								.replaceAll("/f/", ChatColor.WHITE + "").replaceAll("/m/", ChatColor.STRIKETHROUGH + "")
								.replaceAll("/n/", ChatColor.UNDERLINE + "").replaceAll("/l/", ChatColor.BOLD + "")
								.replaceAll("/k/", ChatColor.MAGIC + "").replaceAll("/o/", ChatColor.ITALIC + "")
								.replaceAll("/r/", ChatColor.RESET + ""));
						meta.setAuthor(getConfig().getString("JoinBooks." + a + ".Author").replaceAll("/0/", ChatColor.BLACK + "")
								.replaceAll("/1/", ChatColor.DARK_BLUE + "").replaceAll("/2/", ChatColor.DARK_GREEN + "")
								.replaceAll("/3/", ChatColor.DARK_AQUA + "").replaceAll("/4/", ChatColor.DARK_RED + "")
								.replaceAll("/5/", ChatColor.DARK_PURPLE + "").replaceAll("/6/", ChatColor.GOLD + "")
								.replaceAll("/7/", ChatColor.GRAY + "").replaceAll("/8/", ChatColor.DARK_GRAY + "")
								.replaceAll("/9/", ChatColor.BLUE + "").replaceAll("/a/", ChatColor.GREEN + "")
								.replaceAll("/b/", ChatColor.AQUA + "").replaceAll("/c/", ChatColor.RED + "")
								.replaceAll("/d/", ChatColor.LIGHT_PURPLE + "").replaceAll("/e/", ChatColor.YELLOW + "")
								.replaceAll("/f/", ChatColor.WHITE + "").replaceAll("/m/", ChatColor.STRIKETHROUGH + "")
								.replaceAll("/n/", ChatColor.UNDERLINE + "").replaceAll("/l/", ChatColor.BOLD + "")
								.replaceAll("/k/", ChatColor.MAGIC + "").replaceAll("/o/", ChatColor.ITALIC + "")
								.replaceAll("/r/", ChatColor.RESET + ""));
						List<String> pages = new ArrayList<String>();
						
						int x = 0;
						int y = 500;
						do{
							x++;
							if(!(getConfig().getString("JoinBooks." + a + ".Content.Page-" + x) == null)){
								pages.add(getConfig().getString("JoinBooks." + a + ".Content.Page-" + x).replaceAll("/0/", ChatColor.BLACK + "")
										.replaceAll("/1/", ChatColor.DARK_BLUE + "").replaceAll("/2/", ChatColor.DARK_GREEN + "")
										.replaceAll("/3/", ChatColor.DARK_AQUA + "").replaceAll("/4/", ChatColor.DARK_RED + "")
										.replaceAll("/5/", ChatColor.DARK_PURPLE + "").replaceAll("/6/", ChatColor.GOLD + "")
										.replaceAll("/7/", ChatColor.GRAY + "").replaceAll("/8/", ChatColor.DARK_GRAY + "")
										.replaceAll("/9/", ChatColor.BLUE + "").replaceAll("/a/", ChatColor.GREEN + "")
										.replaceAll("/b/", ChatColor.AQUA + "").replaceAll("/c/", ChatColor.RED + "")
										.replaceAll("/d/", ChatColor.LIGHT_PURPLE + "").replaceAll("/e/", ChatColor.YELLOW + "")
										.replaceAll("/f/", ChatColor.WHITE + "").replaceAll("/m/", ChatColor.STRIKETHROUGH + "")
										.replaceAll("/n/", ChatColor.UNDERLINE + "").replaceAll("/l/", ChatColor.BOLD + "")
										.replaceAll("/k/", ChatColor.MAGIC + "").replaceAll("/o/", ChatColor.ITALIC + "")
										.replaceAll("/r/", ChatColor.RESET + "").replace("/z/", "\n"));
							}else{
								x = 501;
							}
						}while(x < y);
						
						meta.setPages(pages);
						book.setItemMeta(meta);
				        p.getInventory().addItem(book);
				        if(getConfig().getBoolean("JoinBooks." + a + ".EnableMessage") == true){
				        	p.sendMessage(messagePrefix + getConfig().getString("JoinBooks." + a + ".Message").replaceAll("/0/", ChatColor.BLACK + "")
									.replaceAll("/1/", ChatColor.DARK_BLUE + "").replaceAll("/2/", ChatColor.DARK_GREEN + "")
									.replaceAll("/3/", ChatColor.DARK_AQUA + "").replaceAll("/4/", ChatColor.DARK_RED + "")
									.replaceAll("/5/", ChatColor.DARK_PURPLE + "").replaceAll("/6/", ChatColor.GOLD + "")
									.replaceAll("/7/", ChatColor.GRAY + "").replaceAll("/8/", ChatColor.DARK_GRAY + "")
									.replaceAll("/9/", ChatColor.BLUE + "").replaceAll("/a/", ChatColor.GREEN + "")
									.replaceAll("/b/", ChatColor.AQUA + "").replaceAll("/c/", ChatColor.RED + "")
									.replaceAll("/d/", ChatColor.LIGHT_PURPLE + "").replaceAll("/e/", ChatColor.YELLOW + "")
									.replaceAll("/f/", ChatColor.WHITE + "").replaceAll("/m/", ChatColor.STRIKETHROUGH + "")
									.replaceAll("/n/", ChatColor.UNDERLINE + "").replaceAll("/l/", ChatColor.BOLD + "")
									.replaceAll("/k/", ChatColor.MAGIC + "").replaceAll("/o/", ChatColor.ITALIC + "")
									.replaceAll("/r/", ChatColor.RESET + ""));
				        }
				        
					}else{
						
					}
					
				}
			}else{
				a = 501;
				
				if(!(getConfig().getStringList("PlayerList").contains(p.getName()))){
					List<String> playerList = (List<String>) getConfig().getStringList("PlayerList");
			        playerList.add(p.getName());
			        getConfig().set("PlayerList", playerList);
			        saveConfig();
				}
			}
		}while(a < i);
		
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
								meta.setTitle(getConfig().getString("Books." + args[0].toLowerCase() + ".Title").replaceAll("/0/", ChatColor.BLACK + "")
										.replaceAll("/1/", ChatColor.DARK_BLUE + "").replaceAll("/2/", ChatColor.DARK_GREEN + "")
										.replaceAll("/3/", ChatColor.DARK_AQUA + "").replaceAll("/4/", ChatColor.DARK_RED + "")
										.replaceAll("/5/", ChatColor.DARK_PURPLE + "").replaceAll("/6/", ChatColor.GOLD + "")
										.replaceAll("/7/", ChatColor.GRAY + "").replaceAll("/8/", ChatColor.DARK_GRAY + "")
										.replaceAll("/9/", ChatColor.BLUE + "").replaceAll("/a/", ChatColor.GREEN + "")
										.replaceAll("/b/", ChatColor.AQUA + "").replaceAll("/c/", ChatColor.RED + "")
										.replaceAll("/d/", ChatColor.LIGHT_PURPLE + "").replaceAll("/e/", ChatColor.YELLOW + "")
										.replaceAll("/f/", ChatColor.WHITE + "").replaceAll("/m/", ChatColor.STRIKETHROUGH + "")
										.replaceAll("/n/", ChatColor.UNDERLINE + "").replaceAll("/l/", ChatColor.BOLD + "")
										.replaceAll("/k/", ChatColor.MAGIC + "").replaceAll("/o/", ChatColor.ITALIC + "")
										.replaceAll("/r/", ChatColor.RESET + ""));
								meta.setAuthor(getConfig().getString("Books." + args[0].toLowerCase() + ".Author").replaceAll("/0/", ChatColor.BLACK + "")
										.replaceAll("/1/", ChatColor.DARK_BLUE + "").replaceAll("/2/", ChatColor.DARK_GREEN + "")
										.replaceAll("/3/", ChatColor.DARK_AQUA + "").replaceAll("/4/", ChatColor.DARK_RED + "")
										.replaceAll("/5/", ChatColor.DARK_PURPLE + "").replaceAll("/6/", ChatColor.GOLD + "")
										.replaceAll("/7/", ChatColor.GRAY + "").replaceAll("/8/", ChatColor.DARK_GRAY + "")
										.replaceAll("/9/", ChatColor.BLUE + "").replaceAll("/a/", ChatColor.GREEN + "")
										.replaceAll("/b/", ChatColor.AQUA + "").replaceAll("/c/", ChatColor.RED + "")
										.replaceAll("/d/", ChatColor.LIGHT_PURPLE + "").replaceAll("/e/", ChatColor.YELLOW + "")
										.replaceAll("/f/", ChatColor.WHITE + "").replaceAll("/m/", ChatColor.STRIKETHROUGH + "")
										.replaceAll("/n/", ChatColor.UNDERLINE + "").replaceAll("/l/", ChatColor.BOLD + "")
										.replaceAll("/k/", ChatColor.MAGIC + "").replaceAll("/o/", ChatColor.ITALIC + "")
										.replaceAll("/r/", ChatColor.RESET + ""));
								List<String> pages = new ArrayList<String>();
								
								int x = 0;
								int y = 500;
								do{
									x++;
									if(!(getConfig().getString("Books." + args[0].toLowerCase() + ".Content.Page-" + x) == null)){
										pages.add(getConfig().getString("Books." + args[0].toLowerCase() + ".Content.Page-" + x).replaceAll("/0/", ChatColor.BLACK + "")
												.replaceAll("/1/", ChatColor.DARK_BLUE + "").replaceAll("/2/", ChatColor.DARK_GREEN + "")
												.replaceAll("/3/", ChatColor.DARK_AQUA + "").replaceAll("/4/", ChatColor.DARK_RED + "")
												.replaceAll("/5/", ChatColor.DARK_PURPLE + "").replaceAll("/6/", ChatColor.GOLD + "")
												.replaceAll("/7/", ChatColor.GRAY + "").replaceAll("/8/", ChatColor.DARK_GRAY + "")
												.replaceAll("/9/", ChatColor.BLUE + "").replaceAll("/a/", ChatColor.GREEN + "")
												.replaceAll("/b/", ChatColor.AQUA + "").replaceAll("/c/", ChatColor.RED + "")
												.replaceAll("/d/", ChatColor.LIGHT_PURPLE + "").replaceAll("/e/", ChatColor.YELLOW + "")
												.replaceAll("/f/", ChatColor.WHITE + "").replaceAll("/m/", ChatColor.STRIKETHROUGH + "")
												.replaceAll("/n/", ChatColor.UNDERLINE + "").replaceAll("/l/", ChatColor.BOLD + "")
												.replaceAll("/k/", ChatColor.MAGIC + "").replaceAll("/o/", ChatColor.ITALIC + "")
												.replaceAll("/r/", ChatColor.RESET + "").replace("/z/", "\n"));
									}else{
										x = 501;
									}
								}while(x < y);
								
								meta.setPages(pages);
								book.setItemMeta(meta);
						        player.getInventory().addItem(book);
						        if(getConfig().getBoolean("Books." + args[0].toLowerCase() + ".EnableMessage") == true){
						        	player.sendMessage(messagePrefix + getConfig().getString("Books." + args[0].toLowerCase() + ".Message").replaceAll("/0/", ChatColor.BLACK + "")
											.replaceAll("/1/", ChatColor.DARK_BLUE + "").replaceAll("/2/", ChatColor.DARK_GREEN + "")
											.replaceAll("/3/", ChatColor.DARK_AQUA + "").replaceAll("/4/", ChatColor.DARK_RED + "")
											.replaceAll("/5/", ChatColor.DARK_PURPLE + "").replaceAll("/6/", ChatColor.GOLD + "")
											.replaceAll("/7/", ChatColor.GRAY + "").replaceAll("/8/", ChatColor.DARK_GRAY + "")
											.replaceAll("/9/", ChatColor.BLUE + "").replaceAll("/a/", ChatColor.GREEN + "")
											.replaceAll("/b/", ChatColor.AQUA + "").replaceAll("/c/", ChatColor.RED + "")
											.replaceAll("/d/", ChatColor.LIGHT_PURPLE + "").replaceAll("/e/", ChatColor.YELLOW + "")
											.replaceAll("/f/", ChatColor.WHITE + "").replaceAll("/m/", ChatColor.STRIKETHROUGH + "")
											.replaceAll("/n/", ChatColor.UNDERLINE + "").replaceAll("/l/", ChatColor.BOLD + "")
											.replaceAll("/k/", ChatColor.MAGIC + "").replaceAll("/o/", ChatColor.ITALIC + "")
											.replaceAll("/r/", ChatColor.RESET + ""));
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
}
