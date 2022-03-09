package com.izakdvlpr.loginsecurity.managers;

import com.izakdvlpr.loginsecurity.LoginSecurityMain;
import com.izakdvlpr.loginsecurity.listeners.PlayerListener;
import org.bukkit.plugin.PluginManager;

public class ListenersManager {
  public ListenersManager(LoginSecurityMain main) {
    PluginManager manager = main.getServer().getPluginManager();

    manager.registerEvents(new PlayerListener(), main);
  }
}
