package eb.cyrien.MineCordBot.commands.Mcmd;

import eb.cyrien.MineCordBot.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Dcmd implements CommandExecutor {

    private Main plugin;

    public Dcmd(Main plugin) {
        this.plugin = plugin;
    }

    private boolean preCommand(CommandSender commandSender, Command command, String[] args) {
        if(!command.getName().equalsIgnoreCase("dcmd"))
            return usage(commandSender);
        if(!commandSender.hasPermission("minecordbot.dcmd")) {
            commandSender.sendMessage(ChatColor.RED + "You have no permission to do Discord command from Minecraft");
            return false;
        }
        if(args.length == 0 || args == null)
            return usage(commandSender);
        return true;
    }

    private void command(CommandSender commandSender, String[] args) {
        Main.jda.getTextChannelById(Main.botConfig.BINDED_CHANNEL).sendMessage(Main.concatenateArgs(0, args));
        Main.log.info(commandSender.getName() + " Executed a discord command : " + args[0]);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!preCommand(commandSender, command, args))
            return true;
        command(commandSender, args);
        return true;
    }

    private boolean usage(CommandSender commandSender) {
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l=== dcmd usage ==="));
        commandSender.sendMessage("/dcmd <discord command> {other arguments if applies)");
        commandSender.sendMessage("include command executor for discord command.");
        commandSender.sendMessage("i.e : /dcmd !play sample_song");
        return false;
    }
}
