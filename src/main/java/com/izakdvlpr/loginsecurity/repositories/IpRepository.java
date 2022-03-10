package com.izakdvlpr.loginsecurity.repositories;

import com.izakdvlpr.loginsecurity.managers.DatabaseManager;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IpRepository {
  private static Connection connection() {
    return DatabaseManager.getConnection();
  }

  public static void createIpTable() {
    try {
      connection().createStatement().execute("CREATE TABLE IF NOT EXISTS ips (ip VARCHAR(255) NOT NULL);");
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§4[LoginSecurity/MySQL] Error creating user table.");

      e.printStackTrace();
    }
  }

  public static void addIp(String ip) {
    try {
      PreparedStatement preparedStatement = connection().prepareStatement("INSERT INTO ips (ip) VALUES (?);");

      preparedStatement.setString(1, ip);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§4[LoginSecurity/MySQL] There was an error adding the user's ip..");

      e.printStackTrace();
    }
  }

  public static boolean findIp(String ip) {
    try {
      PreparedStatement preparedStatement = connection().prepareStatement("SELECT * FROM ips WHERE ip='" + ip + "';");

      return preparedStatement.executeQuery().next();
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§4[LoginSecurity/MySQL] There was an error getting the ip.");

      e.printStackTrace();
    }

    return false;
  }
}
