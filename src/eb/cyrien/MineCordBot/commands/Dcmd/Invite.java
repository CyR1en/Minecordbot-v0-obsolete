package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.utils.MessengerUtil;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class Invite extends Command {


    private final String HELP = Main.getInstance().getBotConfig().COMMAND_EXECUTOR + "invite";
    private final String DESCRIPTION = "Get the bot's Oauth2(link to add bot to a server)";

    public Invite(Main instance) {
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
        MessengerUtil.sendMessageToDiscord("https://discordapp.com/oauth2/authorize?client_id=" + instance.getBotConfig().BOT_ID + "&scope=bot&permissions=0", e);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        return;

    }
}
