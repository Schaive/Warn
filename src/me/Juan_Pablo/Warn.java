package me.Juan_Pablo;

import static org.bukkit.ChatColor.RED;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Warn extends JavaPlugin implements Listener{
	
	public void onEnable() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.addPermission(new Permissions().warn);
		saveDefaultConfig();
	}
	
	public void onDisable() {
		getServer().getPluginManager().removePermission(
				new Permissions().warn);
	}
      
       @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
               if (cmd.getName().equalsIgnoreCase("warn")) {
            	   if(sender.hasPermission(new Permissions().warn)) {
                       if (args.length < 2) {
                               sender.sendMessage(RED + "/warn <player> <reason>");
                               return true;
                       }
                      
                       final Player target = Bukkit.getServer().getPlayer(args[0]);
                      
                       if (target == null) {
                               sender.sendMessage(RED + "Could not find player " + args[0]);
                               return true;
                       }
                      
                       String msg = "";
                       for (int i = 1; i < args.length; i++) {
                               msg += args[i] + " ";
                       }
                      
                       Object level = this.getConfig().get(target.getName());
                      
                       if (level == null) {
                               target.sendMessage(RED + msg);
                               this.getConfig().set(target.getName(), 1);
                               this.saveConfig();
                               return true;
                       }
                      
                       int l = Integer.parseInt(level.toString());
                       
                       
                      
                       if (l == 2) {
                               target.kickPlayer(RED + msg);
                               this.getConfig().set(target.getName(), 2);
                               this.saveConfig();
                               return true;
                       }
                      
                       if (l == 3) {
                               target.kickPlayer(RED + msg);
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
            	   sender.sendMessage(RED + "You Don't Have Persion To Use This Command");
               }
               return true;
       }
}
