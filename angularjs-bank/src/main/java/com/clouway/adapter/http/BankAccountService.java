package com.clouway.adapter.http;

import com.clouway.core.CurrentUser;
import com.clouway.core.UserProvider;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 08.06.16.
 */
@Singleton
public class BankAccountService extends HttpServlet {
  private UserProvider userProvider;

  @Inject
  public BankAccountService(UserProvider userProvider) {
    this.userProvider = userProvider;
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ServletOutputStream servletOutputStream = response.getOutputStream();
    response.setContentType("application/json;charset=UTF-8");

    CurrentUser currentUser = userProvider.getCurrentUser(request);
    servletOutputStream.print(new Gson().toJson(currentUser.email));

  }
}
