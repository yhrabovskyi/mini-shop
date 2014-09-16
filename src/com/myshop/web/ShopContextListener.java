package com.myshop.web;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import com.myshop.model.LoginSession;

public class ShopContextListener implements ServletContextListener {
  
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    
    ServletContext sc = sce.getServletContext();
    
    // Creates a map, which contains all LoginSession objects
    Map<String, LoginSession> map = new HashMap<String, LoginSession>();
    sc.setAttribute("loginSessionsMap", map);
    
    String dbName = sc.getInitParameter("dbName");
    
    try {
      // Set connection to database and set it as attribute
      String driver = "org.apache.derby.jdbc.EmbeddedDriver";
      try {
        Class.forName(driver).newInstance();
      }
      catch (java.lang.ClassNotFoundException exc) {
        // Change later
      }
      catch (java.lang.InstantiationException exc) {
        // Change later
      }
      catch (java.lang.IllegalAccessException exc) {
        // Change later
      }
      Connection conn = DriverManager.getConnection("jdbc:derby:" + dbName);
      sc.setAttribute("dbConnection", conn);
    }
    catch (SQLException exc) {
      // Change later
      return;
    }
  }
  
  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    
    ServletContext sc = sce.getServletContext();
    try {
      ((Connection) sc.getAttribute("dbConnection")).close();
    }
    catch (SQLException exc) {
      // Change later
    }
  }
}

