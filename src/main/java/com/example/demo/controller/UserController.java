package com.example.demo.controller;


import com.example.demo.domain.EntityProducer;
import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/user")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @PostMapping(path="/add")
  @ResponseBody
  public String addNewUser (@RequestParam String name, @RequestParam String email) {
    userRepository.save(EntityProducer.createUser(name, email));
    return "User saved";
  }

  @GetMapping(path="/all")
  public @ResponseBody List<User> getAllUsers() {
    List<User> allUsers = new ArrayList<>();
    userRepository.findAll().forEach(allUsers::add);
    return allUsers;
  }

}
