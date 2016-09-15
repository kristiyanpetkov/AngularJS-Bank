package com.clouway.core;


/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 14.06.16.
 */
public class Transaction {
  public Integer id;
  public String date;
  public String email;
  public String operation;
  public Double amount;

  public Transaction(Integer id, String date, String email, String operation, Double amount) {
    this.id = id;
    this.date = date;
    this.email = email;
    this.operation = operation;
    this.amount = amount;
  }
}
