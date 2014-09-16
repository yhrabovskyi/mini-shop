package com.myshop.web;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ErrorServlet extends HttpServlet {
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    
    processError(request, response);
  }
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    
    processError(request, response);
  }
  
  private void processError(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    SQLException exc = (SQLException) request.getAttribute("sqlExc");
    StringBuffer sb = new StringBuffer();
    sb.append("Error code is " + exc.getErrorCode());
    sb.append("\n" + exc.getSQLState() + "\n" + exc.toString());
    request.setAttribute("errMsg", sb.toString());
    request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
    return;
  }
}

