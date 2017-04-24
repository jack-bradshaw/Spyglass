package com.matthewtamlin.spyglass.library.value_handler_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.meta_annotations.ValueHandler;
import com.matthewtamlin.spyglass.library.value_handler_adapters.EnumOrdinalHandlerAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a method capable of handling an enum attribute in the form of an integer ordinal. If
 * the Spyglass framework finds an integer value mapped to the specified attribute, it will invoke
 * the method an pass in the value. This annotation should only be applied to methods which
 * satisfy all of the following criteria:
 * <ul>
 * <li>The method has no other handler annotations.</li>
 * <li>The method has at least one integer parameter.</li>
 * <li>Except for one integer parameter, every parameter has a use annotation.</li>
 * </ul>
 */
@Tested(testMethod = "automated")
@ValueHandler(adapterClass = EnumOrdinalHandlerAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EnumOrdinalHandler {
	/**
	 * @return the resource ID of the handled attribute
	 */
	int attributeId();
}