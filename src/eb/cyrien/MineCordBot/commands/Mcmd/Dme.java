package eb.cyrien.MineCordBot.commands.Mcmd;

import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.utils.BotConfig;
import eb.cyrien.MineCordBot.utils.MessengerUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Dme implements CommandExecutor {

    private Main plug;

    public Dme (Main plug) {
        this.plug = plug;
    }

    private boolean preCommand(CommandSender commandSender, Command command) {
        if(!command.getName().equalsIgnoreCase("dme"))
            return usage(commandSender);
        if(!commandSender.hasPermission("minecordbot.dme")) {
            commandSender.sendMessage(ChatColor.RED + "You don't have permission to use /dme");
            return false;
        }
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can do discord me or /dme");
            return false;
        }
        return true;
    }

    private void command(CommandSender commandSender, String[] args) {
        Player p = (Player) commandSender;
        String msg = MessengerUtil.concatenateArgs(0, args);
        for(Player pl : plug.getServer().getOnlinePlayers())
            pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5* " + "&r" + p.getDisplayName() + "&5" + msg));
        for(String s : BotConfig.BINDED_CHANNELS)
            plug.getJda().getTextChannelById(s).sendMessage("`* " + p.getName() + msg + "`");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!preCommand(commandSender, command))
            return true;
        command(commandSender, args);
        return true;
    }

    private boolean usage(CommandSender commandSender) {
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l=== dme usage ==="));
        return false;
    }
}
