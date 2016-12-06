package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.utils.BotConfig;
import eb.cyrien.MineCordBot.utils.MessengerUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;

public class Prefix extends Command {

    private final String HELP = BotConfig.COMMAND_EXECUTOR + "prefix <discord:minecraft> [replace] [new prefix]";
    private final String DESCRIPTION = "See what the prefix for discord or minecraft and change them if you wish to do so.";

    public Prefix(Main instance) {
        super(instance);
        setUsage(HELP);
        setDescription(DESCRIPTION);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        if (!hasPermission(e.getAuthor().getId()))
            if (!e.getAuthor().getId().equals(instance.getBotConfig().OWNER_ID))
                return false;
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        if (args.length == 1) {
            switch (args[0]) {
                case "discord":
                    MessengerUtil.sendMessageToDiscord(e, "`" + instance.getBotConfig().MESSAGE_PREFIX_DISCORD + "`");
                    break;
                case "minecraft":
                    MessengerUtil.sendMessageToDiscord(e, "`" + instance.getBotConfig().MESSAGE_PREFIX_MINECRAFT + "`");
                    break;
            }
        } else if (args.length == 3) {
            if (args[1].equalsIgnoreCase("replace")) {
                changeExecutor(args[0], args[2], e);
            }
            else
                MessengerUtil.sendMessageToDiscord(e, invalidArgsMessage());
        } else {
            MessengerUtil.sendMessageToDiscord(e, invalidArgsMessage());
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if (!success)
            e.getTextChannel().sendMessage(noPermMessage()).queue();
    }

    public void changeExecutor(String s, String nPrefix, MessageReceivedEvent e) {
        switch (s) {
            case "discord":
                instance.getPluginFile().getConfig().set("message_prefix_discord", nPrefix);
                try {
                    instance.getPluginFile().save();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                instance.getBotConfig().reload();
                MessengerUtil.sendMessageToDiscord(e, "`" + s + " prefix changed to: " + nPrefix + "`");
                instance.getMCBLogger().info(s + " prefix changed to: " + nPrefix);
                break;
            case "minecraft":
                instance.getPluginFile().getConfig().set("message_prefix_minecraft", nPrefix);
                try {
                    instance.getPluginFile().save();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                instance.getBotConfig().reload();
                MessengerUtil.sendMessageToDiscord(e, "`" + s + " prefix changed to: " + nPrefix + "`");
                instance.getMCBLogger().info(s + " prefix changed to: " + nPrefix);
                break;
        }
    }
}
