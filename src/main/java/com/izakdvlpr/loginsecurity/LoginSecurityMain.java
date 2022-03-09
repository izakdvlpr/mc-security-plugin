package com.izakdvlpr.loginsecurity;

import com.izakdvlpr.loginsecurity.managers.DatabaseManager;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class LoginSecurityMain extends JavaPlugin {
  public static LoginSecurityMain instance;

  private final DatabaseManager databaseManager = new DatabaseManager(this);

  public static LoginSecurityMain getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    if (!(new File(getDataFolder(), "config.yml")).exists()) saveDefaultConfig();

    new DatabaseManager(this);
  }

  @Override
  public void onDisable() {
    databaseManager.stopConnection();
  }
}
