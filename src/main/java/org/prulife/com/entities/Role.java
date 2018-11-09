package org.prulife.com.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "REF_User_Roles")
@Data
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "ROLE_ID", nullable = true)
    private String roleId;

    @Column(name = "Description", nullable = true)
    private String description;

    @CreationTimestamp
    private Date createdAt;
}
