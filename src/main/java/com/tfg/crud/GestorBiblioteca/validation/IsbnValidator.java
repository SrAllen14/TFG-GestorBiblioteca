/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.validation;

public class IsbnValidator {

    public static boolean isValid(String isbn) {

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

    public static boolean isValidIsbn13(String isbn) {

        if (isbn == null) {
            return false;
        }

        isbn = isbn.replaceAll("-", "").replaceAll(" ", "");

        if (!isbn.matches("\\d{13}")) {
            return false;
        }

        int sum = 0;

        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));

            if (i % 2 == 0) {
                sum += digit;
            } else {
                sum += digit * 3;
            }
        }

        int check = (10 - (sum % 10)) % 10;
        return check == Character.getNumericValue(isbn.charAt(12));
    }

    private static boolean isValidIsbn10(String isbn) {

        int sum = 0;

        for (int i = 0; i < 9; i++) {

            if (!Character.isDigit(isbn.charAt(i))) {
                return false;
            }

            int digit = Character.getNumericValue(isbn.charAt(i));

            sum += digit * (10 - i);
        }

        char lastChar = isbn.charAt(9);

        int checkDigit;

        if (lastChar == 'X') {
            checkDigit = 10;
        } else if (Character.isDigit(lastChar)) {
            checkDigit = Character.getNumericValue(lastChar);
        } else {
            return false;
        }

        sum += checkDigit;

        return sum % 11 == 0;
    }
}
