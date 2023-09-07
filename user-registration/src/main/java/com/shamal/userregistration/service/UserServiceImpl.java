package com.shamal.userregistration.service;

import com.shamal.userregistration.model.UserInformation;
import com.shamal.userregistration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void saveUser(UserInformation user) {
        user.setPassword(encoder().encode(user.getPassword()));
        if(userRepository.count()<1){
            user.setRole("ADMIN");
        }
        userRepository.save(user);
    }

    @Override
    public List<UserInformation> listUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateByID(long id, UserInformation user) {
        UserInformation userDb = userRepository.findById(id).get();
        if(Objects.nonNull(user.getUsername())&&!"".equalsIgnoreCase(user.getUsername())){
            userDb.setUsername(user.getUsername());
        }
        if(Objects.nonNull(user.getEmail())&&!"".equalsIgnoreCase(user.getEmail())) {
            userDb.setEmail(user.getEmail());
        }
        userRepository.save(userDb);
    }

    @Override
    public void saveAdmin(UserInformation user) {
        user.setPassword(encoder().encode(user.getPassword()));
        user.setRole("ADMIN");
        userRepository.save(user);
    }

    @Override
    public List<UserInformation> listUserBy(String keyword) {
        return userRepository.findByUsernameContaining(keyword);
    }
    @Override
    public List<UserInformation> listAllUser(){
        return userRepository.findAll();
    }

    @Override
    public boolean checkEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserInformation findUserById(long id) {
        return userRepository.findById(id).get();
    }
}
