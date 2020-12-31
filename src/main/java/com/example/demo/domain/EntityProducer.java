package com.example.demo.domain;

public class EntityProducer {

  private User user;

  public static User createUser(String name, String email) {
    User user = new User();
    user.setName(name);
    user.setEmail(email);
    return user;
  }

}
