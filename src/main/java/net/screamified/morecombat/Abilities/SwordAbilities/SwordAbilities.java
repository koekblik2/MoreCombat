package net.screamified.morecombat.Abilities.SwordAbilities;

import net.screamified.morecombat.Abilities.Abilities;
import net.screamified.morecombat.Abilities.Ability;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public enum SwordAbilities implements Abilities {

    SWORDSTRIKE {
        @Override
        public Ability getAbility(Entity projectile) {
            return null;
        }

        @Override
        public String toString() { return "SWORDSTRIKE"; }
    };

}
