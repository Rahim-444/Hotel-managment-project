package main.java.com.example.Poo.controller;

import java.security.SecureRandom;

public class TokenGenerator {

  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final int TOKEN_LENGTH = 16; // Length of the token

  public static String generateToken() {
    SecureRandom random = new SecureRandom();
    StringBuilder token = new StringBuilder(TOKEN_LENGTH);

    for (int i = 0; i < TOKEN_LENGTH; i++) {
      int randomIndex = random.nextInt(CHARACTERS.length());
      token.append(CHARACTERS.charAt(randomIndex));
    }

    return token.toString();
  }

}
