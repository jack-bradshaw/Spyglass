package com.matthewtamlin.spyglass.annotations.use_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.annotations.meta_annotations.Use;
import com.matthewtamlin.spyglass.annotations.supplier.Supplier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the value to supply to the annotated parameter when the Spyglass framework invokes the
 * method. The value is obtained by reflectively instantiating the specified supplier and calling
 * its {@link Supplier#get()} method. This annotation should only be applied to parameters which
 * satisfy all of the following criteria:
 * <ul>
 * <li>The parameter is part of a method which has a handler annotation.</li>
 * <li>The parameter has no other use annotations.</li>
 * <li>The parameter accepts a value of the type returned by the specified supplier.</li>
 * </ul>
 */
@Tested(testMethod = "automated")
@Use
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface UseSuppliedValue {
	/**
	 * Defines the class of supplier to instantiate and get the value from. The class must expose
	 * a public no-arg constructor, and the class must be static if nested in another class.
	 *
	 * @return the supplier class
	 */
	Class<? extends Supplier<?>> value();
}