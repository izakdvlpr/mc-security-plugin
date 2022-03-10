package com.izakdvlpr.loginsecurity.repositories;

import com.izakdvlpr.loginsecurity.managers.DatabaseManager;

import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class UserRepository {
  private static Connection connection() {
    return DatabaseManager.getConnection();
  }

  public static void createUserTable() {
    try {
      connection().createStatement().execute("CREATE TABLE IF NOT EXISTS users (" +
        "id INT AUTO_INCREMENT PRIMARY KEY," +
        "uuid VARCHAR(255) NOT NULL," +
        "nickname VARCHAR(255) NOT NULL," +
        "password VARCHAR(255) NOT NULL," +
        "email VARCHAR(255)," +
        "verify TINYINT(1) DEFAULT 0," +
        "discord_id VARCHAR(255)," +
        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
      ");");
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§4[LoginSecurity/MySQL] Error creating user table.");

      e.printStackTrace();
    }
  }

  public static void createUser(String uuid, String nickname, String password) {
    try {
      PreparedStatement preparedStatement = connection().prepareStatement("INSERT INTO users (uuid,nickname,password) VALUES (?,?,?);");

      preparedStatement.setString(1, uuid);
      preparedStatement.setString(2, nickname);
      preparedStatement.setString(3, password);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§4[LoginSecurity/MySQL] There was an error creating a user.");

      e.printStackTrace();
    }
  }

  public static boolean verifyUser(String uuid) {
    try {
      PreparedStatement preparedStatement = connection().prepareStatement("SELECT * FROM users WHERE uuid='" + uuid + "';");

      return preparedStatement.executeQuery().next();
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§4[LoginSecurity/MySQL] An error occurred while verifying the user.");

      e.printStackTrace();
    }

    return false;
  }

  public static String getUserPassword(String uuid) {
    try {
      PreparedStatement preparedStatement = connection().prepareStatement("SELECT * FROM users WHERE uuid='" + uuid + "';");
      ResultSet resultSet;

      if ((resultSet = preparedStatement.executeQuery()).next())
        return resultSet.getString("password");
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§4[LoginSecurity/MySQL] There was an error getting the user.");

      e.printStackTrace();
    }

    return null;
  }
}
