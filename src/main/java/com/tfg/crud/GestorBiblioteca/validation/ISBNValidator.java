package com.tfg.crud.GestorBiblioteca.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ISBNValidator implements ConstraintValidator<ISBN, String> {

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        if (isbn == null) {
            return false;
        }

        isbn = isbn.replaceAll("[^0-9X]", "");

        if (isbn.length() == 10) {
            return isValidIsbn10(isbn);
        }

        if (isbn.length() == 13) {
            return isValidIsbn13(isbn);
        }

        return false;
    }

    private boolean isValidIsbn13(String isbn) {
        if (!isbn.matches("\\d{13}")) return false;

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        int check = (10 - (sum % 10)) % 10;
        return check == Character.getNumericValue(isbn.charAt(12));
    }

    private boolean isValidIsbn10(String isbn) {
        int sum = 0;

        for (int i = 0; i < 9; i++) {
            if (!Character.isDigit(isbn.charAt(i))) return false;
            sum += Character.getNumericValue(isbn.charAt(i)) * (10 - i);
        }

        char last = isbn.charAt(9);
        int checkDigit;

        if (last == 'X') {
            checkDigit = 10;
        } else if (Character.isDigit(last)) {
            checkDigit = Character.getNumericValue(last);
        } else {
            return false;
        }

        sum += checkDigit;
        return sum % 11 == 0;
    }
}