package com.example.demo.repositories;

import com.example.demo.model.ProfessorKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfessorKeyRepository extends JpaRepository<ProfessorKey, UUID> {
}
