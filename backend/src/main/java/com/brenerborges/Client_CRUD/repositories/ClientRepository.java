package com.brenerborges.Client_CRUD.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brenerborges.Client_CRUD.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
