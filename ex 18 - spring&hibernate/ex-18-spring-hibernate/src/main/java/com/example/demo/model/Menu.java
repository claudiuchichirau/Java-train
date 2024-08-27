package com.example.demo.model;

import com.example.demo.services.ProfessorKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Menu {
    private final ProfessorKeyService professorKeyService;

    @Autowired
    public Menu(ProfessorKeyService professorKeyService) {
        this.professorKeyService = professorKeyService;
    }

    public List<ProfessorKey> getProfessorKeys() {
        return professorKeyService.getAllProfessorKeys();
    }
}
