package com.example.demo.services;

import com.example.demo.model.ProfessorKey;
import com.example.demo.repositories.ProfessorKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProfessorKeyService {
    @Autowired
    private ProfessorKeyRepository professorKeyRepository;

    public List<ProfessorKey> getAllProfessorKeys() {
        return Collections.unmodifiableList(professorKeyRepository.findAll());
    }

    public void addProfessorKey(String key) {
        ProfessorKey professorKey = new ProfessorKey(key);
        professorKeyRepository.save(professorKey);
    }
}
