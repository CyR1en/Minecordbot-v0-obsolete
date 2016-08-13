package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Map;

public class Help extends Command {

    public static final String HELP = "USAGE: " + Main.botConfig.COMMAND_EXECUTOR + "help";

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        String cmdExec = Main.botConfig.COMMAND_EXECUTOR;
        String out = "```\n";
        for(Map.Entry<String, Command> entry : Main.commands.entrySet())
            out += cmdExec + entry.getKey() + " - " + entry.getValue().help() +  "\n";
        out += "```";
        e.getTextChannel().sendMessage(out);
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
    }
}
