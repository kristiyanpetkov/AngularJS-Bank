package com.clouway.core;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 16.09.16.
 */
public class UserBalanceDto {
  public final String email;
  public final Double balance;

  public UserBalanceDto(String email, Double balance) {
    this.email = email;
    this.balance = balance;
  }
}
