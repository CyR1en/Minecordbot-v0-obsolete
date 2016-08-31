package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.utils.AvatarUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SetAvatar extends Command {

    private final String HELP = Main.botConfig.COMMAND_EXECUTOR + "setavatar <Image URL>";
    private final String DESCRIPTION = "Change the bot's Avatar";

    public SetAvatar() {
        setUsage(HELP);
        setDescription(DESCRIPTION);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        e.getTextChannel().sendTyping();
        String authID = e.getAuthor().getId();
        if (!hasPermission(authID))
            if (!authID.trim().toLowerCase().equalsIgnoreCase(Main.botConfig.OWNER_ID.trim().toLowerCase()))
                return false;
        try {
            System.out.println(args[0]);
            URL url = new URL(args[0]);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            e.getJDA().getAccountManager().setAvatar(AvatarUtil.getAvatar(in));
            e.getJDA().getAccountManager().update();
            in.close();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            e.getTextChannel().sendMessage("\'MalformedURLException: please check if you entered a correct URL\'");
            Main.log.warning("MalformedURLException");
            return false;
        } catch (IOException e1) {
            e1.printStackTrace();
            Main.log.warning("IOException");
            return false;
        }
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        e.getTextChannel().sendTyping();
        e.getTextChannel().sendMessage("Setting Avatar");
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if (success)
            e.getTextChannel().sendMessage("Success :ok_hand:");
        else
            e.getTextChannel().sendMessage(noPermMessage());
    }

    @Override
    public String noPermMessage() {
        return "```css\nSomething went wrong... Check your permissions or your URL\n```";
    }
}
