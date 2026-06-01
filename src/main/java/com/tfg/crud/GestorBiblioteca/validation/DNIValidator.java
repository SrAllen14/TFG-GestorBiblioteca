package com.tfg.crud.GestorBiblioteca.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador encargado de comprobar que un DNI español tiene
 * un formato correcto y que la letra de control coincide con
 * el número introducido.
 *
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */
class DNIValidator implements ConstraintValidator<DNI, String>{

    private static final String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";
    
    /**
     * Comprueba si el DNI recibido es válido.
     * @param dni DNI a validar
     * @param cvc Contexto de validación
     * @return true si el DNI es válido, false en caso contrario
     */
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
