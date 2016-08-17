package com.clouway.adapter.http;

import com.clouway.core.SessionRepository;
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
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 13.09.16.
 */
@Singleton
public class OnlineUsersService extends HttpServlet {
  private SessionRepository sessionRepository;

  @Inject
  public OnlineUsersService(SessionRepository sessionRepository) {
    this.sessionRepository = sessionRepository;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ServletOutputStream servletOutputStream = resp.getOutputStream();
    resp.setContentType("application/json;charset=UTF-8");
    servletOutputStream.print(new Gson().toJson(sessionRepository.getActiveSessions()));
  }
}
