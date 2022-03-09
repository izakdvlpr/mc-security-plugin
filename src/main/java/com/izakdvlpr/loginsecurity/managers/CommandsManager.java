package com.izakdvlpr.loginsecurity.managers;

import com.izakdvlpr.loginsecurity.LoginSecurityMain;

import com.izakdvlpr.loginsecurity.commands.LoginCommand;
import com.izakdvlpr.loginsecurity.commands.RegisterCommand;

public class CommandsManager {
  public CommandsManager(LoginSecurityMain main) {
    LoginCommand login = new LoginCommand();
    RegisterCommand register = new RegisterCommand();

    main.getCommand("login").setExecutor(login);
    main.getCommand("register").setExecutor(register);
  }
}
