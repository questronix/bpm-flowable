package org.prulife.com.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "REF_TransactionDocs")
@Data
public class TransactionDocs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "ID", nullable = false)
    private int Id;

    @Column(name = "TransactionNo", nullable = false, length = 8)
    private String transactionNo;


   @ManyToOne
    private ListOfDocs listOfDocs;

    @Column(name = "IsSubmitted", nullable = true)
    private String isSubmitted;

    @Column(name = "Date_Submitted", nullable = true)
    private  Date dateSubmitted;

    @Column(name = "CreatedBy", nullable = true)
    private String createdBy;

    @Column(name = "ModifiedBy", nullable = true)
    private String modifiedBy;

    @Column(name = "ModifiedDate", nullable = true)
    private Date modifiedDate;

}
