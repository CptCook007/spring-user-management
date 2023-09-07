package com.shamal.userregistration.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInformation{
    @Id
    @SequenceGenerator(
            name="user-sequence",
            sequenceName = "user-sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user-sequence"
    )
    private long userID;
    private String username;
    private String email;
    private String password;
    @Column(nullable = false,columnDefinition = "VARCHAR(255) DEFAULT 'USER'")
    private String role = "USER";
    public boolean checkValid(){
        return !(username.isEmpty()||email.isEmpty()||password.isEmpty());
    }

}
