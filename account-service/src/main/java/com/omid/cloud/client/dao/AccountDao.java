package com.omid.cloud.client.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omid.cloud.client.model.AccountEntity;


public interface AccountDao extends JpaRepository<AccountEntity, Long>
{

}
