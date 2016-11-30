package eb.cyrien.MineCordBot.utils;

import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;


public class MessengerUtil {

    public static String concatenateArgs(int startIndex, String[] args) {
        String concatenatedString = "";
        for (int i = startIndex; i < args.length; i++)
            concatenatedString += " " + args[i];
        return concatenatedString.trim();
    }

    public static void sendTyping(double sec, MessageReceivedEvent e) {
        e.getTextChannel().sendTyping().queue();
    }

    public static void sendMessageToDiscord(String msg) {
        for (String s : Main.getInstance().getBotConfig().BINDED_CHANNELS)
            Main.getInstance().getJda().getTextChannelById(s).sendMessage(msg).queue();
    }

    public static void sendMessageToDiscord(String textChannelID, String msg) {
        Main.getInstance().getJda().getTextChannelById(textChannelID).sendMessage(msg).queue();
    }

    public static void sendPrivateMessageToUser(String userID, String msg) {
        Main.getInstance().getJda().getUserById(userID).getPrivateChannel().sendMessage(msg).queue();
    }

    public static void sendPrivateMessageToUser(MessageReceivedEvent e, String msg) {
        sendPrivateMessageToUser(e.getAuthor().getId(), msg);
    }

    public static void sendMessageToDiscord(MessageReceivedEvent e, String msg) {
        e.getTextChannel().sendMessage(msg).queue();
    }
}
