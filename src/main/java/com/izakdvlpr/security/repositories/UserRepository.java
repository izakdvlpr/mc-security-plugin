package com.izakdvlpr.security.repositories;

import com.izakdvlpr.security.managers.DatabaseManager;
import com.izakdvlpr.security.models.UserModel;

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
        "id VARCHAR(255) NOT NULL PRIMARY KEY," +
        "nickname VARCHAR(255) NOT NULL," +
        "password VARCHAR(255) NOT NULL," +
        "email VARCHAR(255)," +
        "verify TINYINT(1) DEFAULT 0," +
        "prime TINYINT(1) DEFAULT 0," +
        "discord_id VARCHAR(255)," +
        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
      ");");
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§4[ZKSecurity/MySQL] Error creating user table.");

      e.printStackTrace();
    }
  }

  public static void createUser(String id, String nickname, String password) {
    try {
      PreparedStatement preparedStatement = connection().prepareStatement("INSERT INTO users (id,nickname,password) VALUES (?,?,?);");

      preparedStatement.setString(1, id);
      preparedStatement.setString(2, nickname);
      preparedStatement.setString(3, password);
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      Bukkit.getConsoleSender().sendMessage("§4[ZKSecurity/MySQL] There was an error creating a user.");

      e.printStackTrace();
    }
  }

  public static UserModel findUserById(String id) {
    UserModel user = new UserModel();

    try {
      PreparedStatement preparedStatement = connection().prepareStatement("SELECT * FROM users WHERE id='" + id + "';");
      ResultSet resultSet;

      if ((resultSet = preparedStatement.executeQuery()).next()) {
        user.setData(
          resultSet.getString("id"),
          resultSet.getString("nickname"),
          resultSet.getString("password"),
          resultSet.getString("email"),
          resultSet.getInt("verify"),
          resultSet.getInt("prime"),
          resultSet.getString("discord_id"),
          resultSet.getString("created_at")
        );

        return user.getObject();
      }
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§4[ZKSecurity/MySQL] There was an error getting the user password.");

      e.printStackTrace();
    }

    return null;
  }

  public static boolean verifyUser(String id) {
    try {
      PreparedStatement preparedStatement = connection().prepareStatement("SELECT * FROM users WHERE id='" + id + "';");

      return preparedStatement.executeQuery().next();
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§4[ZKSecurity/MySQL] An error occurred while verifying the user.");

      e.printStackTrace();
    }

    return false;
  }

  public static String getUserPassword(String id) {
    try {
      PreparedStatement preparedStatement = connection().prepareStatement("SELECT password FROM users WHERE id='" + id + "';");
      ResultSet resultSet;

      if ((resultSet = preparedStatement.executeQuery()).next())
        return resultSet.getString("password");
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§4[ZKSecurity/MySQL] There was an error getting the user password.");

      e.printStackTrace();
    }

    return null;
  }

  public static void updateUserEmail(String id, String email) {
    try {
      PreparedStatement preparedStatement = connection().prepareStatement("UPDATE users " +
        "SET email='" + email + "'" +
        " " +
        "WHERE id='" + id + "'" +
        ";");

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      Bukkit.getConsoleSender().sendMessage("§4[ZKSecurity/MySQL] There was an error update user email.");

      e.printStackTrace();
    }
  }

  public static void updateUserVerify(String id, String state) {
    try {
      PreparedStatement preparedStatement = connection().prepareStatement("UPDATE users " +
        "SET verify='" + state + "'" +
        " " +
        "WHERE id='" + id + "'" +
      ";");

      preparedStatement.executeUpdate();
    } catch (Exception e) {
      Bukkit.getConsoleSender().sendMessage("§4[ZKSecurity/MySQL] There was an error update user email.");

      e.printStackTrace();
    }
  }
}
