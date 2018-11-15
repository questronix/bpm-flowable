package org.prulife.com.entities;


import lombok.Data;

import javax.persistence.*;

@Entity(name = "REF_TransactionType")
@Data
public class TransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionType_Id", nullable = false)
    private int transactionTypeId;

    @Column(name = "TransactionType_Desc", nullable = false)
    private String transactionType;


}
