package com.matthewtamlin.spyglass.common.annotations.value_handler_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a method capable of handling a fraction attribute. If the Spyglass framework finds a fraction value mapped
 * to the attribute ID, it will invoke the method and pass in the value after applying the fraction multiplication rule.
 * <p>
 * Android defines two types of fraction resources: base fractions and parent fractions. If the fraction is defined
 * in resources as a base type, then it will be multiplied by the {@code baseMultiplier} before being passed in. If
 * the fraction is defined in resources as a parent type, then it will be multiplied by the {@code parentMultiplier}
 * before being passed in. By default, both multipliers are set to 1.
 * <p>
 * This annotation should only be applied to methods which satisfy all of the following
 * criteria:
 * <ul>
 * <li>The method has no other handler annotations.</li>
 * <li>The method has at least one {@code Number} parameter.</li>
 * <li>Except for one {@code Number} parameter, every parameter has a use annotation.</li>
 * </ul>
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface FractionHandler {
	/**
	 * @return the resource ID of the handled attribute
	 */
	int attributeId();

	/**
	 * @return the value to multiply base fractions by, defaults to 1
	 */
	int baseMultiplier() default 1;

	/**
	 * @return the value to multiply parent fractions by, defaults to 1
	 */
	int parentMultiplier() default 1;
}