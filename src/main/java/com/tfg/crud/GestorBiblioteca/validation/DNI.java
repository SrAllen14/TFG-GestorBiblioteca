package com.tfg.crud.GestorBiblioteca.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación de validación personalizada para comprobar 
 * que un DNI español tiene un formato válido y una letra
 * de control correcta.
 * 
 * Esta anotación puede aplicarse sobre atributos de una
 * entidad o DTO para que la validación se realice 
 * automáticamente mediante la clase {@link DNIValidator}
 *
 * @author Álvaro Allén alvaro.allper.1@educa.jcyl.es
 */

@Documented
@Constraint(validatedBy = DNIValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DNI {
    
    /**
     * Mensaje mostrado cuando el DNI no supera la validación.
     * 
     * @return mensaje de error
     */
    String message() default "DNI inválido";

    /**
     * Permite agrupar validaciones.
     * 
     * @return grupos de validación
     */
    Class<?>[] groups() default {};

    /**
     * Permite asociar información adicional a la validación
     * 
     * @return información adicional de validación
     */
    Class<? extends Payload>[] payload() default {};
}
