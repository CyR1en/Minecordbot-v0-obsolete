package eb.cyrien.CFABot.commands.Dcmd;

import eb.cyrien.CFABot.Command;
import eb.cyrien.CFABot.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class DReload extends Command {

    public static final String HELP = "USAGE: " + Main.botConfig.COMMAND_EXECUTOR + "reload";
    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        if(!hasPermission(e.getAuthor().getId()))
            if(!e.getAuthor().getId().trim().equals(Main.botConfig.OWNER_ID.trim()))
                return false;
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        Main.botConfig.reload();
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if(success)
            e.getTextChannel().sendMessage(":white_check_mark:");
        else
            e.getTextChannel().sendMessage(noPermMessage());
    }
}
