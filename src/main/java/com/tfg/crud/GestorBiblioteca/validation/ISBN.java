package com.tfg.crud.GestorBiblioteca.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación de validación personalizada para comprobar que un
 * ISBN tiene formato válido según los estándares ISBN-10 o 
 * ISBN-13.
 * 
 * Esta anotación puede aplicarse sobre atributos de una entidad
 * o DTO par que la validación se realice automáticamente mediante
 * la clase {@link ISBNValidator}
 *
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */

@Documented
@Constraint(validatedBy = ISBNValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ISBN {
    /**
     * Mensaje mostrado cuando el ISBN no supera la validación.
     * 
     * @return mensaje de error
     */
    String message() default "ISBN inválido";
    
    /**
     * Permite agrupar validaciones.
     * 
     * @return mensaje de error
     */
    Class<?>[] groups() default {};
    
    /**
     * Permite asociar información adicional a la validación.
     * 
     * @return información adicional de validación
     */
    Class<? extends Payload>[] payload() default {};
}
