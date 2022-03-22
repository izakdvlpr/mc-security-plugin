package com.izakdvlpr.security.managers;

import com.izakdvlpr.security.ZKSecurityMain;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
  private static Connection connection;

  public DatabaseManager(ZKSecurityMain main) {
    FileConfiguration config = main.getConfig();

    String host = config.getString("mysql.host");
    String port = Integer.toString(config.getInt("mysql.port"));
    String database = config.getString("mysql.database");
    String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=latin1&useConfigs=maxPerformance";
    String user =  config.getString("mysql.user");
    String password = config.getString("mysql.password");

    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    try {
      connection = DriverManager.getConnection(url, user, password);

      Bukkit.getConsoleSender().sendMessage("§a[ZKSecurity/MySQL] Success connecting to MySQL.");
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§c[ZKSecurity/MySQL] Failed to Connect to MySQL!");

      Bukkit.getConsoleSender().sendMessage("§4[ZKSecurity-MySQL] You need connect to MySQL.");
      Bukkit.getConsoleSender().sendMessage("§4[ZKSecurity/MySQL] You need connect to MySQL.");
      Bukkit.getConsoleSender().sendMessage("§4[ZKSecurity/MySQL] You need connect to MySQL.");

      e.printStackTrace();
    }
  }

  public void stopConnection() {
    try {
      if (!connection.isClosed()) {
        connection.close();

        Bukkit.getConsoleSender().sendMessage("§6[ZKSecurity/MySQL] Success while Disconnecting to MySQL!");
      }
    } catch(Exception exception) {
      Bukkit.getConsoleSender().sendMessage("§c[ZKSecurity/MySQL] Failed to Disconnect to MySQL!");
    }
  }

  public static Connection getConnection(){
    return connection;
  }
}
