package com.myshop.model;

import java.sql.*;
import java.util.*;

/**
 * Represents formed order with id and list of items.
 */
public class Order {
  
  private List<Item> itemList;
  private int id;
  private static int idNext = 1;
  
  public Order() {
    
    itemList = new LinkedList<Item>();
  }
  
  /**
   * Checks if there is enough quantity of items and form order list and id.
   * @param basket basket from which list form order
   * @param conn connection to db
   * @return message if there is no item or no enough quantity of item or null if everything is ok.
   */
  public String form(Basket basket, Connection conn) throws SQLException {
    
    String msg = null;
    List<Item> list = basket.getItemList();
    if (list.isEmpty()) {
      msg = "Basket is empty";
      return msg;
    }
    conn.setAutoCommit(false);
    Statement slctStmt = null;
    Statement updStmt = null;
    ResultSet rs = null;
    String query = null;
    String updateItem = null;
    try {
      Savepoint sv = conn.setSavepoint();
      slctStmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      updStmt = conn.createStatement();
      for (Item i : list) {
        query = "select * from ITEMS where ITEM_ID = " + i.getId();
        rs = slctStmt.executeQuery(query);
        rs.first();
        if (rs.getInt("QUANT") <= 0) {
          msg = i.getName() + " is not available at the moment.";
          conn.rollback(sv);
          break;
        }
        else {
          updateItem = "update ITEMS set QUANT = QUANT - 1 where ITEM_ID = " +
            rs.getInt("ITEM_ID");
          updStmt.executeUpdate(updateItem);
        }
      }
      conn.commit();
      basket.clear();
      
      for (Item i : list) {
        itemList.add(i);
      }
      id = idNext++;
      return msg;
      }
    catch (SQLException exc) {
      throw exc;
    }
    finally {
      if (slctStmt != null) {
        slctStmt.close();
      }
      if (updStmt != null) {
        updStmt.close();
      }
      conn.setAutoCommit(true);
    }
  }
}

