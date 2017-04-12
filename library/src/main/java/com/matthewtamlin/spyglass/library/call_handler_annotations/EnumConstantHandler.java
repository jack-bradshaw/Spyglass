package com.matthewtamlin.spyglass.library.call_handler_annotations;

import com.matthewtamlin.spyglass.library.call_handler_adapters.EnumConstantHandlerAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.CallHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@CallHandler(adapterClass = EnumConstantHandlerAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EnumConstantHandler {
	int attributeId();

	Class<? extends Enum> enumClass();

	int ordinal();

	boolean mandatory() default false;
}