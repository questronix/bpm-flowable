package org.prulife.com.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    @CreationTimestamp
    private Date createdAt;
}
