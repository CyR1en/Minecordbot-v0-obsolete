package eb.cyrien.MineCordBot;

import eb.cyrien.MineCordBot.commands.Dcmd.*;
import eb.cyrien.MineCordBot.commands.Dcmd.Shutdown;
import eb.cyrien.MineCordBot.commands.Mcmd.Dcmd;
import eb.cyrien.MineCordBot.commands.Mcmd.Dme;
import eb.cyrien.MineCordBot.commands.Mcmd.MReload;
import eb.cyrien.MineCordBot.configuration.PluginFile;
import eb.cyrien.MineCordBot.entity.BasicMathSolver;
import eb.cyrien.MineCordBot.entity.Messenger;
import eb.cyrien.MineCordBot.utils.BotConfig;
import eb.cyrien.MineCordBot.utils.CommandListener;
import eb.cyrien.MineCordBot.utils.CommandParser;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    private JDA jda;

    private static Main instance;
    private final CommandParser parser = new CommandParser();

    private HashMap<String, Command> commands = new HashMap<>();

    private PluginFile pluginFile;
    private BotConfig botConfig;

    private Logger logger;

    private long startTime;

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        if (jda != null)
            jda.shutdown();
    }

    private void init() {
        startTime = System.currentTimeMillis();
        logger = getLogger();
        pluginFile = new PluginFile(this, "BotConfig", true);
        botConfig = new BotConfig(this);
        //discord side initialization
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(botConfig.BOT_TOKEN).buildBlocking();
            jda.addEventListener(new BasicMathSolver(this));
            jda.addEventListener(new Messenger(this));
            jda.addEventListener(new CommandListener(this));
            jda.setAutoReconnect(true);
        } catch (LoginException e) {
            e.printStackTrace();
            System.err.println("Could not logger in");
        } catch (InterruptedException | RateLimitedException e) {
            e.printStackTrace();
        }
        instance = this;
        commands.put("ping", new Ping(this));
        commands.put("setavatar", new SetAvatar(this));
        commands.put("ls", new ListPlayer(this));
        commands.put("setgame", new SetGame(this));
        commands.put("reload", new DReload(this));
        commands.put("help", new Help(this));
        commands.put("setusername", new SetUsername(this));
        commands.put("setnick", new SetNick(this));
        commands.put("setstreaming", new SetStreaming(this));
        commands.put("mcmd", new Mcmd(this));
        commands.put("info", new Info(this));
        commands.put("shutdown", new Shutdown(this));
        commands.put("invite", new Invite(this));
        commands.put("setexecutor", new SetExecutor(this));
        commands.put("textchannel", new TextChannel(this));

        //minecraft side initialization
        this.getCommand("dme").setExecutor(new Dme(this));
        this.getCommand("minecordbot").setExecutor(new MReload(this));
        this.getCommand("dcmd").setExecutor(new Dcmd(this));
        getServer().getPluginManager().registerEvents(new Messenger(this), this);
    }

    public JDA getJda() {
        return jda;
    }
    public static Main getInstance() {
        return instance;
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public BotConfig getBotConfig() {
        return botConfig;
    }

    public PluginFile getPluginFile() {
        return pluginFile;
    }

    public Logger getMCBLogger() {
        return logger;
    }

    public CommandParser getParser() {
        return parser;
    }

    public void handleCommand(CommandParser.CommandContainer cmd) {
        if (commands.containsKey(cmd.invoke)) {
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);
            if (safe) {
                commands.get(cmd.invoke).action(cmd.args, cmd.event);
                commands.get(cmd.invoke).executed(true, cmd.event);
            } else
                commands.get(cmd.invoke).executed(false, cmd.event);
        }
    }


    public String getUptime() {
        long currTime = System.currentTimeMillis();
        long diff = currTime - startTime;
        return (int) (diff / 86400000L) + "d " + (int) (diff / 3600000L % 24L) + "h " + (int) (diff / 60000L % 60L) + "m " + (int) (diff / 1000L % 60L) + "s";
    }
}
