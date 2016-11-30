package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.entity.DiscordCommandSender;
import eb.cyrien.MineCordBot.entity.DiscordConsoleCommandSender;
import eb.cyrien.MineCordBot.utils.MessengerUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.bukkit.Bukkit;

public class Mcmd extends Command {

    private final String HELP = Main.getInstance().getBotConfig().COMMAND_EXECUTOR + "mcmd <command>";
    private final String DESCRIPTION = "Do Minecraft commands from discord";

    public Mcmd(Main instance) {
        super(instance);
        setDescription(DESCRIPTION);
        setUsage(HELP);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        if (!hasPermission(e.getAuthor().getId()))
            if (!e.getAuthor().getId().equals(instance.getBotConfig().OWNER_ID))
                return false;
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        if(args.length == 0 || args == null)
            Bukkit.getServer().dispatchCommand(new DiscordCommandSender(e), "");
        else if (args[0].equalsIgnoreCase("help"))
            Bukkit.getServer().dispatchCommand(new DiscordCommandSender(e), MessengerUtil.concatenateArgs(0, args).trim());
        else
            Bukkit.getServer().dispatchCommand(new DiscordConsoleCommandSender(e), MessengerUtil.concatenateArgs(0, args).trim());
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if (!success)
            e.getTextChannel().sendMessage(noPermMessage()).queue();
    }
}
