package com.inholland.nl.wimsmusicstore.model;

public class InputValidator {

    public boolean isPasswordValid(String password) {
        var hasLetters = false;
        var hasDigits = false;
        var hasSpecial = false;

        for (var c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigits = true;
            } else if (Character.isLetter(c)) {
                hasLetters = true;
            } else {
                hasSpecial = true;
            }
        }
        return password.length() > 7 && (hasLetters && hasDigits && hasSpecial);
    }

    public boolean containsOnlyCharacters(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }
}