package eb.cyrien.MineCordBot.entity;

import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.commands.Dcmd.ListPlayer;
import eb.cyrien.MineCordBot.utils.BotConfig;
import eb.cyrien.MineCordBot.utils.MessengerUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
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
        String author = e.getMember().getNickname();
        if (author == null)
            author = e.getAuthor().getName();
        String msg = e.getMessage().getContent();
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getBotConfig().MESSAGE_PREFIX_MINECRAFT + "&r" + " ~ [&9&l" + author + "&r]: " + ChatColor.WHITE + msg));
        }
        instance.getLogger().info(author + " > mc: " + msg);
    }

    public void relayToDiscord(Event e, String msg) {
        String author = ((PlayerEvent) e).getPlayer().getName();
        for (String s : instance.getBotConfig().BINDED_CHANNELS)
            instance.getJda().getTextChannelById(s).sendMessage("**" + instance.getBotConfig().MESSAGE_PREFIX_DISCORD + " ~ " + "[" + author + "]:**  " + msg).queue();
        instance.getLogger().info(author + " > discord: " + msg);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        boolean validChannel = isValidTextChannel(event.getTextChannel().getId());
        boolean notSelf = event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId();
        if (validChannel && notSelf)
            relayToMinecraft(event);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        BotConfig bc = instance.getBotConfig();
        if (ListPlayer.updating)
            ListPlayer.updateList(0.5);
        if ((p.hasPermission("minecordbot.incognito.leave") && bc.HIDE_WITH_PERM == true) || bc.LEAVE_BC == false);
        MessengerUtil.sendMessageToDiscord("```css\n" + p.getName() + " left the game```");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        BotConfig bc = instance.getBotConfig();
        if (ListPlayer.updating)
            ListPlayer.updateList();
        if ((p.hasPermission("minecordbot.incognito.join") && bc.HIDE_WITH_PERM == true) || bc.JOINEVENT_BC == false);
        MessengerUtil.sendMessageToDiscord("```css\n" + p.getName() + " joined the game```");
    }

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent e) {
        if (instance.getBotConfig().DEATHEVENT_BC == false);
        Timer timer = new Timer(); //delay
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String msg = e.getDeathMessage();
                msg.trim();
                if (msg.startsWith("["))
                    msg.substring(1);
                if (msg.endsWith("]"))
                    msg.substring(0, msg.length() - 2);
                MessengerUtil.sendMessageToDiscord("```css\n" + "[" + ChatColor.stripColor(msg) + "]\n" + "```");
            }
        }, 200);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        relayToDiscord(e, e.getMessage());
    }

    private boolean isValidTextChannel(String textChannelID) {
        for (String s : instance.getBotConfig().BINDED_CHANNELS)
            if (s.equalsIgnoreCase(textChannelID))
                return true;
        return false;
    }

}
