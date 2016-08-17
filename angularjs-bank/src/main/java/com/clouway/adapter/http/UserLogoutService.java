package com.clouway.adapter.http;

import com.clouway.core.LogoutDto;
import com.clouway.core.SessionRepository;
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
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 02.06.16.
 */
@Singleton
public class UserLogoutService extends HttpServlet {
  private SessionRepository sessionRepository;


  @Inject
  public UserLogoutService(SessionRepository sessionRepository) {
    this.sessionRepository = sessionRepository;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    ServletOutputStream servletOutputStream = resp.getOutputStream();
    resp.setContentType("application/json;charset=UTF-8");
    ServletInputStream inputStream = req.getInputStream();
    LogoutDto logoutReq = new Gson().fromJson(new InputStreamReader(inputStream), LogoutDto.class);


    if (logoutReq.sessionId != null) {
      sessionRepository.delete(logoutReq.sessionId);
      resp.setStatus(200);
    }
  }
}
