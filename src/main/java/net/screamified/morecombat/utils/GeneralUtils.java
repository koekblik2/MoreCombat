package net.screamified.morecombat.utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class GeneralUtils {
    public static List<LivingEntity> getAllLivingEntitiesInRange(Location loc, double range, @Nullable EntityType entitytype) {
        List<LivingEntity> ret = new ArrayList<>();
        Collection<Entity> entities = new ArrayList<>();
        try {
            if (entitytype != null && entitytype.isAlive()) {
                Predicate<Entity> pred = entity -> entity.getType().equals(entitytype);
                entities = loc.getWorld().getNearbyEntities(loc, range, range, range, pred);
            } else if (!entitytype.isAlive()) {
                return null;
            }
        } catch (NullPointerException e) {
            Predicate<Entity> pred = entity -> entity.getType().isAlive();
            entities = loc.getWorld().getNearbyEntities(loc, range, range, range, pred);
        }
        for (Entity ent : entities) {
            if (ent.getLocation().distance(loc) <= range && ent instanceof LivingEntity)
                ret.add((LivingEntity)ent);
        }
        return ret;
    }

    public static List<Location> circle (Player player, Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
        List<Location> circleblocks = new ArrayList<Location>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - r; x <= cx +r; x++)
            for (int z = cz - r; z <= cz +r; z++)
                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r*r && !(hollow && dist < (r-1)*(r-1))) {
                        Location l = new Location(loc.getWorld(), x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }

        return circleblocks;
    }
}
