package com.omid.cloud.client;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jta.narayana.DbcpXADataSourceWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Import(DbcpXADataSourceWrapper.class)
@EnableTransactionManagement
public class AccountApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(AccountApplication.class, args);
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

