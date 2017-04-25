package com.matthewtamlin.spyglass.annotations.value_handler_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.annotations.meta_annotations.ValueHandler;
import com.matthewtamlin.spyglass.library.value_handler_adapters.TextArrayHandlerAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a method capable of handling a text array attribute. If the Spyglass framework finds
 * a text array value mapped to the specified attribute, it will invoke the method and pass in the
 * value. This annotation should only be applied to methods which satisfy all of the following
 * criteria:
 * <ul>
 * <li>The method has no other handler annotations.</li>
 * <li>The method has at least one CharSequence array parameter.</li>
 * <li>Except for one CharSequence array parameter, every parameter has a use annotation.</li>
 * </ul>
 */
@Tested(testMethod = "automated")
@ValueHandler(adapterClass = TextArrayHandlerAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TextArrayHandler {
	/**
	 * @return the resource ID of the handled attribute
	 */
	int attributeId();
}