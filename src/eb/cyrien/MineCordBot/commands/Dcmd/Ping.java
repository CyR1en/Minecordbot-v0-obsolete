package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.time.temporal.ChronoUnit;

public class Ping extends Command {

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
        e.getChannel().sendMessage("Pong: ...").queue(ms -> {
            if (ms != null) {
                ms.editMessage("Pong: " + e.getMessage().getCreationTime().until(ms.getCreationTime(), ChronoUnit.MILLIS) + "ms").queue();
            }
        });
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        return;
    }

}
