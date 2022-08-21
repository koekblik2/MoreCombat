package net.screamified.morecombat.Abilities;

import net.screamified.morecombat.MoreCombat;
import net.screamified.morecombat.utils.BowAbilityHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Ability {

    protected static Entity AbilityProjectile;
    private static BossBar bar;
    private static int AbilityCooldown;

    public static int getAbilityCoolDown() {
        return AbilityCooldown;
    }

    public Ability(int abilityCooldown, Entity projectile) {
        AbilityProjectile = projectile;
        AbilityCooldown = abilityCooldown;
    }

    public abstract void CastAbility(Player player);


    protected void createBossBar(Player player) {
        bar = Bukkit.createBossBar(ChatColor.RED + "Cooldown: " + String.valueOf(AbilityCooldown), BarColor.PINK, BarStyle.SOLID);
        bar.addPlayer(player);
        bar.setVisible(true);
        AbilityCooldownTimer(player);
    }

    protected void AbilityCooldownTimer(Player p) {
        new BukkitRunnable() {
            final int timer = AbilityCooldown;
            int count = timer;
            final float time = 1.0f/timer;
            float progress = 1.0f;
            @Override
            public void run() {
                progress = progress - time;
                count--;
                bar.setTitle(ChatColor.RED + "Cooldown: " + String.valueOf(count));
                BowAbilityHandler.cooldownHandler.setCooldown(p.getUniqueId(), count);

                if(progress <= 0.0 || count == 0) {
                    bar.setVisible(false);
                    bar.removePlayer(p);
                    this.cancel();
                }
                bar.setProgress(progress);
            }
        }.runTaskTimer(MoreCombat.getPlugin(), 0L, 20L);
    }



}
