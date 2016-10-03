package eb.cyrien.MineCordBot;

import eb.cyrien.MineCordBot.commands.Dcmd.*;
import eb.cyrien.MineCordBot.commands.Mcmd.Dcmd;
import eb.cyrien.MineCordBot.commands.Mcmd.Dme;
import eb.cyrien.MineCordBot.commands.Mcmd.MReload;
import eb.cyrien.MineCordBot.entity.BasicMathSolver;
import eb.cyrien.MineCordBot.entity.Messenger;
import eb.cyrien.MineCordBot.configuration.PluginFile;
import eb.cyrien.MineCordBot.utils.BotConfig;
import eb.cyrien.MineCordBot.utils.CommandListener;
import eb.cyrien.MineCordBot.utils.CommandParser;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    public static JDA jda;
    public static final CommandParser parser = new CommandParser();

    public static HashMap<String, Command> commands = new HashMap<>();

    public static PluginFile config;
    public static BotConfig botConfig;

    public static Logger log;

    private static long startTime;

    @Override
    public void onEnable() {
        init();
    }

    public void init() {
        startTime = System.currentTimeMillis();
        //minecraft side initialization
        log = getLogger();
        config = new PluginFile(this, "BotConfig", true);
        botConfig = new BotConfig();
        this.getCommand("dme").setExecutor(new Dme(this));
        this.getCommand("minecordbot").setExecutor(new MReload(this));
        this.getCommand("dcmd").setExecutor(new Dcmd(this));
        getServer().getPluginManager().registerEvents(new Messenger(), this);

        //discord side initialization
        try {
            jda = new JDABuilder()
                    .addListener(new BasicMathSolver())
                    .addListener(new Messenger())
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
        commands.put("reload", new DReload());
        commands.put("help", new Help());
        commands.put("setusername", new SetUsername());
        commands.put("setnick", new SetNick());
        commands.put("setstreaming", new SetStreaming());
        commands.put("mcmd", new Mcmd());
        commands.put("info", new Info());
    }

    @Override
    public void onDisable() {
        if(jda != null)
            jda.shutdown();
    }

    public static void handleCommand(CommandParser.CommandContainer cmd) {
        if (commands.containsKey(cmd.invoke)) {
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);
            if(safe) {
                commands.get(cmd.invoke).action(cmd.args, cmd.event);
                commands.get(cmd.invoke).executed(safe, cmd.event);
            } else
                commands.get(cmd.invoke).executed(safe, cmd.event);
        }
    }

    public static String concatenateArgs(int startIndex,String[] args) {
        String concatenatedString = "";
        for(int i = startIndex; i < args.length; i++)
            concatenatedString += " " + args[i];
        concatenatedString.trim();
        return concatenatedString;
    }

    public static String getUptime() {
        long currTime = System.currentTimeMillis();
        long diff = currTime - startTime;
        return (int)(diff / 86400000L) + "d " + (int)(diff / 3600000L % 24L) + "h " + (int)(diff / 60000L % 60L) + "m " + (int)(diff / 1000L % 60L) + "s";
    }
}
