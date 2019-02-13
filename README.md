This repo is a simple demonstration of RESTAT (REST Atomic Transaction) with spring boot and narayana.

account-service : create a new account 

HTTP Method : GET

```
http://localhost:8686/account
```

payment-service : create a new payment 

HTTP Method : GET

```
http://localhost:8686/pay
```


trx-service : (RESTAT) wrap a transaction for calling account and payment endpoints  

HTTP Method : GET

```
http://localhost:8686/merge
```

this example is running on Tomcat and Postgresql.

you also need wildfly 9 or later to run restat with following command

```
${WILDFLY_HOME}/bin/standalone.sh --server-config=../../docs/examples/configs/standalone-rts.xml
```