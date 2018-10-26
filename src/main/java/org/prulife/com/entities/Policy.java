package org.prulife.com.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Policy {
    @Id
    @GeneratedValue
    private Long id;

    //PolicyRepository Information
    private String number;
    private String appNo;
    private String status;
    @Column(columnDefinition = "TEXT")
    private String info;

    @CreationTimestamp
    private Date createdAt;

    private Long createdBy;

    public Policy() { }

}
