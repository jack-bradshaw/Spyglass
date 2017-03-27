package com.matthewtamlin.spyglass.library.default_annotations;

import com.matthewtamlin.spyglass.library.default_processors.DefaultToIntegerProcessor;
import com.matthewtamlin.spyglass.library.meta_annotations.Default;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Default(adapterClass = DefaultToIntegerProcessor.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface DefaultToInteger {
	int value();
}