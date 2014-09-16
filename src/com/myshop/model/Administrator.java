package com.myshop.model;

import java.sql.*;
import java.util.*;

/**
 * Represents object that can operates other users and etc.
 */
public class Administrator extends User {
  
  public Administrator() {
    
    
  }
  
  public Administrator(ResultSet rs, Connection conn) throws SQLException {
    
    super(rs, true, new ItemManagement(), true, new UserManagement(), new ArrayList<ProfileOption>(), null);
    List<ProfileOption> list = getProfileOption();
    list.add(new ProfileOption("Register a new user", "registration"));
    list.add(new ProfileOption("User list", "userlist"));
    list.add(new ProfileOption("Add item", "item?op=add"));
  }
}

