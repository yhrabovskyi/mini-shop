package com.myshop.model;

/**
 * Represents object that contains URL to option and it's name.
 */
public class ProfileOption {
  
  String name;
  String url;
  
  public ProfileOption() {
    
    name = null;
    url = null;
  }
  
  public ProfileOption(String name, String url) {
    
    this.name = name;
    this.url = url;
  }
  
  // Fields getters
  public String getName() {
    
    return name;
  }
  
  public String getUrl() {
    
    return url;
  }
}

