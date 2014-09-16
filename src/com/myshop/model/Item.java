package com.myshop.model;

import java.sql.*;
import java.util.*;

/**
 * Represents item, which can be bought by customers, add/delete to/from db.
 */
public class Item {
  
  private int id;
  private String name;
  private int price;
  private int quantity;
  private Clob description;
  private String stringDescr;
  
  public Item() {
    
  }
  
  public Item(int id, String name, int price, int quantity, Clob description) throws SQLException {
    
    this.id = id;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
    this.description = description;
    this.stringDescr = description.getSubString(1L, (int) description.length());
  }
  
  @Override
  public boolean equals(Object obj) {
    
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Item otherItem = (Item) obj;
    return id == otherItem.id;
  }
  
  /**
   * Creates an item object from String parameters.
   * @param conn connection to database
   * @param param map of parameters to set
   * @return an item object
   */
  public static Item createItem(Connection conn, Map<String, String[]> param) throws SQLException {
    
    Item item = new Item();
    item.setName(param.get("name")[0]);
    item.setPrice(Integer.parseInt(param.get("price")[0]));
    item.setQuantity(Integer.parseInt(param.get("quantity")[0]));
    Clob cl = conn.createClob();
    cl.setString(1, param.get("description")[0]);
    item.setDescription(cl);
    item.setStringDescr(cl);
    return item;
  }
  
  /**
   * Creates an item object from String parameters and id (needed when edit form is performed).
   * @param conn connection to database
   * @param param map of parameters to set
   * @param id id of item
   * @return an item object
   */
  public static Item createItem(Connection conn, Map<String, String[]> param, String id) throws SQLException {
    
    Item item = createItem(conn, param);
    item.setId(Integer.parseInt(id));
    return item;
  }
  
  /**
   * Creates a list of items from ResultSet.
   * @param rs ResultSet object created from query
   * @return list of created items
   */
  public static List<Item> createItems(ResultSet rs) throws SQLException {
    
    List<Item> item = new LinkedList<Item>();
    while (rs.next()) {
      Item newItem = new Item(rs.getInt("ITEM_ID"), rs.getString("NAME"), rs.getInt("PRICE"),
        rs.getInt("QUANT"), rs.getClob("DESCR"));
      item.add(newItem);
    }
    return item;
  }
  
  // Fields setters
  public void setId(int id) {
    
    this.id = id;
  }
  
  public void setName(String name) {
    
    this.name = name;
  }
  
  public void setPrice(int price) {
    
    this.price = price;
  }
  
  public void setQuantity(int quantity) {
    
    this.quantity = quantity;
  }
  
  public void setDescription(Clob description) {
    
    this.description = description;
  }
  
  public void setStringDescr(Clob description) throws SQLException {
    
    this.stringDescr = description.getSubString(1L, (int) description.length());
  }
  
  // Fields getters
  public int getId() {
    
    return id;
  }
  
  public String getName() {
    
    return name;
  }
  
  public int getPrice() {
    
    return price;
  }
  
  public int getQuantity() {
    
    return quantity;
  }
  
  public Clob getDescription() {
    
    return description;
  }
  
  public String getStringDescr() {
    
    return stringDescr;
  }
}

