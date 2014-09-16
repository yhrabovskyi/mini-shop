package com.myshop.web;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.myshop.model.*;

public class UserEditFormServlet extends HttpServlet {
  
  /**
   * Checks if authorized user is accessed here and do appropriate action.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    
    ServletContext sc = getServletContext();
    HttpSession session = request.getSession();
    String op = request.getParameter("op");
    String id = request.getParameter("id");
    Connection conn = (Connection) sc.getAttribute("dbConnection");
    User user = null;
    synchronized (session) {
      LoginSession ls = (LoginSession) session.getAttribute("loginSession");
      // No authorized users can't go here
      if (ls == null) {
        return;
      }
      user = ls.getUser();
      // If op parameter is delete
      if ( (op != null) && (op.equals("delete")) && user.isUserManager() ) {
        try {
          user.getUserOperator().removeUserFromDB(request.getParameter("id"), conn);
        }
        catch (SQLException exc) {
          request.setAttribute("sqlExc", exc);
          request.getRequestDispatcher("/error").forward(request, response);
          return;
        }
        request.getRequestDispatcher("/WEB-INF/UserEditFormComplete.jsp").forward(request, response);
        return;
      }
      request.setAttribute("url", request.getRequestURI());
      // If op parameter is "edit" and user can manage users or edits himself
      if ( (op != null) && op.equals("edit") && (user.isUserManager() || (Integer.parseInt(id) == user.getId())) ) {
        try {
          user = UserManagement.getUser(id, conn);
        }
        catch (SQLException exc) {
          request.setAttribute("sqlExc", exc);
          request.getRequestDispatcher("/error").forward(request, response);
          return;
        }
        request.setAttribute("user", user);
        String errMsg = (String) session.getAttribute("errMsg");
        if (errMsg != null) {
          session.removeAttribute("errMsg");
          request.setAttribute("errMsg", errMsg);
        }
        request.getRequestDispatcher("/WEB-INF/UserEditForm.jsp").forward(request, response);
        return;
      }
    }
  }
  
  /**
   * Checks if authorized user is accessed here and do appropriate action
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    
    ServletContext sc = getServletContext();
    Connection conn = (Connection) sc.getAttribute("dbConnection");
    HttpSession session = request.getSession();
    User user = null;
    String op = request.getParameter("op");
    String id = request.getParameter("id");
    synchronized (session) {
      user = ((LoginSession) session.getAttribute("loginSession")).getUser();
      // No authorized users can't go here
      if (user == null) {
        return;
      }
      // If op parameter is "edit" and user can manage users or edits himself
      if ( (op != null) && op.equals("edit") && (user.isUserManager() || (Integer.parseInt(id) == user.getId())) ) {
        String msg = null;
        // Check how form is filled
        try {
          msg = UserManagement.formCheck(true, request.getParameterMap(), conn);
        }
        catch (SQLException exc) {
          request.setAttribute("sqlExc", exc);
          request.getRequestDispatcher("/error").forward(request, response);
          return;
        }
        // If there is error message, set error message, form URL with current parameters and send redirect
        if (msg != null) {
          session.setAttribute("errMsg", msg);
          StringBuffer sb = request.getRequestURL();
          sb.append("?op=");
          sb.append(op);
          sb.append("&id=");
          sb.append(id);
          response.sendRedirect(sb.toString());
          return;
        }
        // If checkForm has nothing to mention
        try {
          UserManagement.editUserAndInDB(id, request.getParameterMap(), conn);
        }
        catch (SQLException exc) {
          request.setAttribute("sqlExc", exc);
          request.getRequestDispatcher("/error").forward(request, response);
          return;
        }
        request.getRequestDispatcher("/WEB-INF/UserEditFormComplete.jsp").forward(request, response);
      }
    }
  }
}

