package eb.cyrien.MineCordBot.commands.Dcmd;


import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.utils.MessengerUtil;
import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SetAvatar extends Command {

    private final String HELP = Main.getInstance().getBotConfig().COMMAND_EXECUTOR + "setavatar <Image URL>";
    private final String DESCRIPTION = "Change the bot's Avatar";

    public SetAvatar(Main instance) {
        super(instance);
        setUsage(HELP);
        setDescription(DESCRIPTION);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        e.getTextChannel().sendTyping();
        String authID = e.getAuthor().getId();
        if (!hasPermission(authID))
            if (!authID.trim().toLowerCase().equalsIgnoreCase(instance.getBotConfig().OWNER_ID.trim().toLowerCase()))
                return false;
        try {
            System.out.println(args[0]);
            URL url = new URL(args[0]);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            e.getJDA().getSelfUser().getManager().setAvatar(Icon.from(in)).queue( consumer ->
                    MessengerUtil.sendMessageToDiscord(e, "Success :ok_hand:")
            );
            in.close();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            e.getTextChannel().sendMessage("\'MalformedURLException: please check if you entered a correct URL\'").queue();
            instance.getLogger().warning("MalformedURLException");
            return false;
        } catch (IOException e1) {
            e1.printStackTrace();
            instance.getLogger().warning("IOException");
            return false;
        }
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if (!success)
            e.getTextChannel().sendMessage(noPermMessage()).queue();
    }

    @Override
    public String noPermMessage() {
        return "```css\nSomething went wrong... Check your permissions or your URL\n```";
    }
}
