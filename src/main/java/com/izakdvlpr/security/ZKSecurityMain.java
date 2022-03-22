package com.izakdvlpr.security;

import com.izakdvlpr.security.managers.CommandsManager;
import com.izakdvlpr.security.repositories.IpRepository;
import com.izakdvlpr.security.managers.DatabaseManager;
import com.izakdvlpr.security.managers.ListenersManager;

import com.izakdvlpr.security.repositories.UserRepository;
import com.izakdvlpr.security.repositories.CodeRepository;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public class ZKSecurityMain extends JavaPlugin {
  public static ZKSecurityMain instance;
  public static ZKSecurityMain getInstance() {
    return instance;
  }

  public static ArrayList usersLogged = new ArrayList();

  private final DatabaseManager databaseManager = new DatabaseManager(this);

  public ZKSecurityMain() {
    ZKSecurityMain.instance = this;
  }

  @Override
  public void onEnable() {
    if (!(new File(getDataFolder(), "config.yml")).exists()) saveDefaultConfig();

    new DatabaseManager(this);

    UserRepository.createUserTable();
    IpRepository.createIpTable();
    CodeRepository.createCodeTable();

    new ListenersManager(this);
    new CommandsManager(this);
  }

  @Override
  public void onDisable() {
    databaseManager.stopConnection();
  }
}
