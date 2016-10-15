package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.Timer;
import java.util.TimerTask;

public class ListPlayer extends Command {
    public static boolean updating;

    private final String HELP = Main.botConfig.COMMAND_EXECUTOR + "ls \n + ls <update:(true or false)> \n + ls <update:true> <update interval(seconds)>";
    private final String DESCRIPTION = "List all players currently online in your minecraft server";

    private static String messageID;
    private static String textChannelID;
    private static MessageReceivedEvent mREvent;

    public ListPlayer() {
        setUsage(HELP);
        setDescription(DESCRIPTION);

    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        String s = generateList();
        if (args.length == 0 || args == null) {
            sendTyping(.3, e);
            sendMessageToDiscord(s, e);
        } else if (args.length > 2) {
            sendMessageToDiscord(invalidArgsMessage(), e);
        } else {
            if (args[0].equalsIgnoreCase("update:true")) {
                updating = true;
                textChannelID = e.getTextChannel().getId();
                messageID = e.getTextChannel().sendMessage(s).getId();
                mREvent = e;
                System.out.println("Done enabling update ls feature");
            } else if (args[0].equalsIgnoreCase("update:false")) {
                updating = false;
                sendMessageToDiscord(successMessage(), e);
            } else
                sendMessageToDiscord(invalidArgsMessage(), e);
        }
    }


    public static void updateList() {
        mREvent.getJDA().getTextChannelById(textChannelID).getMessageById(messageID).updateMessage(generateList());
    }

    public static void updateList(double delay) {
        long time = (long) (delay * 1000);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateList();
                timer.cancel();
            }
        }, time);
    }

    private static String generateList() {
        String blockFix = "```";
        String updatingLabel = "";
        if(updating)
            updatingLabel = ":repeat:";
        int i = 1;
        String s = "Online Players in " + Bukkit.getServer().getName() + " " + updatingLabel + blockFix + "\n";
        if (Bukkit.getServer().getOnlinePlayers().size() == 0 || Bukkit.getServer().getOnlinePlayers() == null)
            s += "There are no players online. ";
        else
            for (Player p : Bukkit.getServer().getOnlinePlayers())
                s += (i++ + ". " + p.getName() + "\n");
        s += blockFix;
        return s;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        return;
    }
}
