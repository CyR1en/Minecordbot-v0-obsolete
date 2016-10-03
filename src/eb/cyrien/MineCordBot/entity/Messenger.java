package eb.cyrien.MineCordBot.entity;

import eb.cyrien.MineCordBot.Main;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_10_R1.command.CraftConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class Messenger extends ListenerAdapter implements Listener {

    public void relayToMinecraft(MessageReceivedEvent e) {
        String author = e.getAuthorNick();
        if(author == null)
            author = e.getAuthorName();
        String msg = e.getMessage().getContent();
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.botConfig.MESSAGE_PREFIX_MINECRAFT + "&r" + " ~ [&9&l" + author + "&r]: " + ChatColor.WHITE + msg));
        }
        Main.log.info(author+ " > mc: " + msg );
    }

    public void relayToDiscord(Event e, String msg) {
        String author = ((PlayerEvent)e).getPlayer().getName();
        Main.jda.getTextChannelById(Main.botConfig.BINDED_CHANNEL).sendMessage("**" + Main.botConfig.MESSAGE_PREFIX_DISCORD + " ~ " + "[" + author + "]:**  " + msg);
        Main.log.info(author + " > discord: " + msg);
    }

    public void sendMessageToDiscord(String msg) {
        Main.jda.getTextChannelById(Main.botConfig.BINDED_CHANNEL).sendMessage(msg);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        boolean validChannel = event.getTextChannel().getId().equals(Main.botConfig.BINDED_CHANNEL);
        boolean notSelf = event.getMessage().getAuthor().getId() != event.getJDA().getSelfInfo().getId();
        boolean notCommand = !event.getMessage().toString().startsWith(Main.botConfig.COMMAND_EXECUTOR);
        if(validChannel && notSelf && notCommand)
            relayToMinecraft(event);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        String p = e.getPlayer().getName();
        sendMessageToDiscord("```css\n" + p + " left the game```");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        String p = e.getPlayer().getName();
        sendMessageToDiscord("```css\n" + p + " joined the game```");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        String msg = e.getDeathMessage();
        sendMessageToDiscord("```css\n" + "["+ msg + "]\n" + "```");
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        relayToDiscord(e, e.getMessage());
    }
}
