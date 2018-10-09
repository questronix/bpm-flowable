package org.prulife.com.entities;


import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Users {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Column(unique=true)
    private String username;

    private String firstName;

    private String lastName;

    @NonNull
    private String password;

    private String role;

    private int status = 1;

    @CreationTimestamp
    private Date createdAt;

    public Users(){ }

}