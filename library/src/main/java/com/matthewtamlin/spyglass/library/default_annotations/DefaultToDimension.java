package com.matthewtamlin.spyglass.library.default_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.units.DimensionUnit;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToDimensionAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.Default;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a default value to the Spyglass framework. This annotation should only be applied to
 * methods which accept integers and have handler annotations, and it should not be used in
 * conjunction with other defaults. The supplied value is converted to units of pixels before
 * being passed to the method.
 */
@Tested(testMethod = "automated")
@Default(adapterClass = DefaultToDimensionAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DefaultToDimension {
	/**
	 * @return the default value
	 */
	float value();

	/**
	 * @return the units of {@link #value()}
	 */
	DimensionUnit unit();
}