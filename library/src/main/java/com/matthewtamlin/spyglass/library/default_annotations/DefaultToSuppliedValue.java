package com.matthewtamlin.spyglass.library.default_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToSuppliedValueAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.Default;
import com.matthewtamlin.spyglass.library.supplier.Supplier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a default to the Spyglass framework. The default value is obtained by reflectively
 * instantiating a supplier and getting a value from it. This annotation should only be applied
 * to methods which accept the type returned by the supplier and have a handler annotation.
 * Furthermore, it should not be used in conjunction with other defaults.
 */
@Tested(testMethod = "automated")
@Default(adapterClass = DefaultToSuppliedValueAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DefaultToSuppliedValue {
	/**
	 * Defines the supplier to instantiate and get the default value from. The class must expose
	 * a public no-arg constructor, and it must be static if nested in another class.
	 *
	 * @return the supplier class
	 */
	Class<? extends Supplier<?>> value();
}