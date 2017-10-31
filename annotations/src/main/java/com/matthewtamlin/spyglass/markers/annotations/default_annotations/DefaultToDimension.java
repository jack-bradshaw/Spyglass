package com.matthewtamlin.spyglass.markers.annotations.default_annotations;

import com.matthewtamlin.spyglass.markers.units.DimensionUnit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a default for the annotated method, so that the Spyglass framework can invoke the method if its handler
 * annotation is not satisfied. Using the supplied unit, the dimension is converted to units of pixels before
 * being passed to the method. This annotation should only be applied to methods which satisfy all of the following
 * criteria:
 * <ul>
 * <li>The method is a non-static member of an Android View subclass.</li>
 * <li>The method has a handler annotation.</li>
 * <li>The method has no other default annotations.</li>
 * <li>The method has at least one {@code Number} parameter.</li>
 * <li>Except for one {@code Number} parameter, every parameter belonging to the method has a use annotation.</li>
 * </ul>
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface DefaultToDimension {
	/**
	 * @return the numerical value of the default dimension, measured in the units passed to {@code unit()}
	 */
	float value();

	/**
	 * @return the units of the value passed to {@code value()}
	 */
	DimensionUnit unit();
}