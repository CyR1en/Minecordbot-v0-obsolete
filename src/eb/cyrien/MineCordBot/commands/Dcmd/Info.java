package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.utils.MessengerUtil;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public class Info extends Command {

    private static final String USAGE = Main.getInstance().getBotConfig().COMMAND_EXECUTOR + "info";
    private static final String DESCRIPTION = "Know Information about MineCordBot";

    public Info(Main instance) {
        super(instance);
        setUsage(USAGE);
        setDescription(DESCRIPTION);
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        String botName = e.getJDA().getSelfInfo().getUsername();
        String botNick = e.getJDA().getGuildById(e.getGuild().getId()).getNicknameForUser(e.getJDA().getUserById(instance.getBotConfig().BOT_ID));
        String bindedChannel = new String();
        ArrayList<String> channels = instance.getBotConfig().BINDED_CHANNELS;
        for (int i = 0; i < channels.size(); i++)
            if (i == channels.size() - 1)
                bindedChannel += e.getJDA().getTextChannelById(channels.get(i)).getName();
            else
                bindedChannel += e.getJDA().getTextChannelById(channels.get(i)).getName() + ", ";
        int textChannels = e.getJDA().getTextChannels().size() - 1;
        int voiceChannels = e.getJDA().getVoiceChannels().size();
        String version = Bukkit.getServer().getPluginManager().getPlugin("MineCordBot").getDescription().getVersion();
        String uptime = instance.getUptime();
        if (botNick == null || botNick == "")
            botNick = botName + " does not have a nickname";
        MessengerUtil.sendTyping(.3, e);
        e.getTextChannel().sendMessage("**MineCordBot** " + " by Cyrien \n" +
                "_Bridge Minecraft and Discord._" +
                "\n~~-------~~ Bot Info ~~-------~~\n" +
                "**Username:** ``" + botName + "``\n" +
                "**Bot Nickname:** ``" + botNick + "``\n" +
                "**Binded to:** ``" + bindedChannel + "``\n" +
                "**Text Channels:** ``" + textChannels + "``\n" +
                "**Voice Channels:** ``" + voiceChannels + "``\n" +
                "**Uptime:** ``" + uptime + "``\n" +
                "**version:** ``" + version + "``");
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        return;
    }
}
