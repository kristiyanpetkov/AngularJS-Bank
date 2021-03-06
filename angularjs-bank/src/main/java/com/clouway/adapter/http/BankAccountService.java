package com.clouway.adapter.http;

import com.clouway.core.CurrentUser;
import com.clouway.core.FundsRepository;
import com.clouway.core.UserBalanceDto;
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
  private FundsRepository fundsRepository;

  @Inject
  public BankAccountService(UserProvider userProvider, FundsRepository fundsRepository) {
    this.userProvider = userProvider;
    this.fundsRepository = fundsRepository;
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ServletOutputStream servletOutputStream = response.getOutputStream();
    response.setContentType("application/json;charset=UTF-8");

    CurrentUser currentUser = userProvider.getCurrentUser(request);
    Double currentBalance = fundsRepository.getBalance(currentUser.email);

    servletOutputStream.print(new Gson().toJson(new UserBalanceDto(currentUser.email, currentBalance)));

  }
}
