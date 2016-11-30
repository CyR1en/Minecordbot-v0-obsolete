package eb.cyrien.MineCordBot;

import net.dv8tion.jda.events.ReadyEvent;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        boolean execHead = event.getMessage().getContent().startsWith(Main.botConfig.COMMAND_EXECUTOR);
        boolean notSelf = event.getMessage().getAuthor().getId() != event.getJDA().getSelfInfo().getId();
        if(execHead && notSelf)
            Main.handleCommand(Main.parser.parse(event.getMessage().getContent(), event));
    }

    @Override
    public void onReady(ReadyEvent event) {
        //Main.log("status", "Logged in as" + event.getJDA().getSelfInfo().getUsername());
    }
}
