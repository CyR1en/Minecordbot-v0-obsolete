package eb.cyrien.CFABot.commands.Mcmd;

import eb.cyrien.CFABot.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MReload implements CommandExecutor {

    private Main plug;

    public MReload(Main plug) {
        this.plug = plug;
    }

    private boolean preCommand(CommandSender commandSender, Command command, String[] args) {
        if(!command.getName().equalsIgnoreCase("CFABot"))
            return usage(commandSender);
        if(!commandSender.hasPermission("CFABot.reload"))
            return noPerm(commandSender);
        if(args == null || args.length < 1 || args.length > 2)
            return usage(commandSender);
        return true;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!preCommand(commandSender, command, args))
            return true;
        Main.botConfig.reload();
        return true;
    }

    private boolean usage(CommandSender cs) {
        cs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l=== CFABot Usage ==="));
        cs.sendMessage("/CFABot reload");
        return false;
    }

    private boolean noPerm(CommandSender cs) {
        cs.sendMessage(ChatColor.RED + "You do not have permission to reload CFABot");
        return false;
    }

}
