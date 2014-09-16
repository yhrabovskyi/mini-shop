package com.myshop.model;

import java.sql.*;
import java.util.List;

/**
 * Abstract class represents a user.
 */
public abstract class User {
  
  public static final int ADMINISTRATOR_PRIVILEGES = 10;
  public static final int MANAGER_PRIVILEGES = 20;
  public static final int CUSTOMER_PRIVILEGES = 40;
  
  private int id;
  private String login;
  private String firstName;
  private String secondName;
  // Indicates type of user
  private int privileges;
  // Indicates if this user can add, edit, delete items
  private boolean itemManager;
  private ItemManagement itemOperator;
  // Indicates if this user can add, edit, delete users
  private boolean userManager;
  private UserManagement userOperator;
  // Contains option available for user
  private List<ProfileOption> profileOption;
  // Contains basket if user can do shopping
  private Basket basket;
  
  public User() {
    
    
  }
  
  /**
   * Public constructor.
   * @param rs contains return values from db table
   * @param im indicates if user can manage items
   * @param io if previous is true, than contains object to operate with items, else - null
   * @param um indicates if user can manage users
   * @param uo if previous is true, than contains object to operate with users, else - null
   * @param po contains options available for users
   * @param b contains basket if user can buy items
   */
  public User(ResultSet rs, boolean im, ItemManagement io, boolean um, UserManagement uo, List<ProfileOption> po, Basket b) throws SQLException {
    
    this.itemManager = im;
    this.itemOperator = io;
    this.userManager = um;
    this.userOperator = uo;
    this.profileOption = po;
    this.basket = b;
    
    id = rs.getInt("USER_ID");
    login = rs.getString("LOGIN");
    firstName = rs.getString("F_NAME");
    secondName = rs.getString("S_NAME");
    privileges = rs.getInt("U_PRVLGS");
  }
  
  /**
   * Creates specific user based on privileges.
   * @param rs ResultSet object from query
   * @param conn connection to db
   * @return user object
   */
  public static User createUser(ResultSet rs, Connection conn) throws SQLException {
    
    User user = null;
    
    // Chooses specific user
    switch (rs.getInt("U_PRVLGS")) {
      case User.ADMINISTRATOR_PRIVILEGES:
        user = new Administrator(rs, conn);
        break;
      case User.MANAGER_PRIVILEGES:
        user = new Manager(rs, conn);
        break;
      case User.CUSTOMER_PRIVILEGES:
        user = new Customer(rs, conn);
        break;
    }
    
    return user;
  }
  
  // Fields getters
  public int getId() {
    
    return id;
  }
  
  public String getLogin() {
    
    return login;
  }
  
  public String getFirstName() {
    
    return firstName;
  }
  
  public String getSecondName() {
    
    return secondName;
  }
  
  public int getPrivileges() {
    
    return privileges;
  }
  
  public boolean isItemManager() {
    
    return itemManager;
  }
  
  public ItemManagement getItemOperator() {
    
    return itemOperator;
  }
  
  public boolean isUserManager() {
    
    return userManager;
  }
  
  public UserManagement getUserOperator() {
    
    return userOperator;
  }
  
  public List<ProfileOption> getProfileOption() {
    
    return profileOption;
  }
  
  public Basket getBasket() {
    
    return basket;
  }
  
  // Fields setters
  public void setId(int id) {
    
    this.id = id;
  }
  
  public void setLogin(String login) {
    
    this.login = login;
  }
  
  public void setFirstName(String firstName) {
    
    this.firstName = firstName;
  }
  
  public void setSecondName(String secondName) {
    
    this.secondName = secondName;
  }
  
  public void setPrivileges(int privileges) {
    
    this.privileges = privileges;
  }
}

