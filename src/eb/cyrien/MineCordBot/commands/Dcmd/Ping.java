package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class Ping extends Command{

    private final String HELP = Main.getInstance().getBotConfig().COMMAND_EXECUTOR + "ping";
    private final String DESCRIPTION = "Test bot's responsiveness";

    public Ping(Main instance) {
        super(instance);
        setUsage(HELP);
        setDescription(DESCRIPTION);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        long startTime = System.currentTimeMillis();
        String mID = e.getTextChannel().sendMessage("Pong.").getId();
        int diff = (int) (System.currentTimeMillis() - startTime);
        e.getTextChannel().getMessageById(mID).updateMessage("Pong. `" + diff +"ms`");
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        return;
    }
}
