package com.ibus.mediabeaver.server.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = EnvironmentPathValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnvironmentPath 
{     
    String message() default "{EnvironmentPath}";    
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


