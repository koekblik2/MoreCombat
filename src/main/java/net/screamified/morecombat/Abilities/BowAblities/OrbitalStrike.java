package net.screamified.morecombat.Abilities.BowAblities;

import net.screamified.morecombat.Abilities.Ability;
import net.screamified.morecombat.MoreCombat;
import net.screamified.morecombat.utils.FireWorkHandler;
import net.screamified.morecombat.utils.GeneralUtils;
import net.screamified.morecombat.utils.Laser.CrystalLaser;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class OrbitalStrike extends Ability {

    public OrbitalStrike(int abilityCooldown, Entity projectile) {
        super(abilityCooldown, projectile);
    }

    FireworkEffect fe = FireworkEffect.builder().flicker(true).trail(false).with(FireworkEffect.Type.BURST).withColor(Color.AQUA).withFade(Color.WHITE).build();
    FireworkEffect fe2 = FireworkEffect.builder().flicker(true).trail(false).with(FireworkEffect.Type.BALL_LARGE).withColor(Color.RED).withFade(Color.BLACK).build();
    FireworkEffect fe3 = FireworkEffect.builder().trail(false).with(FireworkEffect.Type.BURST).withColor(Color.BLACK).withFade(Color.GREEN).build();

    @Override
    public void CastAbility(Player player) {
        createBossBar(player);
        new BukkitRunnable() {
            final double r = 8;
            int waveCount = 6;

            final long ticks = 3*20;
            double count = 0;
            double i = 0;
            final double height = 256;
            double orbitalStrikeEndCount = height;
            CrystalLaser laser;

            @Override
            public void run() {
                if (AbilityProjectile.isOnGround() || !AbilityProjectile.isValid()) {
                    Location aLoc = AbilityProjectile.getLocation();
                    Location orbitalStrikeStart = new Location(aLoc.getWorld(), aLoc.getX(), height, aLoc.getZ());
                    Location orbitalStrikeEnd = new Location(aLoc.getWorld(), aLoc.getX(), orbitalStrikeEndCount, aLoc.getZ());
                    if (laser == null) {
                        try {
                            laser = new CrystalLaser(orbitalStrikeStart, orbitalStrikeEnd, 5, -1);
                            laser.start(MoreCombat.getPlugin());
                        } catch (ReflectiveOperationException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    try {
                        laser.moveEnd(orbitalStrikeEnd);
                    } catch (ReflectiveOperationException e) {
                        throw new RuntimeException(e);
                    }
                    if (i > 360) {
                        FireWorkHandler.instantFirework(fe2, aLoc);
                        aLoc.createExplosion(15);
                        new BukkitRunnable() {
                            int j = 0;
                            @Override
                            public void run() {
                                if (!(j >= waveCount))
                                {
                                    List<Location> circleLoc = GeneralUtils.circle(player, aLoc, (int)r+(j*3), 1, true, false, 0);
                                    for (Location loc : circleLoc) {
                                        loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, loc, 2);
                                    }
                                    j++;
                                } else {
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(MoreCombat.getPlugin(), 0, 5);
                        this.cancel();
                    }
                    if (!(orbitalStrikeEndCount <= aLoc.getY())) {
                        orbitalStrikeEnd.getWorld().spawnParticle(Particle.DRAGON_BREATH, orbitalStrikeEnd, 5);
                        FireWorkHandler.instantFirework(fe3, orbitalStrikeEnd);
                        orbitalStrikeEndCount = orbitalStrikeEndCount - ((height-aLoc.getY()) / ticks);
                        }
                    SpawnSurroundingFireworks(aLoc, r, count, i, height);
                    count = count + ((height-aLoc.getY()) / ticks);
                    i = i + (360 / ticks);
                }
            }
        }.runTaskTimer(MoreCombat.getPlugin(), 0, 1);
    }
    
    private void SpawnSurroundingFireworks(Location aLoc, double r, double count, double i, double height) {
        double x;
        double x1;
        double y = 0;
        double z;
        double z1;

        x = r * Math.cos(Math.toRadians(i+90));
        x1 = r * Math.cos(Math.toRadians(i));
        z = r * Math.sin(Math.toRadians(i));
        z1 = r * Math.sin(Math.toRadians(i+90));

        Location feUpOne = new Location(aLoc.getWorld(), aLoc.getX() - x, aLoc.getY() + (y+count), aLoc.getZ() - z);
        Location feUpTwo = new Location(aLoc.getWorld(), aLoc.getX() + x, aLoc.getY() + (y+count), aLoc.getZ() + z);
        Location feUpThree = new Location(aLoc.getWorld(), aLoc.getX() - x1, aLoc.getY() + (y+count), aLoc.getZ() - z1);
        Location feUpFour = new Location(aLoc.getWorld(), aLoc.getX() + x1, aLoc.getY() + (y+count), aLoc.getZ() + z1);

        Location feDownOne = new Location(aLoc.getWorld(), aLoc.getX() + x, height + (y-count), aLoc.getZ() + z);
        Location feDownTwo = new Location(aLoc.getWorld(), aLoc.getX() - x, height + (y-count), aLoc.getZ() - z);
        Location feDownThree = new Location(aLoc.getWorld(), aLoc.getX() + x1, height + (y-count), aLoc.getZ() + z1);
        Location feDownTFour = new Location(aLoc.getWorld(), aLoc.getX() - x1, height + (y-count), aLoc.getZ() - z1);

        FireWorkHandler.instantFirework(fe, feUpOne);
        FireWorkHandler.instantFirework(fe, feUpTwo);
        FireWorkHandler.instantFirework(fe, feUpThree);
        FireWorkHandler.instantFirework(fe, feUpFour);
//
        FireWorkHandler.instantFirework(fe, feDownOne);
        FireWorkHandler.instantFirework(fe, feDownTwo);
        FireWorkHandler.instantFirework(fe, feDownThree);
        FireWorkHandler.instantFirework(fe, feDownTFour);
    }
}