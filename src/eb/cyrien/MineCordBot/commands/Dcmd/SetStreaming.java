package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.utils.MessengerUtil;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class SetStreaming extends Command {

    private final String HELP = Main.getInstance().getBotConfig().COMMAND_EXECUTOR + "setstreaming <title> <streaming link>";
    private final String DESCRIPTION = "set what the bot is streaming";

    public SetStreaming(Main instance) {
        super(instance);
        setUsage(HELP);
        setDescription(DESCRIPTION);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        if (!hasPermission(e.getAuthor().getId()))
            if (!e.getAuthor().getId().equals(instance.getBotConfig().OWNER_ID))
                return false;
        if (args.length > 2 || args.length == 0 || args.length < 2 || args == null)
            return false;
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        e.getJDA().getAccountManager().setStreaming(args[0], args[1]);
        e.getJDA().getAccountManager().update();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if (success) {
            MessengerUtil.sendTyping(1, e);
            e.getTextChannel().sendMessage("I am now streaming.");
        } else
            e.getTextChannel().sendMessage(noPermMessage());
    }

    @Override
    public String noPermMessage() {
        return "```css\n[Check your arguments or you do not have permission]\n```";
    }
}
