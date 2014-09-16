package com.myshop.web;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Map;
import java.sql.Connection;
import java.sql.SQLException;

import com.myshop.model.LoginSession;

public class ShopSessionListener implements HttpSessionListener {
  
  @Override
  public void sessionCreated(HttpSessionEvent se) {
    
    // Do nothing
  }
  
  @Override
  public void sessionDestroyed(HttpSessionEvent se) {
    
    HttpSession session = se.getSession();
    ServletContext sc = session.getServletContext();
    LoginSession ls = (LoginSession) session.getAttribute("loginSession");
    try {
      ls.logsOut((Connection) sc.getAttribute("dbConnection"), (Map<String, LoginSession>) sc.getAttribute("loginSessionsMap"));
    }
    catch (SQLException exc) {
      // Change later
    }
  }
}

