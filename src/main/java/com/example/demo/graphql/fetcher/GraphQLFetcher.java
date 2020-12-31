package com.example.demo.graphql.fetcher;

import com.example.demo.domain.EntityProducer;
import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import graphql.schema.DataFetcher;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GraphQLFetcher {

  @Autowired
  private UserRepository userRepository;

  public DataFetcher<List<User>> getAllUsers() {
    return environment -> {
      List<User> allUsers = new ArrayList<>();
      userRepository.findAll().forEach(allUsers::add);
      return allUsers;
    };
  }

  public DataFetcher<User> addUser() {
    return environment -> {
      String name = environment.getArgument("name");
      String email = environment.getArgument("email");
      User user = EntityProducer.createUser(name, email);
      userRepository.save(user);
      return user;
    };
  }
}
