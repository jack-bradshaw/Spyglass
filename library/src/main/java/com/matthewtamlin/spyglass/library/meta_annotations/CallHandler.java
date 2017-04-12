package com.matthewtamlin.spyglass.library.meta_annotations;

import com.matthewtamlin.spyglass.library.call_handler_adapters.CallHandlerAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface CallHandler {
	Class<? extends CallHandlerAdapter> adapterClass();
}