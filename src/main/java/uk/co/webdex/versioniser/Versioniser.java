package uk.co.webdex.versioniser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Julien Frisquet
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Versioniser {

    /**
     * Defines which headers are being intercepted for this method
     */
    String[] value() default "";

    /**
     * The header to look for, for headers based versioning.
     */
    String header() default "X-Version";
}
