package com.matthewtamlin.spyglass.library.meta_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.HandlerAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ValueHandler {
	Class<? extends HandlerAdapter> adapterClass();
}