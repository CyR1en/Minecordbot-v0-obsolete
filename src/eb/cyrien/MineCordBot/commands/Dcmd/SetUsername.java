package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.utils.MessengerUtil;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class SetUsername extends Command {

    private final String HELP = Main.getInstance().getBotConfig().COMMAND_EXECUTOR + "setusername <username>";
    private final String DESCRIPTION = "Set the user name of the bot. Limited to a certain amount of times";

    public SetUsername(Main instance) {
        super(instance);
        setUsage(HELP);
        setDescription(DESCRIPTION);
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
        e.getJDA().getAccountManager().setUsername(MessengerUtil.concatenateArgs(0, args));
        e.getJDA().getAccountManager().update();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if (success) {
            MessengerUtil.sendTyping(1, e);
            e.getTextChannel().sendMessage("Username changed! :white_check_mark:");
        } else
            e.getTextChannel().sendMessage(noPermMessage());
    }
}
