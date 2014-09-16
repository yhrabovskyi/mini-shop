package com.myshop.model;

import java.util.*;
import java.sql.*;

/**
 * Creates an object, which represents log in state of user in the shop.
 * Also has static methods to process log in and log out.
 * Object is added to common map in web app, which contains all these objects.
 */
public class LoginSession {
  
  User user;
  
  public LoginSession(User user) {
    
    this.user = user;
  }
  
  /**
   * Logs in to the shop.
   * @param login user login
   * @param password password of login
   * @param connection connection to database
   * @param map contains all login session objects
   * @return new LoginSession object
   */
  public static LoginSession logsIn(String login, String password, Connection connection, Map<String, LoginSession> map) throws SQLException {
    
    String pswdHash = UserManagement.getPasswordHashValue(password);
    
    // Searches user with login and password
    String query = "select * " +
      "from USERS " +
      "where LOGIN = '" + login + "' " +
      "and HASH = '" + pswdHash + "'";
    Statement stmt = null;
    ResultSet rs = null;
    LoginSession ls;
    try {
      stmt = connection.createStatement();
      rs = stmt.executeQuery(query);
      // If there was found a user
      if (rs.next()) {
        ls = new LoginSession(User.createUser(rs, connection));
      }
      else {
        return null;
      }
    }
    catch (SQLException exc) {
      throw exc;
    }
    finally {
      if (stmt != null) {
        stmt.close();
      }
    }
    
    String userLogin = ls.getUser().getLogin();
    // If there is already the same LoginSession object in the shop
    if (map.containsKey(userLogin)) {
      return map.get(userLogin);
    }
    // If there isn't, then add this object and return him
    else {
      map.put(userLogin, ls);
      return ls;
    }
  }
  
  /**
   * Saves user information to database (basket, etc) and removes itself from all sessions.
   * @param connection connection to database
   * @param sessions map contains all active login sessions
   */
  public void logsOut(Connection connection, Map<String, LoginSession> sessions) throws SQLException {
    
    if (getUser().getBasket() != null) {
      getUser().getBasket().saveToDB(connection);
    }
    sessions.remove(getUser().getLogin());
  }
  
  // Fields getters
  public User getUser() {
    
    return user;
  }
}

