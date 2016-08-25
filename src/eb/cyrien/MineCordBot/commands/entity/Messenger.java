package eb.cyrien.MineCordBot.commands.entity;

import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.utils.BotConfig;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Messenger {

    private JDA jda;

    public Messenger(JDA jda) {
        this.jda = jda;
    }

    public void relayToMinecraft(MessageReceivedEvent e) {
        String author = e.getAuthorNick();
        if(author == null)
            author = e.getAuthorName();
        String msg = e.getMessage().getContent();
        for(Player p : Bukkit.getServer().getOnlinePlayers())
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.botConfig.MESSAGE_PREFIX_MINECRAFT + "&r"+ " ~ [&9&l" + author + "&r]: " + ChatColor.WHITE + msg ));
        Main.log.info(author+ " > mc: " + msg );
    }

    public void relayToDiscord(AsyncPlayerChatEvent e) {
        String author = e.getPlayer().getName();
        String msg = e.getMessage();
        //jda.getAccountManager().setNickname(jda.getTextChannelById(botConfig.BINDED_CHANNEL).getGuild(), "MC ~ " + author);
        jda.getTextChannelById(Main.botConfig.BINDED_CHANNEL).sendMessage("**" + Main.botConfig.MESSAGE_PREFIX_DISCORD + " ~ " + "[" + author + "]:**  " + msg);
        Main.log.info(author + " > discord: " + msg);
        //jda.getAccountManager().setNickname(jda.getTextChannelById(botConfig.BINDED_CHANNEL).getGuild(), jda.getSelfInfo().getUsername());
    }
}
