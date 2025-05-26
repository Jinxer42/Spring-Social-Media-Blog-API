package com.example.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.*;

public interface AccountRepository extends JpaRepository<Account,Integer> {

    public ArrayList<Account> findByUsername(String username);
    public ArrayList<Account> findByUsernameAndPassword(String username,String password);
}
