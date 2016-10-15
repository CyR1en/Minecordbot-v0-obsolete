package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Timer;

public class Shutdown extends Command {

    private Timer timer;
    private final String HELP = Main.botConfig.COMMAND_EXECUTOR + "shutdown";
    private final String DESCRIPTION = "Shutdown the bot";

    public Shutdown() {
        setUsage(HELP);
        setDescription(DESCRIPTION);
        timer = new Timer();
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        String authID = e.getAuthor().getId();
        if (!authID.trim().toLowerCase().equalsIgnoreCase(Main.botConfig.OWNER_ID.trim().toLowerCase()))
            return false;
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        sendTyping(0.2, e);
        e.getTextChannel().sendMessage(":wave:");
        e.getJDA().shutdown();
    }


    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        return;
    }
}
