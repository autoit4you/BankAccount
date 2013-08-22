package de.autoit4you.bankaccount;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import net.milkbowl.vault.permission.Permission;


public class Permissions {
	
	
	private Permission permission;

	public boolean setupPermissions(){
		RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServicesManager().getRegistration(Permission.class);
		if(permissionProvider != null)
			permission = permissionProvider.getProvider();
			return (permission != null);
	}
	
	public boolean user(CommandSender sender, String[] args){
		if(args.length < 1 || args[0].equalsIgnoreCase("help")){
			return sender.hasPermission("bankaccount.user.help") || sender.isOp();
		}else if(args[0].equalsIgnoreCase("open")){
			return sender.hasPermission("bankaccount.user.open") || sender.isOp();
		}else if(args[0].equalsIgnoreCase("close")){
			return sender.hasPermission("bankaccount.user.close") || sender.isOp();
		}else if(args[0].equalsIgnoreCase("list")){
			return sender.hasPermission("bankaccount.user.list") || sender.isOp();
		}else if(args[0].equalsIgnoreCase("withdraw")){
			return sender.hasPermission("bankaccount.user.withdraw") || sender.isOp();
		}else if(args[0].equalsIgnoreCase("deposit")){
			return sender.hasPermission("bankaccount.user.deposit") || sender.isOp();
		}else if(args[0].equalsIgnoreCase("balance")){
			return sender.hasPermission("bankaccount.user.balance") || sender.isOp();
		}else if(args[0].equalsIgnoreCase("transfer")){
			return sender.hasPermission("bankaccount.user.transfer") || sender.isOp();
		}else if(args[0].equalsIgnoreCase("adduser") || args[0].equalsIgnoreCase("removeuser")){
			return sender.hasPermission("bankaccount.user.manageuser") || sender.isOp();
		}else if(args[0].equalsIgnoreCase("addadmin") || args[0].equalsIgnoreCase("removeadmin")){
			return sender.hasPermission("bankaccount.user.manageadmin") || sender.isOp();
		}else if(args[0].equalsIgnoreCase("transferownership")) {
			return sender.hasPermission("bankaccount.user.transferownership") || sender.isOp();
		}else if(args[0].equalsIgnoreCase("top")) {
            return sender.hasPermission("bankaccount.user.top");
        }else{
			return false;
		}
	}
}
