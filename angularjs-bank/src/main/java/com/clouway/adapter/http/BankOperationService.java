package com.clouway.adapter.http;

import com.clouway.core.*;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 08.06.16.
 */
@Singleton
public class BankOperationService extends HttpServlet {
  private FundsRepository fundsRepository;
  private Validator validator;
  private UserProvider userProvider;

  @Inject
  public BankOperationService(FundsRepository fundsRepository, Validator validator, UserProvider userProvider) {
    this.fundsRepository = fundsRepository;
    this.validator = validator;
    this.userProvider = userProvider;
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ServletOutputStream servletOutputStream = response.getOutputStream();
    response.setContentType("application/json;charset=UTF-8");
    ServletInputStream inputStream = request.getInputStream();
    BankOperationDTO req = new Gson().fromJson(new InputStreamReader(inputStream), BankOperationDTO.class);
    boolean success = false;

    CurrentUser currentUser = userProvider.getCurrentUser(request);
    String operation = req.operation;

    if (!validator.isValid(req.amount)) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      servletOutputStream.print(new Gson().toJson(new ErrorCodeDTO(422)));
      return;
    }

    if (operation.equalsIgnoreCase("Deposit")) {
      servletOutputStream.print(new Gson().toJson(fundsRepository.deposit(req.amount, currentUser.email)));
      response.setStatus(HttpServletResponse.SC_ACCEPTED);
      return;
    }

    if (operation.equalsIgnoreCase("Withdraw")) {
      success = fundsRepository.withdraw(req.amount, currentUser.email);
    }

    if (success) {
      response.setStatus(HttpServletResponse.SC_ACCEPTED);
      servletOutputStream.print(new Gson().toJson(fundsRepository.getBalance(currentUser.email)));
      return;
    }

    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    servletOutputStream.print(new Gson().toJson(new ErrorCodeDTO(406)));
  }
}
