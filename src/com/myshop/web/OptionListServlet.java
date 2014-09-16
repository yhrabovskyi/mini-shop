package com.myshop.web;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

import com.myshop.model.*;

public class OptionListServlet extends HttpServlet {
  
  /**
   * Gets and shows list option available for user.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    
    HttpSession session = request.getSession();
    synchronized (session) {
      LoginSession ls = (LoginSession) session.getAttribute("loginSession");
      if (ls != null) {
        User user = ls.getUser();
        request.setAttribute("optionList", user.getProfileOption());
        request.getRequestDispatcher("/WEB-INF/OptionList.jsp").forward(request, response);
      }
      else {
        response.sendRedirect("");
      }
    }
  }
}

