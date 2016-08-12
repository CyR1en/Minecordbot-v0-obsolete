package eb.cyrien.CFABot.commands.Mcmd;

import eb.cyrien.CFABot.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MEventListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        String p = e.getPlayer().getName();
        Main.jda.getTextChannelById(Main.botConfig.BINDED_CHANNEL).sendMessage("```css\n" + p + " left the game```");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        String p = e.getPlayer().getName();
        Main.jda.getTextChannelById(Main.botConfig.BINDED_CHANNEL).sendMessage("```css\n" + p + " joined the game```");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        String msg = e.getDeathMessage();
        Main.jda.getTextChannelById(Main.botConfig.BINDED_CHANNEL).sendMessage("```css\n" + "["+ msg + "]\n" + "```");
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Main.relayToDiscord(event);
    }
}
