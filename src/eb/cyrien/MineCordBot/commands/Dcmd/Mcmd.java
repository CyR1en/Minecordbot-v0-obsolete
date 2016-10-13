package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.bukkit.Bukkit;

public class Mcmd extends Command {

    private final String HELP = Main.botConfig.COMMAND_EXECUTOR + "mcmd <command>";
    private final String DESCRIPTION = "Do Minecraft commands from discord";

    public Mcmd() {
        setDescription(DESCRIPTION);
        setUsage(HELP);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        if (!hasPermission(e.getAuthor().getId()))
            if (!e.getAuthor().getId().equals(Main.botConfig.OWNER_ID))
                return false;
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), Main.concatenateArgs(0, args).trim());
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if (success)
            e.getTextChannel().sendMessage(":ok_hand:");
        else
            e.getTextChannel().sendMessage(noPermMessage());
    }
}
