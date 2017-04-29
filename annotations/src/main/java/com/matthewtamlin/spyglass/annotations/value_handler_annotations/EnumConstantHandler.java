package com.matthewtamlin.spyglass.annotations.value_handler_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.annotations.meta_annotations.ValueHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a method capable of handling an enum attribute in the form of an enum constant. If the
 * Spyglass framework finds an integer value mapped to the specified attribute, it will use the
 * the ordinal and the class passed to {@code enumClass()} to get a reference to a specific enum
 * constant. The framework will invoke the method and in doing so, pass in the constant. This
 * annotation should only be applied to methods which satisfy all of the following criteria:
 * <ul>
 * <li>The method has no other handler annotations.</li>
 * <li>The method has at least one parameter of the specified enum type.</li>
 * <li>Every parameter has a use annotation except for one parameter of the specified enum type.
 * </li>
 * </ul>
 */
@Tested(testMethod = "automated")
@ValueHandler
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface EnumConstantHandler {
	/**
	 * @return the resource ID of the handled attribute
	 */
	int attributeId();

	/**
	 * @return the type of enum to for the value
	 */
	Class<? extends Enum> enumClass();
}