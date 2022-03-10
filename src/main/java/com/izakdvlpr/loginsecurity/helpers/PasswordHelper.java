package com.izakdvlpr.loginsecurity.helpers;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHelper {
  public static Boolean verify(String password) {
    return password.matches("^.{8,}$");
  }

  private static String encrypt(String password) {
    int hashSalt = 10;

    return BCrypt.hashpw(password, BCrypt.gensalt(hashSalt));
  }

  private static String castPassword(String password) {
    if (!verify(password)) {
      throw new Error();
    }

    return password;
  }

  public static String generate(String password) {
    String makePassword = castPassword(password);

    return encrypt(makePassword);
  }

  public static boolean compare(String password, String passwordHash) {
    return BCrypt.checkpw(password, passwordHash);
  }
}
