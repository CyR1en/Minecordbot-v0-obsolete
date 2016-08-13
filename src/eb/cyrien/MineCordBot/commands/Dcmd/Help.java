package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Map;

public class Help extends Command {

    private final String HELP = "USAGE: " + Main.botConfig.COMMAND_EXECUTOR + "help <command>";
    private final String DESCRIPTION = "List all commands.";

    public Help() {
        setUsage(HELP);
        setDescription(DESCRIPTION);
    }
    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        if(args.length == 1) {
            if(!Main.commands.containsKey(args[0]))
                e.getTextChannel().sendMessage("`There's no such " + args[0] + " command.`");
            else {
                Command cmd = Main.commands.get(args[0]);
                e.getTextChannel().sendMessage("```\n" + cmd.getUsage() +
                        "\nDescription: " + cmd.getDefinition() + "\n```");
            }
        } else if (args.length == 0 || args == null) {
            String cmdExec = Main.botConfig.COMMAND_EXECUTOR;
            String out = "```\n";
            for (Map.Entry<String, Command> entry : Main.commands.entrySet())
                out += cmdExec + entry.getKey() + "\n";
            out += "```";
            e.getTextChannel().sendMessage(out);
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        return;
    }
}
