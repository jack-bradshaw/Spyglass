package com.matthewtamlin.spyglass.library.call_handler_annotations;

import com.matthewtamlin.spyglass.library.call_handler_adapters.SpecificEnumHandlerAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.CallHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@CallHandler(adapterClass = SpecificEnumHandlerAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SpecificEnumHandler {
	int attributeId();

	Class<? extends Enum> enumClass();

	int ordinal();
}