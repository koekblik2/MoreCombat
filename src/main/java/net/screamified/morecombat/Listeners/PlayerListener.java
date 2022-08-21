package net.screamified.morecombat.Listeners;

import net.screamified.morecombat.Abilities.Abilities;
import net.screamified.morecombat.Abilities.BowAblities.BowAbilities;
import net.screamified.morecombat.utils.AbilitySwitchHandler;
import net.screamified.morecombat.utils.BowAbilityHandler;
import net.screamified.morecombat.utils.PotionEffectHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerListener implements Listener {


    @EventHandler
    public void onPlayerDamageEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            Player player = (Player) event.getDamager();
            LivingEntity targetEntity = (LivingEntity) event.getEntity();

            ItemStack getHeldItem = player.getInventory().getItem(EquipmentSlot.HAND);
            if (!getHeldItem.hasItemMeta()) { return; }
            if (player.getItemInHand().getType().equals(Material.BOW)) { return; }
            if (getHeldItem.getItemMeta().hasLore()) {
                PotionEffectHandler.GiveEntityPotionEffect(getHeldItem, targetEntity);
            }
        }
    }

    @EventHandler
    public void onPlayerShootBow(EntityShootBowEvent event) {
        if (!(event.getBow().hasItemMeta())) { return; }
        Player player = (Player) event.getEntity();
        ItemStack getHeldItem = event.getBow();
        ItemMeta bowItemMeta = getHeldItem.getItemMeta();

        if (!(getHeldItem.getItemMeta().hasLore()) || !(getHeldItem.getItemMeta().hasDisplayName())) { return; }
        if (getHeldItem.getItemMeta().getDisplayName().equals("Bow")) { return; }
        if (event.getEntity() instanceof Player && (event.getForce() == 1.0) ) {
            for (Abilities ability : BowAbilities.values()) {
                if (ChatColor.stripColor(ability.toString()).equalsIgnoreCase(ChatColor.stripColor(bowItemMeta.getDisplayName()))) {
                    int timeLeft = BowAbilityHandler.cooldownHandler.getCooldown(player.getUniqueId());
                    if(timeLeft == 0){
                        ability.getAbility(event.getProjectile()).CastAbility(player);
                    }
                }
            }
        }
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!(player.getItemInHand().getType().equals(Material.BOW)) && !(player.getItemInHand().getType().toString().toLowerCase().contains("sword"))) { return; }
        Action playerAction = event.getAction();
        ItemMeta bowItemMeta = player.getItemInHand().getItemMeta();

        if (!(bowItemMeta.hasLore())) { return; }
        if (!(bowItemMeta.hasDisplayName())) { return; }

        if ((((playerAction == Action.LEFT_CLICK_AIR) || (playerAction == Action.LEFT_CLICK_BLOCK)) && (player.isSneaking()))) {
            event.setCancelled(true);
            AbilitySwitchHandler.PreviousAbility(player.getItemInHand());
        }
        else if ((playerAction == Action.LEFT_CLICK_AIR) || (playerAction == Action.LEFT_CLICK_BLOCK)) {
            event.setCancelled(true);
            AbilitySwitchHandler.NextAbility(player.getItemInHand());
        }
    }
}
