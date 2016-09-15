package com.clouway.adapter.http;

import com.clouway.core.FundsRepository;
import com.clouway.core.Transaction;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 13.06.16.
 */
@Singleton
public class TransactionHistoryService extends HttpServlet {
  private FundsRepository fundsRepository;
  public static final int PAGE_SIZE = 20;

  @Inject
  public TransactionHistoryService(FundsRepository fundsRepository) {
    this.fundsRepository = fundsRepository;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    ServletOutputStream servletOutputStream = response.getOutputStream();
    response.setContentType("application/json;charset=UTF-8");

    List<Transaction> transactions = null;
    Integer currentPage;

    if (request.getParameter("page") == null) {
      currentPage = 1;
    } else {
      currentPage = Integer.valueOf(request.getParameter("page"));
    }

    if (currentPage >= 1) {
      transactions = fundsRepository.getHistory(PAGE_SIZE, (currentPage - 1) * PAGE_SIZE);
    }

    servletOutputStream.print(new Gson().toJson(transactions));
  }
}
