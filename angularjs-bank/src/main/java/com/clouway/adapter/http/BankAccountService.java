package com.clouway.adapter.http;

import com.clouway.core.CookieFinder;
import com.clouway.core.ErrorCodeDTO;
import com.clouway.core.SessionRepository;
import com.clouway.core.Validator;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 08.06.16.
 */
@Singleton
public class BankAccountService extends HttpServlet {
  private Validator validator;
  private CookieFinder cookieFinder;
  private SessionRepository sessionRepository;

  @Inject
  public BankAccountService(Validator validator, CookieFinder cookieFinder, SessionRepository sessionRepository) {
    this.validator = validator;
    this.cookieFinder = cookieFinder;
    this.sessionRepository = sessionRepository;
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ServletOutputStream servletOutputStream = response.getOutputStream();
    response.setContentType("application/json;charset=UTF-8");

    Cookie[] cookies = request.getCookies();
    Cookie cookie = cookieFinder.find(cookies);

    if (cookie == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      servletOutputStream.print(new Gson().toJson(new ErrorCodeDTO(401)));
      return;
    }

    String sessionId = cookie.getValue();
    String email = sessionRepository.getCurrentUserEmail(sessionId);
    servletOutputStream.print(new Gson().toJson(email));

  }
}
