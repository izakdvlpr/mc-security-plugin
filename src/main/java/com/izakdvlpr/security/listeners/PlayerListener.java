package com.izakdvlpr.security.listeners;

import com.izakdvlpr.security.ZKSecurityMain;
import com.izakdvlpr.security.repositories.UserRepository;

import com.connorlinfoot.titleapi.TitleAPI;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    event.setJoinMessage("");

    Player player = event.getPlayer();

    player.getInventory().setArmorContents(null);
    player.getInventory().clear();
    player.setGameMode(GameMode.SURVIVAL);

    for (byte b1 = 0; b1 < 100; b1++)
      player.sendMessage("");

    boolean isCreatedUser = UserRepository.verifyUser(player.getUniqueId().toString());

    if (!isCreatedUser) {
      TitleAPI.sendTitle(player, "§e§lBem-Vindo!", "§fUtilize /registrar <senha> <senha>", 20, 2000, 20);

      player.sendMessage("§cBem-Vindo! Utilize /registrar <senha> <senha>");

      return;
    }

    TitleAPI.sendTitle(player, "§e§lBem-Vindo!", "§aUtilize /login <senha>", 20, 2000, 20);

    player.sendMessage("§aBem-Vindo! Utilize /login <senha>");
  }

  @EventHandler
  public void onPlayerQuitEvent(PlayerQuitEvent event) {
    Player player = event.getPlayer();

    ZKSecurityMain.usersLogged.remove(player.getName());
  }

  @EventHandler
  public void onPlayerMoveEvent(PlayerMoveEvent event) {
    Player player = event.getPlayer();

    if (!ZKSecurityMain.usersLogged.contains(player.getName())) {
      event.setCancelled(true);

      player.teleport(player.getLocation());
    }
  }
}
