package eb.cyrien.MineCordBot.utils;

import eb.cyrien.MineCordBot.Main;

import java.util.ArrayList;

public class BotConfig {

    public static String BOT_TOKEN;
    public static String OWNER_ID;
    public static String BINDED_CHANNEL;
    public static String COMMAND_EXECUTOR;
    public static String BOT_ID;
    public static ArrayList<String> WHITELIST;
    public static String MESSAGE_PREFIX_DISCORD;
    public static String MESSAGE_PREFIX_MINECRAFT;

    public BotConfig() {
        init();
    }

    public void init() {
        BOT_TOKEN = Main.config.getConfig().getString("bot_token");
        OWNER_ID = Main.config.getConfig().getString("owner_id");
        BINDED_CHANNEL = Main.config.getConfig().getString("chat_channel");
        COMMAND_EXECUTOR = Main.config.getConfig().getString("command_executor");
        WHITELIST = (ArrayList)Main.config.getConfig().getStringList("whitelist");
        BOT_ID = Main.config.getConfig().getString("bot_id");
        MESSAGE_PREFIX_DISCORD = Main.config.getConfig().getString("message_prefix_discord");
        MESSAGE_PREFIX_MINECRAFT = Main.config.getConfig().getString("message_prefix_minecraft");
    }

    public void reload() {
        Main.config.reload();
        init();
    }

}
