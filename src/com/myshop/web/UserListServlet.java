package com.myshop.web;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.myshop.model.*;

public class UserListServlet extends HttpServlet {
  
  /**
   * If user can see this page, than gets some users from the table and shows it.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    
    HttpSession session = request.getSession();
    User user = null;
    synchronized (session) {
      user = ((LoginSession) session.getAttribute("loginSession")).getUser();
      // If user can't manage users
      if (!user.isUserManager()) {
        return;
      }
    }
    ServletContext sc = getServletContext();
    String offset = request.getParameter("offset");
    try {
      // Get users with offset from db and add to request
      request.setAttribute("userList", user.getUserOperator().getUsers(offset, (Connection) sc.getAttribute("dbConnection")));
      // Get number of records in users table
      request.setAttribute("numberOfRecords", new Integer(user.getUserOperator().getNumberOfRecords((Connection) sc.getAttribute("dbConnection"))));
    }
    catch (SQLException exc) {
      request.setAttribute("sqlExc", exc);
      request.getRequestDispatcher("/error").forward(request, response);
      return;
    }
    request.setAttribute("url", request.getRequestURI());
    request.getRequestDispatcher("/WEB-INF/UserList.jsp").forward(request, response);
  }
}

