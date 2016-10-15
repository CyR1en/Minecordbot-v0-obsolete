package eb.cyrien.MineCordBot.commands.Dcmd;

import eb.cyrien.MineCordBot.Command;
import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.entity.Messenger;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.bukkit.Bukkit;

/**
 * Created by Ethan on 8/30/2016.
 */
public class Info extends Command {

    private static final String USAGE = Main.botConfig.COMMAND_EXECUTOR + "info";
    private static final String DESCRIPTION = "Know Information about MineCordBot";

    public Info() {
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
        String botNick = e.getJDA().getGuildById(e.getGuild().getId()).getNicknameForUser(e.getJDA().getUserById(Main.botConfig.BOT_ID));
        String bindedChannel = e.getJDA().getTextChannelById(Main.botConfig.BINDED_CHANNEL).getName();
        int textChannels = e.getJDA().getTextChannels().size() - 1;
        int voiceChannels = e.getJDA().getVoiceChannels().size();
        String version = Bukkit.getServer().getPluginManager().getPlugin("MineCordBot").getDescription().getVersion();
        String uptime = Main.getUptime();
        if(botNick == null || botNick == "")
            botNick = botName + " does not have a nickname";
        sendTyping(.3, e);
        e.getTextChannel().sendMessage("**MineCordBot** " + " by Cyrien \n" +
                                        "_Bridge Minecraft and Discord._" +
                                        "\n~~-------~~ Bot Info ~~-------~~\n" +
                                        "**Username:** ``" + botName + "``\n" +
                                        "**Bot Nickname:** ``" + botNick + "``\n" +
                                        "**Binded to:** ``" + bindedChannel + "``\n" +
                                        "**Text Channels:** ``" + textChannels + "``\n" +
                                        "**Voice Channels:** ``" + voiceChannels + "``\n" +
                                        "**Uptime:** ``" + uptime +"``\n" +
                                        "**version:** ``" + version + "``");
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        return;
    }
}
