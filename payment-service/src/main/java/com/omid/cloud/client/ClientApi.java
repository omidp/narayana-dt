package com.omid.cloud.client;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.omid.cloud.client.dao.PaymentDao;
import com.omid.cloud.client.model.PaymentEntity;

@RestController
public class ClientApi
{

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PaymentDao dao;

    @Autowired
    PlatformTransactionManager ptm;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    TransactionManager tm;

    @Autowired
    UserTransaction ut;

    @Autowired
    DataSource ds;

    @PersistenceContext
    EntityManager em;

    @GetMapping("/pay")
    @Transactional(rollbackOn = RuntimeException.class)
    @org.springframework.transaction.annotation.Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public String getApi() throws IllegalStateException, SecurityException, SystemException
    {
        System.out.println(ptm);
        System.out.println(tm);
        System.out.println(ut);
        System.out.println(ds);
        // dao.save(new PaymentEntity(1000L, null));
        // TransactionTemplate tt = new TransactionTemplate(ptm);
        // tt.execute(new TransactionCallbackWithoutResult() {
        //
        // @Override
        // protected void doInTransactionWithoutResult(TransactionStatus status)
        // {
        em.persist(new PaymentEntity(1000L, null));
        // }
        // });

        // tm.rollback();
        // sendUpdate("payment updated");
        throw new RuntimeException("revert");
        // return "paid";
    }

    private void sendUpdate(String message)
    {
        jmsTemplate.convertAndSend("mybroker", message);
    }

}