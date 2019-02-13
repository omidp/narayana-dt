package com.omid.cloud.client.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class AccountEntity implements Serializable
{

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "amt")
    private String name;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public AccountEntity(String name)
    {
        this.name = name;
    }

    public AccountEntity()
    {
    }

    
    
    
}
