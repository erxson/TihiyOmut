package xd.ericsson.tihiyomut;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xd.ericsson.tihiyomut.config.Config;
import xd.ericsson.tihiyomut.config.Emotes;

import java.util.HashMap;
import java.util.Map;

import static io.github.kosmx.emotes.api.events.server.ServerEmoteAPI.forcePlayEmote;
import static io.github.kosmx.emotes.api.events.server.ServerEmoteAPI.setPlayerPlayingEmote;
import static xd.ericsson.tihiyomut.TihiyOmut.*;

public class Sex {
    public static Map<Player, Entity> playersInSexState = new HashMap<>();

    public static void fuck(Player activePlayer, Entity passiveEntity) {
        if (
                passiveEntity.getType() == EntityType.PLAYER &&
                activePlayer.getInventory().getItemInMainHand().getType().isAir() &&
                activePlayer.isSneaking()
        ) {
            playersInSexState.put(activePlayer, passiveEntity);
            Sex.placeInFrontOf(activePlayer, passiveEntity, 0.85);
            Sex.startSex(activePlayer, passiveEntity);

            new BukkitRunnable() {
                @Override
                public void run() {
                    Sex.stopSex(activePlayer, passiveEntity);
                    playersInSexState.remove(activePlayer);
                    if (Config.chatBroadcastEnabled)
                        Bukkit.broadcastMessage(
                                Config.broadcastMessage
                                        .replaceAll("%passive%", passiveEntity.getName())
                                        .replaceAll("%active%", activePlayer.getName())
                        );
                }
            }.runTaskLater(TihiyOmut.plugin, Config.sexDuration * 20L);
        }
    }

    public static void placeInFrontOf(Player activePlayer, Entity passiveEntity, double distance) {
        Vector direction = passiveEntity.getLocation().subtract(activePlayer.getLocation()).toVector().normalize();
        Vector newPosition = activePlayer.getLocation().add(direction.multiply(distance)).toVector();
        passiveEntity.teleport(newPosition.toLocation(passiveEntity.getWorld()));
        passiveEntity.getLocation().setPitch(0.0f);
        passiveEntity.getLocation().setDirection(direction);
        passiveEntity.getLocation().setPitch(0.0f);
        activePlayer.getLocation().setDirection(direction);
        passiveEntity.teleport(passiveEntity.getLocation());
        passiveEntity.teleport(passiveEntity.getLocation().setDirection(direction));
    }

    public static void startSex(Player activePlayer, Entity passiveEntity) {
        Location initialLocation = activePlayer.getLocation().clone();
        for (int x = 0; x < 15; x++) {
            double maxOffset = 0.3;
            double offsetX = (Math.random() - 0.3) * 2 * maxOffset;
            double offsetY = (Math.random() - 0.3) * 2 * maxOffset;
            double offsetZ = (Math.random() - 0.3) * 2 * maxOffset;

            Location particleLocation = initialLocation.clone().add(offsetX, offsetY, offsetZ);
            particleLocation.getWorld().spawnParticle(Particle.HEART, particleLocation, 1, 0, 0, 0, 0);
        }

        forcePlayEmote(Emotes.getUUID(activePlayer, false), activeEmote.get(0));
        forcePlayEmote(Emotes.getUUID(activePlayer, true), activeEmote.get(0));
        forcePlayEmote(Emotes.getUUID(passiveEntity, true), passiveEmote.get(0));
        forcePlayEmote(Emotes.getUUID(passiveEntity, false), passiveEmote.get(0));
    }

    public static void stopSex(Player activePlayer, Entity passiveEntity) {
        Location initialLocation = activePlayer.getLocation().clone();
        Vector particleDirection = passiveEntity.getLocation().subtract(initialLocation).toVector().normalize();

        for (int x = 0; x < 15; x++) {
            Location spawnLocation = initialLocation.clone().add(particleDirection.multiply(x));
            activePlayer.getWorld().spawnParticle(Particle.CLOUD, spawnLocation, 1);
        }

        setPlayerPlayingEmote(Emotes.getUUID(activePlayer, true), null);
        setPlayerPlayingEmote(Emotes.getUUID(activePlayer, false), null);
        if(activePlayer.isSneaking()) activePlayer.setSneaking(false);

        setPlayerPlayingEmote(Emotes.getUUID(passiveEntity, true), null);
        setPlayerPlayingEmote(Emotes.getUUID(passiveEntity, false), null);
        if(passiveEntity.isSneaking()) passiveEntity.setSneaking(false);

    }

    public static boolean isInSexState(Player player) {
        return playersInSexState.containsKey(player) || playersInSexState.containsValue(player);
    }

    public static boolean isNotInSexState(Player player) {
        return !isInSexState(player);
    }

    public static boolean canInitiateSex(Player activePlayer, Entity passiveEntity) {
        return Config.isEnabled && passiveEntity.isSneaking() && activePlayer.hasPermission("tihiyomut.use");
    }

    public static boolean canBypassSex(Player player) {
        return player.hasPermission("tihiyomut.admin") && Config.adminBypass;
    }

}
