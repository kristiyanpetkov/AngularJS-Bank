package com.clouway.core;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 16.09.16.
 */
public class InitialDataDto {
  public final String uuid;
  public final Integer sessionsCount;
  public final Double currentBalance;

  public InitialDataDto(String uuid, Integer sessionsCount, Double currentBalance) {
    this.uuid = uuid;
    this.sessionsCount = sessionsCount;
    this.currentBalance = currentBalance;
  }
}
