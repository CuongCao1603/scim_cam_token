package com.example.token_generate.model;


import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor

@Entity
@Table(name = "tbl_groups")
public class Group {
    //BEGIN: GROUP ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String uuid;
    private String displayName;
    @ManyToMany
    private List<User> users = new ArrayList<>();
    //END: GROUP ATTRIBUTES

    //BEGIN: CONSTRUCTORS
    public Group(){
        this.uuid = UUID.randomUUID().toString();
    }

    public Group(String displayName, List<User> users){
        this();
        this.displayName = displayName;
        this.users = users;
    }// Group
    //END: CONSTRUCTORS
}

