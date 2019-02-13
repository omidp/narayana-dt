package com.omid.cloud.client;

import java.util.Arrays;
import java.util.Properties;

import javax.sql.DataSource;
import javax.sql.XADataSource;
import javax.transaction.TransactionManager;

import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.managed.DataSourceXAConnectionFactory;
import org.apache.commons.dbcp2.managed.ManagedDataSource;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.postgresql.xa.PGXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple;
import com.omid.cloud.client.dao.AccountDao;
import com.omid.cloud.client.model.AccountEntity;

@SpringBootApplication(exclude = { TransactionAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class,
        DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
//@EnableTransactionManagement
//@Import(DbcpXADataSourceWrapper.class)
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


    
    @Autowired
    private TransactionManager tm;

    @Bean
    public XADataSource pgxaDataSource() {
        PGXADataSource ds = new PGXADataSource();
        ds.setURL("jdbc:postgresql://localhost:5432/accountdb");
        ds.setUser("test");
        ds.setPassword("test");
        return ds;
    }
    
    @Bean("transactionManager")
    public PlatformTransactionManager transactionManager() {
        JtaTransactionManager tm = new JtaTransactionManager();
        tm.setTransactionManager(jtaTransactionManager());
        return tm;
    }

    @Bean
    public TransactionManager jtaTransactionManager() {
        return new TransactionManagerImple();
    }


    @Bean
    public DataSource dataSource() {
        DataSourceXAConnectionFactory dataSourceXAConnectionFactory =
                new DataSourceXAConnectionFactory(tm, pgxaDataSource());
        PoolableConnectionFactory poolableConnectionFactory =
                new PoolableConnectionFactory(dataSourceXAConnectionFactory, null);
        GenericObjectPool<PoolableConnection> connectionPool =
                new GenericObjectPool<>(poolableConnectionFactory);
        poolableConnectionFactory.setPool(connectionPool);
        return new ManagedDataSource<>(connectionPool,
                dataSourceXAConnectionFactory.getTransactionRegistry());
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
       LocalContainerEntityManagerFactoryBean em 
         = new LocalContainerEntityManagerFactoryBean();
       em.setDataSource(dataSource());
       em.setPackagesToScan(new String[] { "com.omid.cloud.client.model" });
  
       JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
       em.setJpaVendorAdapter(vendorAdapter);
       em.setJpaProperties(additionalProperties());
  
       return em;
    }
    
    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty(
          "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty(
                "hibernate.temp.use_jdbc_metadata_defaults", "false");
        properties.setProperty(
                "hibernate.jdbc.lob.non_contextual_creation", "true");
        
        return properties;
    }

}

@RestController
class ClientApi
{

    @Autowired
    RestTemplate restTemplate;

    // @Autowired
    // private TransactionManager tm;

//    @Autowired
//    PlatformTransactionManager ptm;

    @Autowired
    AccountDao dao;

    @GetMapping("/account")
    String getApi()
    {
        // System.out.println(tm);
//        System.out.println(ptm);
        dao.save(new AccountEntity("acc1"));
        return "created account";
    }

}
