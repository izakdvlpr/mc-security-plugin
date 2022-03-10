package com.izakdvlpr.loginsecurity;

import com.izakdvlpr.loginsecurity.managers.DatabaseManager;
import com.izakdvlpr.loginsecurity.managers.CommandsManager;
import com.izakdvlpr.loginsecurity.managers.ListenersManager;

import com.izakdvlpr.loginsecurity.repositories.IpRepository;
import com.izakdvlpr.loginsecurity.repositories.UserRepository;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public class LoginSecurityMain extends JavaPlugin {
  public static LoginSecurityMain instance;
  public static LoginSecurityMain getInstance() {
    return instance;
  }

  public static ArrayList usersLogged = new ArrayList();

  private final DatabaseManager databaseManager = new DatabaseManager(this);

  @Override
  public void onEnable() {
    if (!(new File(getDataFolder(), "config.yml")).exists()) saveDefaultConfig();

    new DatabaseManager(this);

    UserRepository.createUserTable();
    IpRepository.createIpTable();

    new ListenersManager(this);
    new CommandsManager(this);
  }

  @Override
  public void onDisable() {
    databaseManager.stopConnection();
  }
}
