package com.clouway.core;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 12.09.16.
 */
public class LogoutDto {
  public final String sessionId;

  public LogoutDto(String sessionId) {
    this.sessionId = sessionId;
  }

  @Override
  public String toString() {
    return "LogoutDto{" +
            "sessionId='" + sessionId + '\'' +
            '}';
  }
}
