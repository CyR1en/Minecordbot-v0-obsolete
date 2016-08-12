package eb.cyrien.CFABot;

import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public abstract class Command {

    public abstract boolean called(String[] args, MessageReceivedEvent e);

    public abstract void action(String[] args, MessageReceivedEvent e);

    public abstract String help();

    public abstract void executed(boolean success, MessageReceivedEvent e);

    public String noPermMessage() {
        return "```css\n[You do not have permission]\n```";
    }

    public static boolean hasPermission(String userID) {
        ArrayList<String> wl = Main.botConfig.WHITELIST;
        for (String s : wl) {
            if(s.equals(userID))
                return true;
        }
        return false;
    }

}
