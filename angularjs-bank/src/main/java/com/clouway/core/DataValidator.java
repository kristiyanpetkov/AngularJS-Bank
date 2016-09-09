package com.clouway.core;

import com.google.inject.Singleton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 27.05.16.
 */
@Singleton
public class DataValidator implements Validator {

  public boolean isValid(String username, String password, String email, String confirmPassword) {
    boolean result = false;
    String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    String passwordPattern = "^[a-zA-Z0-9]{6,16}+$";
    String usernamePattern = "^[a-zA-Z0-9]{6,16}+$";
    Pattern ePattern = Pattern.compile(emailPattern);
    Pattern pPattern = Pattern.compile(passwordPattern);
    Pattern uPattern = Pattern.compile(usernamePattern);
    Matcher eMatcher = ePattern.matcher(email);
    Matcher pMatcher = pPattern.matcher(password);
    Matcher uMatcher = uPattern.matcher(username);
    if (eMatcher.matches() && pMatcher.matches() && uMatcher.matches() && password.equals(confirmPassword)) {
      result = true;
    }
    return result;
  }
}
