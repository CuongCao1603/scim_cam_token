package com.example.token_generate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.token_generate.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}
