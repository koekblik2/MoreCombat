package net.screamified.morecombat.Listeners;

import com.destroystokyo.paper.event.entity.ProjectileCollideEvent;
import net.screamified.morecombat.Abilities.Ability;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class EntityListener implements Listener {

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow &&
                event.getEntity().getShooter() instanceof Player) {

        }
    }
}
