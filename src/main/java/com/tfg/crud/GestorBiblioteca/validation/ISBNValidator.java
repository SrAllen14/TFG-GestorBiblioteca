package com.tfg.crud.GestorBiblioteca.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


/**
 * Validar personalizado encargado de comprobar que un ISBN
 * introducido por el usuario es válido.
 * 
 * Admite tanto ISBN-10 como ISBN-13 y verifica su dígito de
 * control según las reglas de cada formato.
 * 
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */
public class ISBNValidator implements ConstraintValidator<ISBN, String> {

    /**
     * Comprueba si el ISBN recibido cumple las reglas de validación.
     * 
     * @param isbn ISBN a validar
     * @param context Contexto de validación
     * @return true si el ISBN es válido, false en caso contrario
     */
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

    /**
     * Valida un ISBN de 10 dígitos utilizando su algoritmo
     * de comprobación correspondiente.
     * 
     * @param isbn ISBN-10 a validar
     * @return true si el ISBN-10 es válido, false en caso contrario
     */
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

    /**
     * Valida un ISBN de 13 dígitos utilizando su algoritmo
     * de comprobación correspondiente.
     * 
     * @param isbn ISBN-13 a validar
     * @return true si el ISBN-13 es válido, false en caso contrario
     */
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
