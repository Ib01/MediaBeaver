package com.ibus.mediabeaver.server.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;


//@Target( { ElementType.METHOD, ElementType.FIELD })
@Documented
@Constraint(validatedBy = PathExistsValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PathExists 
{   
    String message() default "{PathExists}";    
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    String rootPathField();
    String pathField();
    
}


