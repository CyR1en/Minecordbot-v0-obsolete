package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Timer;
import java.util.TimerTask;

public class ListPlayer extends Command {

    private final String HELP = Main.botConfig.COMMAND_EXECUTOR + "ls \n + ls <update:(true or false)> \n + ls <update:true> <update interval(seconds)>";
    private final String DESCRIPTION = "List all players currently online in your minecraft server";

    private Timer timer;

    private boolean updating;

    public ListPlayer() {
        setUsage(HELP);
        setDescription(DESCRIPTION);
        timer = new Timer();
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        String s = generateList(e);
        if (args.length == 0 || args == null) {
            sendTyping(.3, e);
            sendMessageToDiscord(s, e);
        } else if (args.length > 2) {
            boolean argumentIsNumber = NumberUtils.isNumber(args[1]);
            if (args[0].equalsIgnoreCase("update:true") && argumentIsNumber) {
                String mID = e.getTextChannel().sendMessage(s).getId();
                String tID = e.getTextChannel().getId();
                Timer timer = new Timer();
                updating = true;
                timer.schedule(new Update(mID, tID, e, Long.parseLong(args[1]) * 1000), 0);
            } else
                sendMessageToDiscord(invalidArgsMessage(), e);
        } else {
            if (args[0].equalsIgnoreCase("update:true")) {
                String mID = e.getTextChannel().sendMessage(s).getId();
                String tID = e.getTextChannel().getId();
                updating = true;
                timer.schedule(new Update(mID, tID, e, 5000 ), 0);
            } else if (args[0].equalsIgnoreCase("update:false")) {
                updating = false;
                sendMessageToDiscord(successMessage(), e);
            }
            else
                sendMessageToDiscord(invalidArgsMessage(), e);
        }
    }

    private String generateList(MessageReceivedEvent e) {
        String blockFix = "```";
        int i = 1;
        String s = "Online Players in " + Bukkit.getServer().getName() + blockFix + "\n";
        if (Bukkit.getServer().getOnlinePlayers().size() == 0 || Bukkit.getServer().getOnlinePlayers() == null)
            s += "There are no players online. ";
        else
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                s += (i++ + ". " + p.getName() + "\n");
            }
        s += blockFix;
        return s;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        return;
    }

    public class Update extends TimerTask {

        private String mID;
        private String tID;
        private MessageReceivedEvent mE;
        private long delay;

        public Update(String mID, String tID, MessageReceivedEvent mE, long delay) {
            this.mID = mID;
            this.tID = tID;
            this.mE = mE;
            this.delay = delay;
        }

        @Override
        public void run() {
            if (updating == false) {
                return;
            }
            else {
                mE.getJDA().getTextChannelById(tID).getMessageById(mID).updateMessage(generateList(mE));
                timer.schedule(new Update(mID, tID, mE, delay), delay);
            }
        }
    }
}
