package com.myshop.web;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import com.myshop.model.LoginSession;
import com.myshop.model.Basket;

public class BasketServlet extends HttpServlet {
  
  /**
   * Does operations with items and basket and dispatch to basket page.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    
    ServletContext sc = getServletContext();
    Connection conn = (Connection) sc.getAttribute("dbConnection");
    HttpSession session = request.getSession();
    synchronized (session) {
      LoginSession ls = (LoginSession) session.getAttribute("loginSession");
      if (ls == null) {
        return;
      }
      Basket basket = ls.getUser().getBasket();
      // If there is no basket, do nothing
      if (basket == null) {
        return;
      }
      try {
        String op = request.getParameter("op");
        String id = request.getParameter("id");
        if (op != null) {
          if (op.equals("add")) {
            basket.addItem(id, conn);
          }
          if (op.equals("delete")) {
            basket.removeItem(id, conn);
          }
        }        
        request.setAttribute("itemList", basket.getItemList());
        request.getRequestDispatcher("/WEB-INF/Basket.jsp").forward(request, response);
      }
      catch (SQLException exc) {
        request.setAttribute("sqlExc", exc);
        request.getRequestDispatcher("/error").forward(request, response);
        return;
      }
    }
  }
}

