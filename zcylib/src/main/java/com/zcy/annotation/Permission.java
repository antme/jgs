package com.zcy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

	String permissionID() default "";

	String groupName() default "";

	String permissionName() default "";
	
	String label() default "";
}
