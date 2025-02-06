package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.Versioned;

import java.util.HashMap;
import java.util.Map;

@Service
public class VaultService {

    private final VaultTemplate vaultTemplate;

    @Autowired
    public VaultService(VaultTemplate vaultTemplate) {
        this.vaultTemplate = vaultTemplate;
    }

    // Method to store a secret
    public void storeSecret(String path, String secretKey, String secretValue) {
        Map<String, String> data = new HashMap<>();
        data.put(secretKey, secretValue);

        // Store the secret at the specified path
        Versioned.Metadata createResponse = vaultTemplate
                .opsForVersionedKeyValue("checkmarx")
                .put(path, data);

        System.out.println("Secret written successfully.");
    }

    // Method to retrieve a secret
    public String getSecret(String path, String secretKey) {
        Versioned<Map<String, Object>> readResponse = vaultTemplate
                .opsForVersionedKeyValue("checkmarx")
                .get(path);

        String secretValue = "";
        if (readResponse != null && readResponse.hasData()) {
            secretValue = (String) readResponse.getData().get(secretKey);
        }
        return secretValue;
    }
}
