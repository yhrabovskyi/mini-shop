package com.myshop.web;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.myshop.model.*;

public class OrderVerifiedServlet extends HttpServlet {
  
  /**
   * Show appropriate order page.
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
      if (request.getParameter("dialog").equals("yes")) {
        String msg = null;
        Order order = null;
        try {
          order = new Order();
          msg = order.form(basket, conn);
        }
        catch (SQLException exc) {
          request.setAttribute("sqlExc", exc);
          request.getRequestDispatcher("/error").forward(request, response);
          return;
        }
        // If there is a message (that means can't form an order)
        if (msg != null) {
          request.setAttribute("errMsg", msg);
        }
      }
      if (request.getParameter("dialog").equals("show")) {
        request.setAttribute("itemList", basket.getItemList());
      }
      request.setAttribute("url", request.getRequestURI());
      request.getRequestDispatcher("/WEB-INF/OrderVerified.jsp").forward(request, response);
    }
  }
}

