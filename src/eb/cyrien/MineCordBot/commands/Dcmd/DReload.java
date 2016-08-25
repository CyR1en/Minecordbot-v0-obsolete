package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class DReload extends Command {

    private final String HELP = Main.botConfig.COMMAND_EXECUTOR + "reload";
    private final String DESCRIPTION = " Reload BotConfig.yml";

    public DReload () {
        setUsage(HELP);
        setDescription(DESCRIPTION);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        if(!hasPermission(e.getAuthor().getId()))
            if(!e.getAuthor().getId().trim().equals(Main.botConfig.OWNER_ID.trim()))
                return false;
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        e.getTextChannel().sendTyping();
        Main.botConfig.reload();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if(success)
            e.getTextChannel().sendMessage(":white_check_mark:");
        else
            e.getTextChannel().sendMessage(noPermMessage());
    }

}
