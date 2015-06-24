package me.Juan_Pablo;

import static org.bukkit.ChatColor.BLUE;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.RED;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Warn extends JavaPlugin implements Listener {
	
	public void onEnable() {

		if(getConfig() == null) {
			saveDefaultConfig();
		}		
	}
	
	public void onDisable() {
		saveConfig();
	}
      
       @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
               if (cmd.getName().equalsIgnoreCase("warn")) {
            	   if(sender.hasPermission("Warn.warn")) {
                       if (args.length < 2) {
                               sender.sendMessage(RED + "/warn <player> <reason>");
                               return true;
                       }
                      
                       final Player target = Bukkit.getServer().getPlayer(args[0]);
                      
                       if (target == null) {
                               sender.sendMessage(String.format("%s[%sUMC %sWarning%s]%s Could not find player " + args[0], 
                            		   GRAY,BLUE,RED,GRAY,RED));
                               return true;
                       }
                       
                      
                      
                       String msg = "";
                       for (int i = 1; i < args.length; i++) {
                               msg += args[i] + " ";
                       }
                      
                       Object level = this.getConfig().get(target.getName());
                      
                       if (level == null) {
                               target.sendMessage(RED + "[Warning] " + msg);
                               this.getConfig().set(target.getName(), 1);
                               this.saveConfig();
                               return true;
                       }
                      
                       int l = Integer.parseInt(level.toString());
                       
                       
                      
                       if (l == 2) {
                               target.kickPlayer(RED + "[Warning] " + msg);
                               this.getConfig().set(target.getName(), 2);
                               this.saveConfig();
                               return true;
                       }
                      
                       if (l == 3) {
                               target.kickPlayer(RED + "[Warning] " + msg);
                               target.setBanned(true);
                               this.getConfig().set(target.getName(), 3);
                               this.saveConfig();
                              
                               Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                                       
									public void run() {
                                               target.setBanned(false);
                                       }
                               }, 5 * 20);
                              
                               return true;
                       }
               }
               } else {
            	   sender.sendMessage(String.format("%s[%sUMC %sWarning%s]%s You Don't Have Persion To Use This Command", 
            			   GRAY,BLUE,RED,GRAY,RED));
               }
               return true;
       }
}
