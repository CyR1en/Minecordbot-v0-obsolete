package eb.cyrien.CFABot;

import eb.cyrien.CFABot.commands.Dcmd.*;
import eb.cyrien.CFABot.commands.Mcmd.Dme;
import eb.cyrien.CFABot.commands.Mcmd.MEventListener;
import eb.cyrien.CFABot.configuration.PluginFile;
import eb.cyrien.CFABot.utils.BotConfig;
import eb.cyrien.CFABot.utils.CommandParser;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    public static JDA jda;
    public static final CommandParser parser = new CommandParser();

    public static HashMap<String, Command> commands = new HashMap<>();

    public static PluginFile config;
    public static BotConfig botConfig;

    public static Logger log;

    @Override
    public void onEnable() {
        //minecraft side initialization
        log = getLogger();
        config = new PluginFile(this, "BotConfig", true);
        botConfig = new BotConfig();
        this.getCommand("dme").setExecutor(new Dme(this));
        getServer().getPluginManager().registerEvents(new MEventListener(), this);

        //discord side initialization
        try {
            jda = new JDABuilder()
                    .addListener(new DEventListener())
                    .addListener(new CommandListener())
                    .setBotToken(botConfig.BOT_TOKEN).buildBlocking();
            jda.setAutoReconnect(true);
        } catch (LoginException e) {
            e.printStackTrace();
            System.err.println("Could not log in");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        commands.put("ping", new Ping());
        commands.put("setavatar", new SetAvatar());
        commands.put("ls", new ListPlayer());
        commands.put("setgame", new SetGame());
        commands.put("syntest", new SynTest());
    }

    @Override
    public void onDisable() {
        jda.shutdown();
    }

    public static void handleCommand(CommandParser.CommandContainer cmd) {
        System.out.println(cmd.invoke);
        if (commands.containsKey(cmd.invoke)) {
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);
            System.out.println(safe);
            if(safe) {
                commands.get(cmd.invoke).action(cmd.args, cmd.event);
                commands.get(cmd.invoke).executed(safe, cmd.event);
            } else
                commands.get(cmd.invoke).executed(safe, cmd.event);
        }
    }

    public static void relayToMinecraft(MessageReceivedEvent e) {
        String author = e.getAuthorNick();
        if(author == null)
            author = e.getAuthorName();
        String msg = e.getMessage().getContent();
        for(Player p : Bukkit.getServer().getOnlinePlayers())
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lDiscord&r~[&9&l" + author + "&r]: " + ChatColor.GRAY + msg ));
        log.info(author+ " > mc:" + msg );
    }

    public static void relayToDiscord(AsyncPlayerChatEvent e) {
        String author = e.getPlayer().getName();
        String msg = e.getMessage();
        //jda.getAccountManager().setNickname(jda.getTextChannelById(botConfig.BINDED_CHANNEL).getGuild(), "MC ~ " + author);
        jda.getTextChannelById(botConfig.BINDED_CHANNEL).sendMessage("**Wobros ~ [" + author + "]:**  " + msg);
        log.info(author + " > discord: " + msg);
        //jda.getAccountManager().setNickname(jda.getTextChannelById(botConfig.BINDED_CHANNEL).getGuild(), jda.getSelfInfo().getUsername());
    }

    public static boolean hasPermission(String userID, MessageReceivedEvent e) {
        ArrayList<String> wl = Main.botConfig.WHITELIST;
        for (String s : wl) {
            if(s.equals(userID))
                return true;
        }
        return false;
    }

    public static String concatenateArgs(int startIndex,String[] args) {
        String concatenatedString = "";
        for(int i = startIndex; i < args.length; i++)
            concatenatedString += " " + args[i];
        return concatenatedString;
    }
}
