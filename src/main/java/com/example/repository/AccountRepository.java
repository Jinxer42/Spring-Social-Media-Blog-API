package com.example.repository;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.*;

public interface AccountRepository extends JpaRepository<Account,Integer> {

    public ArrayList<Account> findByUsername(String username);
    public ArrayList<Account> findByUsernameAndPassword(String username,String password);
}
