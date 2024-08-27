package com.example.java_17.model;

import com.example.java_17.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailValidator extends Validator<String>{
    private final ClientRepository clientRepository;

    @Autowired
    public EmailValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public boolean validateEmail(String email) {
        if(!isValidEmail(email)){
            return false;
        }
        if (exists(email)) {
            return false;
        }
        return true;
    }

    @Override
    public void validate(String item) {

    }

    @Override
    public boolean exists(String email) {
        return clientRepository.existsByEmail(email);
    }

    private boolean isValidEmail(String email) {
        if (email.endsWith("@gmail.com") || email.endsWith("@yahoo.com")) {
            String[] parts = email.split("@");
            if (parts.length == 2) {
                String[] nameParts = parts[0].split("\\.");
                return nameParts.length == 2 && !nameParts[0].isEmpty() && !nameParts[1].isEmpty();
            }
        }
        return false;
    }
}
