/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.motorphgui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author rowel
 */
public class Login {
    private HashMap<String, String> credentials;

    public Login(String filename) {
        credentials = loadCredentials("data/logins.csv");
    }

    private HashMap<String, String> loadCredentials(String filename) {
        HashMap<String, String> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    map.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading credentials: " + e.getMessage());
        }
        return map;
    }

    public boolean validate(String username, String password) {
        return credentials.containsKey(username) && credentials.get(username).equals(password);
    }
}

