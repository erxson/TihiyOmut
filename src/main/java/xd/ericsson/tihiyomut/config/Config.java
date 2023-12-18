package xd.ericsson.tihiyomut.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {
    static Plugin plugin;
    static FileConfiguration config;

    public static boolean isEnabled;
    public static boolean adminBypass;
    public static int sexDuration;

    public static boolean chatBroadcastEnabled;
    public static String broadcastMessage;

    public Config(Plugin plugin) {
        Config.plugin = plugin;
        plugin.saveDefaultConfig();
        reloadConfig();
    }

    public static void reloadConfig() {
        plugin.reloadConfig();
        config = plugin.getConfig();

        isEnabled = config.getBoolean("enabled");
        adminBypass = config.getBoolean("admin_bypass");
        sexDuration = config.getInt("sex_duration");

        chatBroadcastEnabled = config.getBoolean("chat_broadcast.enabled");
        broadcastMessage = config.getString("chat_broadcast.message");
    }

    public static void saveConfig() {
        plugin.saveConfig();
    }

    public static void setConfigValue(String path, Object value) {
        config.set(path, value);
    }
}
