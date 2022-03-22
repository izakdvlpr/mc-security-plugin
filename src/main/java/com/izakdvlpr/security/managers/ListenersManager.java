package com.izakdvlpr.security.managers;

import com.izakdvlpr.security.ZKSecurityMain;
import com.izakdvlpr.security.listeners.PlayerListener;
import org.bukkit.plugin.PluginManager;

public class ListenersManager {
  public ListenersManager(ZKSecurityMain main) {
    PluginManager manager = main.getServer().getPluginManager();

    manager.registerEvents(new PlayerListener(), main);
  }
}
