package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.utils.MessengerUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Shutdown extends Command {
    private final String HELP = Main.getInstance().getBotConfig().COMMAND_EXECUTOR + "shutdown";
    private final String DESCRIPTION = "Shutdown the bot";

    public Shutdown(Main instance) {
        super(instance);
        setUsage(HELP);
        setDescription(DESCRIPTION);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        String authID = e.getAuthor().getId();
        if (!authID.trim().toLowerCase().equalsIgnoreCase(instance.getBotConfig().OWNER_ID.trim().toLowerCase()))
            return false;
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        MessengerUtil.sendTyping(0.2, e);
        e.getTextChannel().sendMessage(":wave:").queue();
        e.getJDA().shutdown();
    }


    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        return;
    }
}
