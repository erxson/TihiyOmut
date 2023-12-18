package xd.ericsson.tihiyomut;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import static xd.ericsson.tihiyomut.Sex.*;

public class Events implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player activePlayer = event.getPlayer();
        Entity passiveEntity = event.getRightClicked();

        if (passiveEntity instanceof Player passivePlayer) {
            if (
                isNotInSexState(activePlayer) && isNotInSexState(passivePlayer) &&
                (canInitiateSex(activePlayer, passiveEntity) || canBypassSex(activePlayer)) &&
                !passivePlayer.getName().equals("ericsson_")
            ) {
                fuck(activePlayer, passiveEntity);
            }
        }
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        if (isInSexState(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (isInSexState(player)) {
            event.setCancelled(true);
        }
    }
}