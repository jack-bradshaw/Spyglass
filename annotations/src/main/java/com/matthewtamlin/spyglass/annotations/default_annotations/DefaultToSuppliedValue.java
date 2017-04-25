package com.matthewtamlin.spyglass.annotations.default_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.annotations.meta_annotations.Default;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToSuppliedValueAdapter;
import com.matthewtamlin.spyglass.library.supplier.Supplier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a default for the annotated method, so that the Spyglass framework can invoke the
 * method if its handler annotation is not satisfied. The default value is obtained by reflectively
 * instantiating the specified supplier and calling its {@link Supplier#get()} method. This
 * annotation should only be applied to methods which satisfy all of the following criteria:
 * <ul>
 * <li>The method has a handler annotation.</li>
 * <li>The method has no other default annotations.</li>
 * <li>The method has at least one parameter of the type supplied by the specified supplier.</li>
 * <li>Every parameter has a use annotation except for one parameter of the type supplied by the
 * specified supplier.</li>
 * </ul>
 */
@Tested(testMethod = "automated")
@Default(adapterClass = DefaultToSuppliedValueAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DefaultToSuppliedValue {
	/**
	 * Defines the class of supplier to instantiate and get the default value from. The class must
	 * expose a public no-arg constructor, and the class must be static if nested in another class.
	 *
	 * @return the supplier class
	 */
	Class<? extends Supplier<?>> value();
}