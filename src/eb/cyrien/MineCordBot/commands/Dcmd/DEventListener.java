package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.events.ReadyEvent;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

public class DEventListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        boolean validChannel = event.getTextChannel().getId().equals(Main.botConfig.BINDED_CHANNEL);
        boolean notSelf = event.getMessage().getAuthor().getId() != event.getJDA().getSelfInfo().getId();
        if(validChannel && notSelf)
            Main.relayToMinecraft(event);
    }

    @Override
    public void onReady(ReadyEvent event) {
        //Main.log("status", "Logged in as" + event.getJDA().getSelfInfo().getUsername());
    }
}
