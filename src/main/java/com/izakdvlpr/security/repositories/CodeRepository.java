package com.izakdvlpr.security.repositories;

import com.izakdvlpr.security.utils.RandomStringUtils;
import com.izakdvlpr.security.managers.DatabaseManager;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CodeRepository {
  private static Connection connection() {
    return DatabaseManager.getConnection();
  }

  public static void createCodeTable() {
    try {
      connection().createStatement().execute("CREATE TABLE IF NOT EXISTS codes (" +
        "id VARCHAR(255) NOT NULL PRIMARY KEY," +
        "code VARCHAR(255) NOT NULL," +
        "user_id VARCHAR(255) NOT NULL," +
        "used TINYINT(1) DEFAULT 0," +
        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
        "FOREIGN KEY(user_id) REFERENCES users(id)" +
      ");");
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§4[ZKSecurity/MySQL] Error creating code table.");

      e.printStackTrace();
    }
  }

  public static String createCode(String userId) {
    String uuid = String.valueOf(UUID.randomUUID());
    String code = new RandomStringUtils(6).nextString().toUpperCase();

    try {
      PreparedStatement preparedStatement = connection().prepareStatement("INSERT INTO codes (id,code,user_id) VALUES (?,?,?);");

      preparedStatement.setString(1, uuid);
      preparedStatement.setString(2, code);
      preparedStatement.setString(3, userId);
      preparedStatement.executeUpdate();

      return code;
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§4[ZKSecurity/MySQL] There was an error creating code.");

      e.printStackTrace();
    }

    return null;
  }

  public static boolean verifyCode(String code) {
    try {
      PreparedStatement preparedStatement = connection().prepareStatement("SELECT * FROM codes WHERE code='" + code + "';");

      return preparedStatement.executeQuery().next();
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§4[ZKSecurity/MySQL] An error occurred while verifying the code.");

      e.printStackTrace();
    }

    return false;
  }

  public static boolean verifyCodeByUserId(String userId) {
    try {
      PreparedStatement preparedStatement = connection().prepareStatement("SELECT * FROM codes WHERE user_id='" + userId + "';");

      return preparedStatement.executeQuery().next();
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§4[ZKSecurity/MySQL] An error occurred while verifying the code.");

      e.printStackTrace();
    }

    return false;
  }

  public static String getCodeCreatedTimestamp(String userId) {
    try {
      PreparedStatement preparedStatement = connection().prepareStatement("SELECT created_at FROM codes WHERE user_id='" + userId + "';");
      ResultSet resultSet;

      if ((resultSet = preparedStatement.executeQuery()).next())
        return resultSet.getString("created_at");
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§4[ZKSecurity/MySQL] There was an error getting the code timestamp.");

      e.printStackTrace();
    }

    return null;
  }

  public static void updateCodeUsed(String code, String state) {
    try {
      PreparedStatement preparedStatement = connection().prepareStatement("UPDATE codes " +
        "SET used='" + state + "'" +
        " " +
        "WHERE code='" + code + "'" +
        ";");

      preparedStatement.executeUpdate();
    } catch (Exception e) {
      Bukkit.getConsoleSender().sendMessage("§4[ZKSecurity/MySQL] There was an error update code used.");

      e.printStackTrace();
    }
  }
}
