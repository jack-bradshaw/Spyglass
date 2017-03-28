package com.matthewtamlin.spyglass.library.handler_annotations;


import com.matthewtamlin.spyglass.library.handler_adapters.BooleanHandlerAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.Handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Handler(adapterClass = BooleanHandlerAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface BooleanHandler {
	int attributeId();

	boolean mandatory() default false;
}