package ru.hzerr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a class or method as an element to be added in the future to the FXEngine.
 * <p>
 * This means that some of the code will be moved to the FXEngine.
 *
 * @author HZERR
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface FutureEngineFeature {
}