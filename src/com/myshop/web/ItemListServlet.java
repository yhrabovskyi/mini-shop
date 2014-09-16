package com.myshop.web;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.myshop.model.*;

public class ItemListServlet extends HttpServlet {
  
  /**
   * Gets some items from table and shows it.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    
    ServletContext sc = getServletContext();
    String offset = request.getParameter("offset");
    try {
      // Get items with offset from db and add to request
      request.setAttribute("itemList", ItemManagement.getItems((Connection) sc.getAttribute("dbConnection"), offset));
      // Get number of records in items table
      request.setAttribute("numberOfRecords", new Integer(ItemManagement.getNumberOfRecords((Connection) sc.getAttribute("dbConnection"))));
      request.setAttribute("url", request.getRequestURI());
      request.getRequestDispatcher("/WEB-INF/ItemList.jsp").forward(request, response);
    }
    catch (SQLException exc) {
      request.setAttribute("sqlExc", exc);
      request.getRequestDispatcher("/error").forward(request, response);
      return;
    }
  }
}

