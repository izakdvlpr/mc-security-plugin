package com.izakdvlpr.loginsecurity.managers;

import com.izakdvlpr.loginsecurity.LoginSecurityMain;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
  private static Connection connection;

  public DatabaseManager(LoginSecurityMain main) {
    String host = main.getConfig().getString("MySQL.host");
    String port = Integer.toString(main.getConfig().getInt("MySQL.port"));
    String database = main.getConfig().getString("MySQL.database");
    String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=latin1&useConfigs=maxPerformance";
    String user =  main.getConfig().getString("MySQL.user");
    String password = main.getConfig().getString("MySQL.password");

    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    try {
      connection = DriverManager.getConnection(url, user, password);

      Bukkit.getConsoleSender().sendMessage("§a[LoginSecurity/MySQL] Success connecting to MySQL.");
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§c[LoginSecurity/MySQL] Failed to Connect to MySQL!");

      Bukkit.getConsoleSender().sendMessage("§4[LoginSecurity-MySQL] You need connect to MySQL.");
      Bukkit.getConsoleSender().sendMessage("§4[LoginSecurity/MySQL] You need connect to MySQL.");
      Bukkit.getConsoleSender().sendMessage("§4[LoginSecurity/MySQL] You need connect to MySQL.");

      e.printStackTrace();
    }
  }

  public void stopConnection() {
    try {
      if (!connection.isClosed()) {
        connection.close();

        Bukkit.getConsoleSender().sendMessage("§6[LoginSecurity/MySQL] SUCCESS while Disconnecting to MySQL!");
      }
    } catch(Exception exception) {
      Bukkit.getConsoleSender().sendMessage("§c[LoginSecurity/MySQL] Failed to Disconnect to MySQL!");
    }
  }

  public static Connection getConnection(){
    return connection;
  }
}
