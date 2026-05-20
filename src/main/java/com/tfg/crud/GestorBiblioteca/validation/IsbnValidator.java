/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.validation;

public class IsbnValidator {

    public static boolean isValid(String isbn) {

        if (isbn == null) return false;

        isbn = isbn.replaceAll("-", "").replaceAll(" ", "");

        if (!isbn.matches("\\d{13}")) return false;

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
}