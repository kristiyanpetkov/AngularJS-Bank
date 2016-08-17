package com.clouway.core;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 27.05.16.
 */
public interface Validator {

  boolean isValid(String username, String password, String email, String confirmPassword);

  boolean isValid(String email, String password);
}
