package com.myshop.model;

import java.sql.*;
import java.util.*;

/*
 * Represents object that can operates items.
 */
public class Manager extends User {
  
  public Manager() {
    
    
  }
  
  public Manager(ResultSet rs, Connection conn) throws SQLException {
    
    super(rs, true, new ItemManagement(), false, null, new ArrayList<ProfileOption>(), null);
    List<ProfileOption> list = getProfileOption();
    list.add(new ProfileOption("Add item", "item?op=add"));
  }
}

