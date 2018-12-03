package org.prulife.com.entities;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_ID")
    private Long id;

    @Column(name = "Username", unique=true, nullable = true)
    private String username;

    @Column(name="ROLE_ID", nullable = true)
    private String role;

    @Column(name = "Active", nullable = true)
    private int isActive = 1;

    @Column(name = "Last_Login", nullable = true)
    private Date lastLogin;

    @Column(name = "Last_Logoff", nullable = true)
    private Date lastLogoff;

    @Column(name = "Login_Attempt", nullable = true)
    private int loginAttempt;

    @Column(name = "LA_User_ID", nullable = true, length = 10)
    private String LaUserId;

    @CreationTimestamp
    @Column(name = "Created_Date", nullable = true)
    private Date createdAt;

    @Column(name = "Created_By", nullable = true, length = 20)
    private String createdBy;

    @Column(name = "Modified_Date", nullable = true)
    private Date updatedAt;

    @Column(name = "Modified_By", nullable = true, length = 20)
    private String updatedBy;

    @Column(name = "Firstname", nullable = true)
    private String firstName;

    @Column(name = "Lastname", nullable = true)
    private String lastName;

    public Users(){ }

}