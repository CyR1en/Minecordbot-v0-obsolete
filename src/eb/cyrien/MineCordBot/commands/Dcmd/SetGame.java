package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class SetGame extends Command {

    public static final String HELP = "USAGE: " + Main.botConfig.COMMAND_EXECUTOR + "setgame <text>";

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        String authID = e.getAuthor().getId();
        if(!hasPermission(authID))
            if(!authID.trim().equalsIgnoreCase(Main.botConfig.OWNER_ID.trim()))
                return false;
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        e.getJDA().getAccountManager().setGame(Main.concatenateArgs(0, args));
        e.getJDA().getAccountManager().update();
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