package com.clouway.core;

import com.clouway.adapter.persistence.PersistenceModule;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import javax.servlet.http.Cookie;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 06.07.16.
 */
public class ApplicationModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new PersistenceModule());
  }

  @Provides
  public CookieFinder getCookie() {
    return new CookieFinderImpl();
  }

  @Provides
  public RandomGenerator getUUID() {
    return new RandomGeneratorImpl();
  }

  @Provides
  public Validator getValidator() {
    return new ValidatorImpl();
  }

  @Provides
  public UserProvider getCurrentUser(CookieFinder cookieFinder,SessionRepository sessionRepository) {
    return new CurrentUserProvider(cookieFinder,sessionRepository);
  }
}
