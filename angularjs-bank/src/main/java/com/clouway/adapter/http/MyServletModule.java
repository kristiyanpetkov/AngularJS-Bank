package com.clouway.adapter.http;

import com.google.inject.servlet.ServletModule;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 06.07.16.
 */
public class MyServletModule extends ServletModule {
  @Override
  protected void configureServlets() {
    filter("/*").through(ConnectionFilter.class);
    filter("/*").through(SecurityFilter.class);

    serve("/r/register/").with(UserRegistrationService.class);
    serve("/r/login/").with(UserLoginService.class);
    serve("/r/account/").with(BankAccountService.class);


  }
}
