package com.matthewtamlin.spyglass.common.annotations.value_handler_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a method capable of handling a fraction attribute. If the Spyglass framework finds
 * a fraction value mapped to the specified attribute, it will invoke the method and pass in the
 * value after applying the fraction multiplication rule.
 * <p>
 * Android defines two types of fraction resources: base fractions and parent fractions. The
 * difference is not important unless the {@code baseMultiplier} or {@code parentMultiplier}
 * elements of the annotation are set. If the fraction is defined in resources as a base type,
 * then it will be multiplied by the {@code baseMultiplier} and the {@code parentMultiplier} will
 * be ignored. If the fraction is defined in resources as a parent type, then the opposite case
 * occurs. By default, both multipliers are set to 1 so are effectively ignored.
 * <p>
 * This annotation should only be applied to methods which satisfy all of the following
 * criteria:
 * <ul>
 * <li>The method has no other handler annotations.</li>
 * <li>The method has at least one float parameter.</li>
 * <li>Except for one float parameter, every parameter has a use annotation.</li>
 * </ul>
 */
@Tested(testMethod = "automated")
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface FractionHandler {
	/**
	 * @return the resource ID of the handled attribute
	 */
	int attributeId();

	int baseMultiplier() default 1;

	int parentMultiplier() default 1;
}