package com.myshop.model;

import java.sql.*;
import java.util.*;

/**
 * Represents object that operates with items in database.
 */
public class ItemManagement {
  
  /**
   * Adds new item to ITEMS table.
   * @param item item to add to database
   * @param connection connection to database
   */
  public void addItemToDB(Item item, Connection connection) throws SQLException {
    
    PreparedStatement prStmt = null;
    String insrt = "insert into ITEMS (NAME, PRICE, QUANT, DESCR) " +
      "values(?, ?, ?, ?)";

    try {
      prStmt = connection.prepareStatement(insrt);
      prStmt.setString(1, item.getName());
      prStmt.setInt(2, item.getPrice());
      prStmt.setInt(3, item.getQuantity());
      prStmt.setClob(4, item.getDescription());
      prStmt.executeUpdate();
    }
    catch (SQLException exc) {
      throw exc;
    }
    finally {
      if (prStmt != null) {
        prStmt.close();
      }
    }
  }
  
  /**
   * Edits item to ITEMS table.
   * @param item item to edit in database
   * @param connection connection to database
   */
  public void editItemInDB(Item item, Connection connection) throws SQLException {
    
    PreparedStatement prStmt = null;
    String upd = "update ITEMS " +
      "set NAME = ?, PRICE = ?, QUANT = ?, DESCR = ? " +
      "where ITEM_ID = ?";
    
    try {
      prStmt = connection.prepareStatement(upd);
      prStmt.setString(1, item.getName());
      prStmt.setInt(2, item.getPrice());
      prStmt.setInt(3, item.getQuantity());
      prStmt.setClob(4, item.getDescription());
      prStmt.setInt(5, item.getId());
      prStmt.executeUpdate();
    }
    catch (SQLException exc) {
      throw exc;
    }
    finally {
      if (prStmt != null) {
        prStmt.close();
      }
    }
  }
  
  /**
   * Removes item from ITEMS table.
   * @param item item to remove
   * @param connection connection to database
   */
  public void removeItemFromDB(Item item, Connection connection) throws SQLException {
    
    Statement deleteItem = null;
    String dlt = "delete from ITEMS WHERE ITEM_ID = " + item.getId();
    try {
      deleteItem = connection.createStatement();
      deleteItem.executeUpdate(dlt);
    }
    catch (SQLException exc) {
      throw exc;
    }
    finally {
      if (deleteItem != null) {
        deleteItem.close();
      }
    }
  }
  
  public String checkForm(Map<String, String[]> param) {
    
    // Checks item name
    String s = param.get("name")[0];
    if (s.length() == 0) {
      return "Name field is empty.";
    }
    if (s.length() > 40) {
      return "Name is too long.";
    }
    
    // Checks price
    try {
      Integer.parseInt(param.get("price")[0]);
    }
    catch (NumberFormatException exc) {
      return "Price is not decimal.";
    }
    
    // Checks quantity
    try {
      Integer.parseInt(param.get("quantity")[0]);
    }
    catch (NumberFormatException exc) {
      return "Quantity is not decimal.";
    }
    
    return null;
  }
  
  /**
   * Gets some number of items with offset.
   * @param connection connection to database
   * @param offset how many items to offset
   * @return selected items
   */
  public static List<Item> getItems(Connection connection, String offset) throws SQLException {
    
    int itemsToOffset = 0;
    if (offset != null) {
      itemsToOffset = Integer.parseInt(offset);
    }
    List<Item> list = null;
    
    Statement stmt = null;
    String query = "select * from ITEMS offset " + itemsToOffset + " rows fetch next 10 rows only";
    try {
      stmt = connection.createStatement();
      list = Item.createItems(stmt.executeQuery(query));
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
   * Gets item from database.
   * @param id id of item
   * @param connection connection to database
   * @return item object
   */
  public static Item getItem(int id, Connection connection) throws SQLException {
    
    Item item = null;
    Statement stmt = null;
    String query = "select * from ITEMS where ITEM_ID = " + id;
    try {
      stmt = connection.createStatement();
      List<Item> list = Item.createItems(stmt.executeQuery(query));
      if (!list.isEmpty()) {
        item = list.get(0);
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
    return item;
  }
  
  /**
   * Gets number of existing records in items table.
   * @param conn connection to database
   * @return number of existing records
   */
  public static int getNumberOfRecords(Connection conn) throws SQLException {
    
    int numberOfRecords = 0;
    String query = "select COUNT(*) from ITEMS";
    Statement stmt = null;
    try {
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      if (rs.next()) {
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
}

