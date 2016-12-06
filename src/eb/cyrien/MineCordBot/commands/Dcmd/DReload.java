package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.utils.BotConfig;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;

public class DReload extends Command {

    private final String HELP = BotConfig.COMMAND_EXECUTOR + "reload";
    private final String DESCRIPTION = " Reload BotConfig.yml";

    public DReload (Main instance) {
        super(instance);
        setUsage(HELP);
        setDescription(DESCRIPTION);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        if(!hasPermission(e.getAuthor().getId()))
            if(!e.getAuthor().getId().trim().equals(BotConfig.OWNER_ID.trim()))
                return false;
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        e.getTextChannel().sendTyping();
        try {
            instance.getPluginFile().save();
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }
        instance.getBotConfig().reload();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        if(success)
            e.getTextChannel().sendMessage(":white_check_mark:").queue();
        else
            e.getTextChannel().sendMessage(noPermMessage()).queue();
    }

}
