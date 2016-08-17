package com.clouway.core;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 18.08.16.
 */
public class BankOperationDto {
  public final Double amount;
  public final String operation;

  public BankOperationDto(Double amount, String operation) {
    this.amount = amount;
    this.operation = operation;
  }

  @Override
  public String toString() {
    return "BankOperationDto{" +
            "amount=" + amount +
            ", operation='" + operation + '\'' +
            '}';
  }
}
