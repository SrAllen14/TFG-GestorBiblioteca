/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tfg.crud.GestorBiblioteca.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 *
 * @author Usuario
 */
class DNIValidator implements ConstraintValidator<DNI, String>{

    private static final String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";
    
    @Override
    public boolean isValid(String dni, ConstraintValidatorContext cvc) {
        if (dni == null || dni.isBlank()) {
            return false;
        }

        dni = dni.toUpperCase().replaceAll("\\s", "");

        if (!dni.matches("\\d{8}[A-Z]")) {
            return false;
        }

        String numeros = dni.substring(0, 8);
        char letra = dni.charAt(8);

        int num = Integer.parseInt(numeros);
        char letraCorrecta = LETRAS.charAt(num % 23);

        return letra == letraCorrecta;
    }
    
}
