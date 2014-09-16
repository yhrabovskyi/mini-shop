package com.myshop.web;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.myshop.model.*;

public class ItemDescriptionServlet extends HttpServlet {
  
  /**
   * Gets item from table and shows it.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    
    ServletContext sc = getServletContext();
    String id = request.getParameter("id");
    try {
      request.setAttribute("item", ItemManagement.getItem(Integer.parseInt(id), (Connection) sc.getAttribute("dbConnection")));
      request.getRequestDispatcher("/WEB-INF/ItemDescription.jsp").forward(request, response);
    }
    catch (SQLException exc) {
      request.setAttribute("sqlExc", exc);
      request.getRequestDispatcher("/error").forward(request, response);
      return;
    }
  }
}

