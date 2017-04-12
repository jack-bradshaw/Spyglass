package com.matthewtamlin.spyglass.library.value_handler_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.TextHandlerAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.ValueHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValueHandler(adapterClass = TextHandlerAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface TextHandler {
	int attributeId();

	boolean mandatory() default false;
}