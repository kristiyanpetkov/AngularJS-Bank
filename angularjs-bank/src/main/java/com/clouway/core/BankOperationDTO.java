package com.clouway.core;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 18.08.16.
 */
public class BankOperationDTO {
  public final Double amount;
  public final String operation;

  public BankOperationDTO(Double amount, String operation) {
    this.amount = amount;
    this.operation = operation;
  }

  @Override
  public String toString() {
    return "BankOperationDTO{" +
            "amount=" + amount +
            ", operation='" + operation + '\'' +
            '}';
  }
}
