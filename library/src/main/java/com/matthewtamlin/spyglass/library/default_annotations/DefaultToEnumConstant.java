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
@Tested(testMethod = "automated")
@Default(adapterClass = DefaultToEnumConstantAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DefaultToEnumConstant {
	/**
	 * @return the class of the default
	 */
	Class<? extends Enum> enumClass();

	/**
	 * @return the ordinal of the default
	 */
	int ordinal();
}