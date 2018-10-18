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
    private Boolean status;
    @Column(columnDefinition = "TEXT")
    private String info;

    @CreationTimestamp
    private Date createdAt;

    @ManyToOne
    private Users createdBy;

//    private String agentCode;
//    private String agentName;
//    private String branch;
//    private String nma;
//    private String planDesc;
//    private String planCurrency;
//    private String contractStatus;
//    private String premiumStatus;
//    private double sumAssured = 0.0;
//    private Date rcd;
//    private Date firstIssueDate;

    //Insured Information
//    private String salutation;
//    private String firstName;
//    private String lastName;
//    private String gender;
//    private String occupation;
//    private String hrc;
//    private String vip;
//    private String str;
//    private String nationality;
//    private Date dateOfBirth;
//    private String attainedAge;
//    private String civilStatus;
//    private String telNumber;
//    private String mobileNumber;
//    private String tinOrSss;
//    private String email;

    public Policy() {

    }

    //Owner Information TBF
}
