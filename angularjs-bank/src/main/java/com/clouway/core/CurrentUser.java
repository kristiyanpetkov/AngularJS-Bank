package com.clouway.core;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 14.09.16.
 */
public class CurrentUser {
  public final String email;
  public final String sessionId;

  public CurrentUser(String email, String sessionId) {
    this.email = email;
    this.sessionId = sessionId;
  }
}
