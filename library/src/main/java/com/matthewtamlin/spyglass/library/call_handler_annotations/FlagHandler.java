package com.matthewtamlin.spyglass.library.call_handler_annotations;

import com.matthewtamlin.spyglass.library.meta_annotations.CallHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@CallHandler(adapterClass = FlagHandlerAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface FlagHandler {
	int attributeId();

	int handledFlags();
}