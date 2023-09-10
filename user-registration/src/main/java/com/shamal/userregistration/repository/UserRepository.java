package com.shamal.userregistration.repository;
import com.shamal.userregistration.model.UserInformation;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserInformation,Long>{

    UserInformation findByUsername(String username);
    @Query(value = "SELECT * FROM user_information WHERE username LIKE ?1%", nativeQuery = true)
    List<UserInformation> findByUsernameContaining(String keyword);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
