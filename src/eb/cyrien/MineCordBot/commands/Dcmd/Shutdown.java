package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.entity.BasicMathSolver;
import eb.cyrien.MineCordBot.entity.Messenger;
import eb.cyrien.MineCordBot.utils.CommandListener;
import net.dv8tion.jda.JDABuilder;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import javax.security.auth.login.LoginException;
import java.util.Timer;
import java.util.TimerTask;

public class Shutdown extends Command {

    private Timer timer;
    private final String HELP = Main.botConfig.COMMAND_EXECUTOR + "shutdown";
    private final String DESCRIPTION = "Shutdown the bot";

    public Shutdown() {
        setUsage(HELP);
        setDescription(DESCRIPTION);
        timer = new Timer();
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        String authID = e.getAuthor().getId();
        if (!authID.trim().toLowerCase().equalsIgnoreCase(Main.botConfig.OWNER_ID.trim().toLowerCase()))
            return false;
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        sendTyping(0.2, e);
        e.getTextChannel().sendMessage(":wave:");
        e.getJDA().shutdown();
        //timer.schedule(new Restart(), 3000);
    }


    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        return;
    }

    /*
    class Restart extends TimerTask {

        @Override
        public void run() {
            try {
                Main.jda = new JDABuilder()
                        .addListener(new BasicMathSolver())
                        .addListener(new Messenger())
                        .addListener(new CommandListener())
                        .setBotToken(Main.botConfig.BOT_TOKEN).buildBlocking();
                Main.jda.setAutoReconnect(true);
            } catch (LoginException e) {
                System.err.println("Could not log in");
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer.cancel();
        }
    }
    */
}
