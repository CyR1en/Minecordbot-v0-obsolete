package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.utils.MessengerUtil;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.io.IOException;

public class SetExecutor extends Command {

    private final String HELP = Main.getInstance().getBotConfig().COMMAND_EXECUTOR + "setexecutor <new executor>";
    private final String DESCRIPTION = "Change the bot's command executor";

    public SetExecutor(Main instance) {
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
        if (args == null || args.length == 0 || args.length > 1) {
            MessengerUtil.sendMessageToDiscord(e.getTextChannel().getId(), invalidArgsMessage());
            return;
        }
        changeExecutor(args[0]);
        MessengerUtil.sendMessageToDiscord(e.getTextChannel().getId(), "Command executor changed to `" + args[0] + "`");
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if (!success)
            e.getTextChannel().sendMessage(noPermMessage());
    }

    private void changeExecutor(String executor) {
        instance.getPluginFile().getConfig().set("command_executor", executor);
        try {
            instance.getPluginFile().save();
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }
        instance.getBotConfig().reload();
        instance.getMCBLogger().info("Command executor changed to " + executor);
    }
}
