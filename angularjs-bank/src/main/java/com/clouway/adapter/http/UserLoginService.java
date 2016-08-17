package com.clouway.adapter.http;

import com.clouway.core.ErrorCodeDTO;
import com.clouway.core.LoginDTO;
import com.clouway.core.RandomGenerator;
import com.clouway.core.Session;
import com.clouway.core.SessionRepository;
import com.clouway.core.UserRepository;
import com.clouway.core.Validator;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 27.05.16.
 */
@Singleton
public class UserLoginService extends HttpServlet {
  private UserRepository userRepository;
  private SessionRepository sessionRepository;
  private Validator userValidator;
  private RandomGenerator randomGenerator;

  @Inject
  public UserLoginService(UserRepository userRepository, SessionRepository sessionRepository, Validator userValidator, RandomGenerator randomGenerator) {
    this.userRepository = userRepository;
    this.sessionRepository = sessionRepository;
    this.userValidator = userValidator;
    this.randomGenerator = randomGenerator;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    ServletOutputStream servletOutputStream = response.getOutputStream();
    response.setContentType("application/json;charset=UTF-8");
    ServletInputStream inputStream = request.getInputStream();
    LoginDTO req = new Gson().fromJson(new InputStreamReader(inputStream), LoginDTO.class);

    boolean valid = userValidator.isValid(req.email, req.password);
    if (!valid) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      servletOutputStream.print(new Gson().toJson(new ErrorCodeDTO(400)));
      return;
    }

    boolean isAuthenticated = userRepository.authenticate(req.email, req.password);
    if (isAuthenticated) {
      String uuid = randomGenerator.generateUUID();
      Session session = new Session(req.email, uuid);
      sessionRepository.create(session);
      servletOutputStream.print(new Gson().toJson(uuid));
      return;
    }

    response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    servletOutputStream.print(new Gson().toJson(new ErrorCodeDTO(406)));
  }
}
