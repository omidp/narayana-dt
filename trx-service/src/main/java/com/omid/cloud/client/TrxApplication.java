package com.omid.cloud.client;

import java.util.Arrays;

import org.jboss.jbossts.star.util.TxStatusMediaType;
import org.jboss.jbossts.star.util.TxSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TrxApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(TrxApplication.class, args);
    }

    @Bean
    CommandLineRunner cmd(ApplicationContext ctx)
    {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames)
            {
                // System.out.println(beanName);
            }
        };

    }
    
    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

}

@RestController
class ClientApi
{

    @Autowired
    RestTemplate restTemplate;

    String coordinatorUrl = "http://localhost:8080/rest-at-coordinator/tx/transaction-manager";

    @GetMapping("/merge")
    String getApi()
    {
//        TxSupport txn = new TxSupport(coordinatorUrl);
//
//        // start a transaction
//        txn.startTx();
//
//        // verify that there is an active transaction
//        if (!txn.txStatus().equals(TxStatusMediaType.TX_ACTIVE))
//            throw new RuntimeException("A transaction should be active: " + txn.txStatus());

//        System.out.println("transaction running: " + txn.txStatus());
        String forObject2 = restTemplate.getForObject("http://localhost:8686/account", String.class);
        System.out.println(forObject2);
        String forObject = restTemplate.getForObject("http://localhost:8585/pay", String.class);
        System.out.println(forObject);
     // end the transaction
//        txn.commitTx();
        return "merge";
    }

}
