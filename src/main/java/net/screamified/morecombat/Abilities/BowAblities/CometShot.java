package net.screamified.morecombat.Abilities.BowAblities;

import net.screamified.morecombat.Abilities.Ability;
import net.screamified.morecombat.MoreCombat;
import net.screamified.morecombat.utils.FireWorkHandler;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class CometShot extends Ability {

    FireworkEffect fe1 = FireworkEffect.builder().flicker(true).trail(false).with(FireworkEffect.Type.BURST).withColor(Color.BLACK).withFade(Color.ORANGE).build();

    public CometShot(int abilityCooldown, Entity projectile) {
        super(abilityCooldown, projectile);
    }

    public void CastAbility(Player player) {
        createBossBar(player);
        new BukkitRunnable() {
            public void run() {
                if (AbilityProjectile.isValid()) {
                    FireWorkHandler.instantFirework(fe1, AbilityProjectile.getLocation());
                    AbilityProjectile.getLocation().getWorld().spawnParticle(Particle.FLAME, AbilityProjectile.getLocation(), 10);
                    if (AbilityProjectile.isOnGround()) {
                        AbilityProjectile.remove();
                    }
                } else {
                    AbilityProjectile.getLocation().getWorld().createExplosion(AbilityProjectile.getLocation(), 7.0f);
                    this.cancel();
                }

            }
        }.runTaskTimer(MoreCombat.getPlugin(), 0L, 2L);


    }
}

