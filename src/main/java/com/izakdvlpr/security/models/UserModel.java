package com.izakdvlpr.security.models;

public class UserModel {
  public String id;
  public String nickname;
  public String password;
  public String email;
  public Integer verify;
  public String discord_id;
  public String created_at;

  public void setData(String id, String nickname, String password, String email, Integer verify, String discord_id, String created_at) {
    this.id = id;
    this.nickname = nickname;
    this.password = password;
    this.email = email;
    this.verify = verify;
    this.discord_id = discord_id;
    this.created_at = created_at;
  }

  public UserModel getObject() {
    return new UserModel();
  }
}
