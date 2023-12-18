package xd.ericsson.tihiyomut.config;

import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import io.github.kosmx.emotes.api.events.server.ServerEmoteAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import xd.ericsson.tihiyomut.TihiyOmut;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import static xd.ericsson.tihiyomut.TihiyOmut.plugin;

public class Emotes {

    public static List<KeyframeAnimation> deserializeEmote(String path) {
        InputStream stream = plugin.getResource(path);
        if (stream == null) {
            Bukkit.getServer().getLogger().log(Level.SEVERE, "Не удалось загрузить эмоцию: " + path);
            return null;
        }
        return ServerEmoteAPI.deserializeEmote(stream, path, "json");
    }

    public static UUID getUUID(Entity entity, boolean onlineMode) {
        if (entity instanceof Player player) {
            return getUUID(player, onlineMode);
        }
        return null;
    }

    public static UUID getUUID(Player player, boolean onlineMode) {
        if (onlineMode) {
            return player.getUniqueId();
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getName());
        return offlinePlayer.getUniqueId();
    }
}
