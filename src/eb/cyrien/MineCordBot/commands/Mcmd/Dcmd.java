package eb.cyrien.MineCordBot.commands.Mcmd;

import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.utils.BotConfig;
import eb.cyrien.MineCordBot.utils.MessengerUtil;
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
        if (!command.getName().equalsIgnoreCase("dcmd"))
            return usage(commandSender);
        if (!commandSender.hasPermission("minecordbot.dcmd")) {
            commandSender.sendMessage(ChatColor.RED + "You have no permission to do Discord command from Minecraft");
            return false;
        }
        return !(args.length == 0) || usage(commandSender);
    }

    private void command(CommandSender commandSender, String[] args) {
        for (String s : BotConfig.BINDED_CHANNELS)
            plugin.getJda().getTextChannelById(s).sendMessage(MessengerUtil.concatenateArgs(0, args)).queue(consumer ->
                    plugin.getLogger().info(commandSender.getName() + " Executed a discord command : " + args[0])
            );
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!preCommand(commandSender, command, args))
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
