package eb.cyrien.MineCordBot.entity;

import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.commands.Dcmd.ListPlayer;
import eb.cyrien.MineCordBot.utils.MessengerUtil;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Timer;
import java.util.TimerTask;

public class Messenger extends ListenerAdapter implements Listener {

    protected Main instance;

    public Messenger(Main instance) {
        this.instance = instance;
    }

    public void relayToMinecraft(MessageReceivedEvent e) {
        String author = e.getAuthorNick();
        if(author == null)
            author = e.getAuthorName();
        String msg = e.getMessage().getContent();
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getBotConfig().MESSAGE_PREFIX_MINECRAFT + "&r" + " ~ [&9&l" + author + "&r]: " + ChatColor.WHITE + msg));
        }
        instance.getLogger().info(author+ " > mc: " + msg );
    }

    public void relayToDiscord(Event e, String msg) {
        String author = ((PlayerEvent)e).getPlayer().getName();
        for(String s : instance.getBotConfig().BINDED_CHANNELS)
            instance.jda.getTextChannelById(s).sendMessage("**" + instance.getBotConfig().MESSAGE_PREFIX_DISCORD + " ~ " + "[" + author + "]:**  " + msg);
        instance.getLogger().info(author + " > discord: " + msg);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        boolean validChannel = event.getTextChannel().getId().equals(instance.getBotConfig().BINDED_CHANNELS);
        boolean notSelf = event.getMessage().getAuthor().getId() != event.getJDA().getSelfInfo().getId();
        boolean notCommand = !event.getMessage().toString().startsWith(instance.getBotConfig().COMMAND_EXECUTOR);
        if(validChannel && notSelf && notCommand)
            relayToMinecraft(event);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        String p = e.getPlayer().getName();
        MessengerUtil.sendMessageToDiscord("```css\n" + p + " left the game```");
        if(ListPlayer.updating)
            ListPlayer.updateList(0.5);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        String p = e.getPlayer().getName();
        MessengerUtil.sendMessageToDiscord("```css\n" + p + " joined the game```");
        if(ListPlayer.updating)
            ListPlayer.updateList();
    }

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent e) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String msg = e.getDeathMessage();
                msg.trim();
                if(msg.startsWith("["))
                    msg.substring(1);
                if(msg.endsWith("]"))
                    msg.substring(0, msg.length() -2);
                MessengerUtil.sendMessageToDiscord("```css\n" + "["+ ChatColor.stripColor(msg) + "]\n" + "```");
            }
        }, 200);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        relayToDiscord(e, e.getMessage());
    }

}
