package com.matthewtamlin.spyglass.library.use_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.meta_annotations.Use;
import com.matthewtamlin.spyglass.library.use_adapters.UseDoubleAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tested(testMethod = "automated")
@Use(adapterClass = UseDoubleAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface UseDouble {
	double value();
}