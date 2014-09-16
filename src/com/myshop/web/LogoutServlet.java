package com.myshop.web;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Map;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.myshop.model.LoginSession;

public class LogoutServlet extends HttpServlet {
  
  /**
   * Logs out user from the shop.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    
    HttpSession session = request.getSession();
    synchronized (session) {
      LoginSession ls = (LoginSession) session.getAttribute("loginSession");
      // If a user isn't logged in, return main page
      if (ls == null) {
        // Do nothing.
      }
      else {
        session.removeAttribute("loginSession");
        try {
          ls.logsOut((Connection) (getServletContext().getAttribute("dbConnection")),
            (Map<String, LoginSession>) (getServletContext().getAttribute("loginSessionsMap")));
        }
        catch (SQLException exc) {
          request.setAttribute("sqlExc", exc);
          request.getRequestDispatcher("/error").forward(request, response);
          return;
        }
      }
    }
    response.sendRedirect("");
  }
}

