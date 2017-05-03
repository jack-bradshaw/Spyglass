package com.matthewtamlin.spyglass.annotations.default_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a default for the annotated method, so that the Spyglass framework can invoke the
 * method if its handler annotation is not satisfied. The default value is obtained by using
 * the values supplied to {@code enumClass()} and {@code ordinal()} to get a reference to a
 * specific enum constant. This annotation should only be applied to methods which satisfy all of
 * the following criteria:
 * <ul>
 * <li>The method has a handler annotation.</li>
 * <li>The method has no other default annotations.</li>
 * <li>The method has at least one parameter of the specified enum type.</li>
 * <li>Every parameter has a use annotation except for one parameter of the specified enum type.
 * </li>
 * </ul>
 */
@Tested(testMethod = "automated")
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface DefaultToEnumConstant {
	/**
	 * @return the type of enum to use for the default value
	 */
	Class<? extends Enum> enumClass();

	/**
	 * @return the ordinal of the specific enum constant to use as the default value
	 */
	int ordinal();
}