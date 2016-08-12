package eb.cyrien.CFABot.commands.Dcmd;

import eb.cyrien.CFABot.Command;
import eb.cyrien.CFABot.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.managers.AccountManager;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SynTest implements Command{

    public static final String HELP = "USAGE: " + Main.botConfig.COMMAND_EXECUTOR + "syntest <test string>";
    private static final String CODE_BLOCK = "```";

    private static ArrayList<String> syns;

    public SynTest() {
        syns = new ArrayList<>();
        syns.add("go");
        syns.add("cofeescript");
        syns.add("bash");
        syns.add("ini");
        syns.add("java");
        syns.add("pytho");
        syns.add("rust");
        syns.add("handlebars");
        syns.add("swift");
        syns.add("prolog");
        syns.add("typescript");
        syns.add("elm");
        syns.add("xml");
        syns.add("javascript");
        syns.add("cs");
        syns.add("http");
        syns.add("cpp");
        syns.add("sql");
        syns.add("clojure");
        syns.add("objectivec");
        syns.add("css");
        syns.add("ruby");
        syns.add("makefile");
    }
    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        if(!Main.hasPermission(e.getAuthor().getId(), e))
            return false;
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        e.getJDA().getAccountManager().setGame("Syntax Test");
        e.getJDA().getAccountManager().update();
        String out = "";
        String msg = Main.concatenateArgs(0, args);
        for(String s : syns) {
            out += CODE_BLOCK + s + "\n" + msg + "\n" + CODE_BLOCK + "\n - " + s;
            e.getTextChannel().sendMessage(out);
            out = "";
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if(success) {
            e.getTextChannel().sendMessage(":ok_hand:");
            e.getJDA().getAccountManager().setGame(null);
            e.getJDA().getAccountManager().update();
        } else
            e.getTextChannel().sendMessage("`You don't have permission`");
    }
}
