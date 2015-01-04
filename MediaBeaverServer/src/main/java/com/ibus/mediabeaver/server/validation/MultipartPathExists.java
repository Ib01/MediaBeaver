package com.ibus.mediabeaver.server.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/*
Example usage (apply to class): 

@MultipartPathExists.List({
@MultipartPathExists(message="This directory does not exist on the file system", pathComponents={"tvRootDirectory"}, ownerField = "tvRootDirectory"),
@MultipartPathExists(message="This directory does not exist on the file system", pathComponents={"movieRootDirectory"}, ownerField = "movieRootDirectory"),
@MultipartPathExists(message="This directory does not exist on the file system", pathComponents={"sourceDirectory"}, ownerField = "sourceDirectory") 
})*/


@Documented
@Constraint(validatedBy = MultipartPathExistsValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MultipartPathExists 
{   
    String message() default "{MultipartPathExists}";    
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    String[] pathComponents() default {};
    String ownerField();
    
    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List
    {
    	MultipartPathExists[] value();
    }
    
}


