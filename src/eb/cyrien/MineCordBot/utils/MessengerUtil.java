package eb.cyrien.MineCordBot.utils;

import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class MessengerUtil {

    public static String concatenateArgs(int startIndex, String[] args) {
        String concatenatedString = "";
        for (int i = startIndex; i < args.length; i++)
            concatenatedString += " " + args[i];
        concatenatedString.trim();
        return concatenatedString;
    }

    public static void sendTyping(double sec, MessageReceivedEvent e) {
        long startTime = System.currentTimeMillis();
        long diff = System.currentTimeMillis() - startTime;
        while (diff < (sec * 1000)) {
            e.getTextChannel().sendTyping();
            diff = System.currentTimeMillis() - startTime;
        }
    }

    public static void sendMessageToDiscord(String msg) {
        for (String s : Main.getInstance().getBotConfig().BINDED_CHANNELS)
            Main.getInstance().jda.getTextChannelById(s).sendMessage(msg);
    }

    public static void sendMessageToDiscord(String textChannelID, String msg) {
        Main.getInstance().jda.getTextChannelById(textChannelID).sendMessage(msg);
    }

    public static void sendPrivateMessageToUser(String userID, String msg) {
        Main.getInstance().jda.getUserById(userID).getPrivateChannel().sendMessage(msg);
    }

    public static void sendPrivateMessageToUser(MessageReceivedEvent e, String msg) {
        sendPrivateMessageToUser(e.getAuthor().getId(), msg);
    }

    public static void sendMessageToDiscord(MessageReceivedEvent e, String msg) {
        e.getTextChannel().sendMessage(msg);
    }
}
