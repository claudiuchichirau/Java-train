package com.example.java_17.repositories;

import com.example.java_17.model.Client;
import com.example.java_17.model.ClientType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    boolean existsByEmail(String email);
    List<Client> findByClientType(ClientType clientType);
}
