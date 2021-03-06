package eb.cyrien.MineCordBot.utils;

import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

    private Main instance;

    public CommandListener(Main instance) {
        this.instance = instance;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        boolean execHead = event.getMessage().getContent().startsWith(instance.getBotConfig().COMMAND_EXECUTOR);
        boolean notSelf = event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId();
        if(execHead && notSelf)
            instance.handleCommand(instance.getParser().parse(event.getMessage().getContent(), event));
    }

    @Override
    public void onReady(ReadyEvent event) {
        instance.getLogger().info("Logged in as " + event.getJDA().getSelfUser().getName());
    }
}
