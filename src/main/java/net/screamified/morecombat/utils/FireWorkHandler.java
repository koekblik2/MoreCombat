package net.screamified.morecombat.utils;

import net.screamified.morecombat.MoreCombat;
import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.world.entity.projectile.EntityFireworks;

public class FireWorkHandler {

    public static NamespacedKey fireworkkey = new NamespacedKey(MoreCombat.getPlugin(), "firework");

    public static void instantFirework(FireworkEffect fe, Location loc) {
        Firework f = (Firework)loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta fmeta = f.getFireworkMeta();
        fmeta.getPersistentDataContainer().set(fireworkkey, PersistentDataType.INTEGER, Integer.valueOf(1));
        fmeta.addEffect(fe);
        f.setFireworkMeta(fmeta);
        try {
            Class<?> fireworkClass = Class.forName("org.bukkit.craftbukkit." +
                    getVersion() + "." +
                    "entity.CraftFirework");
            Object firework = fireworkClass.cast(f);
            Method handle = firework.getClass().getMethod("getHandle", new Class[0]);
            Object fireworkObject = handle.invoke(firework, new Object[0]);
            Field expectedLifespan = getNMSClass("EntityFireworks").getDeclaredField("expectedLifespan");
            Field ticksFlown = getNMSClass("EntityFireworks").getDeclaredField("ticksFlown");
            ticksFlown.setAccessible(true);
            ticksFlown.setInt(fireworkObject, expectedLifespan.getInt(fireworkObject) - 1);
            return;
        } catch (Exception e1) {
            e1.printStackTrace();
            return;
        }
    }


    public static String getVersion() {
        return String.valueOf(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]);
    }

    public static Class<?> getNMSClass(String nmsClassString) {
        try {
            String name = "net.minecraft.server." + getVersion() + "." + nmsClassString;
            Class<?> nmsClass = Class.forName(name);
            return nmsClass;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
