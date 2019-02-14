package com.omid.cloud.client;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jta.narayana.DbcpXADataSourceWrapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Import({ DbcpXADataSourceWrapper.class })
@EnableTransactionManagement()
public class PaymentApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(PaymentApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder)
    {
        return builder.build();
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
                System.out.println(beanName);
                if ("transactionManager".equals(beanName))
                {
                    Object bean = ctx.getBean("transactionManager");
                    System.out.println("######################");
                    System.out.println(bean);
                }
            }
        };

    }

    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

}
