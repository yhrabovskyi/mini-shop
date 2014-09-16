package com.myshop.web;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.myshop.model.*;

public class ItemFormServlet extends HttpServlet {
  
  /**
   * Checks if user can manage items, take item from db if exists, delete and show appropriate page or show error message and form.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    
    ServletContext sc = getServletContext();
    HttpSession session = request.getSession();
    User user = ((LoginSession) session.getAttribute("loginSession")).getUser();
    synchronized (session) {
      // If user can't manage items then do nothing (return from method)
      if ( (user == null) || !user.isItemManager() ) {
        return;
      }
      
      // Get item from database if there is a parameter ID
      Item item = null;
      String id = request.getParameter("id");
      try {
        if ( (id != null) && !id.equals("") ) {
          item = ItemManagement.getItem(Integer.parseInt(request.getParameter("id")), (Connection) sc.getAttribute("dbConnection"));
          request.setAttribute("item", item);
        }
        
        // If op parameter is delete
        String op = request.getParameter("op");
        if ( (op != null) && (op.equals("delete")) ) {
          // Delete item and forward to appropriate page
          user.getItemOperator().removeItemFromDB(item, (Connection) sc.getAttribute("dbConnection"));
          request.getRequestDispatcher("/WEB-INF/ItemFormComplete.jsp").forward(request, response);
          return;
        }
      }
      catch (SQLException exc) {
        request.setAttribute("sqlExc", exc);
        request.getRequestDispatcher("/error").forward(request, response);
        return;
      }
      
      // Next commands will be executed if parameter op was add or edit
      // If there was an error message
      String errMsg = (String) session.getAttribute("errMsg");
      if (errMsg != null) {
        session.removeAttribute("errMsg");
        request.setAttribute("errMsg", errMsg);
      }
      request.setAttribute("url", request.getRequestURI());
      request.getRequestDispatcher("/WEB-INF/ItemForm.jsp").forward(request, response);
    }
  }
  
  /**
   * Checks if user can manage items, checks how form is filled, send redirect or does appropriate operation.
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
      // If user can't manage items then do nothing (return from method)
      if ( (user == null) || !user.isItemManager() ) {
        return;
      }
      String msg = user.getItemOperator().checkForm(request.getParameterMap());
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
      Item item = null;
      try {
        if (op.equals("edit")) {
          item = Item.createItem(conn, request.getParameterMap(), id);
          user.getItemOperator().editItemInDB(item, conn);
        }
        if (op.equals("add")) {
          item = Item.createItem(conn, request.getParameterMap());
          user.getItemOperator().addItemToDB(item, conn);
        }
        request.setAttribute("item", item);
        request.getRequestDispatcher("/WEB-INF/ItemFormComplete.jsp").forward(request, response);
      }
      catch (SQLException exc) {
        request.setAttribute("sqlExc", exc);
        request.getRequestDispatcher("/error").forward(request, response);
        return;
      }
    }
  }
}

