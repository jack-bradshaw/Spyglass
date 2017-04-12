package com.matthewtamlin.spyglass.library.handler_annotations;

import com.matthewtamlin.spyglass.library.handler_adapters.DimensionHandlerAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.ValueHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValueHandler(adapterClass = DimensionHandlerAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface DimensionHandler {
	int attributeId();

	boolean mandatory() default false;
}