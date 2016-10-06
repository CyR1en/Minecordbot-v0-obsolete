package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.entity.Messenger;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Map;
import java.util.TreeMap;

public class Help extends Command {

    private final String HELP = Main.botConfig.COMMAND_EXECUTOR + "help <command>";
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
        Map<String, Command> map = new TreeMap<>(Main.commands);
        if(args.length == 1) {
            if(!map.containsKey(args[0]))
                e.getTextChannel().sendMessage("`There's no such " + args[0] + " command.`");
            else {
                Command cmd = map.get(args[0]);
                e.getTextChannel().sendMessage("_help for " + args[0] + "_\n" + "**Usage: **" + "``" + cmd.getUsage()
                        + "``" + "\n**Description: **" + "``" + cmd.getDescription() + "``");
            }
        } else if (args.length == 0 || args == null) {
            String cmdExec = Main.botConfig.COMMAND_EXECUTOR;
            String out = "```\n";
            for (Map.Entry<String, Command> entry : map.entrySet())
                out += cmdExec + entry.getKey() + " - " + entry.getValue().getDescription() + "\n";
            out += "```";
            Messenger.sendTyping(1.3, e);
            e.getTextChannel().sendMessage(out);
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        return;
    }
}
