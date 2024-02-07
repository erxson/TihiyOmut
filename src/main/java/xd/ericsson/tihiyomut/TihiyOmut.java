package xd.ericsson.tihiyomut;

import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xd.ericsson.tihiyomut.commands.SexCommand;
import xd.ericsson.tihiyomut.config.Config;
import xd.ericsson.tihiyomut.config.Emotes;

import java.util.*;

public final class TihiyOmut extends JavaPlugin implements org.bukkit.event.Listener {
    public static Config configManager;
    public static TihiyOmut plugin;

    public static List<KeyframeAnimation> activeEmote;
    public static List<KeyframeAnimation> passiveEmote;

    @Override
    public void onEnable() {

        plugin = this;

        configManager = new Config(this);

        activeEmote = Emotes.deserializeEmote("emotes/active.json");
        passiveEmote = Emotes.deserializeEmote("emotes/passive.json");

        Bukkit.getPluginManager().registerEvents(new Events(), this);

        this.getCommand("sex").setExecutor(new SexCommand());
        this.getCommand("sex").setTabCompleter(new SexCommand());

    }

    @Override
    public void onDisable() { }
}
