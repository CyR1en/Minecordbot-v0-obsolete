package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.utils.MessengerUtil;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class SetNick extends Command {

    private final String HELP = Main.getInstance().getBotConfig().COMMAND_EXECUTOR + "setnick <nickname>";
    private final String DESCRIPTION = "set the user nickname of the bot.";

    public SetNick(Main instance) {
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
        e.getJDA().getAccountManager().setNickname(e.getTextChannel().getGuild(), MessengerUtil.concatenateArgs(0, args));
        e.getJDA().getAccountManager().update();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if (success) {
            MessengerUtil.sendTyping(.8, e);
            e.getTextChannel().sendMessage("Nickname changed! :white_check_mark:");
        } else
            e.getTextChannel().sendMessage(noPermMessage());
    }
}
