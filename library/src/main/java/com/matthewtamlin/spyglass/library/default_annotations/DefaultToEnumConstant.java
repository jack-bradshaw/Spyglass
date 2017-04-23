package com.matthewtamlin.spyglass.library.default_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToEnumConstantAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.Default;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a default value to the Spyglass framework. The default value is obtained by getting
 * the constant from the specified enum class which matches the specified ordinal. This
 * annotation should only be applied to methods which have a handler annotation and accept an enum
 * constant of the specified type. Furthermore, it should not be used in conjunction with other
 * defaults.
 */

/**
 * Defines a default for the annotated method, so that the Spyglass framework can invoke the
 * method if its handler annotation is not satisfied. The default value is obtained by using
 * the values supplied to {@code enumClass()} and {@code ordinal()} to get a reference to a
 * specific enum constant. This annotation should only be applied to methods which satisfy all of
 * the following criteria:
 * <ul>
 * <li>The method has a handler annotation.</li>
 * <li>The method has no other default annotations.</li>
 * <li>The method has at least one parameter of the type passed to {@code enumClass()}.</li>
 * <li>One enum parameter has no use annotation.</li>
 * <li>Every other parameter has a use annotation.</li>
 * </ul>
 */
@Tested(testMethod = "automated")
@Default(adapterClass = DefaultToEnumConstantAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
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