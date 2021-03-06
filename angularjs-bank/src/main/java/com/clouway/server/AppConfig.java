package com.clouway.server;

import com.clouway.adapter.http.BankServletModule;
import com.clouway.core.ApplicationModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 06.07.16.
 */
public class AppConfig extends GuiceServletContextListener {
  @Override
  protected Injector getInjector() {
    return Guice.createInjector
            (new BankServletModule(), new ApplicationModule());
  }
}
