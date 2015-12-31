package me.jonna.CustomBook;

import org.bukkit.plugin.java.JavaPlugin;

public class Config extends JavaPlugin {
	public void createConfig(){
		getConfig().set("Books.Book.Title", "/4/Custom-Book");
		getConfig().set("Books.Book.Author", "/5/jn1234");
		getConfig().set("Books.Book.Content.Page-1", "/a/This book is created using a plugin named /c/Custom-Book/0///c/Books");
		getConfig().set("Books.Book.EnableMessage", false);
		getConfig().set("Books.Book.Message", "/4/You have been given a Custom Book. www.bukkit.org/bukkit-plugins/custom-book/");
		
		getConfig().set("Books.Book.Title", "/4/Custom-Book");
		getConfig().set("Books.Book.Author", "/5/lepel100");
		getConfig().set("Books.Book.Content.Page-1", "/a/This book is created using a plugin named /c/Custom-Book/0///c/Books");
		getConfig().set("Books.Book.EnableMessage", false);
		getConfig().set("Books.Book.Message", "/4/You have been given a Custom Book. www.bukkit.org/bukkit-plugins/custom-book/");
		
		getConfig().set("List.1.Title", "/4/Custom-Book");
		getConfig().set("List.1.Description", "/4/awesome Custom Books plugin: www.bukkit.org/bukkit-plugins/custom-book/");
		
		getConfig().set("PlayerList", "RandomPlayerName382");
		
		saveConfig();
	}
}
