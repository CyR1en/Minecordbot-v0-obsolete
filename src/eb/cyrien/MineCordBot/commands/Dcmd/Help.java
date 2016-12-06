package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.utils.MessengerUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Map;
import java.util.TreeMap;

public class Help extends Command {

    private final String HELP = Main.getInstance().getBotConfig().COMMAND_EXECUTOR + "help [command]";
    private final String DESCRIPTION = "List all commands.";

    public Help(Main instance) {
        super(instance);
        setUsage(HELP);
        setDescription(DESCRIPTION);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        Map<String, Command> map = new TreeMap<>(instance.getCommands());
        if(args.length == 1) {
            if(!map.containsKey(args[0]))
                e.getTextChannel().sendMessage("`There's no such " + args[0] + " command.`").queue();
            else {
                Command cmd = map.get(args[0]);
                e.getTextChannel().sendMessage("_help for " + args[0] + "_\n" + "**Usage: **" + "``" + cmd.getUsage()
                        + "``" + "\n**Description: **" + "``" + cmd.getDescription() + "``").queue();
            }
        } else if (args.length == 0 || args == null) {
            String cmdExec = instance.getBotConfig().COMMAND_EXECUTOR;
            String out = "```\n";
            for (Map.Entry<String, Command> entry : map.entrySet())
                out += cmdExec + entry.getKey() + " - " + entry.getValue().getDescription() + "\n";
            out += "```";
            e.getTextChannel().sendMessage(out).queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        return;
    }
}
