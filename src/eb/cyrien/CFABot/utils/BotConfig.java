package eb.cyrien.CFABot.utils;

import eb.cyrien.CFABot.Main;

import java.util.ArrayList;

public class BotConfig {

    public static String BOT_TOKEN;
    public static String OWNER_ID;
    public static String BINDED_CHANNEL;
    public static String COMMAND_EXECUTOR;
    public static String BOT_ID;
    public static ArrayList<String> WHITELIST;

    public BotConfig() {
        BOT_TOKEN = Main.config.getConfig().getString("bot_token");
        OWNER_ID = Main.config.getConfig().getString("owner_id");
        BINDED_CHANNEL = Main.config.getConfig().getString("chat_channel");
        COMMAND_EXECUTOR = Main.config.getConfig().getString("command_executor");
        WHITELIST = (ArrayList)Main.config.getConfig().getStringList("whitelist");
        BOT_ID = Main.config.getConfig().getString("bot_id");
    }

}
