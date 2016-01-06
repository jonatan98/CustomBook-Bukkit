# CustomBook Bukkit Plugin
Bukkit plugin to generate custom books, on first join, every join or on command execution

Created repository to allow interested people to update the code. Updated code will be updated to the site of the plugin, http://dev.bukkit.org/bukkit-plugins/custom-book/

Dependencies:
* Spigot server:
    * Location: C:\Programming_dependencies\
    * Current file: spigot-1.8.8.jar

Plans:
* Loop in plugin's onCreate to verify all books (check missing variables and remove/transfer outdated variables)
    * Important to do a backup of the Books.xml file every now and then, because some people put a lot of work into books and it would be a shame if they lost it because of a bug
* Check for newer versions of the plugin
    * onCreate; perform the check and store some file or conf value if new version is uploaded
    * onPlayerJoin; 1. check player perms, 2. check file or conf value
    * if possible, auto-update the plugin (not likely, but would be awesome. Might have to be done through another plugin)
* Website where you can write the contents of the book, and it will be converted to XML for you
* Make books buyable (Vault)
    * Permission to buypass price
    * Command to edit prices?

* Idk about this one, but could be cool
    * Make it a library so that it could be used in other people's plugins