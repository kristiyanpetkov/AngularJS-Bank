package com.clouway.core;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 14.09.16.
 */
@Singleton
public class CurrentUserProvider implements UserProvider {
  private CookieFinder cookieFinder;
  private SessionRepository sessionRepository;

  @Inject
  public CurrentUserProvider(CookieFinder cookieFinder, SessionRepository sessionRepository) {
    this.cookieFinder = cookieFinder;
    this.sessionRepository = sessionRepository;
  }

  @Override
  public CurrentUser getCurrentUser(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    Cookie cookie = cookieFinder.find(cookies);
    String sessionId = cookie.getValue();
    String email = sessionRepository.getCurrentUserEmail(sessionId);
    return new CurrentUser(email,sessionId);
  }
}
