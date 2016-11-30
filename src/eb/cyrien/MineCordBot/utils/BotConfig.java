package eb.cyrien.MineCordBot.utils;

import eb.cyrien.MineCordBot.Main;
import eb.cyrien.MineCordBot.configuration.PluginFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class BotConfig {

    private final int SAVE = 0;
    private final int LOAD = 1;

    public static final String CURRENT_CONFIG_VERSION = "0.2.8";

    private Main instance;
    public static String CONFIG_VERSION;
    public static String BOT_TOKEN;
    public static String OWNER_ID;
    public static ArrayList<String> BINDED_CHANNELS;
    public static String COMMAND_EXECUTOR;
    public static String BOT_ID;
    public static ArrayList<String> WHITELIST;
    public static String MESSAGE_PREFIX_DISCORD;
    public static String MESSAGE_PREFIX_MINECRAFT;
    public static boolean DEATHEVENT_BC;
    public static boolean JOINEVENT_BC;
    public static boolean LEAVE_BC;
    public static boolean HIDE_WITH_PERM;

    public BotConfig(Main instance) {
        this.instance = instance;
        YamlConfiguration config = instance.getPluginFile().getConfig();
        if (!config.getKeys(false).contains("CONFIG_VERSION") || !config.getString("CONFIG_VERSION").equals(CURRENT_CONFIG_VERSION)) {
            instance.getMCBLogger().warning("Outdated Config file. Updating BotConfig.yml....");
            updateConfig();
        }
        reload();
    }

    public void init() {
        YamlConfiguration config = instance.getPluginFile().getConfig();
        CONFIG_VERSION = config.getString("CONFIG_VERSION");
        BOT_TOKEN = config.getString("bot_token");
        OWNER_ID = config.getString("owner_id");
        BINDED_CHANNELS = (ArrayList<String>) config.getStringList("chat_channel");
        COMMAND_EXECUTOR = config.getString("command_executor");
        WHITELIST = (ArrayList) config.getStringList("whitelist");
        BOT_ID = config.getString("bot_id");
        MESSAGE_PREFIX_DISCORD = config.getString("message_prefix_discord");
        MESSAGE_PREFIX_MINECRAFT = config.getString("message_prefix_minecraft");
        DEATHEVENT_BC = config.getBoolean("broadcast.deathEvent");
        JOINEVENT_BC = config.getBoolean("broadcast.joinEvent");
        LEAVE_BC = config.getBoolean("broadcast.leaveEvent");
        HIDE_WITH_PERM = config.getBoolean("hide_user_with_incognito_perm");
    }

    public void updateConfig() {
        YamlConfiguration config = instance.getPluginFile().getConfig();
        Set<String> keys = config.getKeys(true);
        preSaveConfig(keys);
        instance.getPluginFile().renew(instance, "BotConfig", true);
        restorePreSaved(keys);
        try {
            instance.getPluginFile().save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        config.set("CONFIG_VERSION", CURRENT_CONFIG_VERSION);
        instance.getMCBLogger().info("BotConfig.yml updated to version " + CURRENT_CONFIG_VERSION);
    }

    public void preSaveConfig(Set<String> keys) {
        for (String s : keys)
            key(s, SAVE);
    }

    public void restorePreSaved(Set<String> keys) {
        for (String s : keys)
            key(s, LOAD);
    }

    private void key(String s, int mode) {
        // 0 = save mode
        // 1 = load mode
        YamlConfiguration config = instance.getPluginFile().getConfig();
        switch (s) {
            case "bot_token":
                if (mode == SAVE)
                    BOT_TOKEN = config.getString(s);
                else
                    config.set(s, BOT_TOKEN);
                break;
            case "owner_id":
                if (mode == SAVE)
                    OWNER_ID = config.getString(s);
                else
                    config.set(s, OWNER_ID);
                break;
            case "chat_channel":
                if (mode == SAVE)
                    BINDED_CHANNELS = (ArrayList) config.getStringList(s);
                else
                    config.set(s, BINDED_CHANNELS);
                break;
            case "command_executor":
                if (mode == SAVE)
                    COMMAND_EXECUTOR = config.getString(s);
                else
                    config.set(s, COMMAND_EXECUTOR);
                break;
            case "whitelist":
                if (mode == SAVE)
                    WHITELIST = (ArrayList) config.getStringList(s);
                else
                    config.set(s, WHITELIST);
                break;
            case "bot_id":
                if (mode == SAVE)
                    BOT_ID = config.getString(s);
                else
                    config.set(s, BOT_ID);
                break;
            case "message_prefix_discord":
                if (mode == SAVE)
                    MESSAGE_PREFIX_DISCORD = config.getString(s);
                else
                    config.set(s, MESSAGE_PREFIX_DISCORD);
                break;
            case "message_prefix_minecraft":
                if (mode == SAVE)
                    MESSAGE_PREFIX_MINECRAFT = config.getString(s);
                else
                    config.set(s, MESSAGE_PREFIX_MINECRAFT);
                break;
            case "broadcast.death_event":
                if (mode == SAVE)
                    DEATHEVENT_BC = config.getBoolean(s);
                else
                    config.set(s, DEATHEVENT_BC);
                break;
            case "broadcast.join_event":
                if (mode == SAVE)
                    JOINEVENT_BC = config.getBoolean(s);
                else
                    config.set(s, JOINEVENT_BC);
                break;
            case "broadcast.leave_event":
                if (mode == SAVE)
                    LEAVE_BC = config.getBoolean(s);
                else
                    config.set(s, LEAVE_BC);
                break;
            case "hide_user_with_incognito_perm":
                if(mode == SAVE)
                    HIDE_WITH_PERM = config.getBoolean(s);
                else
                    config.set(s, HIDE_WITH_PERM);
                break;
        }
    }

    public void reload() {
        init();
        instance.getPluginFile().reload();
    }

}
