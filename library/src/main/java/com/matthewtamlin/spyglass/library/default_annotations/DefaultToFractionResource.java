package com.matthewtamlin.spyglass.library.default_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToFractionResourceAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.Default;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a default for the annotated method, so that the Spyglass framework can invoke the
 * method if its handler annotation is not satisfied. Android defines two types of fraction
 * resources: base fractions and parent fractions. The difference is not important unless
 * the {@code baseMultiplier} or {@code parentMultiplier} elements of the annotation are set. If
 * the fraction is defined in resources as a base type, then it will be multiplied by the {@code
 * baseMultiplier} and the {@code parentMultiplier} will be ignored. If the fraction is defined
 * in resources as a parent type, then the opposite case occurs. By default, both multipliers are
 * set to 1 so are effectively ignored.
 * <p>
 * This annotation should only be applied to methods which satisfy all of the following criteria:
 * <ul>
 * <li>The method has a handler annotation.</li>
 * <li>The method has no other default annotations.</li>
 * <li>The method has at least one float parameter.</li>
 * <li>Except for one float parameter, every parameter has a use annotation.</li>
 * </ul>
 */
@Tested(testMethod = "automated")
@Default(adapterClass = DefaultToFractionResourceAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DefaultToFractionResource {
	/**
	 * @return the resource ID of the default value, must resolve to a fraction resource
	 */
	int resId();

	/**
	 * @return the value to multiply base fractions by, defaults to 1
	 */
	int baseMultiplier() default 1;

	/**
	 * @return the value to multiply parent fractions by, defaults to 1
	 */
	int parentMultiplier() default 1;
}