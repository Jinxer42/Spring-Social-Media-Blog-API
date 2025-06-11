package com.example.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DuplicateUserException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository inAccountRepository)
    {
        this.accountRepository=inAccountRepository;
    }


    public Account addAccount(Account inAccount) throws Exception {
        trimAccount(inAccount);
        if(accountRepository.findByUsername(inAccount.getUsername()).size()>0)
        {
            throw new DuplicateUserException();
        }
        if(inAccount.getUsername().length()==0 || inAccount.getPassword().length()<4)
        {
            throw new Exception("Username and password don't meet requirements");
        }
        return accountRepository.save(inAccount);

    }


    private void trimAccount(Account inAccount)
    {
        inAccount.setPassword(inAccount.getPassword().trim());
        inAccount.setUsername(inAccount.getUsername().trim());
    }


    public Account loginAccount(Account inAccount) throws Exception {
        trimAccount(inAccount);
        ArrayList<Account> matchedAccounts=accountRepository.findByUsernameAndPassword(inAccount.getUsername(),inAccount.getPassword());
        if(matchedAccounts.size()!=1)
        {
            throw new Exception("wrong number of matched accounts. Was not the expected 1 matched account.");
        }
        return matchedAccounts.get(0);

    }


    public boolean doesAccountExist(Integer postedBy) {
        // TODO Auto-generated method stub
        return accountRepository.existsById(postedBy);
        //throw new UnsupportedOperationException("Unimplemented method 'doesAccountExist'");
    }
}
