package com.myshop.model;

import java.sql.*;
import java.util.*;

/**
 * Represents object that can operates with basket and buys items.
 */
public class Customer extends User {
  
  public Customer() {
    
    
  }
  
  public Customer(ResultSet rs, Connection conn) throws SQLException {
    
    super(rs, false, null, false, null, new ArrayList<ProfileOption>(), new Basket(rs.getInt("USER_ID"), conn));
    List<ProfileOption> list = getProfileOption();
    list.add(new ProfileOption("Basket", "basket"));
  }
}

