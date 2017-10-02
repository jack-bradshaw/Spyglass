package com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a method capable of handling an enum attribute in the form of an enum constant object. If the Spyglass
 * framework finds an integer value mapped to the attribute ID, it will use the supplied {@code enumClass()} to get a
 * reference to the enum constant with the mapped value as its ordinal. The framework will then invoke the method and
 * pass in the constant. This annotation should only be applied to methods which satisfy all of the following criteria:
 * <ul>
 * <li>The method has no other handler annotations.</li>
 * <li>The method has at least one parameter of the specified enum type.</li>
 * <li>Every parameter has a use annotation except for one parameter of the specified enum type.
 * </li>
 * </ul>
 *
 * Important note: An exception will be thrown at runtime if the mapped value does not correspond to an ordinal of
 * the enum class, or the enum class is not assignable to the recipient parameter of the annotated method.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface EnumConstantHandler {
	/**
	 * @return the resource ID of the handled attribute
	 */
	int attributeId();

	/**
	 * @return the enum type to use
	 */
	Class<? extends Enum> enumClass();
}