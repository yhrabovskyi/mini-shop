package com.myshop.model;

import java.lang.StringBuilder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

/**
 * Represents object that operates with users in database.
 * Also has static methods to help perform user registration.
 */
public class UserManagement {
  
  /**
   * Removes user from db.
   * @param id id of user
   * @param connection connection to db
   */
  public void removeUserFromDB(String id, Connection connection) throws SQLException {
    
    Statement stmt = null;
    String upd_baskets = "delete from BASKETS where USER_ID = " + id;
    String upd_users = "delete from USERS where USER_ID = " + id;
    try {
      stmt = connection.createStatement();
      stmt.executeUpdate(upd_baskets);
      stmt.executeUpdate(upd_users);
    }
    catch (SQLException exc) {
      throw exc;
    }
    finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }
  
  /**
   * Edits user record in db.
   * @param id id of user to edit
   * @param param parameters from the form
   * @param connection connection to db
   */
  public static void editUserAndInDB(String id, Map<String, String[]> param, Connection connection) throws SQLException {
    
    String query = null;
    String hash = null;
    if ( (param.get("userPassword")[0].length() == 0) || (param.get("userPassword")[0].equals("")) ) {
      query = "update USERS " +
        "set F_NAME = ?, S_NAME = ? " +
        "where USER_ID = ?";
    }
    else {
      query = "update USERS " +
        "set HASH = ?, F_NAME = ?, S_NAME = ? " +
        "where USER_ID = ?";
      hash = getPasswordHashValue(param.get("userPassword")[0]);
    }
    
    PreparedStatement stmt = null;
    try {
      int i = 1;
      stmt = connection.prepareStatement(query);
      if (hash != null) {
        stmt.setString(i++, hash);
      }
      stmt.setString(i++, param.get("name")[0]);
      stmt.setString(i++, param.get("name")[1]);
      stmt.setInt(i++, Integer.parseInt(id));
      stmt.executeUpdate();
    }
    catch (SQLException exc) {
      throw exc;
    }
    finally {
      if (stmt != null) {
        stmt.close();
      }
    }
  }
  
  /**
   * Gets some number of users with offset.
   * @param connection connection to database
   * @param offset how many users to offset
   * @return selected users
   */
  public List<User> getUsers(String offset, Connection connection) throws SQLException {
    
    int usersToOffset = 0;
    if (offset != null) {
      usersToOffset = Integer.parseInt(offset);
    }
    List<User> list = null;
    
    Statement stmt = null;
    String query = "select * from USERS order by U_PRVLGS offset " + usersToOffset + " rows fetch next 10 rows only";
    try {
      stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      list = new ArrayList<User>();
      while (rs.next()) {
        list.add(User.createUser(rs, connection));
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
    return list;
  }
  
  /**
   * Gets one user from db (is using for user edit form to be filled)
   * @param id id of the user
   * @param conn connection to db
   * @return return user object
   */
  public static User getUser(String id, Connection conn) throws SQLException {
    
    int userId = Integer.parseInt(id);
    User user = null;
    Statement stmt = null;
    String query = "select * from USERS where USER_ID = " + userId;
    try {
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      if (rs.next()) {
        user = User.createUser(rs, conn);
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
    return user;
  }
  
  /**
   * Gets number of existing records in users table.
   * @param conn connection to database
   * @return number of existing records
   */
  public int getNumberOfRecords(Connection conn) throws SQLException {
    
    int numberOfRecords = 0;
    String query = "select COUNT(*) from USERS";
    Statement stmt = null;
    try {
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        numberOfRecords = rs.getInt(1);
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
    return numberOfRecords;
  }
  
  /**
   * Registers user in database.
   * @param connection connection to database
   * @param param array of registration form parameters
   * @return null if registration is successed and message as a String if is not
   */
  public static String addUserToDB(Connection connection, Map<String, String[]> param, LoginSession ls)
    throws SQLException {
    
    int privileges = User.CUSTOMER_PRIVILEGES;
    // If user can register privileged users
    if ( (ls != null) && ls.getUser().isUserManager() ) {
      privileges = Integer.parseInt(param.get("privileges")[0]);
    }
    
    Statement stmt = connection.createStatement();
    String insrt = "insert into USERS (LOGIN, HASH, U_PRVLGS, F_NAME, S_NAME) " +
      "values('" + param.get("userLogin")[0] + "', '" +
      UserManagement.getPasswordHashValue(param.get("userPassword")[0]) +
      "', " + privileges + ", '" + param.get("name")[0] + "', '" +
      param.get("name")[1] + "')";
    
    stmt.executeUpdate(insrt);
    return null;
  }
  
  /**
   * Checks how fields were filled.
   * @param isRegistered indicates if form is for registration or is for edit
   * @param param array of registration form parameters
   * @param connection connection to database
   * @return return error message or null if all is ok
   */
  public static String formCheck(boolean isRegistered, Map<String, String[]> param, Connection connection)
    throws SQLException {
    
    if (!isRegistered) {
      String query = null;
      Statement stmt = null;
      ResultSet rs = null;
      try {
        stmt = connection.createStatement();
        // Checks login
        if (param.get("userLogin")[0].length() > 0) {
          query = "select LOGIN " +
            "from USERS " +
            "where LOGIN = '" + param.get("userLogin")[0] + "'";
          rs = stmt.executeQuery(query);
          if (rs.next()) {
            return "User with such login is already exist.";
          }
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
    }
    
    // Checks password
    if (param.get("userPassword")[0].length() > 0) {
      if (!param.get("userPassword")[0].equals(param.get("userPassword")[1])) {
        return "Passwords are not matching.";
      }
    }
    
    // Checks first and second names
    if (param.get("name")[0].length() < 1 && param.get("name")[1].length() < 1) {
      return "First or/and second names are empty.";
    }
    
    // Otherwise to all
    return null;
  }
  
  /**
   * Gets hash value of password string.
   * @param password what to hash
   * @return string based on message digest bytes
   */
  public static String getPasswordHashValue(String password) {
    
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("SHA-256");
    }
    catch (NoSuchAlgorithmException exc) {
      
      // Do nothing! Must to change to something appropriate later.
    }
    
    md.update(password.getBytes());
    return bytesToHexString(md.digest());
  }
  
  /**
	 * Converts array of bytes to hex string representation
	 * @param a array to convert
	 * @return hexadecimal string representation of array
	 */
	private static String bytesToHexString(byte[] a) {
	  
		StringBuilder sb = new StringBuilder();
		String s;
		
		for (byte b : a) {
			s = Integer.toHexString(0xFF & b);
			if (s.length() == 1) {
				sb.append("0");
			}
			sb.append(s);
		}
		
		return new String(sb);
	}
}

