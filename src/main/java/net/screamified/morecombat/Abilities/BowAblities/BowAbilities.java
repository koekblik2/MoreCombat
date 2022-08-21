package net.screamified.morecombat.Abilities.BowAblities;

import net.screamified.morecombat.Abilities.Abilities;
import net.screamified.morecombat.Abilities.Ability;
import net.screamified.morecombat.MoreCombat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public enum BowAbilities implements Abilities {
    COMETSHOT {
        @Override
        public String toString() {
            return String.format(ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + "COMETSHOT");
        }
        public Ability getAbility(Entity projectile) {
            return new CometShot(5, projectile);
        }
    },
    FLAMEWAVE {
        @Override
        public String toString() {
            return String.format(ChatColor.BOLD + "" + ChatColor.RED + "FLAMEWAVE");
        }
        public Ability getAbility(Entity projectile) {
            return new FlameWave(5, projectile);
        }
    },
    ORBITALSTRIKE {
        @Override
        public String toString() {
            return String.format(ChatColor.BOLD + "" + ChatColor.BLUE + "ORBITALSTRIKE");
        }
        public Ability getAbility(Entity projectile) {
            return new OrbitalStrike(4, projectile);
        }
    };

    public static List<String> getAbilityNames() {
        List<String> abilities = new ArrayList<>();
        for (BowAbilities ability : BowAbilities.values()) {
            abilities.add(ChatColor.stripColor(ability.toString()));
            MoreCombat.getPlugin().getLogger().info(ability.toString());
        }

        return abilities;
    }

}
