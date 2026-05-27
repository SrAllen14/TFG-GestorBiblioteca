
package com.tfg.crud.GestorBiblioteca.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ISBNValidator implements ConstraintValidator<ISBN, String> {

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {

        if (isbn == null || isbn.isBlank()) {
            return false;
        }

        isbn = isbn.replaceAll("[-\\s]", "");

        if (isbn.length() == 10) {
            return isValidISBN10(isbn);
        }

        if (isbn.length() == 13) {
            return isValidISBN13(isbn);
        }

        return false;
    }

    private boolean isValidISBN10(String isbn) {
        if (!isbn.matches("\\d{9}[\\dX]")) return false;

        int sum = 0;

        for (int i = 0; i < 9; i++) {
            sum += (isbn.charAt(i) - '0') * (10 - i);
        }

        char last = isbn.charAt(9);
        sum += (last == 'X') ? 10 : (last - '0');

        return sum % 11 == 0;
    }

    private boolean isValidISBN13(String isbn) {
        if (!isbn.matches("\\d{13}")) return false;

        int sum = 0;

        for (int i = 0; i < 13; i++) {
            int digit = isbn.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        return sum % 10 == 0;
    }
}
