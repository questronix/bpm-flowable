package org.prulife.com.entities;


import lombok.Data;

import javax.persistence.*;

@Entity(name = "REF_SubTransactionType")
@Data
public class SubTransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SubTransaction_Id", nullable = false)
    private int subTransactionId;

@Column(name = "SubTransactionType_Desc" , nullable = false)
    private String subTransactionTypeDesc;

    @ManyToOne
    @JoinColumn()
    private TransactionType transactionType;


}
