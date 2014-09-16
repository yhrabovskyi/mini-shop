package com.myshop.model;

import java.sql.*;
import java.util.*;

/**
 * Represents object with what user can do shopping.
 */
public class Basket {
  
  int userId;
  // List of items to buy by user
  List<Item> itemList;
  
  public Basket() {
    
    itemList = new LinkedList<Item>();
  }
  
  /**
   * @param id id of the user, which has this basket object
   * @param conn connection to db
   */
  public Basket(int id, Connection conn) throws SQLException {
    
    this();
    userId = id;
    
    Statement stmt = null;
    ResultSet rs = null;
    String query = "select * from BASKETS where USER_ID = " + userId;
    try {
      stmt = conn.createStatement();
      rs = stmt.executeQuery(query);
      if (rs.next()) {
        List<Integer> ids = parseItemIDs(rs.getClob("ITEMS"));
        for (Integer i : ids) {
          Item item = null;
          if ((item = ItemManagement.getItem(i, conn)) != null) {
            itemList.add(item);
          }
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
  
  /**
   * Adds item to user list.
   * @param id id of item to add
   * @param conn connection to db
   */
  public void addItem(String id, Connection conn) throws SQLException {
    
    int intID = Integer.parseInt(id);
    itemList.add(ItemManagement.getItem(intID, conn));
  }
  
  /**
   * Removes item from user list.
   * @param id id of item to add
   * @param conn connection to db
   */
  public void removeItem(String id, Connection conn) throws SQLException {
    
    int intID = Integer.parseInt(id);
    itemList.remove(ItemManagement.getItem(intID, conn));
  }
  
  /**
   * Saves content of basket to db.
   * @param conn connection to db
   */
  public void saveToDB(Connection conn) throws SQLException {
    
    boolean isRecord;
    // Checks if there is already a record
    Statement stmt = null;
    String query = "select USER_ID from BASKETS where USER_ID = " + userId;
    try {
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      if (rs.next()) {
        isRecord = true;
      }
      else {
        isRecord = false;
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
    
    // Updates db
    PreparedStatement pStmt = null;
    query = null;
    if (isRecord) {
      query = "update BASKETS set ITEMS = ? where USER_ID = ?";
      pStmt = conn.prepareStatement(query);
      pStmt.setClob(1, parseClob(conn.createClob()));
      pStmt.setInt(2, userId);
    }
    else {
      if (!itemList.isEmpty()) {
        query = "insert into BASKETS values(?, ?)";
        pStmt = conn.prepareStatement(query);
        pStmt.setInt(1, userId);
        pStmt.setClob(2, parseClob(conn.createClob()));
      }
    }
    try {
      if (query != null) {
        pStmt.executeUpdate();
      }
    }
    catch (SQLException exc) {
      throw exc;
    }
    finally {
      if (pStmt != null) {
        pStmt.close();
      }
    }
  }
  
  /**
   * Clears item list of basket.
   */
  public void clear() {
    
    itemList.clear();
  }
  
  /**
   * Parses item IDs from Clob object.
   * @param clob from what parse IDs
   * @return list of item IDs
   */
  private List<Integer> parseItemIDs(Clob clob) throws SQLException {
    
    String s = clob.getSubString(1L, (int) clob.length());
    String[] sIds = s.split(" ");
    List<Integer> ids = new ArrayList<Integer>();
    for (int i = 0; i < sIds.length; i++) {
      if (!sIds[i].equals("")) {
        ids.add(new Integer(sIds[i]));
      }
    }
    
    return ids;
  }
  
  /**
   * Parses Clob object from item ids in basket.
   * @param clob to where insert items id
   * @return clob object which was formed from item ids in this basket
   */
  private Clob parseClob(Clob clob) throws SQLException {
    
    if (!itemList.isEmpty()) {
      StringBuilder sb = new StringBuilder();
      for (Item i : itemList) {
        sb.append(i.getId());
        sb.append(" ");
      }
      clob.setString(1, sb.substring(0, sb.length()-1));
    }
    return clob;
  }
  
  // Fields getters
  public List<Item> getItemList() {
    
    return itemList;
  }
}

