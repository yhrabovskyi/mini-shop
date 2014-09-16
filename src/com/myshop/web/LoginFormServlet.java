package com.myshop.web;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.myshop.model.LoginSession;

public class LoginFormServlet extends HttpServlet {
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    
    HttpSession session = request.getSession();
    synchronized (session) {
      // If a user isn't logged in, return LoginFormPage
      if (session.getAttribute("loginSession") == null) {
        request.setAttribute("url", request.getRequestURI());
        // This attribute is for display message if there was attempt to log in
        if (request.getParameter("msg") != null) {
          request.setAttribute("errMsg", "User with such login and password was not found!");
        }
        request.getRequestDispatcher("/WEB-INF/LoginForm.jsp").forward(request, response);
      }
      else {
        request.getRequestDispatcher("/").forward(request, response);
      }
    }
  }
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    
    if ( (request.getParameter("userLogin") != null) && (request.getParameter("userPassword") != null) ) {
      ServletContext sc = getServletContext();
      LoginSession ls = null;
      try {
        Map<String, LoginSession> sessions = (Map<String, LoginSession>) sc.getAttribute("loginSessionsMap");
        ls = LoginSession.logsIn(request.getParameter("userLogin"), request.getParameter("userPassword"),
          (Connection) sc.getAttribute("dbConnection"), sessions);
      }
      catch (SQLException exc) {
        request.setAttribute("sqlExc", exc);
        request.getRequestDispatcher("/error").forward(request, response);
        return;
      }
      
      // If there is no such combination of login and password
      if (ls == null) {
        StringBuffer url = request.getRequestURL();
        url.append("?msg=notfound");
        // Redirect back to LoginFormPage
        response.sendRedirect(url.toString());
        return;
      }
      // Else add LoginSession object to session and redirect to main page
      else {
        HttpSession session = request.getSession();
        synchronized (session) {
          session.setAttribute("loginSession", ls);
        }
        response.sendRedirect("");
        return;
      }
    }
  }
}

