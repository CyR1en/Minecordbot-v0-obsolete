package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class SetUsername extends Command {

    private final String HELP = "USAGE: " + Main.botConfig.COMMAND_EXECUTOR + "setusername";
    private final String DESCRIPTION = "set the user name of the bot. Limited to a certain amount of times";

    public SetUsername() {
        setUsage(HELP);
        setDescription(DESCRIPTION);
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
        e.getJDA().getAccountManager().setUsername(Main.concatenateArgs(0, args));
        e.getJDA().getAccountManager().update();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if(success)
            e.getTextChannel().sendMessage("Username changed! :white_check_mark:");
        else
            e.getTextChannel().sendMessage(noPermMessage());
    }
}
