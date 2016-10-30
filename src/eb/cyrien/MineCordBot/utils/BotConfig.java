package eb.cyrien.MineCordBot.utils;

import eb.cyrien.MineCordBot.Main;

import java.util.ArrayList;

public class BotConfig {

    private Main instance;
    public static String BOT_TOKEN;
    public static String OWNER_ID;
    public static ArrayList<String> BINDED_CHANNELS;
    public static String COMMAND_EXECUTOR;
    public static String BOT_ID;
    public static ArrayList<String> WHITELIST;
    public static String MESSAGE_PREFIX_DISCORD;
    public static String MESSAGE_PREFIX_MINECRAFT;

    public BotConfig(Main instance) {
        this.instance = instance;
        init();
    }

    public void init() {
        BOT_TOKEN = instance.getPluginFile().getConfig().getString("bot_token");
        OWNER_ID = instance.getPluginFile().getConfig().getString("owner_id");
        BINDED_CHANNELS = (ArrayList<String>) instance.getPluginFile().getConfig().getStringList("chat_channel");
        COMMAND_EXECUTOR = instance.getPluginFile().getConfig().getString("command_executor");
        WHITELIST = (ArrayList)instance.getPluginFile().getConfig().getStringList("whitelist");
        BOT_ID = instance.getPluginFile().getConfig().getString("bot_id");
        MESSAGE_PREFIX_DISCORD = instance.getPluginFile().getConfig().getString("message_prefix_discord");
        MESSAGE_PREFIX_MINECRAFT = instance.getPluginFile().getConfig().getString("message_prefix_minecraft");
    }

    public void reload() {
        init();
        instance.getPluginFile().reload();
    }

}
