package com.matthewtamlin.spyglass.markers.annotations.default_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a default for the annotated method, so that the Spyglass framework can invoke the method if its handler
 * annotation is not satisfied. This annotation should only be applied to methods which satisfy all of the following
 * criteria:
 * <ul>
 * <li>The method is a non-static member of an Android View subclass.</li>
 * <li>The method has a handler annotation.</li>
 * <li>The method has no other default annotations.</li>
 * <li>The method has at least one boolean parameter.</li>
 * <li>Except for one boolean parameter, every parameter belonging to the method has a use annotation.</li>
 * </ul>
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface DefaultToBooleanResource {
	/**
	 * @return the resource ID of the default value, must resolve to a boolean resource
	 */
	int resId();
}