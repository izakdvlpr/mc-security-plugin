package com.izakdvlpr.loginsecurity.helpers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHelper {
  public static String encrypt(String password) {
    byte[] arrayOfByte = {};

    try {
      arrayOfByte = MessageDigest.getInstance("MD5").digest(password.getBytes(StandardCharsets.UTF_8));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    StringBuilder stringBuilder = new StringBuilder();

    byte b = 0;
    while (b < arrayOfByte.length) {
      stringBuilder.append(String.format("%02X", 0xFF & arrayOfByte[b]));
      b++;
    }

    return stringBuilder.toString();
  }
}
