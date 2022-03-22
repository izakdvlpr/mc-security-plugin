package com.izakdvlpr.security.services;

import com.izakdvlpr.security.ZKSecurityMain;

import org.bukkit.configuration.file.FileConfiguration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

public class MailerService {
  static FileConfiguration config;

  static Properties properties = new Properties();

  public MailerService(ZKSecurityMain main) {
    config = main.getConfig();

    properties.put("mail.smtp.host", config.getString("smtp.driver.host"));
    properties.put("mail.smtp.port", Integer.toString(config.getInt("smtp.driver.port")));
    properties.put("mail.smtp.auth", Boolean.toString(config.getBoolean("smtp.driver.auth")));
    properties.put("mail.smtp.starttls.enable", Boolean.toString(config.getBoolean("smtp.driver.tls")));
  }

  public static void sendEmail(String from, String to, String subject, String text) {
    Session session = Session.getInstance(
      properties,
      new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(
            config.getString("smtp.auth.user"),
            config.getString("smtp.auth.pass")
          );
        }
      }
    );

    try {
      Message message = new MimeMessage(session);

      message.setFrom(new InternetAddress(from));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      message.setSubject(subject);
      message.setText(text);

      Transport.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
