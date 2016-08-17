package com.clouway.core;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 16.08.16.
 */
public class LoginDTO {
  public final String email;
  public final String password;

  public LoginDTO(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
