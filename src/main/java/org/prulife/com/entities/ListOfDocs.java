package org.prulife.com.entities;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "REF_ListOfDocs")
@Data
public class ListOfDocs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ListDocs Id", nullable = false)
    private int Id;

@ManyToOne
private SubTransactionType subTransactionType;
//    @Column(name = "TransactionType_Id", nullable = false)
//    private int transactionType_Id;
//
//    @Column(name = "SubTransactionType_Id", nullable = false)
//    private  int subTransactionType_Id;

    @Column(name = "Content", nullable = false)
    private String content;

    @Column(name = "IsMandatory", nullable = false)
    private Boolean isMandatory;

    @Column(name = "Date_Created", nullable = true)
    @CreationTimestamp
    private Date dateCreated;


}
