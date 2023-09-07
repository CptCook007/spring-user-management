package com.shamal.userregistration.service;

import com.shamal.userregistration.model.UserInformation;

import java.util.List;

public interface UserService {
    void saveUser(UserInformation user);
    List<UserInformation> listUser();

    void deleteUser(long id);

    void updateByID(long id, UserInformation user);

    void saveAdmin(UserInformation user);
    List<UserInformation> listUserBy(String keyword);
     List<UserInformation> listAllUser();

    boolean checkEmailExist(String email);

    UserInformation findUserById(long id);
}
