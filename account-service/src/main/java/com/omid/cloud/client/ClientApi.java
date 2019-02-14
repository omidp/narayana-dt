package com.omid.cloud.client;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.omid.cloud.client.dao.AccountDao;
import com.omid.cloud.client.model.AccountEntity;

@RestController
public class ClientApi
{

    @Autowired
    RestTemplate restTemplate;

     @Autowired
     private TransactionManager tm;

    @Autowired
    PlatformTransactionManager ptm;

    @Autowired
    AccountDao dao;
    
    @PersistenceContext
    EntityManager em;

    @GetMapping("/account")
    @Transactional(rollbackFor=RuntimeException.class)
    public String getApi()
    {
        // System.out.println(tm);
//        System.out.println(ptm);
//        dao.save(new AccountEntity("acc1"));
//        dao.flush();
        em.persist(new AccountEntity("acc1"));
        return "created account";
    }

}
