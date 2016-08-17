package com.clouway.adapter.http;

import com.clouway.core.ErrorCodeDto;
import com.clouway.core.RegistrationDto;
import com.clouway.core.User;
import com.clouway.core.UserRepository;
import com.clouway.core.Validator;
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
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 27.05.16.
 */
@Singleton
public class UserRegistrationService extends HttpServlet {
  private UserRepository userRepository;
  private Validator userValidator;

  @Inject
  public UserRegistrationService(UserRepository userRepository, Validator userValidator) {
    this.userRepository = userRepository;
    this.userValidator = userValidator;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    ServletOutputStream outputStream = response.getOutputStream();
    response.setContentType("application/json;charset=UTF-8");
    ServletInputStream inputStream = request.getInputStream();
    RegistrationDto req = new Gson().fromJson(new InputStreamReader(inputStream), RegistrationDto.class);

    boolean valid = userValidator.isValid(req.username, req.password, req.email, req.confirmPassword);
    if (!valid) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      outputStream.print(new Gson().toJson(new ErrorCodeDto(400)));
      return;
    }

    if (checkIfExist(req.email)) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      outputStream.print(new Gson().toJson(new ErrorCodeDto(403)));
      return;
    }

    userRepository.register(new User(req.username, req.password, req.email));
  }

  private boolean checkIfExist(String email) {
    User user = userRepository.findByEmail(email);
    return user != null;
  }
}

