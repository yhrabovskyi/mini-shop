package com.myshop.web;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.myshop.model.*;

public class RegistrationFormServlet extends HttpServlet {
  
  /**
   * Shows registration form based on privileges
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    
    HttpSession session = request.getSession();
    synchronized (session) {
      // If there was an error message
      String errMsg = (String) session.getAttribute("errMsg");
      if (errMsg != null) {
        session.removeAttribute("errMsg");
        request.setAttribute("errMsg", errMsg);
      }
      request.setAttribute("url", request.getRequestURI());
      request.getRequestDispatcher("/WEB-INF/UserRegistrationForm.jsp").forward(request, response);
    }
  }
  
  /**
   * Checks if registration form is filled fine and register user or redirect to this page with error message.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    
    HttpSession session = request.getSession();
    Connection conn = (Connection) (getServletContext().getAttribute("dbConnection"));
    Map<String, String[]> paramMap = request.getParameterMap();
    // If all fields were filled fine
    String msg = null;
    try {
      msg = UserManagement.formCheck(false, paramMap, conn);
      if (msg == null) {
        // If registration in the database is completed, then forwards to complete page
        synchronized (session) {
          if ( (msg = UserManagement.addUserToDB(conn, paramMap, (LoginSession) session.getAttribute("loginSession"))) == null ) {
            response.sendRedirect("regok?login=" + paramMap.get("userLogin")[0]);
            return;
         }
        }
      }
    }
    catch (SQLException exc) {
      request.setAttribute("sqlExc", exc);
      request.getRequestDispatcher("/error").forward(request, response);
      return;
    }
    // Else adds error message and redirect to doGet method
    synchronized (session) {
      session.setAttribute("errMsg", msg);
    }
    response.sendRedirect(request.getRequestURL().toString());
  }
}

