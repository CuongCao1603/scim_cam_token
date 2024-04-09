package com.example.token_generate.model;


import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "tbl_accounts")
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // @Column(unique = true)
    private String secretKey = "cuongcao1"; // Giá trị cố định

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user; // Quan hệ 1:1 với User
}

