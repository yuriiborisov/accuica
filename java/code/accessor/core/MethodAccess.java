package code.accessor.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodAccess {
	String entity();
//	DefaultPrivilege[] privileges() default {};
	String description() default "";
	String humanName() default "";
	boolean filter() default false;
	String key();
	boolean isSystem() default false;
}
