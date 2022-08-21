package net.screamified.morecombat.Abilities;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public interface Abilities {

    public abstract Ability getAbility(Entity projectile);
    public abstract String toString();
}
