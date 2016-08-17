package com.clouway.core;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 22.08.16.
 */
public class RegistrationDto {
  public final String email;
  public final String password;
  public final String username;
  public final String confirmPassword;

  public RegistrationDto(String email, String password, String username, String confirmPassword) {
    this.email = email;
    this.password = password;
    this.username = username;
    this.confirmPassword = confirmPassword;
  }
}
