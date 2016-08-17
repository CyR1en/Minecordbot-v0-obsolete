package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class SetNick extends Command {

    private final String HELP = "USAGE: " + Main.botConfig.COMMAND_EXECUTOR + "setnick";
    private final String DESCRIPTION = "set the user nickname of the bot.";

    public SetNick() {
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
        e.getJDA().getAccountManager().setNickname(e.getJDA().getTextChannelById
                (Main.botConfig.BINDED_CHANNEL).getGuild(), Main.concatenateArgs(0, args));
        e.getJDA().getAccountManager().update();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if(success)
            e.getTextChannel().sendMessage("Nickname changed! :white_check_mark:");
        else
            e.getTextChannel().sendMessage(noPermMessage());
    }
}
