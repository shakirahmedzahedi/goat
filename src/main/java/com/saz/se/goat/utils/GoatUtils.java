package com.saz.se.goat.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GoatUtils {
    public static String generateRandomString() {
        Random random = new Random();

        // Generate 3 random letters
        StringBuilder letters = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            char randomLetter = (char) (random.nextInt(26) + 'A'); // A-Z letters
            letters.append(randomLetter);
        }

        // Generate 3 random numbers
        StringBuilder numbers = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int randomNumber = random.nextInt(10); // 0-9 numbers
            numbers.append(randomNumber);
        }

        // Combine the letters and numbers into a single list
        List<Character> combinedList = new ArrayList<>();
        for (char c : letters.toString().toCharArray()) {
            combinedList.add(c);
        }
        for (char c : numbers.toString().toCharArray()) {
            combinedList.add(c);
        }

        // Shuffle the list to randomize the order of characters
        Collections.shuffle(combinedList);

        // Convert the shuffled list back into a string
        StringBuilder result = new StringBuilder();
        for (char c : combinedList) {
            result.append(c);
        }

        return result.toString();
    }
    public static void main(String[] args) {
        String randomString = generateRandomString();
        System.out.println("Random String: " + randomString);
    }
}
