package com.izakdvlpr.loginsecurity.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    event.setJoinMessage("Seja bem-vindo, " + event.getPlayer().getName() + "!");
  }
}
